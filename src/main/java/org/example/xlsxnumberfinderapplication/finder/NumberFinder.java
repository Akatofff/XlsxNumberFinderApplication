package org.example.xlsxnumberfinderapplication.finder;

import java.util.List;

public class NumberFinder {
    public static int findNthMin(List<Integer> numbers, int n) {
        // Используем алгоритм Quickselect (Hoare's selection algorithm)
        // В среднем работает за O(n), в худшем случае O(n^2)

        int[] arr = numbers.stream().mapToInt(i -> i).toArray();
        return quickSelect(arr, 0, arr.length - 1, n - 1);
    }

    private static int quickSelect(int[] arr, int left, int right, int k) {
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

    private static int partition(int[] arr, int left, int right) {
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

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}