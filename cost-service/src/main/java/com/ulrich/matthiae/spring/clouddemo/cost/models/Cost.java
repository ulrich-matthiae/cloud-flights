package com.ulrich.matthiae.spring.clouddemo.cost.models;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class Cost {
    private BigDecimal price;
    private Integer localServerPort;

}
