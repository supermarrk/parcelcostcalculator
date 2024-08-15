package com.pajunma.home.exam.parcelcostcalculator.service.impl;

import com.pajunma.home.exam.parcelcostcalculator.models.VoucherItem;
import com.pajunma.home.exam.parcelcostcalculator.service.HttpService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@Slf4j
@Service
public class HttpServiceImpl implements HttpService {

    @Override
    public VoucherItem callGET(String uri) {
        HttpRequest request = null;
        HttpResponse<String> response = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .timeout(Duration.of(10, SECONDS))
                    .GET()
                    .build();

            response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            log.info("Voucher API is down.");
        }

        if (response != null && response.body() != null) {
            return new Gson().fromJson(response.body(), VoucherItem.class);
        }
        return new VoucherItem();
    }
}
