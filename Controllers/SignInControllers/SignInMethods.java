package Controllers.SignInControllers;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SignInMethods {
    protected Stage stage;
    protected Scene scene;
    protected Parent root;

    protected Image captchaImage;

    protected Image[] captchaImages =
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

    protected String[] captchaCodes =
    {
        "3EEkyrP", 
        "3wr7H76",
        "5ZVw4dS",
        "75NP2DW",
        "F5nGuKm",
        "8t3Umzg",
        "A8t6HbV",
        "awf8pCB",
        "B2V7KLp",
        "blC5CIN",
        "buPWUnT",
        "bybNmeZ",
        "dTaFKWK",
        "EW8dS9H",
        "3dIHDds"
    };

    public enum Regex {
        emailRegex("^[a-zA-Z]{1,1}[a-zA-Z0-9-_.]{4,63}@[a-zA-Z]+\\.[a-zA-z]+$"),
        usernameRegex("[a-zA-Z]{1,1}[a-zA-Z0-9_]{2,12}"),
        passwordRegex("^[a-zA-Z0-9]{5,15}$"),
        nameRegex("^[a-zA-Z ]{2,20}$"),
        phoneNumberRegex("^[0-9]{4,10}$");

        public String regexStr;

        Regex(String regex) {
            this.regexStr = regex;
        }
    }

    public enum ErrorMessage {
        emailErrorMessage("valid chars: a-Z(one at least) 0-9 - _ ."),
        usernameErrorMessage("valid chars: (start with)a-Z 0-9 _"),
        passwordErrorMessage("valid chars: a-Z 0-9 (length: min 6-max 15)"),
        nameErrorMessage("valid chars: a-Z space"),
        phoneNumberErrorMessage("valid chars: 0-9 (length: min 4-max 10)"),
        captchaErrorMessage("your Input doesn't mach");

        public String errorMessage;

        ErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

    public boolean isValid(String input, String regex){
        return input.matches(regex);
    }

    public void check(TextField textField, Label label, Regex reg, ErrorMessage errMsg) {
        String input = textField.getText();
        String regex = reg.regexStr;
        if(!isValid(input, regex)){
            toError(label, errMsg);
        } else if (isValid(input, regex)) {
            toCorrect(label);
        }
    }

    public void checkCaptcha(PasswordField captchoPasswordField, Label label, String realCode, ErrorMessage errMsg) {
        if (captchoPasswordField.getText().equals(realCode)) {
            toCorrect(label);
        } else {
            toError(label, errMsg);
        }
    }

    public boolean isEveryThingOk(Label[] messages) {
        boolean isEveryThingOk = true;
        for (int i = 0; i < messages.length; i++) {
            if (messages[i] == null || !messages[i].getTextFill().equals(green)) {
                isEveryThingOk = false;
                break;
            }
        }

        if (!isEveryThingOk) {
            findErrors(messages);
        }

        return isEveryThingOk;
    }

    public void findErrors(Label[] messages) {
        for (int i = 0; i < messages.length; i++) {
            if (messages[i] != null && messages[i].getTextFill().equals(green)) {
                //Do nothing
            } else {
                if (messages[i] == null) {
                    messages[i] = new Label();
                }
                messages[i].setText("* Fill this field correctlly");
                messages[i].setTextFill(red);
            }
        }
    }
    //-------------------------------------------------
    protected Color green = Color.web("#03A313");
    protected Color red = Color.web("#FF6347");
    protected Color orang = Color.web("#FFA515");
    public void toError(Label label, ErrorMessage errMsg) {
        label.setTextFill(orang);

        label.setText(errMsg.errorMessage);
    }

    public void toCorrect(Label label) {
        label.setTextFill(green);

        label.setText("valid Input");
    }
}
