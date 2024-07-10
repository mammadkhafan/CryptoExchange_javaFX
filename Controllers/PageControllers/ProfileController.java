package Controllers.PageControllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import Controllers.ForAllControllers.LabelFlexible;
import Controllers.ForAllControllers.MostHaveInitialize;
import Controllers.ForAllControllers.PageController;
import Controllers.ForAllControllers.SignInMethods;
import MainPackage.ErrorMessage;
import MainPackage.Regex;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class ProfileController extends PageController implements Initializable, MostHaveInitialize, LabelFlexible{
    @FXML
    private MenuButton pagemenuButton;

    @FXML
    private TextField firstName, lastName, username, password, walletId, phoneNumber, email;

    @FXML
    private Label firstNameMessageLabel, lastNameMessageLabel, passwordMessageLabel, phoneNumberMessageLabel, emailMessageLabel;

    @FXML
    private ImageView profileImage;

    @FXML
    private ComboBox<String> countryNumbersComboBox;

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

    private boolean initialized = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        countryNumbersComboBox.getItems().addAll(countryNumbers);
    }

    @Override
    public void initializeWithMouseMove(MouseEvent event) {
        if (!initialized) {
            initialized = true;
            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
            countryNumbersComboBox.setValue(user.getCountryStartingNumber());
            phoneNumber.setText(user.getPhoneNumberWithoutCountry());
            email.setText(user.getEmail());
            password.setText(user.getPassword());
            username.setText(user.getUsername());
            walletId.setText(user.getWalletId());
        }
    }

    @FXML
    private void editFirstName() {
        if (SignInMethods.isValid(firstName.getText(), Regex.nameRegex.regexStr)) {
            user.setFirstName(firstName.getText());
            toCorrect(firstNameMessageLabel);
        } else toError(firstNameMessageLabel, ErrorMessage.nameErrorMessage);
        
    }

    @FXML
    private void editLastName() {
        if (SignInMethods.isValid(firstName.getText(), Regex.nameRegex.regexStr)) {
            user.setFirstName(firstName.getText());
            toCorrect(lastNameMessageLabel);
        } else toError(lastNameMessageLabel, ErrorMessage.nameErrorMessage);
    }

    @FXML
    private void editPhoneNumber() {
        if (SignInMethods.isValid(firstName.getText(), Regex.nameRegex.regexStr)) {
            user.setPhoneNumberWithoutCountry(phoneNumber.getText());
            toCorrect(phoneNumberMessageLabel);
        } else toError(phoneNumberMessageLabel, ErrorMessage.phoneNumberErrorMessage);
    }

    @FXML
    private void editEmail() {
        if (SignInMethods.isValid(email.getText(), Regex.emailRegex.regexStr)) {
            user.setEmail(email.getText());
            toCorrect(emailMessageLabel);
        } else toError(emailMessageLabel, ErrorMessage.emailErrorMessage);
    }

    @FXML
    private void editProfileImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("PNG Files", "*.png")
        );

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            profileImage.setImage(image);
        }
    }

    @FXML
    private void editPassword() {
        if (SignInMethods.isValid(firstName.getText(), Regex.nameRegex.regexStr)) {
            user.setFirstName(firstName.getText());
            toCorrect(passwordMessageLabel);
        } else toError(passwordMessageLabel, ErrorMessage.passwordErrorMessage);
    }
}
