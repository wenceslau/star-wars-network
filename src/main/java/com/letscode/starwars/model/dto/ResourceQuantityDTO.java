package com.letscode.starwars.model.dto;

import com.letscode.starwars.model.Enuns.ResourceType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResourceQuantityDTO {

    private ResourceType resourceType;
    private Integer quantity;

}
