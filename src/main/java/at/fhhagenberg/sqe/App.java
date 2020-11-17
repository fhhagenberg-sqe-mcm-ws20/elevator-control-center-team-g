package at.fhhagenberg.sqe;

import java.rmi.RemoteException;

import at.fhhagenberg.sqe.model.Building;
import at.fhhagenberg.sqe.model.Elevator;
import at.fhhagenberg.sqe.util.ClockTickChangeException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import sqelevator.IElevator;

/**
 * <p>
 * App class.
 * </p>
 *
 * @author Simon Bergmaier
 * @version $Id: $Id
 */
public class App extends Application {

	private Scene scene = null;
	private TextArea console = null;

	private final ElevatorControlCenter ecc = new ElevatorControlCenter();

	private IElevator mElevatorSystem = null;

	public App(IElevator ElevatorSystem) {
		mElevatorSystem = ElevatorSystem;
	}

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

		ecc.update(mElevatorSystem);
		Building building = ecc.getBuilding();
		InitFromBuilding(building);
		UpdateFromBuilding(building);

//        AddElevator(1);
//        AddElevator(2);
//        AddElevator(3);
//
//        setElevatorFloor(3,1,true);
//        setTargetFloor(2,2);
//        setTargetFloor(1,1);
	}

	private void InitFromBuilding(Building building) throws Exception {
		// Create floor GUI
		for (int i = 0; i < building.getNrOfFloors(); i++) {
			// TODO show floors
		}
		// Create GUI for each elevator
		for (int i = 0; i < building.getNrOfElevators(); i++) {
			Elevator thisElev = building.getElevator(i);
			AddElevator(building.getNrOfFloors());
		}
	}

	private void UpdateFromBuilding(Building building) throws Exception {
		for (int i = 0; i < building.getNrOfElevators(); i++) {
			// get elevator
			Elevator thisElev = building.getElevator(i);
			// Place the Elevator on the correct floor
			setElevatorFloor(i + 1, thisElev.getCurrentFloor(), thisElev.getDoorStatus());
			// make the target floor orange
			setTargetFloor(i + 1, thisElev.getTarget());
			// grey out floors that are not serviced
			for (int j = 0; j < building.getNrOfFloors(); j++) {
				setFloorService(i, j, thisElev.IsServicedFloor(j));
			}
		}

	}

	private void AddElevator(int amountFloors) throws Exception {
		// get the part of the UI that contains all elevators
		HBox elevators = (HBox) scene.lookup("#Elevators");

		// add new elevator to UI
		elevators.getChildren().add(FXMLLoader.load(getClass().getResource("/OneElevator.fxml")));
		scene.getRoot().applyCss();

		// Get number of elevator
		int elevatornum = elevators.getChildren().size();

		// set correct text to be displayed
		Label lab = (Label) scene.lookup("#LabelElevatorNew");
		lab.setId("LabelElevator" + elevatornum);
		lab.setText("Elevator " + elevatornum);

		// set ID to correct number
		GridPane gp = (GridPane) scene.lookup("#GridpaneElevatorNew");
		gp.setId("GridpaneElevator" + elevatornum);

		ToggleButton tb = (ToggleButton) scene.lookup("#ToggleButtonElevatorNew");
		tb.setId("ToggleButtonElevator" + elevatornum);
		tb.setText("Automatic");

		@SuppressWarnings("unchecked")
		ChoiceBox<Integer> cb = (ChoiceBox<Integer>) scene.lookup("#ChoiceBoxElevatorNew");
		cb.setId("ChoiceBoxElevator" + elevatornum);
		cb.setDisable(true);

		// To apply all new IDs to respecting elements
		scene.getRoot().applyCss();

		ObservableList<Integer> cbData = FXCollections.observableArrayList();

		tb.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				tb.setText("Manual");
				writeToConsole("Set Elevator " + elevatornum + " to Manual Mode");
			} else {
				tb.setText("Automatic");
				writeToConsole("Set Elevator " + elevatornum + " to Automatic Mode");
			}
			cb.setDisable(!newValue);
		});

		// set size of grid elements
		gp.getColumnConstraints().get(0).setMinWidth(100);
		gp.getColumnConstraints().get(0).setMaxWidth(100);
		gp.getColumnConstraints().get(1).setMinWidth(100);
		gp.getColumnConstraints().get(1).setMaxWidth(100);

		// create floors for this elevator
		for (int i = 0; i < amountFloors; i++) {
			AddFloorNumber(elevatornum, i, amountFloors - i);
			cbData.add(amountFloors - i);
		}
		cb.setItems(cbData);
		cb.setValue(1);

		cb.setOnAction(event -> {
			@SuppressWarnings("unchecked")
			ChoiceBox<Integer> cb1 = (ChoiceBox<Integer>) event.getSource();

			if (!cb1.isDisabled()) {
				int selected = cb1.getValue() - 1;
				try {
					setElevatorFloor(elevatornum, selected, 2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		setElevatorFloor(elevatornum, 0, 2);
	}

	private void setFloorService(int elevatornum, int floornum, boolean isToBeServiced) {
		Circle circle = (Circle) scene.lookup("#Indicator" + (elevatornum + 1) + "-" + (floornum + 1));
		if (isToBeServiced) {
			circle.setStroke(Paint.valueOf(("BLACK")));
		} else {
			circle.setStroke(Paint.valueOf(("LIGHTGREY")));
		}
	}

	private void setTargetFloor(int elevatornum, int floornum) {
		// return if there is no target floor
		if (floornum == -1) {
			return;
		}

		GridPane gp = (GridPane) scene.lookup("#GridpaneElevator" + elevatornum);
		for (Node floor : gp.getChildren()) {
			if (GridPane.getColumnIndex(floor) != null && GridPane.getColumnIndex(floor) == 1) {
				((Circle) ((StackPane) floor).getChildren().get(0)).setFill(Paint.valueOf(("WHITE")));
			}
		}
		Circle circle = (Circle) scene.lookup("#Indicator" + elevatornum + "-" + (floornum + 1));
		circle.setFill(Paint.valueOf("#ff8c00"));
	}

	private void setElevatorFloor(int elevatornum, int floornum, int doorstatus) throws Exception {
		GridPane gp = (GridPane) scene.lookup("#GridpaneElevator" + elevatornum);

		if (gp.getRowCount() <= floornum) {
			throw new Exception("Floor doesn't exist!");
		}

		for (Node floor : gp.getChildren()) {
			if (GridPane.getColumnIndex(floor) != null && GridPane.getColumnIndex(floor) == 0) {
				((ImageView) floor).setImage(null);
			}
		}

		writeToConsole("Elevator " + elevatornum + " moved to floor " + (floornum + 1));

		ImageView imageView = new ImageView();
		switch (doorstatus) {
		case 1:
			imageView.setImage(new Image("/ElevatorOpen.png"));
			break;
		case 2:
			imageView.setImage(new Image("/ElevatorClosed.png"));
			break;
		case 3:
			// TODO
			break;
		case 4:
			// TODO
			break;
		default:
			break;
		}

		gp.add(imageView, 0, gp.getRowCount() - floornum - 1);

		@SuppressWarnings("unchecked")
		ChoiceBox<Integer> cb = (ChoiceBox<Integer>) scene.lookup("#ChoiceBoxElevator" + elevatornum);
		if (cb.isDisabled())
			cb.setValue(floornum + 1);

		// Align Elevator icon to center of grid
		GridPane.setHalignment(imageView, HPos.CENTER);
		GridPane.setValignment(imageView, VPos.CENTER);
	}

	private void writeToConsole(String text) {
		console.setText(console.getText() + System.lineSeparator() + text);
	}

	private void AddFloorNumber(int elevatornum, int rownum, int floornum) {
		// Create circle around floor number

		GridPane gp = (GridPane) scene.lookup("#GridpaneElevator" + elevatornum);

		Circle c1 = new Circle(40);
		c1.setId("Indicator" + elevatornum + "-" + floornum);
		c1.setFill(Paint.valueOf("WHITE"));
		c1.setStrokeWidth(5);
		c1.setStroke(Paint.valueOf("BLACK"));
		c1.setStrokeType(StrokeType.INSIDE);
		// gp.add(c1, 1, rownr);

		StackPane stackPane = new StackPane();

		stackPane.getChildren().add(c1);

		GridPane.setHalignment(c1, HPos.CENTER);
		GridPane.setValignment(c1, VPos.CENTER);

		// create floor number
		Text txt = new Text(Integer.toString(floornum));
		txt.setFont(Font.font(20));
		// gp.add(txt, 1, rownr);
		stackPane.getChildren().add(txt);
		gp.add(stackPane, 1, rownum);

		GridPane.setHalignment(txt, HPos.CENTER);
		GridPane.setValignment(txt, VPos.CENTER);

		// set fixed height
		gp.getRowConstraints().add(new RowConstraints(100));
	}

}
