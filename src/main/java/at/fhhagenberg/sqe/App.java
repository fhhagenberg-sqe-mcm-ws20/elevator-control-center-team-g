package at.fhhagenberg.sqe;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * <p>App class.</p>
 *
 * @author Simon Bergmaier
 * @version $Id: $Id
 */
public class App extends Application {

    private Scene scene = null;
    private TextArea console = null;

    /** {@inheritDoc} */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View.fxml"));

        scene = new Scene(root);

        primaryStage.setTitle("Elevator Control Center");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        console = (TextArea) scene.lookup("#txtConsole");
        
        AddElevator(1);
        AddElevator(2);
        AddElevator(3);
    }
    
    private void AddElevator(int amountFloors) throws Exception {
    	// get the part of the UI that contains all elevators
    	HBox elevators =  (HBox) scene.lookup("#Elevators");
    	
    	// add new elevator to UI
        elevators.getChildren().add(FXMLLoader.load(getClass().getResource("/OneElevator.fxml")));

        scene.getRoot().applyCss();
    	
        // Get number of elevator
        int Elevatornum = elevators.getChildren().size();
        // set correct text to be displayed
        Label lab = (Label) scene.lookup("#LabelElevatorNew");
        lab.setId("LabelElevator"+Elevatornum);
        lab.setText("Elevator " + Elevatornum);
        GridPane gp = (GridPane) scene.lookup("#GridpaneElevatorNew");
        // set ID to correct number
        gp.setId("GridpaneElevator" + Elevatornum);
        ToggleButton tb = (ToggleButton) scene.lookup("#ToggleButtonElevatorNew");
        tb.setId("ToggleButtonElevator"+Elevatornum);
        tb.setText("Automatic");



        ChoiceBox<Integer> cb = (ChoiceBox<Integer>) scene.lookup("#ChoiceBoxElevatorNew");
        cb.setId("ChoiceBoxElevator"+Elevatornum);
        cb.setDisable(true);

        tb.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    tb.setText("Manual");
                    writeToConsole("Set Elevator " + Elevatornum + " to Manual Mode");
                }  else {
                    tb.setText("Automatic");
                    writeToConsole("Set Elevator " + Elevatornum + " to Automatic Mode");
                }
                cb.setDisable(!newValue);
            }
        });

        // set size of grid elems
        gp.getColumnConstraints().get(0).setMinWidth(100);
        gp.getColumnConstraints().get(0).setMaxWidth(100);
        gp.getColumnConstraints().get(1).setMinWidth(100);
        gp.getColumnConstraints().get(1).setMaxWidth(100);
        
        // create floors for this elevator
        for (int i = 0; i < amountFloors; i++) {
        	AddFloorNumber(Elevatornum, i, amountFloors - i);
            cb.getItems().add(amountFloors - i);
        }

        scene.getRoot().applyCss();

        cb.setValue(1);

        cb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ChoiceBox<Integer> cb = (ChoiceBox<Integer>) event.getSource();
                int selected = cb.getValue() - 1;
                try {
                    setElevatorFloor(Elevatornum,selected,false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        setElevatorFloor(Elevatornum,0, false);
    }

    private void setTargetFloor(int elevatornum, int floornum) {
        GridPane gp = (GridPane) scene.lookup("#GridpaneElevator"+elevatornum);
        for(Node floor: gp.getChildren()) {
            if(GridPane.getColumnIndex(floor) != null && GridPane.getColumnIndex(floor) == 1) {
                ((Circle)((StackPane) floor).getChildren().get(0)).setFill(Paint.valueOf(("WHITE")));
            }
        }
        Circle circle = (Circle) scene.lookup("#Indicator"+ elevatornum + "-" + (floornum+1));
        circle.setFill(Paint.valueOf("#ff8c00"));
    }

    private void setElevatorFloor(int elevatornum, int floornum, boolean open) throws Exception {
        GridPane gp = (GridPane) scene.lookup("#GridpaneElevator"+elevatornum);

        if(gp.getRowCount() <= floornum) {
            throw new Exception("Floor doesn't exist!");
        }

        for (Node floor:gp.getChildren()) {
            if(GridPane.getColumnIndex(floor) != null && GridPane.getColumnIndex(floor) == 0) {
                ((ImageView) floor).setImage(null);
            }
        }


        writeToConsole("Elevator " + elevatornum + " moved to floor " + (floornum+1));

        floornum = gp.getRowCount() - floornum - 1;

        ImageView imageView = new ImageView();
        if(open)  imageView.setImage(new Image("/ElevatorOpen.png"));
        else imageView.setImage(new Image("/ElevatorClosed.png"));
        gp.add(imageView,0,floornum);
    }

    private void writeToConsole(String text) {
        console.setText(console.getText() + System.lineSeparator() + text);
    }

    private void AddFloorNumber(int elevatornum, int rownr, int floornum) {
    	// Create circle around floor number

        GridPane gp = (GridPane) scene.lookup("#GridpaneElevator"+elevatornum);

    	Circle c1 = new Circle(40);
    	c1.setId("Indicator"+ elevatornum + "-" + floornum);
        c1.setFill(Paint.valueOf("WHITE"));
        c1.setStrokeWidth(5);
        c1.setStroke(Paint.valueOf("BLACK"));
        c1.setStrokeType(StrokeType.INSIDE);
        //gp.add(c1, 1, rownr);

        StackPane stackPane = new StackPane();

        stackPane.getChildren().add(c1);
        
        GridPane.setHalignment(c1, HPos.CENTER);
        GridPane.setValignment(c1, VPos.CENTER);
        
        // create floor number
        Text txt = new Text(Integer.toString(floornum));
        txt.setFont(Font.font(20));
        //gp.add(txt, 1, rownr);
        stackPane.getChildren().add(txt);
        gp.add(stackPane,1,rownr);

        GridPane.setHalignment(txt, HPos.CENTER);
        GridPane.setValignment(txt, VPos.CENTER);
        
        // set fixed height
        gp.getRowConstraints().add(new RowConstraints(100));
    }
    
}
