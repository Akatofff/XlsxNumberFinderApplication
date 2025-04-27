package org.example.xlsxnumberfinderapplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.xlsxnumberfinderapplication.service.NumberFinderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@Tag(name = "Number Finder", description = "Find Nth smallest number in XLSX file")
public class NumberFinderController {

    private final NumberFinderService numberFinderService;

    public NumberFinderController(NumberFinderService numberFinderService) {
        this.numberFinderService = numberFinderService;
    }

    @PostMapping(value = "/find-nth-min", consumes = "multipart/form-data")
    @Operation(summary = "Find Nth smallest number in XLSX file")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully found the number"),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Integer> findNthMinNumber(
            @Parameter(description = "XLSX file with numbers in first column", required = true,
                    schema = @Schema(type = "string", format = "binary"))
            @RequestPart("file") MultipartFile file,
            @Parameter(description = "N for Nth smallest number (1-based)", example = "1", required = true)
            @RequestParam("n") int n) {

        try {
            int result = numberFinderService.findNthMinFromXlsx(file, n);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}