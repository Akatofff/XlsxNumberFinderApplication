package org.example.xlsxnumberfinderapplication.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class NumberFinderService {

    public int findNthMinFromXlsx(MultipartFile file, int n) throws IOException {
        if (n <= 0) {
            throw new IllegalArgumentException("N must be positive");
        }

        List<Integer> numbers = readNumbersFromXlsx(file.getInputStream());

        if (numbers.size() < n) {
            throw new IllegalArgumentException("N is larger than the number of elements in the file");
        }

        return findNthMin(numbers, n);
    }

    private List<Integer> readNumbersFromXlsx(InputStream inputStream) throws IOException {
        List<Integer> numbers = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    numbers.add((int) cell.getNumericCellValue());
                }
            }
        }

        return numbers;
    }

    private int findNthMin(List<Integer> numbers, int n) {
        int[] arr = numbers.stream().mapToInt(i -> i).toArray();
        return quickSelect(arr, 0, arr.length - 1, n - 1);
    }

    private int quickSelect(int[] arr, int left, int right, int k) {
        if (left == right) {
            return arr[left];
        }

        int pivotIndex = partition(arr, left, right);

        if (k == pivotIndex) {
            return arr[k];
        } else if (k < pivotIndex) {
            return quickSelect(arr, left, pivotIndex - 1, k);
        } else {
            return quickSelect(arr, pivotIndex + 1, right, k);
        }
    }

    private int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left;

        for (int j = left; j < right; j++) {
            if (arr[j] < pivot) {
                swap(arr, i, j);
                i++;
            }
        }

        swap(arr, i, right);
        return i;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}