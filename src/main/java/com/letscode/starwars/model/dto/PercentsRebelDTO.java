package com.letscode.starwars.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PercentsRebelDTO {

    private Integer totalRebel;
    private Integer totalTraitor;
    private BigDecimal percentRebel;
}
