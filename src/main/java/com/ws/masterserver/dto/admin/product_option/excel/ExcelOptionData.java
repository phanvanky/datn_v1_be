package com.ws.masterserver.dto.admin.product_option.excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelOptionData {
    private String id;
    private String color;
    private Integer size;
    private Long price;
    private Integer quantity;
}
