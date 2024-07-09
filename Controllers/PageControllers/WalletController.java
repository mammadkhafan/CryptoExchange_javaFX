package Controllers.PageControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import CoinPackage.CoinsOfCSV;
import Controllers.ForAllControllers.PageController;
import MainPackage.CoinsInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class WalletController extends PageController implements Initializable{
    @FXML
    private MenuButton pageMenuButton;

    @FXML
    private GridPane amountOfEachCoinGridPane;

    @FXML
    private Label allMoneyWelth;

    @FXML
    private LineChart<Integer , Double> welthChart;

    private ArrayList<ImageView> images = new ArrayList<>();
    private ArrayList<Label> amounts = new ArrayList<>();

    private final int imageColumn = 0;
    private final int amountColumn = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRows();
        setAllMoneyWelthLabelText();
    }

    private void setAllMoneyWelthLabelText() {
        allMoneyWelth.setText(String.format("%.2d", Double.toString(user.getMoneyWelth())));
    }

    private void setRows() {
        CoinsOfCSV coinsOfCSV = new CoinsOfCSV("C:\\Users\\ASUS\\Desktop\\TermTow\\FinalProject(TermTwo)\\src\\Files\\currency_prices.csv"); 
        CoinsInfo[] allCoins = coinsOfCSV.getAllcoinsAsArray();   
        for (int i = 0; i < allCoins.length; i++) {
            if (user.getCoinWelthAt(i) != 0) {
                addRow(allCoins[i]);
            }
        }
    }

    private void addRow(CoinsInfo coin) {
        amountOfEachCoinGridPane.getRowConstraints().add(new RowConstraints());

        int row = amountOfEachCoinGridPane.getRowCount() - 1;

        amountOfEachCoinGridPane.setVgap(10);

        
        ImageView image = new ImageView();
        image.setImage(coin.getCoinsImage());
        Label amount = new Label(Integer.toString(user.getCoinWelthAt(coin.getCoinsIndex())));


        amountOfEachCoinGridPane.add(image, amountColumn, row);
        amountOfEachCoinGridPane.add(amount, imageColumn, row);

        amounts.add(amount);
        images.add(image);
    }
}
