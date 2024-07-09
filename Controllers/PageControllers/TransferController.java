package Controllers.PageControllers;

import java.net.URL;
import java.util.ResourceBundle;
import CoinPackage.CoinsNameAndIndex;
import CoinPackage.CoinsOfCSV;
import Controllers.ForAllControllers.LabelFlexible;
import Controllers.ForAllControllers.PageController;
import MainPackage.ErrorMessage;
import MainPackage.Main;
import MainPackage.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.DoubleStringConverter;
import java.util.function.UnaryOperator;

public class TransferController extends PageController implements Initializable, LabelFlexible{
    @FXML
    private MenuButton pagMenuButton, coinsNameMenuButton;

    @FXML
    private TextField walletIdTextField, amountTextField, priceTextField;

    @FXML
    private Label searchsResultIdLabel, amountMessageLabel, priceMessageLabel, transferMessageLabel;

    private User destinationUser;

    private CoinsOfCSV coinsOfCSV;
    private MenuItem[] menuItemsOfchoseYourCoin;
    private boolean charge = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        coinsOfCSV = new CoinsOfCSV("C:\\Users\\ASUS\\Desktop\\TermTow\\FinalProject(TermTwo)\\src\\Files\\currency_prices.csv");
        try {
            menuItemsOfchoseYourCoin = new MenuItem[coinsOfCSV.getAllCoins().size()];
            for (int i = 0; i < coinsOfCSV.getAllCoins().size(); i++) {
                menuItemsOfchoseYourCoin[i] = new MenuItem(coinsOfCSV.getAllCoins().get(i).getName());
                MenuItem menuItem = menuItemsOfchoseYourCoin[i];
                menuItem.setOnAction(event -> {
                    coinsNameMenuButton.setText(menuItem.getText()); // Update the button text
                });
            }
            coinsNameMenuButton.getItems().addAll(menuItemsOfchoseYourCoin);
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

    @FXML
    private void afterSearchButton() {
        
        String walletId = walletIdTextField.getText();
        User foundUser = Main.book.getUserWithWalletId(walletId);

        if (foundUser == null) {
            destinationUser = null;
            searchsResultIdLabel.setText("user not found");
            charge = false;
        } else if (foundUser.equals(user)) {
            destinationUser = null;
            searchsResultIdLabel.setText("you are charging your wallet");
            charge = true;
        } else {
            destinationUser = foundUser;
            searchsResultIdLabel.setText("user's username: " + destinationUser.getUsername());
            charge = false;
        }
        
    }

    private boolean checkAmountWithArrow() {
        if (!coinsNameMenuButton.getText().equals("Coins name")) {
            if (user.getCoinWelthAt(CoinsNameAndIndex.getCoinsNameAndIndexOfName(coinsNameMenuButton.getText()).getIndex()) < Integer.parseInt(amountTextField.getText()) + 1) {
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
        if (!charge) {
            if (user.getMoneyWelth() < Double.parseDouble(priceTextField.getText()) + 0.1) {
                toError(priceMessageLabel, ErrorMessage.lackOfPrice);
                return false;
            } else {
                toInvisible(priceMessageLabel);
                return true;
            }
        }
        return true;
        
    }

    @FXML
    private boolean checkAmount() {
        if (!coinsNameMenuButton.getText().equals("Coins name")) {
            if (user.getCoinWelthAt(CoinsNameAndIndex.getCoinsNameAndIndexOfName(coinsNameMenuButton.getText()).getIndex()) < Integer.parseInt(amountTextField.getText())) {
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

    @FXML
    private boolean checkPrice() {
        if (!charge) {
            if (user.getMoneyWelth() < Double.parseDouble(priceTextField.getText())) {
                toError(priceMessageLabel, ErrorMessage.lackOfPrice);
                return false;
            } else {
                toInvisible(priceMessageLabel);
                return true;
            }
        }
        return true;
    }

    @FXML 
    private void afterTransfer() {
        if (searchsResultIdLabel.getText().contains("not found")) {
            transferMessageLabel.setText("set the correct wallet id for destinatoin wallet");
            return;
        }
        else if (coinsNameMenuButton.getText().equals("Coins name") && amountTextField.getText().equals("0")) {
            transferMessageLabel.setText("set Coins name");
            return;
        }
        else if (!checkPrice()) {
            transferMessageLabel.setText("lack of money");
            return;
        }
        else if (!checkAmount()) {
            transferMessageLabel.setText("lack of coin");
            return;
        }
        if (charge) {
            user.increaseMoneyWelth(Double.parseDouble(priceTextField.getText()));
            return;
        }

        transferMessageLabel.setText("Done!");
        destinationUser.increaseMoneyWelth(Double.parseDouble(priceTextField.getText()));
        destinationUser.increseCoinWelthAt(CoinsNameAndIndex.getCoinsNameAndIndexOfName(coinsNameMenuButton.getText()).getIndex()
        , Integer.parseInt(amountTextField.getText()));

        user.decreaseMoneyWelth(Double.parseDouble(priceTextField.getText()));
        user.decreaseCoinWelthAt(CoinsNameAndIndex.getCoinsNameAndIndexOfName(coinsNameMenuButton.getText()).getIndex()
        , Integer.parseInt(amountTextField.getText()));

    }

}
