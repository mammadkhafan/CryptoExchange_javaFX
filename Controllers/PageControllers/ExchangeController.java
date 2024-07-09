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
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import BookPackage.ExchangeType;
import BookPackage.PendingExchange;
import CoinPackage.CoinsNameAndIndex;
import CoinPackage.CoinsOfCSV;
import Controllers.ForAllControllers.SignInMethods;
import MainPackage.Main;

public class ExchangeController extends SignInMethods implements Initializable{
    @FXML
    private MenuButton pageMenuButton, choseYourCoinMenuButton;

    @FXML
    private MenuItem homeMenuItem;

    @FXML
    private RadioButton buyRadioButton, sellRadioButton;

    @FXML
    private TextField amountTextField, priceTextField;

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
                    // You can add additional actions here if needed
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

        TextFormatter<Double> textFormatterForPriceTextField = new TextFormatter<>(new DoubleStringConverter(), 0.0, doubleFilter);
        priceTextField.setTextFormatter(textFormatterForPriceTextField);
    
    }

    @FXML
    private void increaseAmountValue() {
        int currentAmount = Integer.parseInt(amountTextField.getText());
        amountTextField.setText(String.valueOf(currentAmount + 1));
    }

    @FXML
    private void decreaseAmountValue() {
        int currentAmount = Integer.parseInt(amountTextField.getText());
        if (currentAmount - 1 >= 0) 
            amountTextField.setText(String.valueOf(currentAmount - 1));
    }

    @FXML
    private void increasePriceValue() {
        double currentPrice = Double.parseDouble(priceTextField.getText());
        String formattedPrice = String.format("%.1f", currentPrice + 0.1);
        priceTextField.setText(formattedPrice);
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

    @FXML
    private void openPagesMenuButton() {
        pageMenuButton.show();
    }

    @FXML
    private void afterRequestRegistration() {
        int amount = Integer.parseInt(amountTextField.getText());
        double price = Double.parseDouble(priceTextField.getText());
        boolean sw = true;

        if (amount == 0) {
            toError(amountMessageLabel, ErrorMessage.amountEmptyErrorMessage);
            sw = false;
        } else toInvisible(amountMessageLabel);

        if (price == 0.0) {
            toError(priceMessageLabel, ErrorMessage.priceEmptyErrorMessage);
            sw = false;
        } else toInvisible(priceMessageLabel);

        if (choseYourCoinMenuButton.getText().equals("Chose your coin")) {
            toError(choseYourCoinMessageLabel, ErrorMessage.choseYourCoinEmptyErrorMessage);
            sw = false;
        } else toInvisible(choseYourCoinMessageLabel);

        if (!buyRadioButton.isSelected() && !sellRadioButton.isSelected()) {
            toError(typeOfExchangeMessageLabel, ErrorMessage.typeOfExchangeDoesNotSelectedErrorMessage);
            sw = false;
        } else toInvisible(typeOfExchangeMessageLabel);
         
        if (sw) {
            PendingExchange newExchange = null;
            if (buyRadioButton.isSelected()) {
                newExchange = new PendingExchange(null, ExchangeType.BUY, CoinsNameAndIndex.getCoinsNameAndIndexOfName(choseYourCoinMenuButton.getText()), amount, price);
            } 
            else if (sellRadioButton.isSelected()) {
                newExchange = new PendingExchange(null, ExchangeType.SELL, CoinsNameAndIndex.getCoinsNameAndIndexOfName(choseYourCoinMenuButton.getText()), amount, price);
            }

            String dealsResult = Main.book.weldTheDeal(newExchange).getDealsResult();
            dealsResultlabel.setText(dealsResult);
        }
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

