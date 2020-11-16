package at.fhhagenberg.sqe;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
        
        AddElevator(scene, 1);
        AddElevator(scene, 2);
        AddElevator(scene, 3);
   
       
        
        

        
    }
    
    void AddElevator(Scene scene, int floornum) throws IOException {
    	// get the part of the UI that contains all elevators
    	HBox elevators =  (HBox) scene.lookup("#Elevators");
    	
    	// add new elevator to UI
        elevators.getChildren().add(FXMLLoader.load(getClass().getResource("/OneElevator.fxml")));
    	
        // Get number of elevator
        int Elevatornum = elevators.getChildren().size();
        // get the vbox of this elevator
        VBox vbox = (VBox) elevators.getChildren().get(Elevatornum - 1);
        // set correct text to be displayed
        Label lab = (Label) vbox.getChildren().get(0);
        lab.setText("Elevator " + Elevatornum);
        // get gridpane for this elevator
        ScrollPane scroll = (ScrollPane) vbox.getChildren().get(1);
        GridPane gp1 = (GridPane) scroll.getContent();
        // set ID to correct number
        gp1.setId("GridpaneElevator" + Elevatornum);
    	
        // set size of grid elems
        gp1.getColumnConstraints().get(0).setMinWidth(100);
        gp1.getColumnConstraints().get(0).setMaxWidth(100);
        gp1.getColumnConstraints().get(1).setMinWidth(100);
        gp1.getColumnConstraints().get(1).setMaxWidth(100);
        
        // create floors for this elevator
        for (int i = 0; i < floornum; i++) {
        	AddFloorNumber(gp1, i, floornum - i);
		}
    }
    
    void AddFloorNumber(GridPane gp, int rownr, int floornum) {
    	// Create circle around floor number
    	Circle c1 = new Circle(40);
        c1.setFill(Paint.valueOf("WHITE"));
        c1.setStrokeWidth(5);
        c1.setStroke(Paint.valueOf("BLACK"));
        c1.setStrokeType(StrokeType.INSIDE);
        gp.add(c1, 1, rownr);
        
        GridPane.setHalignment(c1, HPos.CENTER);
        GridPane.setValignment(c1, VPos.CENTER);
        
        // create floor number
        Text txt = new Text(Integer.toString(floornum));
        txt.setFont(Font.font(20));
        gp.add(txt, 1, rownr);
        GridPane.setHalignment(txt, HPos.CENTER);
        GridPane.setValignment(txt, VPos.CENTER);
        
        // set fixed height
        gp.getRowConstraints().add(new RowConstraints(100));
    }
    
}
