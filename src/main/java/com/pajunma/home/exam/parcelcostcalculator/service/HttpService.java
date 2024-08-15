package com.pajunma.home.exam.parcelcostcalculator.service;

import com.pajunma.home.exam.parcelcostcalculator.models.VoucherItem;

public interface HttpService {
    VoucherItem callGET(String uri);
}
