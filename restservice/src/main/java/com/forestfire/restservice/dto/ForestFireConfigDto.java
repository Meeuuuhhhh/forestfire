package com.forestfire.restservice.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ForestFireConfigDto {
    
    int width;
    int length;
    int propagationPercentage;
    
}
