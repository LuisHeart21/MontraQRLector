package com.example.montraqrlector.utilies;

import com.example.montraqrlector.models.GoogleSheetsResponse;
import com.example.montraqrlector.models.IGoogleSheets;

public class Common {
    public static String BASE_URL = "https://script.google.com/macros/s/AKfycbygLMqHzzl3lSU8lHvNYsviOdMOcYCLgcYL2GYXBGS3W8YCv5LqWp99i4S7uCWSE32wIg/";
    public static String GOOGLE_SHEET_ID = "12XSV8hxrTdtOW_mSGnNa_Sa7tgy22QFJmwLsuhw3dEU";
    public static String SHEET_NAME = "personas";

    public static IGoogleSheets iGSGetMethodClient(String baseUrl) {
        return GoogleSheetsResponse.getClientGetMethod(baseUrl)
                .create(IGoogleSheets.class);
    }
}