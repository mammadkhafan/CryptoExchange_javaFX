package CoinPackage;

import MainPackage.CoinsInfo;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import com.opencsv.CSVReader;
import java.time.LocalDateTime;
import HomePagePackage.RowOfCsvFile;

public class CoinsOfCSV {
    /*All of coins in .csv file is USD, EUR, TOMAN, YEN, GBP */

    private CoinsInfo USD = new CoinsInfo("USD", "../../Image/coinIcons/USD.png");
    private CoinsInfo EUR = new CoinsInfo("EUR", "../../Image/coinIcons/EUR.png");
    private CoinsInfo TOMAN = new CoinsInfo("TOMAN", "../../Image/coinIcons/TOMAN.png");
    private CoinsInfo YEN = new CoinsInfo("YEN", "../../Image/coinIcons/YEN.png");
    private CoinsInfo GBP = new CoinsInfo("GBP", "../../Image/coinIcons/GBP.png");

    private ArrayList<CoinsInfo> allCoins = new ArrayList<>();

    private List<RowOfCsvFile> rowOfCsvFileList = new ArrayList<>();

    public CoinsOfCSV(String absolutePathOfCSV) {
        initialize(absolutePathOfCSV);
    }

    public void initialize(String absolutePathOfCSV) {
        importDataSet(absolutePathOfCSV);

        allCoins.add(USD);
        allCoins.add(EUR);
        allCoins.add(TOMAN);
        allCoins.add(YEN);
        allCoins.add(GBP);
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

                RowOfCsvFile RowOfCsvFile = new RowOfCsvFile(date, time, prices);
                rowOfCsvFileList.add(RowOfCsvFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getPriceOfCoin(RowOfCsvFile rowOfCsvFile, CoinsNameAndIndex coin) {
        return rowOfCsvFile.getPriceAt(coin.getIndex());
    }

    public double getMax_MInPriceOfCoin(CoinsNameAndIndex coin, SetMax_MinColumn max_min) {
        double maxPrice = Double.MIN_VALUE;
        double minPrice = Double.MAX_VALUE;
        double change = 0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS");
        for (int i = 0; i < rowOfCsvFileList.size() - 1; i++) {
            
            LocalTime time1 = LocalTime.parse(rowOfCsvFileList.get(i).getTime(), formatter);
            LocalTime time2 = LocalTime.parse(rowOfCsvFileList.get(i + 1).getTime(), formatter);

            if (max_min.equals(SetMax_MinColumn.MAX))
                maxPrice = Math.max(maxPrice, getPriceOfCoin(rowOfCsvFileList.get(i), coin));
            else
                minPrice = Math.min(minPrice, getPriceOfCoin(rowOfCsvFileList.get(i), coin));

            if (IscurrentTimebetween(time1, time2)) {
                double delta;
                if (i == 0) {
                    delta = getPriceOfCoin(rowOfCsvFileList.get(i), coin) - getPriceOfCoin(rowOfCsvFileList.get(rowOfCsvFileList.size() - 1), coin);
                    change = delta / getPriceOfCoin(rowOfCsvFileList.get(rowOfCsvFileList.size() - 1), coin);
                } else {
                    delta = getPriceOfCoin(rowOfCsvFileList.get(i), coin) - getPriceOfCoin(rowOfCsvFileList.get(i - 1), coin);
                    change = delta / getPriceOfCoin(rowOfCsvFileList.get(i - 1), coin);
                }
                
                
                break;
            }
        }

        if (max_min.equals(SetMax_MinColumn.MAX)) return maxPrice;
        else if (max_min.equals(SetMax_MinColumn.MIN)) return minPrice;
        else return Math.round(change * 100.0) / 100.0;
    }

    public RowOfCsvFile getCurrentTimesRawOfCSVFile() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS");
        for (int i = 0; i < rowOfCsvFileList.size() - 1; i++) {
            
            LocalTime time1 = LocalTime.parse(rowOfCsvFileList.get(i).getTime(), formatter);
            LocalTime time2 = LocalTime.parse(rowOfCsvFileList.get(i + 1).getTime(), formatter);

            if (IscurrentTimebetween(time1, time2)) {
                return rowOfCsvFileList.get(i);
            }
        }
        return null;
    }

    public static boolean IscurrentTimebetween(LocalTime time1, LocalTime time2) {
        LocalDateTime now = LocalDateTime.now();
        int hourNow = now.getHour() + 1;
        if (hourNow == 24) {
            hourNow = 0;
        }
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

    public ArrayList<CoinsInfo> getAllCoins() {
        return allCoins;
    }

    public List<RowOfCsvFile> getRowOfCsvFileList() {
        return rowOfCsvFileList;
    }

    public CoinsInfo[] getAllcoinsAsArray() {
        CoinsInfo[] toArray;
        if (allCoins.size() == 0) {
            allCoins.add(USD);
            allCoins.add(EUR);
            allCoins.add(TOMAN);
            allCoins.add(YEN);
            allCoins.add(GBP);
        }
        
        toArray = new CoinsInfo[allCoins.size()];
        for (int i = 0; i < toArray.length; i++) {
            toArray[i] = allCoins.get(i);
        }
        

        return toArray;
    }
}