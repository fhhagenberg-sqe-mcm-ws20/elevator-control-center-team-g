package at.fhhagenberg.sqe;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

/**
 * <p>App class.</p>
 *
 * @author Simon Bergmaier
 * @version $Id: $Id
 */
public class App extends Application {


    /** {@inheritDoc} */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View.fxml"));

        Scene scene = new Scene(root, 300,275);

        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
