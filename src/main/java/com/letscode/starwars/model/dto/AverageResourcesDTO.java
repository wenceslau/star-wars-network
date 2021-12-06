package com.letscode.starwars.model.dto;

import com.letscode.starwars.model.Enuns;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AverageResourcesDTO {
    private String guns;
    private String munition;
    private String food;
    private String water;
}
