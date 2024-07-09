package Controllers.SignInControllers;

import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import Controllers.ForAllControllers.SignInMethods;
import MainPackage.ErrorMessage;
import MainPackage.Main;
import MainPackage.Regex;
import MainPackage.User;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.time.LocalDateTime;
public class ForgetPasswordController extends SignInMethods{
    @FXML
    private Label 
            emailMessage, 
            codeMessage, 
            newPasswordMessage, 
            repeatPasswordMessage;

    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField 
            codePasswordField, 
            newPasswordPasswordField, 
            repeatPasswordPasswordField;
    @FXML 
    private Button submitButton, loginButton;
    
    @FXML
    private FontAwesomeIcon backIcon;

    private String code;
    private int lastSecondEmailSent = -1;
    private boolean codeEntered = false;

    @FXML
    public void checkEmail() {
        String email = emailTextField.getText();
        int nowSecond = LocalDateTime.now().getSecond();
        if ((nowSecond >= lastSecondEmailSent && nowSecond < 60) || (nowSecond < lastSecondEmailSent)) {
            toError(emailMessage, ErrorMessage.emailSentInLastMinuteErrorMessage);
        }
        else if (Main.book.findUserWithEmail(email)) {
            toInvisible(emailMessage);
            code = generateRandomCode();
            sendForgotPasswordEmail(email, code);
        }
        else {
            toError(emailMessage, ErrorMessage.emailNotFoundErrorMessage);
        }
    }

    @FXML
    public void checkCode() {
        afterSubmit();
    }

    @FXML
    public void checkNewPassword() {
        check(newPasswordPasswordField, newPasswordMessage, Regex.passwordRegex, ErrorMessage.passwordErrorMessage);
    }

    @FXML
    public void checkRepeatPassword() {
        if (newPasswordPasswordField.getText().equals(repeatPasswordPasswordField.getText()) && !newPasswordPasswordField.getText().isEmpty()) {
            toCorrect(repeatPasswordMessage);
        } else if (!repeatPasswordPasswordField.getText().isEmpty()){
            Color red = Color.web("#FF6347");
            repeatPasswordMessage.setTextFill(red);
            repeatPasswordMessage.setText("Repeat password and password are not the same");
        }
    }

    @FXML
    private void checkCodeEnteredBeforSetNewPassword() {
        if (!codeEntered) {
            toError(codeMessage, ErrorMessage.interCodeBeforeSetPassword);
            newPasswordPasswordField.setEditable(false);
            repeatPasswordPasswordField.setEditable(false);
        } else {
            newPasswordPasswordField.setEditable(true);
            repeatPasswordPasswordField.setEditable(true);
        }
    }

    @FXML
    public void afterSubmit() {
        String inputCode = codePasswordField.getText();
        if (inputCode.equals(code)) {
            toCorrect(codeMessage);
            codeEntered = true;
            emailTextField.setDisable(true);
        } else {
            Color red = Color.web("#FF6347");
            codeMessage.setTextFill(red);
            codeMessage.setText("Not correct verificatoin code");
            codeEntered = false;
        }
    }

    @FXML
    public void afterLogin(ActionEvent event) throws IOException {
        Label[] messages = 
                {emailMessage, 
                codeMessage, 
                newPasswordMessage, 
                repeatPasswordMessage};
        if (isEveryThingOk(messages)) {
            User user = Main.book.getUserWithEmail(emailTextField.getText());
            user.setPassword(repeatPasswordPasswordField.getText());
            changeScene(event, "../../FXMLFiles/HomePage.fxml", user.getUsername());
        }
    }

    @FXML
    public void afterBack(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../../FXMLFiles/LoginPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private String generateRandomCode() {
        return String.valueOf((int) (Math.random() * 9000) + 1000);
    }


    public void sendForgotPasswordEmail(String recipientEmail, String newPassword) {
        final String username = "mywallet.exchanger@gmail.com";
        final String password = "licmwgwyuunjovnz";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail));
            message.setSubject("Forgot Password Recovery");
            message.setText("Your new password is: " + newPassword);

            Transport.send(message);

            toCorrect(emailMessage);

        } catch (MessagingException e) {
            emailMessage.setTextFill(orang);

            emailMessage.setText(e.getMessage());
        }
    }
}
