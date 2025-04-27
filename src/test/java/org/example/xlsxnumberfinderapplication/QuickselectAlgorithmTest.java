package org.example.xlsxnumberfinderapplication;

import org.example.xlsxnumberfinderapplication.service.NumberFinderService;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuickselectAlgorithmTest {

    private final NumberFinderService service = new NumberFinderService();

    @Test
    void testQuickselectWithVariousInputs() {
        List<Integer> unique = List.of(9, 3, 7, 1, 5);
        assertEquals(1, service.findNthMin(unique, 1));
        assertEquals(3, service.findNthMin(unique, 2));
        assertEquals(5, service.findNthMin(unique, 3));
        assertEquals(7, service.findNthMin(unique, 4));
        assertEquals(9, service.findNthMin(unique, 5));

        List<Integer> duplicates = List.of(3, 1, 4, 1, 5, 9, 2, 6, 5);
        assertEquals(1, service.findNthMin(duplicates, 1));
        assertEquals(1, service.findNthMin(duplicates, 2));
        assertEquals(2, service.findNthMin(duplicates, 3));
        assertEquals(3, service.findNthMin(duplicates, 4));
    }

    @Test
    void testQuickselectWithLargeInput() {
        List<Integer> largeList = new java.util.Random().ints(1000, 0, 10000)
                .boxed()
                .toList();

        int thirdMin = service.findNthMin(largeList, 3);
        int tenthMin = service.findNthMin(largeList, 10);
        int hundredthMin = service.findNthMin(largeList, 100);

        assertTrue(thirdMin <= service.findNthMin(largeList, 4));
        assertTrue(tenthMin <= service.findNthMin(largeList, 11));
        assertTrue(hundredthMin <= service.findNthMin(largeList, 101));
    }

    @Test
    void quickSelect_ShouldHandleAllCases() {
        NumberFinderService service = new NumberFinderService();
        int[] testArray = {10, 5, 8, 3, 7};

        assertEquals(3, service.quickSelect(testArray.clone(), 0, 4, 0)); // 1-е минимальное
        assertEquals(5, service.quickSelect(testArray.clone(), 0, 4, 1)); // 2-е
        assertEquals(7, service.quickSelect(testArray.clone(), 0, 4, 2)); // 3-е
        assertEquals(8, service.quickSelect(testArray.clone(), 0, 4, 3)); // 4-е
        assertEquals(10, service.quickSelect(testArray.clone(), 0, 4, 4)); // 5-е
    }
}