package at.fhhagenberg.sqe;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import sqelevator.IElevator;

@ExtendWith(ApplicationExtension.class)
public class AppTest {
    private Button button;
    MockBuilding mockBuilding;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     * @throws Exception 
     */
    @Start
    public void start(Stage stage) throws Exception {
    	mockBuilding = new MockBuilding(4,3,500);

        mockBuilding.setServicesFloors(0,0,true);
        mockBuilding.setServicesFloors(0,1,true);
        mockBuilding.setServicesFloors(0,2,true);
        mockBuilding.setServicesFloors(1,0,true);
        mockBuilding.setServicesFloors(1,1,true);
        mockBuilding.setServicesFloors(1,2,true);
        mockBuilding.setServicesFloors(2,0,true);
        mockBuilding.setServicesFloors(2,1,true);
        //for CodeCoverage
        mockBuilding.setServicesFloors(2,2,false);
        
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
        
        var app = new App(mockBuilding);
        app.start(stage);
    }

    /**
     * @param robot - Will be injected by the test runner.
     * @throws InterruptedException 
     */
    @Test
    public void testButtonWithText(FxRobot robot) throws InterruptedException {
        //FxAssert.verifyThat(".button", LabeledMatchers.hasText("Click me!"));
    	TimeUnit.SECONDS.sleep(10);
    }


}