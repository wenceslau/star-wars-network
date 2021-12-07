package com.letscode.starwars.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExchangeResourseDTO {
    List<ResourceQuantityDTO> resourcesOffer;
    List<ResourceQuantityDTO> resourcesRequest;
}
