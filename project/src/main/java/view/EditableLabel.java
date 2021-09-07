package view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;


public class EditableLabel extends VBox {

    Label label = new Label();
    TextField textField = new TextField();
    String backup;
    BooleanProperty tfFocused = new SimpleBooleanProperty(false);

    public EditableLabel() {
        getChildren().add(label);
        configActions();
        tfFocused.bind(textField.focusedProperty());
    }

    private void configActions() {

        label.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                getChildren().clear();
                getChildren().add(textField);
                textField.setText(backup = label.getText());
                textField.requestFocus();
            }
        });

        textField.focusedProperty().addListener((obs, newVal, oldVal) -> {
            if(newVal) {
                toLabel();
            }
        });

        textField.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                toLabel();
            }

            if (event.getCode().equals(KeyCode.ESCAPE)) {
                getChildren().clear();
                getChildren().add(label);
                label.setText(backup);
            }
        });
    }

    private void toLabel() {
        getChildren().clear();
        getChildren().add(label);
        label.setText(textField.getText());
    }

}
