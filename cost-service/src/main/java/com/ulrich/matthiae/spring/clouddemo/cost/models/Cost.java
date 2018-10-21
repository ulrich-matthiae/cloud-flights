package com.ulrich.matthiae.spring.clouddemo.cost.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cost {
    private BigDecimal price;
    private String currency;
    private Integer localServerPort;

}
