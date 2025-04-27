package org.example.xlsxnumberfinderapplication;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.xlsxnumberfinderapplication.service.NumberFinderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NumberFinderServiceTest {

    @Autowired
    MockMvc mockMvc;
    private NumberFinderService service;

    @BeforeEach
    void setUp() {
        service = new NumberFinderService();
    }

    @Test
    void findNthMinFromXlsx_ShouldReturnCorrectValue() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            var sheet = workbook.createSheet();
            sheet.createRow(0).createCell(0).setCellValue(10);
            sheet.createRow(1).createCell(0).setCellValue(5);
            sheet.createRow(2).createCell(0).setCellValue(8);
            sheet.createRow(3).createCell(0).setCellValue(3);
            workbook.write(outputStream);
        }

        MultipartFile file = new MockMultipartFile(
                "test.xlsx", "test.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                outputStream.toByteArray());

        assertEquals(5, service.findNthMinFromXlsx(file, 2));
    }

    @Test
    void readNumbersFromXlsx_ShouldHandleEmptyFile() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            workbook.createSheet();
            workbook.write(outputStream);
        }

        MultipartFile file = new MockMultipartFile(
                "empty.xlsx", "empty.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                outputStream.toByteArray());

        assertThrows(IllegalArgumentException.class, () -> service.findNthMinFromXlsx(file, 1));
    }

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
    void findNthMin_ShouldThrowWhenNIsInvalid() {
        List<Integer> numbers = List.of(1, 2, 3);

        assertThrows(IllegalArgumentException.class, () -> service.findNthMin(numbers, 0)); // n < 1
        assertThrows(IllegalArgumentException.class, () -> service.findNthMin(numbers, 4)); // n > size
    }
}