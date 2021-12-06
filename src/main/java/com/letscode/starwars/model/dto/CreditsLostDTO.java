package com.letscode.starwars.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreditsLostDTO {

    private Integer totalTraitor;
    private Integer totalCreditsLost;
    private List<ResourceQuantityDTO> listResourceQuantityDTO;
}
