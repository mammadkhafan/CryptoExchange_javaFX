package EachHourInfo;

import java.util.List;

public class RowOfCsvFile {
    private String date;
    private String time;
    private List<Double> prices;

    public RowOfCsvFile(String date, String time, List<Double> prices) {
        this.date = date;
        this.time = time;
        this.prices = prices;
    }

    public double getPriceAt(int index) {
        return prices.get(index);
    }

    public String getTime() {
        return this.time;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Time: " + time + ", Prices: " + prices;
    }

    // Getters and setters if needed
}

