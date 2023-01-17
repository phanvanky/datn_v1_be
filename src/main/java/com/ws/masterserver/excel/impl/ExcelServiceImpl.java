package com.ws.masterserver.excel.impl;

import com.ws.masterserver.dto.admin.product_option.excel.ExcelOptionData;
import com.ws.masterserver.dto.admin.product_option.excel.ExcelOptionResponse;
import com.ws.masterserver.dto.customer.product.ColorResponse;
import com.ws.masterserver.dto.customer.size.response.SizeResponse;
import com.ws.masterserver.excel.ExcelService;
import com.ws.masterserver.repository.ProductRepository;
import com.ws.masterserver.service.ProductOptionService;
import com.ws.masterserver.utils.base.rest.ResData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    public static final int COLUMN_INDEX_COLOR = 0;
    public static final int COLUMN_INDEX_SIZE = 1;
    public static final int COLUMN_INDEX_PRICE = 2;
    public static final int COLUMN_INDEX_QUANTITY = 3;

    @Autowired
    ProductOptionService productOptionService;

    @Override
    public  List<ExcelOptionResponse> importProductToFileExcel(MultipartFile file) throws IOException {

        List<ExcelOptionResponse> listData = new ArrayList<>();
        // Get file
        InputStream inputStream =  new BufferedInputStream(file.getInputStream());

        // Get workbook
        String fileName = file.getOriginalFilename();
       String excelPath = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        Workbook workbook = getWorkbook(inputStream, excelPath);
        // Get sheet
        Sheet sheet = workbook.getSheetAt(0);

        // Get all rows
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() == 0) {
                // Ignore header
                continue;
            }

            // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            // Read cells and set value for book object
            ExcelOptionData dto = new ExcelOptionData();
            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }
                // Set value for book object
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case COLUMN_INDEX_COLOR:
                        dto.setColor((String) getCellValue(cell));
                        break;
                    case COLUMN_INDEX_SIZE:
                        dto.setSize(new BigDecimal((double) cellValue).intValue());
                        break;
                    case COLUMN_INDEX_QUANTITY:
                        dto.setQuantity(new BigDecimal((double) cellValue).intValue());
                        break;
                    case COLUMN_INDEX_PRICE:
                        dto.setPrice(new BigDecimal((double) cellValue).longValue());
                        break;
                    default:
                        break;
                }

            }
            ExcelOptionResponse res = new ExcelOptionResponse();
            res.setId(dto.getId());
            ResData<SizeResponse> sizeRes = productOptionService.findSizeByName(String.valueOf(dto.getSize()));
            res.setSizeId(sizeRes.getData().getSizeId());
            res.setSizeName(sizeRes.getData().getSizeName());
            ResData<ColorResponse> colorRes = productOptionService.findCoLorByHex(dto.getColor());
            res.setColorId(colorRes.getData().getColorId());
            res.setColorName(colorRes.getData().getColorName());
            res.setPrice(dto.getPrice());
            res.setQty(dto.getQuantity());
            listData.add(res);
        }
        workbook.close();
        inputStream.close();
        return listData;
    }

    // Get Workbook
    private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    // Get cell value
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }
        return cellValue;
    }
}
