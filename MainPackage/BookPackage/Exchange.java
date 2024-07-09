package BookPackage;

import java.time.LocalDateTime;

import CoinPackage.CoinsNameAndIndex;

public class Exchange {
    protected CoinsNameAndIndex coinsNameAndIndex;
    protected int amountOfCoin;
    protected double priceOfEachCoin;
    protected String date;
    protected String time;


    public Exchange() {
        String dateAndTime = LocalDateTime.now().toString();
        date = dateAndTime.substring(0, 10);
        time = dateAndTime.substring(11, 19);
    }

    public CoinsNameAndIndex getCoinsNameAndIndex() {
        return this.coinsNameAndIndex;
    }

    public void setCoinsNameAndIndex(CoinsNameAndIndex coinsNameAndIndex) {
        this.coinsNameAndIndex = coinsNameAndIndex;
    }

    public int getAmountOfCoin() {
        return this.amountOfCoin;
    }

    public void setAmountOfCoin(int amountOfCoin) {
        this.amountOfCoin = amountOfCoin;
    }

    public double getPriceOfEachCoin() {
        return this.priceOfEachCoin;
    }

    public void setPriceOfEachCoin(double priceOfEachCoin) {
        this.priceOfEachCoin = priceOfEachCoin;
    }

    public String getCoinsName() {
        return this.coinsNameAndIndex.getName();
    }

    public int getCoinsIndex() {
        return this.coinsNameAndIndex.getIndex();
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    
}
