package org.example.xlsxnumberfinderapplication.service;

import org.example.xlsxnumberfinderapplication.helper.XlsxHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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
        return XlsxHelper.readNumbersFromXlsx(inputStream);
    }

    public int findNthMin(List<Integer> numbers, int n) {
        if (n <= 0 || n > numbers.size()) {
            throw new IllegalArgumentException("N must be between 1 and " + numbers.size());
        }
        int[] arr = numbers.stream().mapToInt(i -> i).toArray();
        return quickSelect(arr, 0, arr.length - 1, n - 1); // n-1 для 0-based индекса
    }

    public int quickSelect(int[] arr, int left, int right, int k) {
        if (left <= right) {
            int pivotIndex = partition(arr, left, right);

            if (pivotIndex == k) {
                return arr[pivotIndex];
            } else if (pivotIndex < k) {
                return quickSelect(arr, pivotIndex + 1, right, k);
            } else {
                return quickSelect(arr, left, pivotIndex - 1, k);
            }
        }
        return arr[left];
    }

    private int partition(int[] arr, int left, int right) {
        int pivot = arr[right]; // Опорный элемент
        int i = left; // Индекс для элементов меньше pivot

        for (int j = left; j < right; j++) {
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, right); // Ставим pivot на правильное место
        return i;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}