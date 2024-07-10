package Controllers.PageControllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import CoinPackage.CoinsOfCSV;
import Controllers.ForAllControllers.MostHaveInitialize;
import Controllers.ForAllControllers.PageController;
import MainPackage.CoinsInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class WalletController extends PageController implements MostHaveInitialize, Initializable{
    @FXML
    private MenuButton pageMenuButton;

    @FXML
    private GridPane amountOfEachCoinGridPane;

    @FXML
    private Label allMoneyWelth;


    // private CategoryAxis xAxis = new CategoryAxis();
    // private NumberAxis yAxis = new NumberAxis();

    @FXML
    private BarChart<String, Number> barChart;

    

    private ArrayList<ImageView> images = new ArrayList<>();
    private ArrayList<Label> amounts = new ArrayList<>();

    private final int imageColumn = 0;
    private final int amountColumn = 1;

    private boolean initialized = false;

    @Override
    public void initializeWithMouseMove(MouseEvent event) {
        if (!initialized) {
            initialized = true;
            setRows();
            setAllMoneyWelthLabelText();
            setAllMoneyWelthLabelText();
            // Initialize the BarChart with data
            
            XYChart.Series<String, Number> series1 = new XYChart.Series<>();
            series1.setName("Series 1");

            // Add data to the series
            series1.getData().add(new XYChart.Data<>(Integer.toString(LocalDateTime.now().getHour() + 1), user.getMoneyWelth() + 3.2));

            // Add the series to the BarChart
            barChart.getData().add(series1);
        }
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    private void setAllMoneyWelthLabelText() {
        allMoneyWelth.setText(String.format("%.2f", user.getMoneyWelth()));
    }

    private void setRows() {
        CoinsOfCSV coinsOfCSV = new CoinsOfCSV("C:\\Users\\ASUS\\Desktop\\TermTow\\FinalProject(TermTwo)\\src\\Files\\currency_prices.csv"); 
        CoinsInfo[] allCoins = coinsOfCSV.getAllcoinsAsArray();   
        for (CoinsInfo coin : allCoins) {
            if (user.getCoinWelthAt(coin.getCoinsIndex()) != 0) {
                addRow(coin);
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

        amountOfEachCoinGridPane.add(image, imageColumn, row);
        amountOfEachCoinGridPane.add(amount, amountColumn, row);

        amounts.add(amount);
        images.add(image);
    }

    

    
}
