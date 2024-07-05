package Controllers.SignInControllers;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class SignInMethods {

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
        phoneNumberErrorMessage("valid chars: 0-9 (length: min 4-max 10)");

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
