package Controllers.PageControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import java.util.HashSet;
import java.util.Set;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import BookPackage.Book;
import BookPackage.ComplitedExchange;
import BookPackage.Exchange;
import BookPackage.ExchangeType;
import BookPackage.PendingExchange;
import CoinPackage.CoinsNameAndIndex;
import CoinPackage.CoinsOfCSV;
import CoinPackage.SetMax_MinColumn;
import Controllers.ForAllControllers.PageController;
import MainPackage.Main;

public class TokenController extends PageController implements Initializable{
    @FXML
    private MenuButton pageMenuButton, choseYourCoinMenuButton;

    @FXML 
    private TextField priceTextField, allExchangeTextField, changeTextField;

    @FXML
    private GridPane historyGridPane;

    private ArrayList<Label> weldingsTypes = new ArrayList<>();
    private ArrayList<Label> exchangesTypes = new ArrayList<>();
    private ArrayList<Label> exchangesvalues = new ArrayList<>();

    private final int weldingsTypeColumn = 0;
    private final int exchangeTypeColumn = 1;
    private final int exchangesValueColumn = 2;

    private CoinsOfCSV coinsOfCSV;
    private MenuItem[] menuItemsOfchoseYourCoin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        coinsOfCSV = new CoinsOfCSV("C:\\Users\\ASUS\\Desktop\\TermTow\\FinalProject(TermTwo)\\src\\Files\\currency_prices.csv");
        try {
            menuItemsOfchoseYourCoin = new MenuItem[coinsOfCSV.getAllCoins().size()];
            for (int i = 0; i < coinsOfCSV.getAllCoins().size(); i++) {
                menuItemsOfchoseYourCoin[i] = new MenuItem(coinsOfCSV.getAllCoins().get(i).getName());
                MenuItem menuItem = menuItemsOfchoseYourCoin[i];
                menuItem.setOnAction(event -> {
                    choseYourCoinMenuButton.setText(menuItem.getText()); // Update the button text
                    cleanColumns();
                    fillHistoryGridPane();
                    fillAllInfo();
                });
            }
            choseYourCoinMenuButton.getItems().addAll(menuItemsOfchoseYourCoin);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void cleanColumns() {
        weldingsTypes.clear();
        exchangesTypes.clear();
        exchangesvalues.clear();

        int rowCount = historyGridPane.getRowCount();
        for (int i = rowCount - 1; i > 0; i--) {
            removeRow(historyGridPane, i);
        }
    }

    private void fillHistoryGridPane() {
        for (int i = 0; i < Main.book.getPendingExchangesSize(); i++) {
            if (Main.book.getPendingExchangeAt(i).getUser().equals(user) 
            && Main.book.getPendingExchangeAt(i).getCoinsName().equals(choseYourCoinMenuButton.getText())) {
                addRow(Main.book.getPendingExchangeAt(i));
            }
        }
        for (int i = 0; i < Main.book.getComplitedExchangesSize(); i++) {
            if ((Main.book.getComplitedExchangeAt(i).getSellerUser().equals(user) 
            || Main.book.getComplitedExchangeAt(i).getBuyerUser().equals(user)) 
            && Main.book.getComplitedExchangeAt(i).getCoinsName().equals(choseYourCoinMenuButton.getText())) {
                addRow(Main.book.getComplitedExchangeAt(i));
            }
        }
    }

    private void addRow(Exchange exchange) {
        historyGridPane.getRowConstraints().add(new RowConstraints());

        int row = historyGridPane.getRowCount() - 1;


        Label weldingType = null;
        Label exchangeType = null;

        if (exchange instanceof PendingExchange) {
            PendingExchange pendingExchange = (PendingExchange)exchange;

            weldingType = new Label("PENDING");
            exchangeType = new Label(pendingExchange.getExchangeType().toString());
        }
        else if (exchange instanceof ComplitedExchange) {
            ComplitedExchange complitedExchange = (ComplitedExchange)exchange;

            weldingType = new Label("COMPLITED");
            if (complitedExchange.getBuyerUser().equals(user)) {
                exchangeType = new Label(ExchangeType.BUY.toString());
            } else if (complitedExchange.getSellerUser().equals(user)) {
                exchangeType = new Label(ExchangeType.SELL.toString());
            }
        }
        Label exchangevalue = new Label(exchange.getCoinsName()
         + "(" + String.format("%.1f", exchange.getPriceOfEachCoin()) + ") * " + exchange.getAmountOfCoin());

        historyGridPane.add(weldingType, weldingsTypeColumn, row);
        historyGridPane.add(exchangeType, exchangeTypeColumn, row);
        historyGridPane.add(exchangevalue, exchangesValueColumn, row);

        weldingsTypes.add(weldingType);
        exchangesTypes.add(exchangeType);
        exchangesvalues.add(exchangevalue);
    }

    public void removeRow(GridPane gridPane, int row) {
        Set<Node> nodesToRemove = new HashSet<>();

        for (Node child : gridPane.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(child);
            if (rowIndex != null && rowIndex == row) {
                nodesToRemove.add(child);
            }
        }

        gridPane.getChildren().removeAll(nodesToRemove);
    }

    private void fillAllInfo() {
        double price = coinsOfCSV.getPriceOfCoin(coinsOfCSV.getCurrentTimesRawOfCSVFile(), CoinsNameAndIndex.getCoinsNameAndIndexOfName(choseYourCoinMenuButton.getText()));
        priceTextField.setText(String.format("%.2f", price));
    
        double max = coinsOfCSV.getMax_MInPriceOfCoin(CoinsNameAndIndex.getCoinsNameAndIndexOfName(choseYourCoinMenuButton.getText()), SetMax_MinColumn.MAX);
        double min = coinsOfCSV.getMax_MInPriceOfCoin(CoinsNameAndIndex.getCoinsNameAndIndexOfName(choseYourCoinMenuButton.getText()), SetMax_MinColumn.MIN);
    
        double change = ((max - min) / min) * 100; // Use 100 instead of 0.01 for percentage
        changeTextField.setText(String.format("%.2f", change) + "%");
    
        Book book = Main.book;
        double exchangeAmount = 0;
        for (int i = 0; i < book.getPendingExchangesSize(); i++) {
            if (book.getComplitedExchangeAt(i).getCoinsName().equals(choseYourCoinMenuButton.getText())) {
                exchangeAmount += book.getComplitedExchangeAt(i).getPriceOfEachCoin() * book.getComplitedExchangeAt(i).getAmountOfCoin();
            }
        }
    
        allExchangeTextField.setText(String.format("%.2f", exchangeAmount));
    }
    
}
