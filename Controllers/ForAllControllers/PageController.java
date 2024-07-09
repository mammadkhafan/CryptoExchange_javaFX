package Controllers.ForAllControllers;

import java.io.IOException;

import MainPackage.Main;
import MainPackage.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;

public class PageController {
    protected User user;

    protected FXMLLoader loader;
    protected Stage stage;
    protected Scene scene;
    protected Parent root;
    protected PageController controller;


    private void changeScene(ActionEvent event, String path) throws IOException {
        loader = new FXMLLoader(getClass().getResource(path));
        root = loader.load();
        controller = loader.getController();
        controller.setUser(user);

        if (event.getSource() instanceof Node) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        } else {
            stage = Main.getPrimaryStage();
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    

    @FXML
    protected MenuButton pageMenuButton;
    
    @FXML
    protected void openPagesMenuButton() {
        pageMenuButton.show();
    }

    @FXML
    protected void homeMenuItemPressed(ActionEvent event) throws IOException {
        changeScene(event, "../../FXMLFiles/HomePage.fxml");
    }

    @FXML
    protected void exchangeMenuItemPressed(ActionEvent event) throws IOException {
        changeScene(event, "../../FXMLFiles/ExchangePage.fxml");
    }

    @FXML
    protected void swapMenuItemPressed(ActionEvent event) throws IOException {
        changeScene(event, "../../FXMLFiles/SwapPage.fxml");
    }

    @FXML
    protected void historyMenuItemPressed(ActionEvent event) throws IOException {
        changeScene(event, "../../FXMLFiles/HistoryPage.fxml");
    }

    @FXML
    protected void tokenMenuItemPressed(ActionEvent event) throws IOException {
        changeScene(event, "../../FXMLFiles/TokenPage.fxml");
    }

    @FXML
    protected void trensferMenuItemPressed(ActionEvent event) throws IOException {
        changeScene(event, "../../FXMLFiles/TransferPage.fxml");
    }

    @FXML
    protected void walletMenuItemPressed(ActionEvent event) throws IOException {
        changeScene(event, "../../FXMLFiles/WalletPage.fxml");
    }

    @FXML
    protected void profileMenuItemPressed(ActionEvent event) throws IOException {
        changeScene(event, "../../FXMLFiles/ProfilePage.fxml");
    }


    protected User getUser() {
        return this.user;
    }

    protected void setUser(User user) {
        this.user = user;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
