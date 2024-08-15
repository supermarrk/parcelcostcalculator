package com.pajunma.home.exam.parcelcostcalculator;

import static org.assertj.core.api.Assertions.assertThat;

import com.pajunma.home.exam.parcelcostcalculator.controller.ParcelController;
import com.pajunma.home.exam.parcelcostcalculator.models.ParcelDeliveryCharge;
import com.pajunma.home.exam.parcelcostcalculator.models.ParcelDimension;
import com.pajunma.home.exam.parcelcostcalculator.models.ParcelSize;
import com.pajunma.home.exam.parcelcostcalculator.service.Calculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ParcelcostcalculatorApplicationTests {

	@Autowired
	private ParcelController parcelController;

	@Autowired
	private Calculator parcelCostCalculator;

	@Test
	@DisplayName("Test Context Load")
	void contextLoads() {
		// Verify that the context loads and the beans are correctly initialized
		assertThat(parcelController).isNotNull();
		assertThat(parcelCostCalculator).isNotNull();
	}

	@Test
	@DisplayName("Test Rejected")
	void testRejected() {
		// Prepare test data
		ParcelDimension parcelDimension = new ParcelDimension();
		parcelDimension.setWeight(51);
		parcelDimension.setHeight(20);
		parcelDimension.setWidth(15);
		parcelDimension.setLength(25);

		String voucherCode = "DISCOUNT2024";

		// Call the method
		ParcelDeliveryCharge parcel = parcelCostCalculator.calculateParcelDeliveryCharge(parcelDimension, voucherCode);

		// Perform assertions to verify the result
		assertThat(parcel).isNotNull();
		assertThat(parcel.getParcelSize()).isEqualTo(ParcelSize.REJECTED.toString());
	}

	@Test
	@DisplayName("Test Heavy")
	void testHeavy() {
		// Prepare test data
		ParcelDimension parcelDimension = new ParcelDimension();
		parcelDimension.setWeight(49);
		parcelDimension.setHeight(20);
		parcelDimension.setWidth(15);
		parcelDimension.setLength(25);

		String voucherCode = "INVALIDCODE";

		ParcelDeliveryCharge parcel = parcelCostCalculator.calculateParcelDeliveryCharge(parcelDimension, voucherCode);

		assertThat(parcel).isNotNull();
		assertThat(parcel.getParcelSize()).isEqualTo(ParcelSize.HEAVY.toString());
	}

	@Test
	@DisplayName("Test Small")
	void testSmall() {
		// Prepare test data
		ParcelDimension parcelDimension = new ParcelDimension();
		parcelDimension.setWeight(10);
		parcelDimension.setHeight(10);
		parcelDimension.setWidth(10);
		parcelDimension.setLength(10);

		String voucherCode = "DISCOUNT2024";

		// Call the method
		ParcelDeliveryCharge parcel = parcelCostCalculator.calculateParcelDeliveryCharge(parcelDimension, voucherCode);

		// Perform assertions to verify the result
		assertThat(parcel).isNotNull();
		assertThat(parcel.getParcelSize()).isEqualTo(ParcelSize.SMALL.toString());
	}

	@Test
	@DisplayName("Test Medium")
	void testMedium() {
		// Prepare test data
		ParcelDimension parcelDimension = new ParcelDimension();
		parcelDimension.setWeight(10);
		parcelDimension.setHeight(10);
		parcelDimension.setWidth(10);
		parcelDimension.setLength(15);

		String voucherCode = "MYNT";

		// Call the method
		ParcelDeliveryCharge parcel = parcelCostCalculator.calculateParcelDeliveryCharge(parcelDimension, voucherCode);

		// Perform assertions to verify the result
		assertThat(parcel).isNotNull();
		assertThat(parcel.getParcelSize()).isEqualTo(ParcelSize.MEDIUM.toString());
	}

	@Test
	@DisplayName("Test Large")
	void testLarge() {
		// Prepare test data
		ParcelDimension parcelDimension = new ParcelDimension();
		parcelDimension.setWeight(8);
		parcelDimension.setHeight(20);
		parcelDimension.setWidth(15);
		parcelDimension.setLength(25);

		String voucherCode = "GFI";

		// Call the method
		ParcelDeliveryCharge parcel = parcelCostCalculator.calculateParcelDeliveryCharge(parcelDimension, voucherCode);

		// Perform assertions to verify the result
		assertThat(parcel).isNotNull();
		assertThat(parcel.getParcelSize()).isEqualTo(ParcelSize.LARGE.toString());
	}

}
