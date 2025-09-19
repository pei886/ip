package momo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.Objects;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Momo momo;

    private final Image userImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/DaUser.jpg")));
    private final Image momoImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/DaMomo.jpg")));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Momo instance */
    public void setMomo(Momo m) {
        momo = m;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Momo's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = momo.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMomoDialog(response, momoImage)
        );
        userInput.clear();
    }
}
