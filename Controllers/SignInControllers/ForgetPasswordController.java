package Controllers.SignInControllers;

import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
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
    private boolean isSend = false;
    
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    public void checkEmail() {
        String email = emailTextField.getText();
        // email = "msharifrazavianm@gmail.com";
        if (!email.isEmpty()) {
            code = generateRandomCode();
            sendForgotPasswordEmail(email, code);
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
        if (newPasswordPasswordField.getText().equals(repeatPasswordPasswordField.getText())) {
            toCorrect(repeatPasswordMessage);
        } else {
            Color red = Color.web("#FF6347");
            repeatPasswordMessage.setTextFill(red);

            repeatPasswordMessage.setText("Repeat password and password are not the same");
        }
    }

    @FXML
    public void afterSubmit() {
        String inputCode = codePasswordField.getText();
        if (inputCode.equals(code)) {
            toCorrect(codeMessage);
        } else {
            Color red = Color.web("#FF6347");
            codeMessage.setTextFill(red);

            codeMessage.setText("Not correct verificatoin code");
        }
    }

    @FXML
    public void afterLogin() {
        Label[] messages = 
                {emailMessage, 
                codeMessage, 
                newPasswordMessage, 
                repeatPasswordMessage};
        if (isEveryThingOk(messages)) {
            System.out.println("wellcom to user pannel");
        }
    }

    @FXML
    public void afterBack(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../../FXMLFiles/loginPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private String generateRandomCode() {
        return String.valueOf((int) (Math.random() * 9000) + 1000);
    }


    public static void sendForgotPasswordEmail(String recipientEmail, String newPassword) {
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

            System.out.println("Forgot password email sent successfully");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
