package com.example.module;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InvestmentCalculator {
    private double initialAmount;
    private double annualRate;
    private int years;

    public InvestmentCalculator(double initialAmount, double annualRate, int years) {
        this.initialAmount = initialAmount;
        this.annualRate = annualRate;
        this.years = years;
    }

    public double calculateFutureValue(int year) {
        return initialAmount * Math.pow(1 + annualRate / 100, year);
    }

    public List<Double> calculateFutureValuesOverYears() {
        List<Double> futureValues = new ArrayList<>();
        for (int i = 1; i <= years; i++) {
            futureValues.add(calculateFutureValue(i));
        }
        return futureValues;
    }

    public void saveResultsToFile(String filePath, List<Double> futureValues) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < years; i++) {
                writer.write("Year " + (i + 1) + ": " + String.format("%.2f", futureValues.get(i)) + "\n");
            }
        }
    }

    public void generateAndShowChart(List<Double> futureValues) {
        XYChart chart = new XYChartBuilder().width(800).height(600).title("Інвестиційний калькулятор").xAxisTitle("Рік").yAxisTitle("Майбутній прибуток").build();
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);

        List<Integer> yearsList = new ArrayList<>();
        for (int i = 1; i <= years; i++) {
            yearsList.add(i);
        }

        chart.addSeries("Майбутній прибуток", yearsList, futureValues);

        new SwingWrapper<>(chart).displayChart();
    }
}
