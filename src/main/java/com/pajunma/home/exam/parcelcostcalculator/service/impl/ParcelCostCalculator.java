package com.pajunma.home.exam.parcelcostcalculator.service.impl;

import com.pajunma.home.exam.parcelcostcalculator.configs.GcashParcelConfig;
import com.pajunma.home.exam.parcelcostcalculator.models.ParcelDeliveryCharge;
import com.pajunma.home.exam.parcelcostcalculator.models.ParcelDimension;
import com.pajunma.home.exam.parcelcostcalculator.models.ParcelSize;
import com.pajunma.home.exam.parcelcostcalculator.models.VoucherItem;
import com.pajunma.home.exam.parcelcostcalculator.service.Calculator;
import com.pajunma.home.exam.parcelcostcalculator.service.HttpService;
import com.pajunma.home.exam.parcelcostcalculator.service.WeightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Slf4j
@Service
public class ParcelCostCalculator implements Calculator, WeightService {

    private final GcashParcelConfig gcashParcelConfig;
    private final HttpService httpService;

    public ParcelCostCalculator(GcashParcelConfig gcashParcelConfig, HttpService httpService) {
        this.gcashParcelConfig = gcashParcelConfig;
        this.httpService = httpService;
    }

    @Override
    public ParcelDeliveryCharge calculateParcelDeliveryCharge(ParcelDimension parcelDimension, String voucherCode) {
        double volume = parcelDimension.getHeight() * parcelDimension.getWidth() * parcelDimension.getLength();
        String deliveryCharge;

        // Check if Parcel is too heavy
        ParcelDeliveryCharge rejectedParcel = rejectParcel(parcelDimension, gcashParcelConfig.getReject(), ParcelSize.REJECTED.toString());
        if (rejectedParcel != null) {
            return rejectedParcel;
        }

        deliveryCharge = calculateChargeForHeavyItems(parcelDimension, gcashParcelConfig.getHeavy(), "Parcel is heavy.");
        if (deliveryCharge != null) {
            return checkForDiscount(voucherCode, deliveryCharge, ParcelSize.HEAVY.toString());
        }

        deliveryCharge = calculateCharge(volume, gcashParcelConfig.getSmall(), "Parcel is small.");
        if (deliveryCharge != null) {
            return checkForDiscount(voucherCode, deliveryCharge, ParcelSize.SMALL.toString());
        }

        deliveryCharge = calculateCharge(volume, gcashParcelConfig.getMedium(), "Parcel is medium.");
        if (deliveryCharge != null) {
            return checkForDiscount(voucherCode, deliveryCharge, ParcelSize.MEDIUM.toString());
        }

        log.info("Parcel is large.");
        deliveryCharge = String.valueOf(convertStringToDouble(gcashParcelConfig.getLarge().getMultiplier()) * volume);
        return checkForDiscount(voucherCode, deliveryCharge, ParcelSize.LARGE.toString());
    }

    @Override
    public ParcelDeliveryCharge checkForDiscount(String voucherCode, String deliveryCharge, String parcelSize) {
        if (StringUtils.hasLength(voucherCode)) {
            // Call MYNT's voucher API
            VoucherItem voucherItem = httpService.callGET(gcashParcelConfig.getUrl() + String.format(gcashParcelConfig.getVoucherApi(), voucherCode));
            if (StringUtils.hasLength(voucherItem.getExpiry())) {
                LocalDate voucherValidity = LocalDate.parse(voucherItem.getExpiry());
                LocalDate now = LocalDate.now();

                // Check if voucher has not yet expired
                if (now.isBefore(voucherValidity) || now.isEqual(voucherValidity)) {
                    log.info("Voucher is still valid.");
                    if (voucherItem.getDiscount() != null) {
                        return ParcelDeliveryCharge.builder()
                                .deliveryCost("PHP " + (Double.parseDouble(deliveryCharge) - voucherItem.getDiscount()))
                                .discount(voucherItem.getDiscount())
                                .isDiscounted(true)
                                .parcelSize(parcelSize)
                                .build();
                    }
                } else {
                    log.info("Voucher has expired.");
                }
            }
        }
        return ParcelDeliveryCharge.builder()
                .deliveryCost("PHP " + deliveryCharge)
                .discount(0.0)
                .isDiscounted(false)
                .parcelSize(parcelSize)
                .build();
    }

    private double convertStringToDouble(String stringValue) {
        return Double.parseDouble(stringValue);
    }

    private String calculateChargeForHeavyItems(ParcelDimension parcelDimension, GcashParcelConfig.Rule rule, String logMessage) {
        if (rule != null && convertStringToDouble(rule.getLimit()) < parcelDimension.getWeight()) {
            log.info(logMessage);
            return String.valueOf(Double.parseDouble(rule.getMultiplier()) * parcelDimension.getWeight());
        }
        return null;
    }

    private String calculateCharge(double volume, GcashParcelConfig.Rule rule, String logMessage) {
        if (rule != null && volume < convertStringToDouble(rule.getLimit())) {
            log.info(logMessage);
            return String.valueOf(convertStringToDouble(rule.getMultiplier()) * volume);
        }
        return null;
    }

    @Override
    public ParcelDeliveryCharge rejectParcel(ParcelDimension parcelDimension, GcashParcelConfig.Rule rule, String logMessage) {
        if (rule != null && convertStringToDouble(rule.getLimit()) < parcelDimension.getWeight()) {
            log.info(logMessage);
            rule.getMultiplier();
            return ParcelDeliveryCharge.builder()
                    .deliveryCost(rule.getMultiplier())
                    .parcelSize(ParcelSize.REJECTED.toString())
                    .build();
        }
        return null;
    }
}
