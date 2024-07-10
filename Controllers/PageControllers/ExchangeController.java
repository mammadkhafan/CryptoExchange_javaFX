package Controllers.PageControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.UnaryOperator;

import BookPackage.ComplitedExchange;
import BookPackage.Exchange;
import BookPackage.ExchangeType;
import BookPackage.PendingExchange;
import CoinPackage.CoinsNameAndIndex;
import CoinPackage.CoinsOfCSV;
import Controllers.ForAllControllers.LabelFlexible;
import Controllers.ForAllControllers.PageController;
import MainPackage.ErrorMessage;
import MainPackage.Main;

public class ExchangeController extends PageController implements Initializable, LabelFlexible{
    @FXML
    private MenuButton pageMenuButton, choseYourCoinMenuButton;

    @FXML
    private RadioButton buyRadioButton, sellRadioButton;

    @FXML
    private TextField amountTextField, priceTextField;

    @FXML
    private GridPane historyGridPane;

    private ArrayList<Label> weldingsTypes = new ArrayList<>();
    private ArrayList<Label> exchangesTypes = new ArrayList<>();
    private ArrayList<Label> exchangesvalues = new ArrayList<>();

    private final int weldingsTypeColumn = 0;
    private final int exchangeTypeColumn = 1;
    private final int exchangesValueColumn = 2;


    @FXML
    private Label choseYourCoinMessageLabel, amountMessageLabel, priceMessageLabel, typeOfExchangeMessageLabel,
            dealsResultlabel, moreInfoAboutDealsResult, personWhoYouDealWithLabel;

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
                });
            }
            choseYourCoinMenuButton.getItems().addAll(menuItemsOfchoseYourCoin);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        


        // TextFormatter for amountTextField to allow only integer values
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty()) {
                return change;
            }
            try {
                Integer.parseInt(newText);
                return change;
            } catch (NumberFormatException e) {
                return null;
            }
        };

        TextFormatter<Integer> textFormatterForAmountTextField = new TextFormatter<>(new javafx.util.converter.IntegerStringConverter(), 0, integerFilter);
        amountTextField.setTextFormatter(textFormatterForAmountTextField);

        // TextFormatter for priceTextField to allow only valid double values with a single '.'
        UnaryOperator<TextFormatter.Change> doubleFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty()) {
                return change;
            }
            if (newText.equals(".")) {
                return change;
            }
            try {
                // Ensure there is at most one decimal point
                if (newText.chars().filter(ch -> ch == '.').count() > 1) {
                    return null;
                }
                Double.parseDouble(newText);
                return change;
            } catch (NumberFormatException e) {
                return null;
            }
        };

        TextFormatter<Double> textFormatterForPriceTextField = 
        new TextFormatter<>(new DoubleStringConverter(), 0.0, doubleFilter);
        priceTextField.setTextFormatter(textFormatterForPriceTextField);
    
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

    @FXML
    private void increaseAmountValue() {
        if (checkAmountWithArrow()) {
            int currentAmount = Integer.parseInt(amountTextField.getText());
            amountTextField.setText(String.valueOf(currentAmount + 1));
        }
        
    }

    @FXML
    private void decreaseAmountValue() {
        int currentAmount = Integer.parseInt(amountTextField.getText());
        if (currentAmount - 1 >= 0) 
            amountTextField.setText(String.valueOf(currentAmount - 1));
    }

    @FXML
    private void increasePriceValue() {
        if (checkPriceWithArrow()) {
            double currentPrice = Double.parseDouble(priceTextField.getText());
            String formattedPrice = String.format("%.1f", currentPrice + 0.1);
            priceTextField.setText(formattedPrice);
        }
        
    }

    @FXML
    private void decreasePriceValue() {
        double currentPrice = Double.parseDouble(priceTextField.getText());
        String formattedPrice = "";
        if (currentPrice - 0.1 >= 0) { 
            formattedPrice += String.format("%.1f", currentPrice - 0.1);
            priceTextField.setText(formattedPrice);
        }
    }

    private boolean checkAmountWithArrow() {
        if (buyRadioButton.isSelected()) {
            return true;
        }
        if (!choseYourCoinMenuButton.getText().equals("Chose your coin")) {
            if (user.getCoinWelthAt(CoinsNameAndIndex.getCoinsNameAndIndexOfName(choseYourCoinMenuButton.getText()).getIndex()) < Integer.parseInt(amountTextField.getText()) + 1) {
                toError(amountMessageLabel, ErrorMessage.lackOfAmount);
                return false;
            } else {
                toInvisible(amountMessageLabel);
                return true;
            }
        } else {
            toError(amountMessageLabel, ErrorMessage.amountEmptyErrorMessage);
            return false;
        }
    }

    private boolean checkPriceWithArrow() {
        if (sellRadioButton.isSelected()) {
            return true;
        }
        if (user.getMoneyWelth() < Double.parseDouble(priceTextField.getText()) + 0.1) {
            toError(priceMessageLabel, ErrorMessage.lackOfPrice);
            return false;
        } else {
            toInvisible(priceMessageLabel);
            return true;
        }
        
    }

    @FXML
    private void afterRequestRegistration() {
        int amount = Integer.parseInt(amountTextField.getText());
        double price = Double.parseDouble(priceTextField.getText());

        if (amount == 0) {
            toError(amountMessageLabel, ErrorMessage.amountEmptyErrorMessage);
            return;
        } else toInvisible(amountMessageLabel);

        if (amount > user.getCoinWelthAt(CoinsNameAndIndex.getCoinsNameAndIndexOfName(choseYourCoinMenuButton.getText()).getIndex()) && sellRadioButton.isSelected()) {
            toError(amountMessageLabel, ErrorMessage.lackOfAmount);
            return;
        } else toInvisible(amountMessageLabel);

        if (price * amount > user.getMoneyWelth() && buyRadioButton.isSelected()) {
            toError(priceMessageLabel, ErrorMessage.lackOfPrice);
            return;
        } else toInvisible(priceMessageLabel);

        if (price == 0.0) {
            toError(priceMessageLabel, ErrorMessage.priceEmptyErrorMessage);
            return;
        } else toInvisible(priceMessageLabel);

        if (choseYourCoinMenuButton.getText().equals("Chose your coin")) {
            toError(choseYourCoinMessageLabel, ErrorMessage.choseYourCoinEmptyErrorMessage);
            return;
        } else toInvisible(choseYourCoinMessageLabel);

        if (!buyRadioButton.isSelected() && !sellRadioButton.isSelected()) {
            toError(typeOfExchangeMessageLabel, ErrorMessage.typeOfExchangeDoesNotSelectedErrorMessage);
            return;
        } else toInvisible(typeOfExchangeMessageLabel);
         

        PendingExchange newExchange = null;
        if (buyRadioButton.isSelected()) {
            newExchange = new PendingExchange(user, ExchangeType.BUY, CoinsNameAndIndex.getCoinsNameAndIndexOfName(choseYourCoinMenuButton.getText()), amount, price);
        } 
        else if (sellRadioButton.isSelected()) {
            newExchange = new PendingExchange(user, ExchangeType.SELL, CoinsNameAndIndex.getCoinsNameAndIndexOfName(choseYourCoinMenuButton.getText()), amount, price);
        }

        String dealsResult = Main.book.weldTheDeal(newExchange).getDealsResult();
        dealsResultlabel.setText(dealsResult);

    }

    @FXML
    private void afterHomeMenuItem(ActionEvent event) throws IOException{
        try {
            root = FXMLLoader.load(getClass().getResource("../../FXMLFiles/HomePage.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
}

