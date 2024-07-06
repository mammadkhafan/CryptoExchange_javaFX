package Controllers.PageControllers;

import java.net.URL;
import java.util.ResourceBundle;

import MainPackage.CoinsInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import java.util.ArrayList;

public class HomeController implements Initializable{
    @FXML
    private GridPane gridPane;
    @FXML
    private RadioMenuItem sortByPriceRadioMenuItem;

    private final int assetColumn = 0;
    private final int imageColumn = 1;
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

    private CoinsInfo BTC = new CoinsInfo("BTC", "../../Image/coinIcons/BTC.png");
    private CoinsInfo DOGE = new CoinsInfo("DOGE", "../../Image/coinIcons/DOGE.png");
    private CoinsInfo DASH = new CoinsInfo("DASH", "../../Image/coinIcons/DASH.png");
    private CoinsInfo LTC = new CoinsInfo("LTC", "../../Image/coinIcons/LTC.png");

    private CoinsInfo[] allCoins = {BTC, DOGE, DASH, LTC};


    private void addRow(CoinsInfo coin) {
        gridPane.getRowConstraints().add(new RowConstraints());

        int row = gridPane.getRowCount() - 1;

        gridPane.setVgap(10);

        Label asset = new Label(coin.getName());
        ImageView image = new ImageView();
        image.setImage(coin.getCoinsImage());
        Label price = new Label(coin.getPrice());
        Label change = new Label(coin.getChange());
        Label maxPrice = new Label(coin.getMaxPrice());
        Label minPrice = new Label(coin.getMinPrice());

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
        prices.get(row - 1).setText(coin.getPrice());
        changes.get(row - 1).setText(coin.getChange());
        maxPrices.get(row - 1).setText(coin.getMaxPrice());
        minPrices.get(row - 1).setText(coin.getMinPrice());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BTC.setChange("+1.31%");
        DOGE.setChange("+2.22%");
        DASH.setChange("+4.83%");
        LTC.setChange("+3.27%");

        BTC.setPrice("2,465,907,360");
        DOGE.setPrice("4,525");
        DASH.setPrice("1,633,184");
        LTC.setPrice("3,693,200");

        BTC.setMaxPrice("2,465,907,360");
        DOGE.setMaxPrice("4,525");
        DASH.setMaxPrice("1,633,184");
        LTC.setMaxPrice("3,693,200");

        BTC.setMinPrice("2,465,907,360");
        DOGE.setMinPrice("4,525");
        DASH.setMinPrice("1,633,184");
        LTC.setMinPrice("3,693,200");

        for (int i = 0; i < allCoins.length; i++) {
            addRow(allCoins[i]);
        }
    }

    private void showTabel() {
        for (int i = 0; i < allCoins.length; i++) {
            setRow(allCoins[i], i + 1);
        }
    }

    @FXML
    private void sortByPrice() {
        for (int i = allCoins.length - 1; i > -1; i--) {
            for (int j = 0; j < i; j++) {   
                if (Double.parseDouble(ExtractNumber(allCoins[j].getPrice())) < Double.parseDouble(ExtractNumber(allCoins[j+1].getPrice()))) {
                    CoinsInfo temt = allCoins[j];
                    allCoins[j] = allCoins[j + 1];
                    allCoins[j + 1] = temt;
                }
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


}
