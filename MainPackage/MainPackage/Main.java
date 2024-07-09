package MainPackage;

import java.io.IOException;
import java.util.ArrayList;
import BookPackage.Book;
import BookPackage.ComplitedExchange;
import BookPackage.ExchangeType;
import BookPackage.PendingExchange;
import CoinPackage.CoinsNameAndIndex;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
    public static Book book = new Book(new ArrayList<User>());
    private static Stage primaryStage;
    public static void main(String[] args) {
        //HARD CODE
        User user = new User("mohammad mahdi", "sharif razavian", "mammad", "sharifrazavianm@gmail.com", "+989028021906", "13045949", null);
        book.addUser(user);
        user.increseCoinWelthAt(CoinsNameAndIndex.USD.getIndex(), 5);
        book.addToPendingExchanges(new PendingExchange(user, ExchangeType.BUY, CoinsNameAndIndex.USD, 15, 1.05));
        book.addToComplitedExchanges(new ComplitedExchange(user, user, CoinsNameAndIndex.EUR, 5, 4.5));
        //--------
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLFiles/LoginPage.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("/FXMLFiles/SignUp.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("/FXMLFiles/ForgetPassword.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("/FXMLFiles/HomePage.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("/FXMLFiles/ExchangePage.fxml"));
        // // Parent root = FXMLLoader.load(getClass().getResource("/FXMLFiles/SwapPage.fxml"));
        // // Parent root = FXMLLoader.load(getClass().getResource("/FXMLFiles/WalletPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
