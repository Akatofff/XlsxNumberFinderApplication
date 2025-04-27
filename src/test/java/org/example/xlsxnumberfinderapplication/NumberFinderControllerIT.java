package org.example.xlsxnumberfinderapplication;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayOutputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class NumberFinderControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findNthMin_ShouldReturnCorrectValue() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();
            sheet.createRow(0).createCell(0).setCellValue(10);
            sheet.createRow(1).createCell(0).setCellValue(5);
            sheet.createRow(2).createCell(0).setCellValue(8);
            sheet.createRow(3).createCell(0).setCellValue(3);
            sheet.createRow(4).createCell(0).setCellValue(7);
            workbook.write(outputStream);
        }
        byte[] fileContent = outputStream.toByteArray();

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                fileContent
        );

        mockMvc.perform(
                        multipart("/api/find-nth-min")
                                .file(file)
                                .param("n", "2")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void findNthMin_ShouldReturnBadRequestForInvalidN() throws Exception {
        MockMultipartFile file = createTestXlsxFile();

        mockMvc.perform(multipart("/api/find-nth-min")
                        .file(file)
                        .param("n", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findNthMin_ShouldReturnBadRequestForEmptyFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "empty.xlsx", "empty.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                new byte[0]);

        mockMvc.perform(multipart("/api/find-nth-min")
                        .file(file)
                        .param("n", "1"))
                .andExpect(status().isBadRequest());
    }

    private MockMultipartFile createTestXlsxFile() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (var workbook = new XSSFWorkbook()) {
            var sheet = workbook.createSheet();
            sheet.createRow(0).createCell(0).setCellValue(10);
            sheet.createRow(1).createCell(0).setCellValue(5);
            sheet.createRow(2).createCell(0).setCellValue(8);
            workbook.write(outputStream);
        }
        return new MockMultipartFile(
                "test.xlsx", "test.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                outputStream.toByteArray());
    }
}