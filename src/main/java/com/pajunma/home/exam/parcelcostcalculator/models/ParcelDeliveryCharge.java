package com.pajunma.home.exam.parcelcostcalculator.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParcelDeliveryCharge {
    private String deliveryCost;
    private double discount;
    private boolean isDiscounted;
    private String parcelSize;
}
