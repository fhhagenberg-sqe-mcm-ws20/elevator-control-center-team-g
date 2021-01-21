package at.fhhagenberg.sqe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import sqelevator.IElevator;

@ExtendWith(ApplicationExtension.class)
public class AppTest {
	private Button button;
	MockBuilding mockBuilding;
	App app;

	/**
	 * Will be called with {@code @Before} semantics, i. e. before each test method.
	 *
	 * @param stage - Will be injected by the test runner.
	 * @throws Exception
	 */
	@Start
	public void start(Stage stage) throws Exception {
		mockBuilding = new MockBuilding(4, 3, 500);

		mockBuilding.setServicesFloors(0, 0, true);
		mockBuilding.setServicesFloors(0, 1, true);
		mockBuilding.setServicesFloors(0, 2, true);
		mockBuilding.setServicesFloors(1, 0, true);
		mockBuilding.setServicesFloors(1, 1, true);
		mockBuilding.setServicesFloors(1, 2, true);
		mockBuilding.setServicesFloors(2, 0, true);
		mockBuilding.setServicesFloors(2, 1, true);
		mockBuilding.setServicesFloors(3, 0, true);
		mockBuilding.setServicesFloors(3, 1, true);
		mockBuilding.setServicesFloors(3, 2, true);
		// for CodeCoverage
		mockBuilding.setServicesFloors(2, 2, false);

		mockBuilding.mElevators[0].mElevatorFloor = 2;
		mockBuilding.mElevators[0].mElevatorDoorStatus = IElevator.ELEVATOR_DOORS_OPEN;

		mockBuilding.mElevators[2].mElevatorDoorStatus = IElevator.ELEVATOR_DOORS_OPENING;
		mockBuilding.mElevators[3].mElevatorDoorStatus = IElevator.ELEVATOR_DOORS_CLOSING;

		mockBuilding.mElevators[1].mTarget = 1;
		mockBuilding.mElevators[1].mCommittedDirection = IElevator.ELEVATOR_DIRECTION_UP;

		mockBuilding.mElevators[2].mCommittedDirection = IElevator.ELEVATOR_DIRECTION_DOWN;

		mockBuilding.mFloors[0].mFloorButtonUP = true;
		mockBuilding.mFloors[1].mFloorButtonDOWN = true;
		mockBuilding.mFloors[2].mFloorButtonUP = true;

		mockBuilding.mElevators[0].MockFloorButtonPressed[1] = true;

		mockBuilding.mElevators[0].mElevatorWeight = 4711;
		mockBuilding.mElevators[0].mElevatorSpeed = 817;
		mockBuilding.mElevators[0].mElevatorAccel = 4;

		app = new App(mockBuilding);
		app.start(stage);
	}
	
//	@AfterEach
//    public void CleanupThreads(){
//        System.out.println("After Each CleanupThreads() method called");
//        app.Shutdown();
//    }

	/**
	 * @param robot - Will be injected by the test runner.
	 * @throws InterruptedException
	 */
	@Test
	public void testButtonWithText(FxRobot robot) throws InterruptedException {
		TimeUnit.SECONDS.sleep(10);
		app.getTextFromConsole();
	}

	/**
	 * @param robot - Will be injected by the test runner.
	 * @throws InterruptedException
	 */
	@Test
	public void testButtonAutoToManual(FxRobot robot) throws InterruptedException {
		// check that it has the initial state
		FxAssert.verifyThat("#ToggleButtonElevator1", LabeledMatchers.hasText("Automatic"));

		// click on toggle button
		robot.clickOn("#ToggleButtonElevator1");

		// check that the togglebutton has changed
		FxAssert.verifyThat("#ToggleButtonElevator1", LabeledMatchers.hasText("Manual"));
	}

	/**
	 * @param robot - Will be injected by the test runner.
	 * @throws InterruptedException
	 */
	@Test
	public void testButtonAutoToManualToAuto(FxRobot robot) throws InterruptedException {
		// check that it has the initial state
		FxAssert.verifyThat("#ToggleButtonElevator1", LabeledMatchers.hasText("Automatic"));

		// click on toggle button
		robot.clickOn("#ToggleButtonElevator1");

		// check that the togglebutton has changed
		FxAssert.verifyThat("#ToggleButtonElevator1", LabeledMatchers.hasText("Manual"));

		// click on toggle button
		robot.clickOn("#ToggleButtonElevator1");

		// check that the togglebutton has changed
		FxAssert.verifyThat("#ToggleButtonElevator1", LabeledMatchers.hasText("Automatic"));
	}

	/**
	 * @param robot - Will be injected by the test runner.
	 * @throws InterruptedException
	 */
	@Test
	public void testManuallySetFloorWithChoicebox(FxRobot robot) throws InterruptedException {
		// check that target is set to a floor thats not what we want to set it to
		assertNotEquals(1, mockBuilding.mElevators[0].mTarget);
		
		// check that it has the initial state
		FxAssert.verifyThat("#ToggleButtonElevator1", LabeledMatchers.hasText("Automatic"));

		// click on toggle button
		robot.clickOn("#ToggleButtonElevator1");

		// check that the togglebutton has changed
		FxAssert.verifyThat("#ToggleButtonElevator1", LabeledMatchers.hasText("Manual"));

		// click on a floor
		robot.clickOn("#ChoiceBoxElevator1").clickOn(LabeledMatchers.hasText("2"));

		// check that it has been set in the backend
		assertEquals(1, mockBuilding.mElevators[0].mTarget);
	}
	
	/**
	 * @param robot - Will be injected by the test runner.
	 * @throws InterruptedException
	 */
	@Test
	public void testCommittedDirectionUp(FxRobot robot) throws InterruptedException {
		// check that it has the initial state
		FxAssert.verifyThat("#ToggleButtonElevator4", LabeledMatchers.hasText("Automatic"));

		// click on toggle button
		robot.clickOn("#ToggleButtonElevator4");

		// check that the togglebutton has changed
		FxAssert.verifyThat("#ToggleButtonElevator4", LabeledMatchers.hasText("Manual"));

		// click on a floor
		robot.clickOn("#ChoiceBoxElevator4").clickOn(LabeledMatchers.hasText("2"));

		// wait for update
		TimeUnit.SECONDS.sleep(2);
	}
	
	/**
	 * @param robot - Will be injected by the test runner.
	 * @throws InterruptedException
	 */
	@Test
	public void testCommittedDirectionDown(FxRobot robot) throws InterruptedException {
		mockBuilding.mElevators[3].mElevatorFloor = 1;
		
		// wait for update
		TimeUnit.SECONDS.sleep(2);
		
		// check that it has the initial state
		FxAssert.verifyThat("#ToggleButtonElevator4", LabeledMatchers.hasText("Automatic"));

		// click on toggle button
		robot.clickOn("#ToggleButtonElevator4");

		// check that the togglebutton has changed
		FxAssert.verifyThat("#ToggleButtonElevator4", LabeledMatchers.hasText("Manual"));

		// click on a floor
		robot.clickOn("#ChoiceBoxElevator4").clickOn(LabeledMatchers.hasText("1"));

		// wait for update
		TimeUnit.SECONDS.sleep(2);
	}
	
	/**
	 * @param robot - Will be injected by the test runner.
	 * @throws InterruptedException
	 */
	@Test
	public void testCommittedDirectionUncom(FxRobot robot) throws InterruptedException {
		mockBuilding.mElevators[3].mElevatorFloor = 1;
		
		// wait for update
		TimeUnit.SECONDS.sleep(2);
		
		// check that it has the initial state
		FxAssert.verifyThat("#ToggleButtonElevator4", LabeledMatchers.hasText("Automatic"));

		// click on toggle button
		robot.clickOn("#ToggleButtonElevator4");

		// check that the togglebutton has changed
		FxAssert.verifyThat("#ToggleButtonElevator4", LabeledMatchers.hasText("Manual"));

		// click on a floor
		robot.clickOn("#ChoiceBoxElevator4").clickOn(LabeledMatchers.hasText("2"));

		// wait for update
		TimeUnit.SECONDS.sleep(2);
	}

	/**
	 * @param robot - Will be injected by the test runner.
	 * @throws InterruptedException
	 */
	@Test
	public void testSetSpeedUpdate(FxRobot robot) throws InterruptedException {
		// check that it has the initial state
		FxAssert.verifyThat("#Elevator4Stats", LabeledMatchers.hasText("0" + "f/s|" + "0" + "f/s^2|" + "0" + "lbs"));

		// change value in backend
		mockBuilding.mElevators[3].mElevatorSpeed = 4711;
		// wait for update
		TimeUnit.SECONDS.sleep(2);

		// check that it has changed
		FxAssert.verifyThat("#Elevator4Stats", LabeledMatchers.hasText("4711" + "f/s|" + "0" + "f/s^2|" + "0" + "lbs"));
	}

}