package BookPackage;

import java.util.ArrayList;
import MainPackage.User;

public class Book {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<PendingExchange> pendingExchanges = new ArrayList<>();
    private ArrayList<ComplitedExchange> complitedExchanges = new ArrayList<>();

    public DealsRespond weldTheDeal(PendingExchange incomingPendingExchange) {
        for (int i = 0; i < pendingExchanges.size(); i++) {
            if (pendingExchanges.get(i).isComplitableWith(incomingPendingExchange)) {
                
                if (incomingPendingExchange.getExchangeType().equals(ExchangeType.BUY)) {

                    if (incomingPendingExchange.getAmountOfCoin() > pendingExchanges.get(i).getAmountOfCoin()) {
                        incomingPendingExchange.getUser().increseCoinWelthAt(incomingPendingExchange.getCoinsIndex(), pendingExchanges.get(i).getAmountOfCoin());
                        incomingPendingExchange.getUser().decreaseMoneyWelth(pendingExchanges.get(i).getPriceOfEachCoin() * pendingExchanges.get(i).getAmountOfCoin());
                        pendingExchanges.add(incomingPendingExchange);

                        pendingExchanges.get(i).getUser().decreaseCoinWelthAt(incomingPendingExchange.getCoinsIndex(), pendingExchanges.get(i).getAmountOfCoin());
                        pendingExchanges.get(i).getUser().increaseMoneyWelth(pendingExchanges.get(i).getPriceOfEachCoin() * pendingExchanges.get(i).getAmountOfCoin());
                        onePendingToComplited(pendingExchanges.get(i), incomingPendingExchange, pendingExchanges.get(i).getPriceOfEachCoin());

                        return DealsRespond.FOUNDANDSTAY;
                    }

                    else if (incomingPendingExchange.getAmountOfCoin() < pendingExchanges.get(i).getAmountOfCoin()) {
                        incomingPendingExchange.getUser().increseCoinWelthAt(incomingPendingExchange.getCoinsIndex(), incomingPendingExchange.getAmountOfCoin());
                        incomingPendingExchange.getUser().decreaseMoneyWelth(pendingExchanges.get(i).getPriceOfEachCoin() * incomingPendingExchange.getAmountOfCoin());

                        pendingExchanges.get(i).getUser().decreaseCoinWelthAt(incomingPendingExchange.getCoinsIndex(), incomingPendingExchange.getAmountOfCoin());
                        pendingExchanges.get(i).getUser().increaseMoneyWelth(pendingExchanges.get(i).getPriceOfEachCoin() * incomingPendingExchange.getAmountOfCoin());

                        return DealsRespond.FOUNDANDDONE;
                    }

                    else if (incomingPendingExchange.getAmountOfCoin() == pendingExchanges.get(i).getAmountOfCoin()) {
                        incomingPendingExchange.getUser().increseCoinWelthAt(incomingPendingExchange.getCoinsIndex(), incomingPendingExchange.getAmountOfCoin());
                        incomingPendingExchange.getUser().decreaseMoneyWelth(pendingExchanges.get(i).getPriceOfEachCoin() * incomingPendingExchange.getAmountOfCoin());

                        pendingExchanges.get(i).getUser().decreaseCoinWelthAt(incomingPendingExchange.getCoinsIndex(), incomingPendingExchange.getAmountOfCoin());
                        pendingExchanges.get(i).getUser().increaseMoneyWelth(pendingExchanges.get(i).getPriceOfEachCoin() * incomingPendingExchange.getAmountOfCoin());
                        twoPendingsToComplited(pendingExchanges.get(i), incomingPendingExchange, pendingExchanges.get(i).getPriceOfEachCoin());

                        return DealsRespond.FOUNDANDDONE;
                    }
                }

                if (incomingPendingExchange.getExchangeType().equals(ExchangeType.SELL)) {

                    if (incomingPendingExchange.getAmountOfCoin() > pendingExchanges.get(i).getAmountOfCoin()) {
                        incomingPendingExchange.getUser().decreaseCoinWelthAt(incomingPendingExchange.getCoinsIndex(), pendingExchanges.get(i).getAmountOfCoin());
                        incomingPendingExchange.getUser().increaseMoneyWelth(pendingExchanges.get(i).getPriceOfEachCoin() * pendingExchanges.get(i).getAmountOfCoin());
                        pendingExchanges.add(incomingPendingExchange);

                        pendingExchanges.get(i).getUser().increseCoinWelthAt(incomingPendingExchange.getCoinsIndex(), pendingExchanges.get(i).getAmountOfCoin());
                        pendingExchanges.get(i).getUser().decreaseMoneyWelth(pendingExchanges.get(i).getPriceOfEachCoin() * pendingExchanges.get(i).getAmountOfCoin());
                        onePendingToComplited(pendingExchanges.get(i), incomingPendingExchange, pendingExchanges.get(i).getPriceOfEachCoin());

                        return DealsRespond.FOUNDANDSTAY;
                    }

                    else if (incomingPendingExchange.getAmountOfCoin() < pendingExchanges.get(i).getAmountOfCoin()) {
                        incomingPendingExchange.getUser().decreaseCoinWelthAt(incomingPendingExchange.getCoinsIndex(), incomingPendingExchange.getAmountOfCoin());
                        incomingPendingExchange.getUser().increaseMoneyWelth(pendingExchanges.get(i).getPriceOfEachCoin() * incomingPendingExchange.getAmountOfCoin());

                        pendingExchanges.get(i).getUser().increseCoinWelthAt(incomingPendingExchange.getCoinsIndex(), incomingPendingExchange.getAmountOfCoin());
                        pendingExchanges.get(i).getUser().decreaseMoneyWelth(pendingExchanges.get(i).getPriceOfEachCoin() * incomingPendingExchange.getAmountOfCoin());

                        return DealsRespond.FOUNDANDDONE;
                    }

                    else if (incomingPendingExchange.getAmountOfCoin() == pendingExchanges.get(i).getAmountOfCoin()) {
                        incomingPendingExchange.getUser().decreaseCoinWelthAt(incomingPendingExchange.getCoinsIndex(), pendingExchanges.get(i).getAmountOfCoin());
                        incomingPendingExchange.getUser().increaseMoneyWelth(pendingExchanges.get(i).getPriceOfEachCoin() * pendingExchanges.get(i).getAmountOfCoin());

                        pendingExchanges.get(i).getUser().increseCoinWelthAt(incomingPendingExchange.getCoinsIndex(), pendingExchanges.get(i).getAmountOfCoin());
                        pendingExchanges.get(i).getUser().decreaseMoneyWelth(pendingExchanges.get(i).getPriceOfEachCoin() * pendingExchanges.get(i).getAmountOfCoin());
                        twoPendingsToComplited(pendingExchanges.get(i) , incomingPendingExchange, pendingExchanges.get(i).getPriceOfEachCoin());

                        return DealsRespond.FOUNDANDDONE;
                    }
                }
            }
        }
        pendingExchanges.add(incomingPendingExchange);
        return DealsRespond.NOTFOUND;
    }

    private void onePendingToComplited(PendingExchange whoComplited, PendingExchange whoStayed, double priceOfEachCoin) {
        ComplitedExchange newComplitedExchange;
        if (whoComplited.getExchangeType().equals(ExchangeType.BUY)) {
            newComplitedExchange = new ComplitedExchange(whoComplited.getUser(), whoStayed.getUser(), whoComplited.getCoinsNameAndIndex(), whoComplited.getAmountOfCoin(), priceOfEachCoin);
        } else {
            newComplitedExchange = new ComplitedExchange(whoStayed.getUser(), whoComplited.getUser(), whoComplited.getCoinsNameAndIndex(), whoComplited.getAmountOfCoin(), priceOfEachCoin);
        }
        complitedExchanges.add(newComplitedExchange);
        pendingExchanges.remove(whoComplited);
    }

    private void twoPendingsToComplited(PendingExchange pending1, PendingExchange pending2, double priceOfEachCoin) {
        onePendingToComplited(pending1, pending2, priceOfEachCoin);
        pendingExchanges.remove(pending2);
    }

    public void addUser(User newUser) {
        users.add(newUser);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public boolean findUser(String username, String password) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                if (users.get(i).getPassword().equals(password)) {
                    return true;
                } else return false;
                
            }
        }
        return false;
    }
}