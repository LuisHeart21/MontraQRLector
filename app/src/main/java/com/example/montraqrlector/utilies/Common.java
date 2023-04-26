package com.example.montraqrlector.utilies;

import com.example.montraqrlector.models.GoogleSheetsResponse;
import com.example.montraqrlector.models.IGoogleSheets;

public class Common {
    public static String BASE_URL = "https://script.google.com/macros/s/AKfycbzBthnVWXEgfZCs9QceU9kY6Z7AByV7-IFHOR5hcDFIwpcHKX2BvxHSMg0kCZpdZ4R3/";
    public static String GOOGLE_SHEET_ID = "1Bvp1q5ayBYwdoTTpdjPzu84HVRp3z64cEKOKzW9l6ak";
    public static String SHEET_NAME = "personas";

    public static IGoogleSheets iGSGetMethodClient(String baseUrl) {
        return GoogleSheetsResponse.getClientGetMethod(baseUrl)
                .create(IGoogleSheets.class);
    }
}