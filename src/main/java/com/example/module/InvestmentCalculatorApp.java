package com.example.module;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class InvestmentCalculatorApp extends Application {

    private InvestmentCalculator calculator;
    private TableView<ResultEntry> resultTable;
    private ObservableList<ResultEntry> resultEntries;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Інвестиційний калькулятор");

        Label initialLabel = new Label("Початкова сума:");
        TextField initialTextField = new TextField();

        Label rateLabel = new Label("Річна ставка (%):");
        TextField rateTextField = new TextField();

        Label yearsLabel = new Label("Тривалість (роки):");
        TextField yearsTextField = new TextField();

        Button calculateButton = new Button("Розрахувати");

        Label resultLabel = new Label("Майбутній прибуток:");

        Button saveButton = new Button("Зберегти результати у файл");

        // Ініціалізація таблиці та списку
        resultTable = new TableView<>();
        resultEntries = FXCollections.observableArrayList();
        resultTable.setItems(resultEntries);

        TableColumn<ResultEntry, String> inputColumn = new TableColumn<>("Початкова сума");
        TableColumn<ResultEntry, String> rateColumn = new TableColumn<>("Річна ставка (%)");
        TableColumn<ResultEntry, String> yearsColumn = new TableColumn<>("Тривалість (роки)");
        TableColumn<ResultEntry, String> resultColumn = new TableColumn<>("Майбутній прибуток");

        inputColumn.setCellValueFactory(cellData -> cellData.getValue().inputDataProperty());
        rateColumn.setCellValueFactory(cellData -> cellData.getValue().rateDataProperty());
        yearsColumn.setCellValueFactory(cellData -> cellData.getValue().yearsDataProperty());
        resultColumn.setCellValueFactory(cellData -> cellData.getValue().resultDataProperty());

        resultTable.getColumns().addAll(inputColumn, rateColumn, yearsColumn, resultColumn);

        String darkGrayColor = "-fx-background-color: #404040; -fx-text-fill: white;";

        calculateButton.setStyle(darkGrayColor);
        saveButton.setStyle(darkGrayColor);

        calculateButton.setOnAction(e -> {
            try {
                double initialAmount = Double.parseDouble(initialTextField.getText());
                double annualRate = Double.parseDouble(rateTextField.getText());
                int years = Integer.parseInt(yearsTextField.getText());

                calculator = new InvestmentCalculator(initialAmount, annualRate, years);
                List<Double> futureValues = calculator.calculateFutureValuesOverYears();
                double futureValue = futureValues.get(futureValues.size() - 1);

                resultLabel.setText("Майбутній прибуток: " + String.format("%.2f", futureValue));

                calculator.generateAndShowChart(futureValues);

                // Додавання результату до таблиці
                resultEntries.add(new ResultEntry(String.valueOf(initialAmount), String.valueOf(annualRate),
                        String.valueOf(years), String.format("%.2f", futureValue)));

            } catch (NumberFormatException ex) {
                resultLabel.setText("Будь ласка, введіть коректні значення.");
            }
        });

        saveButton.setOnAction(e -> {
            try {
                double initialAmount = Double.parseDouble(initialTextField.getText());
                double annualRate = Double.parseDouble(rateTextField.getText());
                int years = Integer.parseInt(yearsTextField.getText());

                List<Double> futureValues = calculator.calculateFutureValuesOverYears();

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Зберегти результати у файл");
                File file = fileChooser.showSaveDialog(primaryStage);

                if (file != null) {
                    calculator.saveResultsToFile(file.getAbsolutePath(), futureValues);
                    resultLabel.setText("Результати збережено у файл: " + file.getAbsolutePath());

                    // Додавання результату до таблиці
                    resultEntries.add(new ResultEntry(String.valueOf(initialAmount), String.valueOf(annualRate),
                            String.valueOf(years), "Results saved to: " + file.getAbsolutePath()));
                }

            } catch (NumberFormatException | IOException ex) {
                resultLabel.setText("Помилка при збереженні результатів у файл.");
            }
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: #006400;");
        layout.getChildren().addAll(initialLabel, initialTextField, rateLabel, rateTextField,
                yearsLabel, yearsTextField, calculateButton, resultLabel, saveButton, resultTable);

        Scene scene = new Scene(layout, 600, 600);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}