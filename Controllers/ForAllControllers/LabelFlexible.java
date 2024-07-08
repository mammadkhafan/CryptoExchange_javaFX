package Controllers.ForAllControllers;

import Controllers.ForAllControllers.SignInMethods.ErrorMessage;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public interface LabelFlexible {    
    public default void toError(Label label, ErrorMessage errMsg) {
        Color orang = Color.web("#FFA515");
        label.setTextFill(orang);
        label.setText(errMsg.errorMessage);
    }

    public default void toCorrect(Label label) {
        Color green = Color.web("#03A313");
        label.setTextFill(green);
        label.setText("valid Input");
    }

    public default void toInvisible(Label label) {
        label.setTextFill(Color.BLACK);
        label.setText("");
    }
}
