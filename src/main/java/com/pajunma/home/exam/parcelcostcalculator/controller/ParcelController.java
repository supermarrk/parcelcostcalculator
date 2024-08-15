package com.pajunma.home.exam.parcelcostcalculator.controller;

import com.pajunma.home.exam.parcelcostcalculator.models.ParcelDeliveryCharge;
import com.pajunma.home.exam.parcelcostcalculator.models.ParcelDimension;
import com.pajunma.home.exam.parcelcostcalculator.service.Calculator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Parcel Controller", description = "API for calculating parcel delivery charges")
public class ParcelController {

    private Calculator parcelCostCalculator;

    public ParcelController(Calculator parcelCostCalculator) {
        this.parcelCostCalculator = parcelCostCalculator;
    }

    @Operation(summary = "Calculate parcel delivery charge", description = "Calculates the delivery charge based on volume (volume = height * width * length) and weight, including optional voucher code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully calculated delivery charge",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ParcelDeliveryCharge.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/delivery-charge")
    public ParcelDeliveryCharge calculateCostBasedOnWeightAndVolume(
            @RequestBody ParcelDimension parcelDimension,
            @RequestParam(required = false) String voucherCode) {
        return parcelCostCalculator.calculateParcelDeliveryCharge(parcelDimension, voucherCode);
    }
}
