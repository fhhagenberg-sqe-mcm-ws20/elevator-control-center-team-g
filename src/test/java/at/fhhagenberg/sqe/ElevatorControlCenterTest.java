package at.fhhagenberg.sqe;

import at.fhhagenberg.sqe.model.Floor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorControlCenterTest {

    private final int NROFELEVATORS = 5;
    private final int NROFFLOORS = 5;
    private final int FLOORHEIGHT = 500;
    private final int RETURNSUCCESS = 0;
    private final int ERRORREMOTEEXCEPTION = -1;
    private final int ERRORTICKCHANGE = -2;

    ElevatorControlCenter ecc;
    MockBuilding mockBuilding;

    @BeforeEach
    private void setUp() {
        ecc = new ElevatorControlCenter();

        mockBuilding = new MockBuilding(NROFELEVATORS,NROFFLOORS,FLOORHEIGHT);

        try {
            for(int i = 0; i < NROFELEVATORS; i++) {
                for(int j = 0; j < NROFFLOORS - 1; j++) {
                    mockBuilding.setServicesFloors(i,j,true);
                }
            }
        } catch(RemoteException re) {
            re.printStackTrace();
        }



        assertEquals(RETURNSUCCESS, ecc.update(mockBuilding));
    }

    @AfterEach
    private void tearDown() {
        ecc = null;
    }

    @Test
    public void testInit(){
        assertEquals(NROFELEVATORS, ecc.getBuilding().getNrOfElevators());
        assertEquals(NROFFLOORS, ecc.getBuilding().getNrOfFloors());
        assertEquals(FLOORHEIGHT, ecc.getBuilding().getFloorheight());
    }

    @Test
    public void testInitFloorNumbers(){
        for (int i = 0; i < ecc.getBuilding().getNrOfFloors(); i++) {
            Floor fl = ecc.getBuilding().getFloor(i);
            assertEquals(i,fl.getFloorNumber());
        }
    }

    @Test
    public void testClockTickChange() {
        mockBuilding.toggleClockTickShouldAdvance();
        assertEquals(ERRORTICKCHANGE, ecc.update(mockBuilding));
        mockBuilding.toggleClockTickShouldAdvance();
    }

    @Test
    public void testRemoteException() {
        mockBuilding.toggleShouldThrowRemoteException();
        assertEquals(ERRORREMOTEEXCEPTION, ecc.update(mockBuilding));
        mockBuilding.toggleShouldThrowRemoteException();
    }

    @Test
    public void testElevatorCapacity(){
        mockBuilding.mElevators[0].mElevatorCapacity = 10;
        assertEquals(RETURNSUCCESS, ecc.update(mockBuilding));
        assertEquals(10, ecc.getBuilding().getElevator(0).getCapacity());
    }

    @Test
    public void testElevatorTarget(){
        mockBuilding.mElevators[0].mTarget = 3;
        assertEquals(RETURNSUCCESS, ecc.update(mockBuilding));
        assertEquals(3, ecc.getBuilding().getElevator(0).getTarget());
    }

    @Test
    public void testInvalidElevatorTarget() {
        mockBuilding.mElevators[0].mTarget = 10;
        Exception exception  = assertThrows(IllegalArgumentException.class, () -> ecc.update(mockBuilding));

        String expectedMessage = "Target was not valid!";
        assertEquals(expectedMessage, exception.getMessage());
    }


    @Test
    public void testElevatorWeight() {
        mockBuilding.mElevators[0].mElevatorWeight = 100;
        assertEquals(RETURNSUCCESS, ecc.update(mockBuilding));
        assertEquals(100, ecc.getBuilding().getElevator(0).getWeight());
    }
    
    @Test
    public void testIsFloorButtonUpPressed() {
    	mockBuilding.mFloors[0].mFloorButtonUP = true;
    	assertEquals(RETURNSUCCESS, ecc.update(mockBuilding));

        assertTrue(ecc.getBuilding().getFloor(0).isButtonUpPressed());
    }
    
    @Test
    public void testIsFloorButtonDownPressed() {
    	mockBuilding.mFloors[0].mFloorButtonDOWN = true;
    	assertEquals(RETURNSUCCESS, ecc.update(mockBuilding));

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
    public void testElevatorGetAcceleration() {
    	mockBuilding.mElevators[0].mElevatorAccel = 8;
    	assertEquals(RETURNSUCCESS, ecc.update(mockBuilding));
    	
        assertEquals(8, ecc.getBuilding().getElevator(0).getAcceleration());
    }
    
    @Test
    public void testElevatorGetDirection() {
    	mockBuilding.mElevators[0].mCommittedDirection = IElevator.ELEVATOR_DIRECTION_UP;
    	assertEquals(RETURNSUCCESS, ecc.update(mockBuilding));
    	
        assertEquals(IElevator.ELEVATOR_DIRECTION_UP, ecc.getBuilding().getElevator(0).getDirection());
    }
    
    @Test
    public void testElevatorGetDoorStatus() {
    	mockBuilding.mElevators[0].mElevatorDoorStatus = IElevator.ELEVATOR_DOORS_OPEN;
    	assertEquals(RETURNSUCCESS, ecc.update(mockBuilding));
    	
        assertEquals(IElevator.ELEVATOR_DOORS_OPEN, ecc.getBuilding().getElevator(0).getDoorStatus());
    }
    
    @Test
    public void testElevatorGetCurrentFloor() {
    	mockBuilding.mElevators[0].mElevatorFloor = 1;
    	assertEquals(RETURNSUCCESS, ecc.update(mockBuilding));
    	
        assertEquals(1, ecc.getBuilding().getElevator(0).getCurrentFloor());
    }
    
    @Test
    public void testElevatorGetPositionFeet() {
    	mockBuilding.mElevators[0].mElevatorPosition = 14;
    	assertEquals(RETURNSUCCESS, ecc.update(mockBuilding));
    	
        assertEquals(14, ecc.getBuilding().getElevator(0).getPositionFeet());
    }
    
    @Test
    public void testElevatorGetSpeed() {
    	mockBuilding.mElevators[0].mElevatorSpeed = 8;
    	assertEquals(RETURNSUCCESS, ecc.update(mockBuilding));
    	
        assertEquals(8, ecc.getBuilding().getElevator(0).getSpeed());
    }


}
