package at.fhhagenberg.sqe;

import at.fhhagenberg.sqe.model.Floor;
import at.fhhagenberg.sqe.util.ClockTickChangeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sqelevator.IElevator;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorControlCenterTest {


    ElevatorControlCenter ecc;
    MockBuilding mockBuilding;

    @BeforeEach
    private void setUp() throws Exception{
        ecc = new ElevatorControlCenter();

        mockBuilding = new MockBuilding(3,3,500);

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

        ecc.update(mockBuilding);
    }

    @AfterEach
    private void tearDown() {
        ecc = null;
    }

    @Test
    public void testInit(){
        assertEquals(3, ecc.getBuilding().getNrOfElevators());
        assertEquals(3, ecc.getBuilding().getNrOfFloors());
        assertEquals(500, ecc.getBuilding().getFloorheight());
    }

    @Test
    public void testInitFloorNumbers(){

        Floor fl1 = ecc.getBuilding().getFloor(0);
        assertEquals(0,fl1.getFloorNumber());
        Floor fl2 = ecc.getBuilding().getFloor(1);
        assertEquals(1,fl2.getFloorNumber());
        Floor fl3 = ecc.getBuilding().getFloor(2);
        assertEquals(2,fl3.getFloorNumber());

    }



    @Test
    public void testElevatorCapacity() throws RemoteException, ClockTickChangeException {
        mockBuilding.mElevators[0].mElevatorCapacity = 10;
        ecc.update(mockBuilding);
        assertEquals(10, ecc.getBuilding().getElevator(0).getCapacity());
    }

    @Test
    public void testElevatorTarget() throws RemoteException, ClockTickChangeException {
        mockBuilding.mElevators[0].mTarget = 2;
       ecc.update(mockBuilding);
        assertEquals(2, ecc.getBuilding().getElevator(0).getTarget());
    }

    @Test
    public void testInvalidElevatorTarget() {
        mockBuilding.mElevators[0].mTarget = 10;
        Exception exception  = assertThrows(IllegalArgumentException.class, () -> ecc.update(mockBuilding));

        String expectedMessage = "Target was not valid!";
        assertEquals(expectedMessage, exception.getMessage());
    }


    @Test
    public void testElevatorWeight() throws RemoteException, ClockTickChangeException {
        mockBuilding.mElevators[0].mElevatorWeight = 100;
       ecc.update(mockBuilding);
        assertEquals(100, ecc.getBuilding().getElevator(0).getWeight());
    }
    
    @Test
    public void testIsFloorButtonUpPressed() throws RemoteException, ClockTickChangeException {
    	mockBuilding.mFloors[0].mFloorButtonUP = true;
    	ecc.update(mockBuilding);

        assertTrue(ecc.getBuilding().getFloor(0).isButtonUpPressed());
    }
    
    @Test
    public void testIsFloorButtonDownPressed() throws RemoteException, ClockTickChangeException {
    	mockBuilding.mFloors[0].mFloorButtonDOWN = true;
    	ecc.update(mockBuilding);

        assertTrue(ecc.getBuilding().getFloor(0).isButtonDownPressed());
    }
    
    @Test
    public void testElevatorAddRemoveIsPressedButton() {
    	ecc.getBuilding().getElevator(0).AddPressedButton(1);
        assertTrue(ecc.getBuilding().getElevator(0).IsPressedButton(1));
    	ecc.getBuilding().getElevator(0).RemovePressedButton(1);
        assertFalse(ecc.getBuilding().getElevator(0).IsPressedButton(1));
    }
    
    @Test
    public void testRemoveIsServicedFloor() {
    	ecc.getBuilding().getElevator(0).AddServicedFloor(ecc.getBuilding().getFloor(1));
    	assertTrue(ecc.getBuilding().getElevator(0).IsServicedFloor(ecc.getBuilding().getFloor(1)));
    	ecc.getBuilding().getElevator(0).RemoveServicedFloor(ecc.getBuilding().getFloor(1));
    	assertFalse(ecc.getBuilding().getElevator(0).IsServicedFloor(ecc.getBuilding().getFloor(1)));
    }
    
    @Test
    public void testElevatorGetAcceleration() throws RemoteException, ClockTickChangeException {
    	mockBuilding.mElevators[0].mElevatorAccel = 8;
    	ecc.update(mockBuilding);
    	
        assertEquals(8, ecc.getBuilding().getElevator(0).getAcceleration());
    }
    
    @Test
    public void testElevatorGetDirection() throws RemoteException, ClockTickChangeException {
    	mockBuilding.mElevators[0].mCommittedDirection = IElevator.ELEVATOR_DIRECTION_UP;
    	ecc.update(mockBuilding);
    	
        assertEquals(IElevator.ELEVATOR_DIRECTION_UP, ecc.getBuilding().getElevator(0).getDirection());
    }
    
    @Test
    public void testElevatorGetDoorStatus() throws RemoteException, ClockTickChangeException {
    	mockBuilding.mElevators[0].mElevatorDoorStatus = IElevator.ELEVATOR_DOORS_OPEN;
    	ecc.update(mockBuilding);
    	
        assertEquals(IElevator.ELEVATOR_DOORS_OPEN, ecc.getBuilding().getElevator(0).getDoorStatus());
    }
    
    @Test
    public void testElevatorGetCurrentFloor() throws RemoteException, ClockTickChangeException {
    	mockBuilding.mElevators[0].mElevatorFloor = 1;
    	ecc.update(mockBuilding);
    	
        assertEquals(1, ecc.getBuilding().getElevator(0).getCurrentFloor());
    }
    
    @Test
    public void testElevatorGetPositionFeet() throws RemoteException, ClockTickChangeException {
    	mockBuilding.mElevators[0].mElevatorPosition = 14;
    	ecc.update(mockBuilding);
    	
        assertEquals(14, ecc.getBuilding().getElevator(0).getPositionFeet());
    }
    
    @Test
    public void testElevatorGetSpeed() throws RemoteException, ClockTickChangeException {
    	mockBuilding.mElevators[0].mElevatorSpeed = 8;
    	ecc.update(mockBuilding);
    	
        assertEquals(8, ecc.getBuilding().getElevator(0).getSpeed());
    }


}
