package at.fhhagenberg.sqe;


import javafx.concurrent.Task;
import javafx.stage.Stage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import sqelevator.IElevator;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

@ExtendWith(ApplicationExtension.class)
public class AppRemoteExceptionTest {

    MockBuildingWithRemoteException mockBuilding;
    App app;

    @Start
    public void start(Stage stage) throws Exception {

        mockBuilding = new MockBuildingWithRemoteException(4, 3, 500);

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
    
    @AfterEach
    public void CleanupThreads(){
        System.out.println("After Each CleanupThreads() method called");
        app.Shutdown();
    }

    @Test
    public void testInitialConnect() throws InterruptedException {
        App testApp = new App();

        app.isMock = false;

        Task<IElevator> task = new Task<IElevator>() {
            @Override
            protected IElevator call() throws Exception {
                return app.Connect();
            }
        };

        task.setOnSucceeded( e -> {

        });
        Thread th = new Thread(task);
        th.start();


        TimeUnit.SECONDS.sleep(2);
        app.isMock = true;
        
        th.stop();
    }

    @Test
    @Disabled("Causes other tests to fail")
    public void testReconnect() throws InterruptedException {
        // check that it has the initial state
        FxAssert.verifyThat("#Elevator4Stats", LabeledMatchers.hasText("0" + "f/s|" + "0" + "f/s^2|" + "0" + "lbs"));

        mockBuilding.throwExceptionsOn();

        // change value in backend
        mockBuilding.mElevators[3].mElevatorSpeed = 4711;

        // wait for update
        TimeUnit.SECONDS.sleep(2);

        assertThat(app.getTextFromConsole(), containsString("Connection to Elevator lost, trying to reconnect"));

        mockBuilding.throwExceptionsOff();

        TimeUnit.SECONDS.sleep(2);

        FxAssert.verifyThat("#Elevator4Stats", LabeledMatchers.hasText("0" + "f/s|" + "0" + "f/s^2|" + "0" + "lbs"));

    }
}
