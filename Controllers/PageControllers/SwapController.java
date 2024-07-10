package Controllers.PageControllers;

import java.net.URL;
import java.util.ResourceBundle;
import CoinPackage.CoinsNameAndIndex;
import CoinPackage.CoinsOfCSV;
import Controllers.ForAllControllers.LabelFlexible;
import Controllers.ForAllControllers.PageController;
import MainPackage.ErrorMessage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;

public class SwapController extends PageController implements Initializable, LabelFlexible{
    @FXML
    private MenuButton pageMenuButton, originMenuButton, destinationMenuButton;

    @FXML 
    private TextField amountTextField, destinationTextField;

    @FXML 
    private Label originAndDestinationMessageLabel, amountOfSwapingLabel, swapResultLabel;

    @FXML
    private Button swapItButton;

    private CoinsOfCSV coinsOfCSV;
    private MenuItem[] menuItemsOfOrigin;
    private MenuItem[] menuItemsOfDestination;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        swapItButton.setDisable(true);
        coinsOfCSV = new CoinsOfCSV("C:\\Users\\ASUS\\Desktop\\TermTow\\FinalProject(TermTwo)\\src\\Files\\currency_prices.csv");


        menuItemsOfOrigin = new MenuItem[coinsOfCSV.getAllCoins().size()];
        for (int i = 0; i < coinsOfCSV.getAllCoins().size(); i++) {
            menuItemsOfOrigin[i] = new MenuItem(coinsOfCSV.getAllCoins().get(i).getName());
            MenuItem menuItem = menuItemsOfOrigin[i];
            menuItem.setOnAction(event -> {
                originMenuButton.setText(menuItem.getText()); // Update the button text
                disableTheSameMenuItemInDestination();
                convertAmount();
            });
        }
        originMenuButton.getItems().addAll(menuItemsOfOrigin);


        menuItemsOfDestination = new MenuItem[coinsOfCSV.getAllCoins().size()];
        for (int i = 0; i < coinsOfCSV.getAllCoins().size(); i++) {
            menuItemsOfDestination[i] = new MenuItem(coinsOfCSV.getAllCoins().get(i).getName());
            MenuItem menuItem = menuItemsOfDestination[i];
            menuItem.setOnAction(event -> {
                destinationMenuButton.setText(menuItem.getText()); // Update the button text
                disableTheSameMenuItemInOrigin();
                convertAmount();
            });
        }
        destinationMenuButton.getItems().addAll(menuItemsOfDestination);

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

    }

    private void disableTheSameMenuItemInDestination() {
        String selectedOrigin = originMenuButton.getText();
        for (int i = 0; i < menuItemsOfDestination.length; i++) {
            if (menuItemsOfDestination[i].getText().equals(selectedOrigin)) {
                menuItemsOfDestination[i].setDisable(true);
            } else {
                menuItemsOfDestination[i].setDisable(false);
            }
        }
    }
    
    private void disableTheSameMenuItemInOrigin() {
        String selectedDestination = destinationMenuButton.getText();
        for (int i = 0; i < menuItemsOfOrigin.length; i++) {
            if (menuItemsOfOrigin[i].getText().equals(selectedDestination)) {
                menuItemsOfOrigin[i].setDisable(true);
            } else {
                menuItemsOfOrigin[i].setDisable(false);
            }
        }
    }

    @FXML
    private void increaseAmountValue() {
        if (originMenuButton.getText().equals("Origin") || destinationMenuButton.getText().equals("Destination")) {
            toError(originAndDestinationMessageLabel, ErrorMessage.selectOriginAndDestinationErrorMessage);
            swapItButton.setDisable(true);
        }
        else if 
        (user.getCoinWelthAt(CoinsNameAndIndex.getCoinsNameAndIndexOfName(originMenuButton.getText()).getIndex()) - 1
        < Double.parseDouble(amountTextField.getText()) ) 
        {
            toError(originAndDestinationMessageLabel, ErrorMessage.lackOfAmount);
        } 
        else {
            swapItButton.setDisable(false);
            toInvisible(originAndDestinationMessageLabel);
            int currentAmount = Integer.parseInt(amountTextField.getText());
            amountTextField.setText(String.valueOf(currentAmount + 1));
            convertAmount();
        }
    }

    @FXML
    private void decreaseAmountValue() {
        if (originMenuButton.getText().equals("Origin") || destinationMenuButton.getText().equals("Destination")) {
            toError(originAndDestinationMessageLabel, ErrorMessage.selectOriginAndDestinationErrorMessage);
            swapItButton.setDisable(true);
        } else {
            swapItButton.setDisable(false);
            toInvisible(originAndDestinationMessageLabel);
            int currentAmount = Integer.parseInt(amountTextField.getText());
            if (currentAmount - 1 >= 0) {
                amountTextField.setText(String.valueOf(currentAmount - 1));
            }
            convertAmount();
        }
    }

    @FXML
    private void convertAmount() {
        if (!(amountTextField.getText().equals("0") || originMenuButton.getText().equals("Origin") || destinationMenuButton.getText().equals("Destination"))) {
            int originAmount = Integer.parseInt(amountTextField.getText());
            String originCoinsName = originMenuButton.getText();
            String destinationCoinsName = destinationMenuButton.getText();

            double swapingPrice = originAmount * coinsOfCSV.getPriceOfCoin(coinsOfCSV.getCurrentTimesRawOfCSVFile(), CoinsNameAndIndex.getCoinsNameAndIndexOfName(originCoinsName));
            double destinatoinDoubleAmount = originAmount * (coinsOfCSV.getPriceOfCoin(coinsOfCSV.getCurrentTimesRawOfCSVFile(), CoinsNameAndIndex.getCoinsNameAndIndexOfName(destinationCoinsName)) / coinsOfCSV.getPriceOfCoin(coinsOfCSV.getCurrentTimesRawOfCSVFile(), CoinsNameAndIndex.getCoinsNameAndIndexOfName(originCoinsName)));
            int destinatoinIntAmount = (int) destinatoinDoubleAmount;
            double floatPart = (double) (destinatoinDoubleAmount - destinatoinIntAmount);

            destinationTextField.setText(destinatoinIntAmount + " " + destinationCoinsName +" and " + floatPart);
            amountOfSwapingLabel.setText(String.format("%.2f", swapingPrice) + " is in swap");
        }
        
    }


    @FXML
    private void afterSwapIt() {
        if (!(amountTextField.getText().equals("0") || originMenuButton.getText().equals("Origin") || destinationMenuButton.getText().equals("Destination"))) {
            int originAmount = Integer.parseInt(amountTextField.getText());
            String originCoinsName = originMenuButton.getText();
            String destinationCoinsName = destinationMenuButton.getText();

            double destinatoinDoubleAmount = originAmount * (coinsOfCSV.getPriceOfCoin(coinsOfCSV.getCurrentTimesRawOfCSVFile(), CoinsNameAndIndex.getCoinsNameAndIndexOfName(destinationCoinsName)) / coinsOfCSV.getPriceOfCoin(coinsOfCSV.getCurrentTimesRawOfCSVFile(), CoinsNameAndIndex.getCoinsNameAndIndexOfName(originCoinsName)));
            int destinatoinIntAmount = (int) destinatoinDoubleAmount;

            user.increseCoinWelthAt(CoinsNameAndIndex.getCoinsNameAndIndexOfIndex(destinatoinIntAmount).getIndex(), destinatoinIntAmount);
            user.decreaseCoinWelthAt(CoinsNameAndIndex.getCoinsNameAndIndexOfIndex(originAmount).getIndex(), originAmount);
            swapItButton.setText("Done!");
        } else {
            swapResultLabel.setText("check these: you set the origin amount on 0 of origin or destinatoin haven't sat");
        }
    }
}
