package Controllers.SignInControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import java.io.File;

public class SignUpController extends SignInMethods implements Initializable{
    @FXML
    private Label firstNameMessage, lastNameMessage, usernameMessage, emailMessage, phoneNumberMessage, passwordMessage, repeatPasswordMessage, captchaMessage, profileImageName;

    @FXML
    private TextField firstNameTextField, lastNameTextField, usernameTextField, emailTextField, phoneNumberTextField;

    @FXML
    private PasswordField passwordPasswordField, repeatPasswordPasswordField, captchaCodePasswordField;

    @FXML
    private Button pickFileButton, createMyAccountButton;

    @FXML
    private FontAwesomeIcon backIcon, changeIcon;

    @FXML
    private ImageView captchaImageView, profileImage;

    @FXML
    private ComboBox<String> countryNumbersComboBox;

    private Image captchaImage;

    private String[] countryNumbers = 
    {"Brazil +55", 
    "Costa Rica +506", 
    "Emirates +971", 
    "Fiji +619", 
    "Hong Kong +852", 
    "India +91", 
    "Indonesia +62", 
    "Iran +98", 
    "Iraq +964", 
    "Japan +81", 
    "Jordan +962", 
    "Mexico +52", 
    "North Korea +850", 
    "Oman +968", 
    "Pakistan +92", 
    "Panama +507", 
    "Peru +51", 
    "Philippines +63", 
    "Qatar +974", 
    "South Korea +82", 
    "Turkey +90", 
    "Uabakistan +998", 
    "Uruguay +598", 
    "USA +1", 
    "Vietnam +84", 
    "Yemen +967"};

    private Image[] captchaImages =
    {
    new Image(getClass().getResourceAsStream("../../Image/captchas/captcha1.png")),
    new Image(getClass().getResourceAsStream("../../Image/captchas/captcha2.png")),
    new Image(getClass().getResourceAsStream("../../Image/captchas/captcha3.png")),
    new Image(getClass().getResourceAsStream("../../Image/captchas/captcha4.png")),
    new Image(getClass().getResourceAsStream("../../Image/captchas/captcha5.png")),
    new Image(getClass().getResourceAsStream("../../Image/captchas/captcha6.png")),
    new Image(getClass().getResourceAsStream("../../Image/captchas/captcha7.png")),
    new Image(getClass().getResourceAsStream("../../Image/captchas/captcha8.png")),
    new Image(getClass().getResourceAsStream("../../Image/captchas/captcha9.png")),
    new Image(getClass().getResourceAsStream("../../Image/captchas/captcha10.png")),
    new Image(getClass().getResourceAsStream("../../Image/captchas/captcha11.png")),
    new Image(getClass().getResourceAsStream("../../Image/captchas/captcha12.png")),
    new Image(getClass().getResourceAsStream("../../Image/captchas/captcha13.png")),
    new Image(getClass().getResourceAsStream("../../Image/captchas/captcha14.png")),
    new Image(getClass().getResourceAsStream("../../Image/captchas/captcha15.png"))
    };
    

    private Stage stage;
    private Scene scene;
    private Parent root;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        countryNumbersComboBox.getItems().addAll(countryNumbers);
        changeCaptchaCodeImage();
    }

    public void changeCaptchaCodeImage() {
        int lastIndexOfCaptchase = 10;
        int randomIndex = (int)(Math.random() * (lastIndexOfCaptchase));        
        captchaImage = captchaImages[randomIndex];
        captchaImageView.setImage(captchaImage);
    }

    
    @FXML
    private void checkFirstName(KeyEvent event) {
        check(firstNameTextField, firstNameMessage, Regex.nameRegex, ErrorMessage.nameErrorMessage);
    }

    @FXML
    private void checkUsername(KeyEvent event) {
        check(usernameTextField, usernameMessage, Regex.usernameRegex, ErrorMessage.usernameErrorMessage);
    }

    @FXML
    private void checkPhoneNumber(KeyEvent event) {
        check(phoneNumberTextField, phoneNumberMessage, Regex.phoneNumberRegex, ErrorMessage.phoneNumberErrorMessage);
    }

    @FXML
    private void checkLastName(KeyEvent event) {
        check(lastNameTextField, lastNameMessage, Regex.nameRegex, ErrorMessage.nameErrorMessage);
    }

    @FXML
    private void checkEmail(KeyEvent event) {
        check(emailTextField, emailMessage, Regex.emailRegex, ErrorMessage.emailErrorMessage);    
    }

    @FXML
    private void checkPassword(KeyEvent event) {
        check(passwordPasswordField, passwordMessage, Regex.passwordRegex, ErrorMessage.passwordErrorMessage);
    }

    @FXML
    private void checkCaptchaCode(ActionEvent event) {
        // Implement your logic here
    }

    @FXML
    private void checkRepeatPassword(KeyEvent event) {
        if (!passwordPasswordField.getText().equals("")) {
            if (passwordPasswordField.getText().equals(repeatPasswordPasswordField.getText())) {
                toCorrect(repeatPasswordMessage);
            } else {
                repeatPasswordMessage.setTextFill(orang);
    
                repeatPasswordMessage.setText("Repeat password and password are not the same");
            }
        } else {
            repeatPasswordMessage.setTextFill(orang);

            repeatPasswordMessage.setText("Fill the password fiels FIRST!");
        }
        
    }

    @FXML
    private void afterPickFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("PNG Files", "*.png")
        );

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            profileImage.setImage(image);
            profileImageName.setText(file.getName());
            profileImageName.setTextFill(green);
        }
    }

    @FXML
    private void afterCreateMyAccount(ActionEvent event) {
        Label[] messages = {firstNameMessage, lastNameMessage, usernameMessage, emailMessage, phoneNumberMessage, passwordMessage,  repeatPasswordMessage, captchaMessage, profileImageName};
        if (isEveryThingOk(messages)) {
            System.out.println("wellcom to user pannel");
        }
    }

    @FXML
    private void afterChange() {
        changeCaptchaCodeImage();
    }

    @FXML
    private void afterBack(MouseEvent event) throws IOException {
        // Implement your logic here
        root = FXMLLoader.load(getClass().getResource("../../FXMLFiles/LoginPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
