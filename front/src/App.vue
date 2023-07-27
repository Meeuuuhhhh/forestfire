<template>
  <div id="app">
    Longueur : <input v-model="width"><br/>
    Hauteur : <input v-model="length"><br/>
    Pourcentage : <input v-model="propagationPercentage"><br/>
    <button v-on:click="init()"> Initialiser une nouvelle carte </button>
    <hr/>
    <div id="table-container">
      <table border="1">
        <tbody>
          <tr v-bind:key="idx1" v-for="(row, idx1) in grid">
            <td :style="'width: 20px; height: 20px;background-color:'+grid[idx1][idx2]" v-bind:key="idx2" class="table-success" v-for="(col, idx2) in row">                     
              &nbsp;
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    Etape {{step}} <br/>
    <button v-if="grid != null && isIn2D('RED', grid)" v-on:click="nextStep()">Etape suivante</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'App',
  components: {
  },
  data (){
    return {
      step: 0,
      width: 10,
      length: 10,
      propagationPercentage: 50,
      grid: null
    }
  },
  methods: {
    //Call init function via API CALL
    init: function(){
      axios
        .get('http://127.0.0.1:8080/forestfire/init?width='+this.width+'&length='+this.length+'&propagationPercentage='+this.propagationPercentage)
        .then((response) => {
          this.grid = response.data;
          this.step = 0;
        })
        .catch(error => console.log(error));
    },
    //Call next step function via API CALL
    nextStep: function(){
      axios
        .post('http://127.0.0.1:8080/forestfire/next?width='+this.width+'&length='+this.length+'&propagationPercentage='+this.propagationPercentage, this.grid)
        .then((response) => { 
          console.log(this.grid = response.data);
          this.step++;
        }) //
        .catch(error => console.log(error));
    },
    //Search if value is in 2d array
    isIn2D: function(search,array){
      for (var i = 0; i <array.length; i++) {
        for (var j = 0; j < array[i].length; j++) {
            if(array[i][j] == search){
              return true
            }
        }
      }
      return false
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

#table-container{
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
