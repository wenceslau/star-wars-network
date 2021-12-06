package com.letscode.starwars.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PercentTraitorsDTO {

    private Integer totalRebel;
    private Integer totalTraitor;
    private BigDecimal percentTraitor;

}
