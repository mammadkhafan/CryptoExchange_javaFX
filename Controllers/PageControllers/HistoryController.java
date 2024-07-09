package Controllers.PageControllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import BookPackage.ComplitedExchange;
import BookPackage.Exchange;
import BookPackage.ExchangeType;
import BookPackage.PendingExchange;
import Controllers.ForAllControllers.MostHaveInitialize;
import Controllers.ForAllControllers.PageController;
import MainPackage.Main;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class HistoryController extends PageController implements MostHaveInitialize{
    @FXML
    private MenuButton pageMenuButton;

    @FXML
    private GridPane historyGridPane;

    private ArrayList<Label> dates = new ArrayList<>();
    private ArrayList<Label> times = new ArrayList<>();
    private ArrayList<Label> weldingsTypes = new ArrayList<>();
    private ArrayList<Label> exchangesTypes = new ArrayList<>();
    private ArrayList<Label> exchangesvalues = new ArrayList<>();

    private final int dateColumn = 0;
    private final int timeColumn = 1;
    private final int weldingsTypeColumn = 2;
    private final int exchangeTypeColumn = 3;
    private final int exchangesValueColumn = 4;

    private boolean initialized = false;
    
    
    @Override
    public void initializeWithMouseMove(MouseEvent event) {
        if (!initialized) {
            initialized = true;
            cleanColumns();
            fillHistoryGridPane();
        }
    }

    private void cleanColumns() {
        dates.clear();
        times.clear();
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
            if (Main.book.getPendingExchangeAt(i).getUser().equals(user)) {
                addRow(Main.book.getPendingExchangeAt(i));
            }
        }
        for (int i = 0; i < Main.book.getComplitedExchangesSize(); i++) {
            if ((Main.book.getComplitedExchangeAt(i).getSellerUser().equals(user) 
            || Main.book.getComplitedExchangeAt(i).getBuyerUser().equals(user))) {
                addRow(Main.book.getComplitedExchangeAt(i));
            }
        }
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

    private void addRow(Exchange exchange) {
        historyGridPane.getRowConstraints().add(new RowConstraints());

        int row = historyGridPane.getRowCount() - 1;


        Label date = null;
        Label time = null;
        Label weldingType = null;
        Label exchangeType = null;

        date = new Label(exchange.getDate());
        time = new Label(exchange.getTime());
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

        historyGridPane.add(date, dateColumn, row);
        historyGridPane.add(time, timeColumn, row);
        historyGridPane.add(weldingType, weldingsTypeColumn, row);
        historyGridPane.add(exchangeType, exchangeTypeColumn, row);
        historyGridPane.add(exchangevalue, exchangesValueColumn, row);

        dates.add(date);
        times.add(time);
        weldingsTypes.add(weldingType);
        exchangesTypes.add(exchangeType);
        exchangesvalues.add(exchangevalue);
    }
    
}
