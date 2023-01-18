package com.ws.masterserver.excel.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOTest {
    private String id;
    private String color;
    private Integer size;
    private Long price;
    private Integer quantity;
}
