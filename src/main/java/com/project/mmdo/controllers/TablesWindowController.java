package com.project.mmdo.controllers;

import com.project.mmdo.model.Rat;
import com.project.mmdo.model.Result;
import com.project.mmdo.model.SimplexTable;
import com.project.mmdo.model.SimplexTableRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import static com.project.mmdo.model.Rat.ZERO;

public class TablesWindowController {

    @FXML
    private TableView TableView;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ScrollPane scrollPaneG;
    @FXML
    private ScrollPane insidePaneTable;
    @FXML
    private Button buttonGomori;
    private SimplexTableRow[] simplexTableRows = null;

    @FXML
    void initialize() {
        buttonGomori.setOnAction(event -> {
            scrollPaneG.setVisible(true);
        });

    }

    public void systemToWindow(SimplexTableRow[] mainTable) {
        simplexTableRows = mainTable;
        equalityToScreen();
    }

    private String equalityToString(SimplexTableRow row) {
        if (row == null || row.row == null) {
            throw new IllegalArgumentException("SimplexTableRow or its row array cannot be null");
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < row.row.length; i++) {
            Rat rat = row.row[i];
            if (rat.getDen() == 1) {
                sb.append(rat.getNum());
            } else {
                sb.append(String.format("%d/%d", rat.getNum(), rat.getDen()));
            }
            sb.append(" x").append(i);
            if (i < row.row.length - 1) {
                sb.append(" + ");
            }
        }

        sb.append(" = ");

        Rat firstElement = row.row[0];
        if (firstElement.getDen() == 1) {
            sb.append(firstElement.getNum());
        } else {
            sb.append(String.format("%d/%d", firstElement.getNum(), firstElement.getDen()));
        }

        return sb.toString();
    }

    private void equalityToScreen() {
        VBox vbox = new VBox();
        Node currentContent = scrollPane.getContent();
        if (currentContent != null) {
            vbox.getChildren().add(currentContent);
        }


        Label closingSystemLabel = new Label("Обернемо нерівності в рівності шляхом введення вільних змінних та штучних (якщо вони потрібні)");
        closingSystemLabel.setStyle("-fx-font-size: 16px;-fx-alignment: center;");
        vbox.getChildren().add(closingSystemLabel);

        for (int i = 0; i < simplexTableRows.length; i++) {
            if (i == 0) {
                Label systemLabel = new Label("{");
                systemLabel.setStyle("-fx-font-size: 16px;-fx-alignment: center;");
                vbox.getChildren().add(systemLabel);
            }

            Label equationLabel = new Label(equalityToString(simplexTableRows[i]));
            equationLabel.setStyle("-fx-font-size: 16px;-fx-alignment: center;");
            vbox.getChildren().add(equationLabel);
        }
        VBox.setMargin(vbox, new Insets(30, 50, 50, 50));
        scrollPane.setContent(vbox);
    }


    public void gomoriFirstTableToScreen(SimplexTable table) {
        createFirstTable(table, scrollPaneG);
    }

    public void allGomoriTableToScreen(SimplexTable table) {
        createAllTable(table, scrollPaneG);
    }


    private void createFirstTable(SimplexTable table, ScrollPane scrollPane) {

        VBox vbox = new VBox();
        TableView<ObservableList<String>> tableView = new TableView<>();

        Node currentContent = scrollPane.getContent();

        if (currentContent != null) {
            vbox.getChildren().add(currentContent);
        }

        vbox.getChildren().add(tableView);
        createTable(table, tableView);


        VBox.setMargin(tableView, new Insets(30, 50, 50, 50));

        scrollPane.setContent(vbox);
    }

    public void resultToScreen(Result result, boolean isOk) {
        setAResult(result, isOk, scrollPane);
    }
    public void resultGomoriToScreen(Result result, boolean isOk) {
        setAResult(result, isOk, scrollPaneG);
    }

    public void tablesToScreen(SimplexTable table) {
        createAllTable(table, scrollPane);
    }

    private void setAResult(Result result, boolean isOk, ScrollPane scrollPane) {
        String text = "";
        VBox vbox = new VBox();

        if (result == null) {
            text = "Розв'язок відсутній";
        } else if (isOk) {
            text = "Розв'язок цілочисельний";
        } else {
            text = "Розв'язок не цілочисельний";
            buttonGomori.setVisible(true);
        }

        Label resText = new Label(text);
        resText.setStyle("-fx-font-size: 16px; -fx-alignment: center;");

        Label resLabel1 = new Label("F = " + (result.objFunctNum.getDen() == 1 ? String.valueOf(result.objFunctNum.getNum()) : result.objFunctNum.getNum() + "/" + result.objFunctNum.getDen()));
        resLabel1.setStyle("-fx-font-size: 16px; -fx-alignment: center;");

        StringBuilder resX = new StringBuilder();
        for (int i = 0; i < result.resultsOfX.length; i++) {
            Rat value = result.resultsOfX[i];
            String fraction = value.getDen() == 1 ? String.valueOf(value.getNum()) : value.getNum() + "/" + value.getDen();
            resX.append("x").append(i).append(" = ").append(fraction).append("\n");
        }
        Label resLabel2 = new Label(resX.toString());
        resLabel2.setStyle("-fx-font-size: 16px; -fx-alignment: center;");

        Node currentContent = scrollPane.getContent();
        if (currentContent != null) {
            vbox.getChildren().add(currentContent);
        }

        vbox.getChildren().addAll(resText, resLabel1, resLabel2);

        vbox.setStyle("-fx-background-color: #ffcab0; -fx-border-width: 2px; -fx-padding: 10px;-fx-alignment: center;");

        scrollPane.setContent(vbox);
    }



    private void createAllTable(SimplexTable table, ScrollPane scrollPane) {
        //VBox у ScrollPane
        VBox vbox = new VBox();
        TableView<ObservableList<String>> tableView = new TableView<>();

        Node currentContent = scrollPane.getContent();

        if (currentContent != null) {
            vbox.getChildren().add(currentContent);
        }

        vbox.getChildren().add(tableView);
        createTable(table, tableView);

        VBox.setMargin(tableView, new Insets(50));

        scrollPane.setContent(vbox);
    }

    public void vidsichenyaToScreen(Rat[] vidsichenia) {
        VBox vbox = new VBox();
        Node currentContent = scrollPaneG.getContent();
        if (currentContent != null) {
            vbox.getChildren().add(currentContent);
        }

        Label closingSystemLabel = new Label("Оскільки розв'язок не цілочисельний, то нова умова обмеження:");
        closingSystemLabel.setStyle("-fx-font-size: 15px; -fx-alignment: center;");
        vbox.getChildren().add(closingSystemLabel);

        Label equationLabel = new Label(vidsicheniaToString(vidsichenia));
        equationLabel.setStyle("-fx-font-size: 16px; -fx-alignment: center;");
        vbox.getChildren().add(equationLabel);

        VBox.setMargin(vbox, new Insets(20, 50, 50, 50));
        scrollPaneG.setContent(vbox);
    }

    private String vidsicheniaToString(Rat[] vidsichenia) {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < vidsichenia.length; i++) {
            Rat rat = vidsichenia[i];
            if (rat.getDen() == 1) {
                sb.append(rat.getNum());
            } else {
                sb.append(String.format("%d/%d", rat.getNum(), rat.getDen()));
            }
            sb.append(" x").append(i);
            if (i < vidsichenia.length - 1) {
                sb.append(" + ");
            }
        }

        sb.append(" = ");

        Rat firstElement = vidsichenia[0];
        if (firstElement.getDen() == 1) {
            sb.append(firstElement.getNum());
        } else {
            sb.append(String.format("%d/%d", firstElement.getNum(), firstElement.getDen()));
        }

        return sb.toString();
    }


    public void firstTableToScreen(SimplexTable table) {

        createFirstTable(table, scrollPane);
    }

    public void elementToScreen(int minRow, int minColumn, SimplexTable table) {
        setElemnt(minRow, minColumn, table, scrollPane);
    }

    public void elementGomoriToScreen(int minRow, int minColumn, SimplexTable table) {
        setElemnt(minRow, minColumn, table, scrollPaneG);
    }

    private void setElemnt(int minRow, int minColumn, SimplexTable table, ScrollPane scrollPane) {
        String text = "Направлючий стовбець: A" + minColumn +
                "\n Направляючий рядок: x" + table.functionValues[table.functionValues[minRow].valNum].valNum;

        Rat value = table.get(minRow, minColumn);
        String fraction = value.getDen() == 1 ? String.valueOf(value.getNum()) : value.getNum() + "/" + value.getDen();
        String directionElement = "\n Направляючий елемент x = " + fraction;

        if (value.getDen() == 0) {
            directionElement = "\n Направляючий елемент x = 0"; // Якщо знаменник рівний 0, встановити текст як "0"
        }

        String finalText = text + directionElement;
        VBox vbox = new VBox();

        Label resText = new Label(finalText);
        resText.setStyle("-fx-font-size: 16px; -fx-alignment: center;");


        Node currentContent = scrollPane.getContent();
        if (currentContent != null) {
            vbox.getChildren().add(currentContent);
        }

        vbox.getChildren().addAll(resText);

        scrollPane.setContent(vbox);
    }

    private static void createTable(SimplexTable table, TableView<ObservableList<String>> tableView) {
        int totalColumns = table.getColumns() + table.getRows();

        for (int i = 0; i < totalColumns; i++) {
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    i == 0 && i < totalColumns - 1 ? "" :
                            (i == 1 ? "B" :
                                    (i == 2 ? "A0" :
                                            (i > 2 && (i - 3) < table.functionValues.length ? "A" + table.functionValues[i - 3].valNum : ""))));
            final int colIndex = i;
            column.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().size() > colIndex ? data.getValue().get(colIndex) : ""));
            tableView.getColumns().add(column);
        }

        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        ObservableList<String> functionValuesRow = FXCollections.observableArrayList();
        functionValuesRow.add(" ");
        functionValuesRow.add("C");
        functionValuesRow.add(" - ");

        for (int i = 0; i < table.functionValues.length; i++) {
            Rat coefficientM = table.functionValues[i].value.coefficientM;
            Rat numAfter = table.functionValues[i].value.numAfter;
            String col;

            if (coefficientM.equalsTo(ZERO) && numAfter.equalsTo(ZERO)) {
                col = "0";
            } else {
                StringBuilder sb = new StringBuilder();
                if (!coefficientM.equalsTo(ZERO)) {
                    if (coefficientM.getDen() == 1) {
                        sb.append(coefficientM.getNum());
                    } else {
                        sb.append(coefficientM.getNum()).append("/").append(coefficientM.getDen());
                    }
                    sb.append("M");
                    if (numAfter.greaterThan(ZERO)) {
                        sb.append("+");
                    }
                }
                if (!numAfter.equalsTo(ZERO)) {
                    if (numAfter.getDen() == 1) {
                        sb.append(numAfter.getNum());
                    } else {
                        sb.append(numAfter.getNum()).append("/").append(numAfter.getDen());
                    }
                }
                col = sb.toString();
            }

            functionValuesRow.add(col);
        }

        data.add(functionValuesRow);

        // Add main table rows
        for (int i = 0; i < table.getRows(); i++) {
            ObservableList<String> rowData = FXCollections.observableArrayList();

            rowData.add(String.valueOf(table.numThatInBasis[i]));
            rowData.add("x" + table.mainTable[i].numOfX + ": ");
            for (int j = 0; j < table.getColumns(); j++) {
                Rat value = table.get(i, j);
                String fraction;
                if (value.getDen() == 1) {
                    fraction = String.valueOf(value.getNum());
                } else {
                    fraction = value.getNum() + "/" + value.getDen();
                }
                rowData.add(fraction);
            }

            data.add(rowData);
        }

        // Add indexes row
        ObservableList<String> indexesRow = FXCollections.observableArrayList();
        indexesRow.add(" ");
        indexesRow.add(" ");

        for (int i = 0; i < table.indexes.length; i++) {
            Rat coefficientM = table.indexes[i].coefficientM;
            Rat numAfter = table.indexes[i].numAfter;
            String col;

            if (coefficientM.equalsTo(ZERO) && numAfter.equalsTo(ZERO)) {
                col = "0";
            } else {
                StringBuilder sb = new StringBuilder();
                if (!coefficientM.equalsTo(ZERO)) {
                    if (coefficientM.getDen() == 1) {
                        sb.append(coefficientM.getNum());
                    } else {
                        sb.append(coefficientM.getNum()).append("/").append(coefficientM.getDen());
                    }
                    sb.append("M");
                    if (numAfter.greaterThan(ZERO)) {
                        sb.append("+");
                    }
                }
                if (!numAfter.equalsTo(ZERO)) {
                    if (numAfter.getDen() == 1) {
                        sb.append(numAfter.getNum());
                    } else {
                        sb.append(numAfter.getNum()).append("/").append(numAfter.getDen());
                    }
                }
                col = sb.toString();
            }

            indexesRow.add(col);
        }

        data.add(indexesRow);

        tableView.setItems(data);
        tableView.setColumnResizePolicy(tableView.CONSTRAINED_RESIZE_POLICY);
    }

}

