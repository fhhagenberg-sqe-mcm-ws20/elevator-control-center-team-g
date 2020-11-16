package at.fhhagenberg.sqe;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;

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

        Scene scene = new Scene(root);

        primaryStage.setTitle("FXML Welcome");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        HBox elevators =  (HBox) scene.lookup("#Elevators");
        elevators.getChildren().add(FXMLLoader.load(getClass().getResource("/OneElevator.fxml")));
        
        VBox vbox = (VBox) elevators.getChildren().get(0);
        ScrollPane scroll = (ScrollPane) vbox.getChildren().get(1);
        GridPane gp1 = (GridPane) scroll.getContent();
        
        //GridPane gp1 = (GridPane) elevators.getChildren().get(0).lookup("#GridpaneElevator1");
   
        gp1.getColumnConstraints().get(0).setMinWidth(100);
        gp1.getColumnConstraints().get(0).setMaxWidth(100);
        gp1.getColumnConstraints().get(1).setMinWidth(100);
        gp1.getColumnConstraints().get(1).setMaxWidth(100);
        
        for (int i = 0; i < 10; i++) {
        	AddFloorNumber(gp1, i);
		}
        
    }
    
    void AddFloorNumber(GridPane gp, int rownr) {
    	Circle c1 = new Circle(40);
        c1.setFill(Paint.valueOf("WHITE"));
        c1.setStrokeWidth(5);
        c1.setStroke(Paint.valueOf("BLACK"));
        c1.setStrokeType(StrokeType.INSIDE);
        
        gp.add(c1, 1, rownr);
        
        GridPane.setHalignment(c1, HPos.CENTER);
        GridPane.setValignment(c1, VPos.CENTER);
        
        Text txt = new Text("0");
        txt.setFont(Font.font(20));
        
        gp.add(txt, 1, rownr);
        
        GridPane.setHalignment(txt, HPos.CENTER);
        GridPane.setValignment(txt, VPos.CENTER);
        
        gp.getRowConstraints().add(new RowConstraints(100));
        
       // gp.getRowConstraints().get(rownr).setMinHeight(100);
        //gp.getRowConstraints().get(rownr).setMaxHeight(100);
    }
    
}
