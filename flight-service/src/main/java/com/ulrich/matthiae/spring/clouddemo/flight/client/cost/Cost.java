package com.ulrich.matthiae.spring.clouddemo.flight.client.cost;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cost {
    private BigDecimal price;

    private String currency;

    private Integer localServerPort;

}
