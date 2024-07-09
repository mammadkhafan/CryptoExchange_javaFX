package Controllers.SignInControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Controllers.ForAllControllers.SignInMethods;
import MainPackage.ErrorMessage;
import MainPackage.Main;
import MainPackage.Regex;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class LoginController extends SignInMethods implements Initializable{
    @FXML
    private Button  loginButton, signUpButton, forgetPasswordButton;
    
    @FXML
    private PasswordField  passwordPasswordField, captchaCodePasswordField;

    @FXML
    private Label   usernameMessage, passwordMessage, captchaCodeMessage, loginMessageLabel;

    @FXML
    private TextField usernameTextField; 

    @FXML
    private ImageView captchaImageView;

    private int randomIndex;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeCaptchaCodeImage();
    }

    @FXML
    private void checkUsername(KeyEvent event) {
        check(usernameTextField, usernameMessage, Regex.usernameRegex, ErrorMessage.usernameErrorMessage);
    }

    @FXML
    private void checkPassword(KeyEvent event) {
        check(passwordPasswordField, passwordMessage, Regex.passwordRegex, ErrorMessage.passwordErrorMessage);
    }

    @FXML
    private void checkCaptchaCode(ActionEvent event) {
        checkCaptcha(captchaCodePasswordField, captchaCodeMessage, captchaCodes[randomIndex], ErrorMessage.captchaErrorMessage);
    }

    @FXML
    public void afterLogin(ActionEvent event) throws IOException{
        Label[] messages = {usernameMessage, passwordMessage, captchaCodeMessage};
        if (isEveryThingOk(messages)) {
            if (Main.book.findUserWithUsernameAndPassword(usernameTextField.getText(), passwordPasswordField.getText())) {
                changeScene(event, "../../FXMLFiles/HomePage.fxml", usernameTextField.getText());
            } else {
                loginMessageLabel.setText("Account didn't found");
            }
        }
    }

    @FXML
    public void afterSignUp(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../../FXMLFiles/SignUp.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void afterForgetPassword(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("../../FXMLFiles/ForgetPassword.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void afterChange() {
        changeCaptchaCodeImage();
    }

    public void changeCaptchaCodeImage() {
        randomIndex = (int)(Math.random() * (captchaImages.length));        
        captchaImage = captchaImages[randomIndex];
        captchaImageView.setImage(captchaImage);
    }
}

