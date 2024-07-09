package MainPackage;

import java.io.IOException;
import java.util.ArrayList;
import BookPackage.Book;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
    public static Book book = new Book(new ArrayList<User>());
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws IOException {
        // Parent root = FXMLLoader.load(getClass().getResource("/FXMLFiles/LoginPage.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("/FXMLFiles/SignUp.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("/FXMLFiles/ForgetPassword.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("/FXMLFiles/HomePage.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("/FXMLFiles/ExchangePage.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLFiles/SwapPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
