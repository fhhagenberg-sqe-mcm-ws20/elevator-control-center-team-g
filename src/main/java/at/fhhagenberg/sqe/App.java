package at.fhhagenberg.sqe;

import at.fhhagenberg.sqe.model.Building;
import at.fhhagenberg.sqe.model.Elevator;
import at.fhhagenberg.sqe.model.Floor;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sqelevator.IElevator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

	private IElevator mElevatorSystem;

	private final String OurOrange = "#ff8c00";

	/**
	 * <p>Constructor for App.</p>
	 *
	 * @param ElevatorSystem a {@link sqelevator.IElevator} object.
	 */
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

	/**
	 * <p>InitFromBuilding.</p>
	 *
	 * @param building a {@link at.fhhagenberg.sqe.model.Building} object.
	 * @throws java.lang.Exception if any.
	 */
	public void InitFromBuilding(Building building) throws Exception {
		// Create floor GUI
		AddFloors(building.getNrOfFloors());
		// Create GUI for each elevator
		for (int i = 0; i < building.getNrOfElevators(); i++) {
			Elevator thisElev = building.getElevator(i);
			AddElevator(building.getNrOfFloors());
		}
	}

	/**
	 * <p>UpdateFromBuilding.</p>
	 *
	 * @param building a {@link at.fhhagenberg.sqe.model.Building} object.
	 * @throws java.lang.Exception if any.
	 */
	public void UpdateFromBuilding(Building building) throws Exception {
		// update all elevators
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
			// show comitted direction of elevator
			ShowComittedDirection(i+1, thisElev.getDirection());
			// show speed and acceleration and weight and capacity of elevator
			ShowElevatorStats(i+1, thisElev.getSpeed(), thisElev.getWeight(), thisElev.getCapacity(), thisElev.getAcceleration());
			// show which buttons in this elevator are pressed
			ShowElevatorButtonsPressed(i+1, thisElev.GetPressedButtons(), thisElev.GetServicedFloors());
		}

		// update all Floors
		for (int j = 0; j < building.getNrOfFloors(); j++) {
			// show which buttons are pressed on which floors
			setButtonUpColor(j, building.getFloor(j).isButtonUpPressed());
			setButtonDownColor(j, building.getFloor(j).isButtonDownPressed());
		}
	}
	
	
	private void ShowElevatorButtonsPressed(int elevatornr, HashSet<Integer> PressedButtons, HashSet<Floor> ServicedFloors){
		for (Floor fl : ServicedFloors) {
			if(PressedButtons.contains(fl.getFloorNumber())) {
				Circle circle = (Circle) scene.lookup("#Indicator" + elevatornr + "-" + (fl.getFloorNumber()+1));
				circle.setStroke(Paint.valueOf("LIGHTGREEN"));
			}	
		}
	}
	
	
	private void ShowElevatorStats(int elevatornr, int speed, int weight, int capacity, int acceleration){
		// get label
		Label stats = (Label) scene.lookup("#Elevator" + elevatornr + "Stats");
		// set updated data
		stats.setText(speed + "f/s|" + acceleration + "f/s^2|" + weight + "lbs");
	}

	/**
	 * <p>ShowComittedDirection.</p>
	 *
	 * @param elevatornr a int.
	 * @param direction a int.
	 */
	private void ShowComittedDirection(int elevatornr, int direction) {
		// get the arrows
		SVGPath dirdown = (SVGPath) scene.lookup("#Elevator" + elevatornr + "DirDown");
		SVGPath dirup = (SVGPath) scene.lookup("#Elevator" + elevatornr + "DirUp");
		
		// color arrows according to committed direction of the elevator
		switch (direction) {
		case IElevator.ELEVATOR_DIRECTION_UNCOMMITTED:
			dirdown.setFill(Paint.valueOf("LIGHTGREY"));
			dirup.setFill(Paint.valueOf("LIGHTGREY"));
			break;
		case IElevator.ELEVATOR_DIRECTION_UP:
			dirdown.setFill(Paint.valueOf("LIGHTGREY"));
			dirup.setFill(Paint.valueOf(OurOrange));
			break;
		case IElevator.ELEVATOR_DIRECTION_DOWN:
			dirdown.setFill(Paint.valueOf(OurOrange));
			dirup.setFill(Paint.valueOf("LIGHTGREY"));
			break;
		default:
			writeToConsole("DEBUG ERROR: Invalid value in direction of elevator");
			break;
		}
	}

	/**
	 * <p>setButtonUpColor.</p>
	 *
	 * @param floornr a int.
	 * @param isPressed a {@link java.lang.Boolean} object.
	 */
	private void setButtonUpColor(int floornr, Boolean isPressed) {
		SVGPath arrow = (SVGPath) scene.lookup("#ArrowUp" + floornr);
		if (isPressed) {
			arrow.setFill(Paint.valueOf(OurOrange));
		} else {
			arrow.setFill(Paint.valueOf("LIGHTGREY"));
		}
	}

	/**
	 * <p>setButtonDownColor.</p>
	 *
	 * @param floornr a int.
	 * @param isPressed a {@link java.lang.Boolean} object.
	 */
	private void setButtonDownColor(int floornr, Boolean isPressed) {
		SVGPath arrow = (SVGPath) scene.lookup("#ArrowDown" + floornr);
		if (isPressed) {
			arrow.setFill(Paint.valueOf(OurOrange));
		} else {
			arrow.setFill(Paint.valueOf("LIGHTGREY"));
		}
	}

	/**
	 * <p>AddFloors.</p>
	 *
	 * @param NrOfFloors a int.
	 */
	private void AddFloors(int NrOfFloors) {
		// get the part of the UI that contains all floors
		VBox Floors = (VBox) scene.lookup("#Floors");

		// add Floors
		for (int i = 0; i < NrOfFloors; i++) {
			// Calc number of floor (floors have to be added with highest first)
			int floornum = NrOfFloors - i - 1;

			// Get gridpane that should show floor numbers
			GridPane gp = (GridPane) scene.lookup("#GridpaneFloors");

			// Draw circle for Floor number
			Circle c1 = new Circle(40);
			c1.setId("IndicatorFloor" + (floornum + 1));
			c1.setFill(Paint.valueOf("WHITE"));
			c1.setStrokeWidth(5);
			c1.setStroke(Paint.valueOf("BLACK"));
			c1.setStrokeType(StrokeType.INSIDE);
			// new Stackpane for centering floor number and circle
			StackPane stackPane = new StackPane();
			stackPane.getChildren().add(c1);
			GridPane.setHalignment(c1, HPos.CENTER);
			GridPane.setValignment(c1, VPos.CENTER);

			// create floor number
			Text txt = new Text(Integer.toString(floornum + 1));
			txt.setFont(Font.font(20));
			stackPane.getChildren().add(txt);
			// add stackpane to correct element of gridpane
			gp.add(stackPane, 1, i);
			// center
			GridPane.setHalignment(txt, HPos.CENTER);
			GridPane.setValignment(txt, VPos.CENTER);
			// set fixed height
			gp.getRowConstraints().add(new RowConstraints(100));

			// draw arrow up
			SVGPath ArrowUp = new SVGPath();
			ArrowUp.setId("ArrowUp" + floornum);
			ArrowUp.setContent("M45 0 L21 60 L66 60 Z");
			ArrowUp.setFill(Paint.valueOf("LIGHTGREY"));
			ArrowUp.setStroke(Paint.valueOf("BLACK"));
			// draw arrow down
			SVGPath ArrowDown = new SVGPath();
			ArrowDown.setId("ArrowDown" + floornum);
			ArrowDown.setContent("M45 0 L21 60 L66 60 Z");
			ArrowDown.setFill(Paint.valueOf("LIGHTGREY"));
			ArrowDown.setStroke(Paint.valueOf("BLACK"));
			ArrowDown.setRotate(180);
			// create new hbox for arrows
			HBox arrows = new HBox();
			// add arrows to hbox
			arrows.getChildren().add(ArrowUp);
			arrows.getChildren().add(ArrowDown);
			// add hbox to gridplane
			gp.add(arrows, 0, i);
			// align arrows to center of available space
			arrows.setAlignment(Pos.CENTER);

			ArrowDown.setOnMouseClicked(event -> ArrowDown.setFill(Paint.valueOf("#ff8c00")));
		}
		// refresh UI
		scene.getRoot().applyCss();
	}

	/**
	 * <p>AddElevator.</p>
	 *
	 * @param amountFloors a int.
	 * @throws java.lang.Exception if any.
	 */
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

		// set IDs to correct number
		GridPane gp = (GridPane) scene.lookup("#GridpaneElevatorNew");
		gp.setId("GridpaneElevator" + elevatornum);

		ToggleButton tb = (ToggleButton) scene.lookup("#ToggleButtonElevatorNew");
		tb.setId("ToggleButtonElevator" + elevatornum);
		tb.setText("Automatic");

		@SuppressWarnings("unchecked")
		ChoiceBox<Integer> cb = (ChoiceBox<Integer>) scene.lookup("#ChoiceBoxElevatorNew");
		cb.setId("ChoiceBoxElevator" + elevatornum);
		cb.setDisable(true);
		
		SVGPath dirUp = (SVGPath) scene.lookup("#ElevatorDirUpNew");
		dirUp.setId("Elevator" + elevatornum + "DirUp");
		
		SVGPath dirDown = (SVGPath) scene.lookup("#ElevatorDirDownNew");
		dirDown.setId("Elevator" + elevatornum + "DirDown");
		
		Label stats = (Label) scene.lookup("#ElevatorStatsNew");
		stats.setId("Elevator" + elevatornum + "Stats");

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

	/**
	 * <p>setFloorService.</p>
	 *
	 * @param elevatornum a int.
	 * @param floornum a int.
	 * @param isToBeServiced a boolean.
	 */
	private void setFloorService(int elevatornum, int floornum, boolean isToBeServiced) {
		Circle circle = (Circle) scene.lookup("#Indicator" + (elevatornum + 1) + "-" + (floornum + 1));
		if (isToBeServiced) {
			circle.setStroke(Paint.valueOf(("BLACK")));
		} else {
			circle.setStroke(Paint.valueOf(("LIGHTGREY")));
		}
	}

	/**
	 * <p>setTargetFloor.</p>
	 *
	 * @param elevatornum a int.
	 * @param floornum a int.
	 */
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
		circle.setFill(Paint.valueOf(OurOrange));
	}

	/**
	 * <p>setElevatorFloor.</p>
	 *
	 * @param elevatornum a int.
	 * @param floornum a int.
	 * @param doorstatus a int.
	 * @throws java.lang.Exception if any.
	 */
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
		case IElevator.ELEVATOR_DOORS_OPEN:
			imageView.setImage(new Image("/ElevatorOpen.png"));
			break;
		case IElevator.ELEVATOR_DOORS_CLOSED:
			imageView.setImage(new Image("/ElevatorClosed.png"));
			break;
		case IElevator.ELEVATOR_DOORS_OPENING:
			imageView.setImage(new Image("/ElevatorOpening.png"));
			break;
		case IElevator.ELEVATOR_DOORS_CLOSING:
			imageView.setImage(new Image("/ElevatorClosing.png"));
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

	/**
	 * <p>writeToConsole.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 */
	private void writeToConsole(String text) {
		console.setText(console.getText() + System.lineSeparator() + text);
	}

	/**
	 * <p>AddFloorNumber.</p>
	 *
	 * @param elevatornum a int.
	 * @param rownum a int.
	 * @param floornum a int.
	 */
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
