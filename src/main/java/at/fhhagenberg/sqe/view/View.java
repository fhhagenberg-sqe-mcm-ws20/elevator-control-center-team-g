package at.fhhagenberg.sqe.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;

/**
 * <p>View class.</p>
 *
 * @author Simon Bergmaier
 * @version $Id: $Id
 */
public class View {
    @FXML
    private Text actiontarget;

    /**
     * <p>handleSubmitButtonAction.</p>
     *
     * @param event a {@link javafx.event.ActionEvent} object.
     */
    @FXML protected void handleSubmitButtonAction(ActionEvent event) {
        actiontarget.setText("Sign in button pressed");
    }
}
