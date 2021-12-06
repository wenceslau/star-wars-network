package com.letscode.starwars.model.dto;

import com.letscode.starwars.model.Enuns;
import com.letscode.starwars.model.Enuns.ResourceType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResourceDTO {

    private Long codeRebel;
    private ResourceType resourceType;
    private Integer credit;
    private Integer quantity;

}
