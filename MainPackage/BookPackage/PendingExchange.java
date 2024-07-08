package BookPackage;

import CoinPackage.CoinsNameAndIndex;
import MainPackage.User;

public class PendingExchange {
    private User user;
    private ExchangeType exchangeType;
    private CoinsNameAndIndex coinsNameAndIndex;
    private int amountOfCoin;
    private double priceOfEachCoin;

    public PendingExchange(User user, ExchangeType exchangeType, CoinsNameAndIndex coinsNameAndIndex, int amountOfCoin, double priceOfEachCoin) {
        this.user = user;
        this.exchangeType = exchangeType;
        this.coinsNameAndIndex = coinsNameAndIndex;
        this.amountOfCoin = amountOfCoin;
        this.priceOfEachCoin = priceOfEachCoin;
    }

    public boolean isComplitableWith(PendingExchange incomingPendingExchange) {
        boolean sw = true;

        if (!(incomingPendingExchange.getCoinsNameAndIndex().equals(this.coinsNameAndIndex))) {
            return false;
        }
        else {
            if (this.exchangeType.equals(ExchangeType.BUY)) {
                if (incomingPendingExchange.exchangeType.equals(ExchangeType.BUY)) {
                    return false;
                } 
                else if (!(incomingPendingExchange.getPriceOfEachCoin() >= this.priceOfEachCoin)) {
                   return false;
                }
            }

            else if (this.exchangeType.equals(ExchangeType.SELL)) {
                if (incomingPendingExchange.exchangeType.equals(ExchangeType.SELL)) {
                    return false;
                }
                else if (!(incomingPendingExchange.getPriceOfEachCoin() <= this.priceOfEachCoin)) {
                    return false;
                }
            }
        }

        return sw;
        
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ExchangeType getExchangeType() {
        return this.exchangeType;
    }

    public void setExchangeType(ExchangeType exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getCoinsName() {
        return this.coinsNameAndIndex.getName();
    }

    public int getCoinsIndex() {
        return this.coinsNameAndIndex.getIndex();
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
    
}
