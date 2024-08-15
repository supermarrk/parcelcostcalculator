package com.pajunma.home.exam.parcelcostcalculator.service;

import com.pajunma.home.exam.parcelcostcalculator.models.ParcelDeliveryCharge;
import com.pajunma.home.exam.parcelcostcalculator.models.ParcelDimension;

public interface Calculator {
    ParcelDeliveryCharge calculateParcelDeliveryCharge(ParcelDimension parcelDimension, String voucherCode);

    ParcelDeliveryCharge checkForDiscount(String voucherCode, String deliveryCharge, String parcelSize);

}
