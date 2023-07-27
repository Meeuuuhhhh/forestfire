package com.forestfire.restservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.forestfire.restservice.dto.ForestFireConfigDto;
import com.forestfire.restservice.model.ForestFirePosition;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ForestFireService {

    Logger logger = LoggerFactory.getLogger(ForestFireService.class);

    public static final String STATUS_FIRE  = "RED";
    public static final String STATUS_BURNT = "GREY";
    public static final String STATUS_TREE  = "GREEN";

    /**
     * Init a new forest fire map with config file
     * @param config
     * @return
     * @throws Exception
     */
    public String[][] initForestFire(ForestFireConfigDto config) throws Exception{

        if(config.getWidth() <= 0 || config.getLength() <=0){
            throw new Exception("La taille de votre map n'est pas correcte");
        }

        //Init full grid
        String[][] fireForestNew = new String[config.getWidth()][config.getLength()];
        for(int x = 0; x < config.getWidth();x++){
            for(int y = 0; y < config.getLength();y++){
                fireForestNew[x][y] = STATUS_TREE;
            }
        }

        //Define randomly number of starting fires
        Random r = new Random();
        int nbFire = r.nextInt(5) + 1;

        //Initialize fires
        for(int i=0; i < nbFire; i++ ){
            fireForestNew[r.nextInt(config.getWidth())][r.nextInt(config.getLength())] = STATUS_FIRE;
        }

        return fireForestNew;
    }
    
    /**
     * Calculate the new step for a forest fire map
     * @param config
     * @param fireForestActual
     * @return
     * @throws Exception
     */
    public String[][] calculateNextStep(ForestFireConfigDto config, String[][] fireForestActual) throws Exception{

        if(config.getWidth() <= 0 || config.getLength() <=0 || config.getPropagationPercentage() < 0 || config.getPropagationPercentage() > 100){
            throw new Exception("Votre configuration est incorrecte");
        }

        String[][] fireForestNew = new String[config.getWidth()][config.getLength()];
        Random r = new Random();

        for(int x = 0; x < config.getWidth();x++){
            for(int y = 0; y < config.getLength();y++){

                switch(fireForestActual[x][y]){
                    //position is on fire : can spread
                    case STATUS_FIRE:

                        //spread is multidirectionnal on four directions
                        List<ForestFirePosition> possibleSpreadPositions = new ArrayList<ForestFirePosition>();
                        possibleSpreadPositions.add(new ForestFirePosition(x, y-1)); // top
                        possibleSpreadPositions.add(new ForestFirePosition(x+1, y)); // right
                        possibleSpreadPositions.add(new ForestFirePosition(x, y+1)); // bottom
                        possibleSpreadPositions.add(new ForestFirePosition(x-1, y)); // left

                        //for each spread possible position : 
                        for(ForestFirePosition spreadPosition : possibleSpreadPositions){

                            //we check new position is valid and green 
                            if(isValidPosition(spreadPosition, config)
                                && fireForestActual[spreadPosition.getX()][spreadPosition.getY()].equals(STATUS_TREE))
                            {
                                    if(config.getPropagationPercentage() >= r.nextInt(100) + 1){ //+1 otherwise result is 0 - 99
                                        //fire spreads
                                        fireForestNew[spreadPosition.getX()][spreadPosition.getY()] = STATUS_FIRE;
                                    }
                            }

                        }

                        //old position becomes burnt
                        fireForestNew[x][y] = STATUS_BURNT;

                        break;
                    //position is already burnt : position does not change in new map
                    case STATUS_BURNT:
                    //position is green
                    case STATUS_TREE:
                        if(fireForestNew[x][y] == null){
                            fireForestNew[x][y] = fireForestActual[x][y];
                        }
                        break;
                }
            }
        }

        return fireForestNew;
    }

    /**
     * Check if a x,y position is valid for a given configuration
     * @param x
     * @param y
     * @param config
     * @return
     */
    private boolean isValidPosition(ForestFirePosition ffPosition, ForestFireConfigDto config){
        if(ffPosition.getX() >= 0 
            && ffPosition.getX() < config.getWidth()
            && ffPosition.getY() >= 0
            && ffPosition.getY() < config.getLength()){
                return true;
        }else{
            return false;
        }
    }
    
}
