package BookPackage;

import CoinPackage.CoinsNameAndIndex;
import MainPackage.User;

public class ComplitedExchange {
    private User buyerUser;
    private User sellerUser;
    private CoinsNameAndIndex coinsNameAndIndex;
    private int amountOfSoldCoins;
    private double priceOfEachCoin;

    public ComplitedExchange(User buyerUser, User sellerUser, CoinsNameAndIndex coinsNameAndIndex, int amountOfSoldCoins, double priceOfEachCoin) {
        this.buyerUser = buyerUser;
        this.sellerUser = sellerUser;
        this.coinsNameAndIndex = coinsNameAndIndex;
        this.amountOfSoldCoins = amountOfSoldCoins;
        this.priceOfEachCoin = priceOfEachCoin;
    }



    public User getBuyerUser() {
        return this.buyerUser;
    }

    public void setBuyerUser(User buyerUser) {
        this.buyerUser = buyerUser;
    }

    public User getSellerUser() {
        return this.sellerUser;
    }

    public void setSellerUser(User sellerUser) {
        this.sellerUser = sellerUser;
    }


    public CoinsNameAndIndex getCoinsNameAndIndex() {
        return this.coinsNameAndIndex;
    }

    public void setCoinsNameAndIndex(CoinsNameAndIndex coinsNameAndIndex) {
        this.coinsNameAndIndex = coinsNameAndIndex;
    }


    public int getAmountOfSoldCoins() {
        return this.amountOfSoldCoins;
    }

    public void setAmountOfSoldCoins(int amountOfSoldCoins) {
        this.amountOfSoldCoins = amountOfSoldCoins;
    }


    public double getPriceOfEachCoin() {
        return this.priceOfEachCoin;
    }

    public void setPriceOfEachCoin(double priceOfEachCoin) {
        this.priceOfEachCoin = priceOfEachCoin;
    }


}
