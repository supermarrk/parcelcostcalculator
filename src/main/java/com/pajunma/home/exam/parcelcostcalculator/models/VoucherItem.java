package com.pajunma.home.exam.parcelcostcalculator.models;

import lombok.Data;

@Data
public class VoucherItem {
    private String code;
    private Double discount = 0.0;
    private String expiry;
    private String error;
}
