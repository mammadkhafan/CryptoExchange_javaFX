package BookPackage;

import CoinPackage.CoinsNameAndIndex;
import MainPackage.User;

public class PendingExchange extends Exchange{
    private User user;
    private ExchangeType exchangeType;

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

    public void decreaseAmountofCoins(int amount) {
        amountOfCoin -= amount;
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
}
