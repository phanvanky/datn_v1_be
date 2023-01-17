package com.ws.masterserver.dto.admin.product_option.excel;

import com.ws.masterserver.dto.customer.product.ColorResponse;
import com.ws.masterserver.dto.customer.size.response.SizeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelOptionResponse {
    private String id;
    private String colorId;
    private String colorName;
    private String sizeId;
    private String sizeName;
    private Long price;
    private Integer qty;
    private String image;
}
