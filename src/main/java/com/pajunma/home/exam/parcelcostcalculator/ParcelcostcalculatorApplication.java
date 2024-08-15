package com.pajunma.home.exam.parcelcostcalculator;

import com.pajunma.home.exam.parcelcostcalculator.configs.GcashParcelConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(GcashParcelConfig.class)
public class ParcelcostcalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParcelcostcalculatorApplication.class, args);
	}

}
