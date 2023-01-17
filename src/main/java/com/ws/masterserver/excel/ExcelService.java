package com.ws.masterserver.excel;

import com.ws.masterserver.dto.admin.product.create_update.OptionDto;
import com.ws.masterserver.dto.admin.product_option.excel.ExcelOptionResponse;
import com.ws.masterserver.excel.impl.DTOTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ExcelService {
    List<ExcelOptionResponse> importProductToFileExcel(MultipartFile file) throws IOException;
}
