package Controllers.ForAllControllers;


import java.io.IOException;
import MainPackage.ErrorMessage;
import MainPackage.Main;
import MainPackage.Regex;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SignInMethods implements LabelFlexible{
    protected FXMLLoader loader;
    protected PageController controller;
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


    public void changeScene(ActionEvent event, String path, String username) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        root = loader.load();
        controller = loader.getController();
        controller.setUser(Main.book.getUserWithUsername(username));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        controller.setStage(stage);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static boolean isValid(String input, String regex){
        return input.matches(regex);
    }

    public void check(TextField textField, Label label, Regex reg, ErrorMessage errMsg) {
        String input = textField.getText();
        String regex = reg.regexStr;
        if(!isValid(input, regex) && !label.getText().isEmpty()){
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

    //------------------------------
    protected Color green = Color.web("#03A313");
    protected Color red = Color.web("#FF6347");
    protected Color orang = Color.web("#FFA515");
}
