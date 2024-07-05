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

    private void addRaw(CoinsInfo coin) {
        gridPane.getRowConstraints().add(new RowConstraints());

        int row = gridPane.getRowCount() - 1;

        setRaw(coin, row);
    }

    private void setRaw(CoinsInfo coin, int i) {
        gridPane.setVgap(10);

        ImageView imageView = new ImageView();
        imageView.setImage(coin.getCoinsImage());

        gridPane.add(new Label(coin.getName()), assetColumn, i);
        gridPane.add(imageView, imageColumn, i);  
        gridPane.add(new Label(coin.getPrice()), priceColumn, i);
        gridPane.add(new Label(coin.getChange()), changeColumn, i);
        gridPane.add(new Label(coin.getMaxPrice()), maxPriceColumn, i);
        gridPane.add(new Label(coin.getMinPrice()), minPriceColumn, i);
    }

    private CoinsInfo BTC = new CoinsInfo("BTC", "../../Image/coinIcons/BTC.png");
    private CoinsInfo DOGE = new CoinsInfo("DOGE", "../../Image/coinIcons/DOGE.png");
    private CoinsInfo DASH = new CoinsInfo("DASH", "../../Image/coinIcons/DASH.png");
    private CoinsInfo LTC = new CoinsInfo("LTC", "../../Image/coinIcons/LTC.png");

    private CoinsInfo[] allCoins = {BTC, DOGE, DASH, LTC};


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
            addRaw(allCoins[i]);
        }
    }

    private void showTabel() {
        for (int i = 0; i < allCoins.length; i++) {
            setRaw(allCoins[i], i + 1);
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
