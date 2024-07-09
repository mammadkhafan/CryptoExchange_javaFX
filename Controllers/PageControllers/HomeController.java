package Controllers.PageControllers;

import java.net.URL;
import java.util.ResourceBundle;
import MainPackage.CoinsInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import java.util.ArrayList;
import java.util.Collections;
import CoinPackage.*;

public class HomeController implements Initializable{
    @FXML
    private GridPane gridPane;

    @FXML
    private Label assetLabel;

    @FXML
    private MenuButton pageMenuButton;

    private final int assetColumn = 1;
    private final int imageColumn = 0;
    private final int priceColumn = 2;
    private final int changeColumn = 3;
    private final int maxPriceColumn = 4;
    private final int minPriceColumn = 5;

    private ArrayList<Label> assets = new ArrayList<>();
    private ArrayList<ImageView> images = new ArrayList<>();
    private ArrayList<Label> prices = new ArrayList<>();
    private ArrayList<Label> changes = new ArrayList<>();
    private ArrayList<Label> maxPrices = new ArrayList<>();
    private ArrayList<Label> minPrices = new ArrayList<>();

    CoinsOfCSV coinsOfCSV = new CoinsOfCSV("C:\\Users\\ASUS\\Desktop\\TermTow\\FinalProject(TermTwo)\\src\\Files\\currency_prices.csv");

    private void addRow(CoinsInfo coin) {
        gridPane.getRowConstraints().add(new RowConstraints());

        int row = gridPane.getRowCount() - 1;

        gridPane.setVgap(10);

        Label asset = new Label(coin.getName());
        ImageView image = new ImageView();
        image.setImage(coin.getCoinsImage());
        Label price = new Label(Double.toString(coin.getPrice()));
        Label change = new Label(coin.getChange());
        Label maxPrice = new Label(Double.toString(coin.getMaxPrice()));
        Label minPrice = new Label(Double.toString(coin.getMinPrice()));

        gridPane.add(asset, assetColumn, row);
        gridPane.add(image, imageColumn, row);
        gridPane.add(price, priceColumn, row);
        gridPane.add(change, changeColumn, row);
        gridPane.add(maxPrice, maxPriceColumn, row);
        gridPane.add(minPrice, minPriceColumn, row);

        assets.add(asset);
        images.add(image);
        prices.add(price);
        changes.add(change);
        maxPrices.add(maxPrice);
        minPrices.add(minPrice);
        
    }

    private void setRow(CoinsInfo coin, int row) {
        assets.get(row - 1).setText(coin.getName());
        images.get(row - 1).setImage(coin.getCoinsImage());
        prices.get(row - 1).setText(Double.toString(coin.getPrice()));
        changes.get(row - 1).setText(coin.getChange());
        maxPrices.get(row - 1).setText(Double.toString(coin.getMaxPrice()));
        minPrices.get(row - 1).setText(Double.toString(coin.getMinPrice()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPrices();
        SetMax_MinColumn(SetMax_MinColumn.MAX);
        SetMax_MinColumn(SetMax_MinColumn.MIN);
        SetMax_MinColumn(SetMax_MinColumn.CHANGE);
        showTabelForFirstTime();
    }

    private void setPrices() {
        double[] nowPrices = new double[coinsOfCSV.getAllCoins().size()];

        for (int j = 0; j < nowPrices.length; j++) {
            nowPrices[j] = coinsOfCSV.getPriceOfCoin(coinsOfCSV.getCurrentTimesRawOfCSVFile(), CoinsNameAndIndex.getCoinsNameAndIndexOfIndex(j));
        }

        for (int i = 0; i < coinsOfCSV.getAllCoins().size(); i++) {
            coinsOfCSV.getAllCoins().get(i).setPrice(nowPrices[i]);
        }
    }

    private void SetMax_MinColumn(SetMax_MinColumn max_min) {
        for (int i = 0; i < coinsOfCSV.getAllCoins().size(); i++) {
            if (max_min.equals(SetMax_MinColumn.MAX)) 
                coinsOfCSV.getAllCoins().get(i).setMaxPrice(coinsOfCSV.getMax_MInPriceOfCoin(CoinsNameAndIndex.getCoinsNameAndIndexOfIndex(i), max_min));
            else if (max_min.equals(SetMax_MinColumn.MIN))
                coinsOfCSV.getAllCoins().get(i).setMinPrice(coinsOfCSV.getMax_MInPriceOfCoin(CoinsNameAndIndex.getCoinsNameAndIndexOfIndex(i), max_min));
            else 
                coinsOfCSV.getAllCoins().get(i).setChangeNumber(coinsOfCSV.getMax_MInPriceOfCoin(CoinsNameAndIndex.getCoinsNameAndIndexOfIndex(i), max_min));
        }
    }

    private void showTabel() {
        for (int i = 0; i < coinsOfCSV.getAllCoins().size(); i++) {
            setRow(coinsOfCSV.getAllCoins().get(i), i + 1);
        }
    }

    private void showTabelForFirstTime() {
        for (int i = 0; i < coinsOfCSV.getAllCoins().size(); i++) {
            addRow(coinsOfCSV.getAllCoins().get(i));
        }
    }

    
    @FXML
    private void sortByPrice() {
        sortCoins(SortBy.PRICE, SortType.ASCENDING);
    }

    @FXML
    private void sortAscendingByChange() {
        sortCoins(SortBy.CHANGE, SortType.ASCENDING);
    }

    @FXML
    private void sortDescendingByChange() {
        sortCoins(SortBy.CHANGE, SortType.DESCENDING);
    }

    @FXML
    private void sortAscendingByMaxPrice() {
        sortCoins(SortBy.MAXPRICE, SortType.ASCENDING);
    }

    @FXML
    private void sortDescendingByMaxPrice() {
        sortCoins(SortBy.MAXPRICE, SortType.DESCENDING);
    }

    @FXML
    private void sortAscendingByMinPrice() {
        sortCoins(SortBy.MINPRICE, SortType.ASCENDING);
    }

    @FXML
    private void sortDescendingByMinPrice() {
        sortCoins(SortBy.MINPRICE, SortType.DESCENDING);
    }

    @FXML 
    private void A_zSort() {
        sortCoins(SortBy.ALFABET, SortType.ASCENDING);
    }

    @FXML 
    private void z_ASort() {
        sortCoins(SortBy.ALFABET, SortType.DESCENDING);
    }

    private void sortCoins(SortBy sortBy, SortType sortType) {
        switch (sortBy) {
            case ALFABET:
                if (sortType == SortType.ASCENDING) {
                    Collections.sort(coinsOfCSV.getAllCoins(), (c1, c2) -> c1.getName().compareTo(c2.getName()));
                } else {
                    Collections.sort(coinsOfCSV.getAllCoins(), (c1, c2) -> c2.getName().compareTo(c1.getName()));
                }
                break;
            case PRICE:
                if (sortType == SortType.DESCENDING) {
                    Collections.sort(coinsOfCSV.getAllCoins(), (c1, c2) -> Double.compare(c1.getPrice(), c2.getPrice()));
                } else {
                    Collections.sort(coinsOfCSV.getAllCoins(), (c1, c2) -> Double.compare(c2.getPrice(), c1.getPrice()));
                }
                break;
            case CHANGE:
                if (sortType == SortType.DESCENDING) {
                    Collections.sort(coinsOfCSV.getAllCoins(), (c1, c2) -> Double.compare(Double.parseDouble(ExtractNumber(c1.getChange())), Double.parseDouble(ExtractNumber(c2.getChange()))));
                } else {
                    Collections.sort(coinsOfCSV.getAllCoins(), (c1, c2) -> Double.compare(Double.parseDouble(ExtractNumber(c2.getChange())), Double.parseDouble(ExtractNumber(c1.getChange()))));
                }
                break;
            case MAXPRICE:
                if (sortType == SortType.DESCENDING) {
                    Collections.sort(coinsOfCSV.getAllCoins(), (c1, c2) -> Double.compare(c1.getMaxPrice(), c2.getMaxPrice()));
                } else {
                    Collections.sort(coinsOfCSV.getAllCoins(), (c1, c2) -> Double.compare(c2.getMaxPrice(), c1.getMaxPrice()));
                }
                break;
            case MINPRICE:
                if (sortType == SortType.DESCENDING) {
                    Collections.sort(coinsOfCSV.getAllCoins(), (c1, c2) -> Double.compare(c1.getMinPrice(), c2.getMinPrice()));
                } else {
                    Collections.sort(coinsOfCSV.getAllCoins(), (c1, c2) -> Double.compare(c2.getMinPrice(), c1.getMinPrice()));
                }
        }
    
        showTabel();
    }

    private String ExtractNumber(String number) {
        String extractedNumber = "";
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) >= 48 && number.charAt(i) <= 57) {
                extractedNumber += number.charAt(i);
            }
        }
        return extractedNumber;
    }

    @FXML
    private void openPagesMenuButton() {
        pageMenuButton.show();
    }
}

enum SortBy {
    ALFABET("ALFABET"),
    PRICE("PRICE"),
    CHANGE("CHANGE"),
    MAXPRICE("MAXPRICE"),
    MINPRICE("MINPRICE");

    private String by;

    SortBy(String by) {
        this.by = by;
    }

    public String getDescription() {
        return by;
    }
}

enum SortType {
    ASCENDING(),
    DESCENDING();
}


