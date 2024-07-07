package Controllers.PageControllers;

import java.net.URL;
import java.util.ResourceBundle;
import MainPackage.CoinsInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import java.util.ArrayList;
import java.util.Collections;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import HomePagePackage.CryptoData;

public class HomeController implements Initializable{
    @FXML
    private GridPane gridPane;

    @FXML
    private Label assetLabel;

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

    private CoinsInfo USD = new CoinsInfo("USD", "../../Image/coinIcons/USD.png");
    private CoinsInfo EUR = new CoinsInfo("EUR", "../../Image/coinIcons/EUR.png");
    private CoinsInfo TOMAN = new CoinsInfo("TOMAN", "../../Image/coinIcons/TOMAN.png");
    private CoinsInfo YEN = new CoinsInfo("YEN", "../../Image/coinIcons/YEN.png");
    private CoinsInfo GBP = new CoinsInfo("GBP", "../../Image/coinIcons/GBP.png");

    private ArrayList<CoinsInfo> allCoins = new ArrayList<>();

    List<CryptoData> cryptoDataList = new ArrayList<>();


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
        importDataSet("C:\\Users\\ASUS\\Desktop\\TermTow\\FinalProject(TermTwo)\\src\\Files\\currency_prices.csv");

        allCoins.add(USD);
        allCoins.add(EUR);
        allCoins.add(TOMAN);
        allCoins.add(YEN);
        allCoins.add(GBP);

        setPrices();
        setMax_MInColumn(SetMax_MInColumn.MAX);
        setMax_MInColumn(SetMax_MInColumn.MIN);
        showTabelForFirstTime();
    }

    private void importDataSet(String absoloutPath) {
        try (CSVReader reader = new CSVReader(new FileReader(absoloutPath))) {
            String[] nextLine;
            boolean skipHeader = true;  // Assuming first line is header
            while ((nextLine = reader.readNext()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;  // Skip the header line
                }

                if (nextLine.length < 2) {
                    continue;
                }

                String date = nextLine[0].trim();
                String time = nextLine[1].trim();

                List<Double> prices = new ArrayList<>();
                for (int i = 2; i < nextLine.length; i++) {
                    try {
                        double price = Double.parseDouble(nextLine[i].trim());
                        prices.add(price);
                    } catch (NumberFormatException e) {
                        // Handle the case where parsing fails (e.g., log an error)
                        System.err.println("Error parsing price: " + nextLine[i]);
                    }
                }

                CryptoData cryptoData = new CryptoData(date, time, prices);
                cryptoDataList.add(cryptoData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPrices() {
        double[] nowPrices = new double[allCoins.size()];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS");
        for (int i = 0; i < cryptoDataList.size() - 1; i++) {
            
            LocalTime time1 = LocalTime.parse(cryptoDataList.get(i).getTime(), formatter);
            LocalTime time2 = LocalTime.parse(cryptoDataList.get(i + 1).getTime(), formatter);            

            if (currentTimeIsbetween(time1, time2)) {
                for (int j = 0; j < nowPrices.length; j++) {
                    nowPrices[j] = getPriceOfCoin(cryptoDataList.get(i), Coins.getCoinOfIndex(j));
                }
                break;
            }
        }

        for (int i = 0; i < allCoins.size(); i++) {
            allCoins.get(i).setPrice(nowPrices[i]);
        }
    }

    private double getPriceOfCoin(CryptoData cryptoData, Coins coin) {
        return cryptoData.getPriceAt(coin.getIndex());
    }

    private void setMax_MInColumn(SetMax_MInColumn max_min) {
        for (int i = 0; i < allCoins.size(); i++) {
            if (max_min.equals(SetMax_MInColumn.MAX)) 
                allCoins.get(i).setMaxPrice(getMax_MInPriceOfCoin(Coins.getCoinOfIndex(i), max_min));
            else
                allCoins.get(i).setMinPrice(getMax_MInPriceOfCoin(Coins.getCoinOfIndex(i), max_min));
        }
    }

    private double getMax_MInPriceOfCoin(Coins coin, SetMax_MInColumn max_min) {
        double maxPrice = Double.MIN_VALUE;
        double minPrice = Double.MAX_VALUE;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS");
        for (int i = 0; i < cryptoDataList.size() - 1; i++) {
            
            LocalTime time1 = LocalTime.parse(cryptoDataList.get(i).getTime(), formatter);
            LocalTime time2 = LocalTime.parse(cryptoDataList.get(i + 1).getTime(), formatter); 

            if (max_min.equals(SetMax_MInColumn.MAX)) 
                maxPrice = Math.max(maxPrice, getPriceOfCoin(cryptoDataList.get(i), coin));
            else
                minPrice = Math.min(minPrice, getPriceOfCoin(cryptoDataList.get(i), coin));

            if (currentTimeIsbetween(time1, time2)) {
                break;
            }
        }

        if (max_min.equals(SetMax_MInColumn.MAX)) return maxPrice;
        else return minPrice;
    }

    private void showTabel() {
        for (int i = 0; i < allCoins.size(); i++) {
            setRow(allCoins.get(i), i + 1);
        }
    }

    private void showTabelForFirstTime() {
        for (int i = 0; i < allCoins.size(); i++) {
            addRow(allCoins.get(i));
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


    public boolean currentTimeIsbetween(LocalTime time1, LocalTime time2) {
        LocalDateTime now = LocalDateTime.now();
        int hourNow = now.getHour() + 1;
        int minuteNow = now.getMinute();

        int hour1 = time1.getHour();
        int minute1 = time1.getMinute();

        int hour2 = time2.getHour();
        int minute2 = time2.getMinute();

        if (hour1 <= hourNow && hour2 > hourNow && minute1 == minuteNow) return true;
        else if (hour1 == hour2 && hour1 == hourNow) {
            if (minute1 < minute2) {
                if (minute1 <= minuteNow && minute2 > minuteNow) return true;
                else return false;
            } else return false;
        } else return false;
    }

    private void sortCoins(SortBy sortBy, SortType sortType) {
        switch (sortBy) {
            case ALFABET:
                if (sortType == SortType.ASCENDING) {
                    Collections.sort(allCoins, (c1, c2) -> c1.getName().compareTo(c2.getName()));
                } else {
                    Collections.sort(allCoins, (c1, c2) -> c2.getName().compareTo(c1.getName()));
                }
                break;
            case PRICE:
                if (sortType == SortType.DESCENDING) {
                    Collections.sort(allCoins, (c1, c2) -> Double.compare(c1.getPrice(), c2.getPrice()));
                } else {
                    Collections.sort(allCoins, (c1, c2) -> Double.compare(c2.getPrice(), c1.getPrice()));
                }
                break;
            case CHANGE:
                if (sortType == SortType.DESCENDING) {
                    Collections.sort(allCoins, (c1, c2) -> Double.compare(Double.parseDouble(ExtractNumber(c1.getChange())), Double.parseDouble(ExtractNumber(c2.getChange()))));
                } else {
                    Collections.sort(allCoins, (c1, c2) -> Double.compare(Double.parseDouble(ExtractNumber(c2.getChange())), Double.parseDouble(ExtractNumber(c1.getChange()))));
                }
                break;
            case MAXPRICE:
                if (sortType == SortType.DESCENDING) {
                    Collections.sort(allCoins, (c1, c2) -> Double.compare(c1.getMaxPrice(), c2.getMaxPrice()));
                } else {
                    Collections.sort(allCoins, (c1, c2) -> Double.compare(c2.getMaxPrice(), c1.getMaxPrice()));
                }
                break;
            case MINPRICE:
                if (sortType == SortType.DESCENDING) {
                    Collections.sort(allCoins, (c1, c2) -> Double.compare(c1.getMinPrice(), c2.getMinPrice()));
                } else {
                    Collections.sort(allCoins, (c1, c2) -> Double.compare(c2.getMinPrice(), c1.getMinPrice()));
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
    ASCENDING("Ascending"),
    DESCENDING("Descending");

    private String type;

    SortType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

enum Coins {
    USD("UDS", 0),
    EUR("EUR",  1),
    TOMAN("TOMAN", 2),
    YEN("YEN", 3),
    GBP("GBP", 4);

    private String coinName;
    private int index;

    Coins(String coinName, int index) {
        this.coinName = coinName;
        this.index = index;
    }

    public String getCoinName() {
        return coinName;
    }

    public int getIndex() {
        return index;
    }

    public static Coins getCoinOfIndex(int index) {
        switch (index) {
            case 0:
                
                return Coins.USD;
            case 1:
                
                return Coins.EUR;
            case 2:
                
                return Coins.TOMAN;
            case 3:
                
                return Coins.YEN;
            case 4:
                
                return Coins.GBP;
        
            default:
                return null;
        }
    } 
}

enum SetMax_MInColumn {
    MAX(),
    MIN();
}


