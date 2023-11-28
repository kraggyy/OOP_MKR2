package com.example.module;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ResultEntry {

    private final StringProperty inputData;
    private final StringProperty rateData;
    private final StringProperty yearsData;
    private final StringProperty resultData;

    public ResultEntry(String inputData, String rateData, String yearsData, String resultData) {
        this.inputData = new SimpleStringProperty(inputData);
        this.rateData = new SimpleStringProperty(rateData);
        this.yearsData = new SimpleStringProperty(yearsData);
        this.resultData = new SimpleStringProperty(resultData);
    }

    public String getInputData() {
        return inputData.get();
    }

    public String getRateData() {
        return rateData.get();
    }

    public String getYearsData() {
        return yearsData.get();
    }

    public String getResultData() {
        return resultData.get();
    }

    public StringProperty inputDataProperty() {
        return inputData;
    }

    public StringProperty rateDataProperty() {
        return rateData;
    }

    public StringProperty yearsDataProperty() {
        return yearsData;
    }

    public StringProperty resultDataProperty() {
        return resultData;
    }
}

