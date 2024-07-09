package BookPackage;

import CoinPackage.CoinsNameAndIndex;
import MainPackage.User;

public class ComplitedExchange extends Exchange{
    private User buyerUser;
    private User sellerUser;


    public ComplitedExchange(User buyerUser, User sellerUser, CoinsNameAndIndex coinsNameAndIndex, int amountOfSoldCoins, double priceOfEachCoin) {
        this.buyerUser = buyerUser;
        this.sellerUser = sellerUser;
        this.coinsNameAndIndex = coinsNameAndIndex;
        this.amountOfCoin = amountOfSoldCoins;
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

}
