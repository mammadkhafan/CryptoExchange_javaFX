package BookPackage;

import CoinPackage.CoinsNameAndIndex;

public class Exchange {
    protected CoinsNameAndIndex coinsNameAndIndex;
    protected int amountOfCoin;
    protected double priceOfEachCoin;


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

    
}
