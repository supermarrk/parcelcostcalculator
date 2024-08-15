package com.pajunma.home.exam.parcelcostcalculator.service;

import com.pajunma.home.exam.parcelcostcalculator.configs.GcashParcelConfig;
import com.pajunma.home.exam.parcelcostcalculator.models.ParcelDeliveryCharge;
import com.pajunma.home.exam.parcelcostcalculator.models.ParcelDimension;

public interface WeightService {
    ParcelDeliveryCharge rejectParcel(ParcelDimension parcelDimension, GcashParcelConfig.Rule rule, String logMessage);
}
