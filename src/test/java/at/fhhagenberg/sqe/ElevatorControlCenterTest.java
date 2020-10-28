package at.fhhagenberg.sqe;

import at.fhhagenberg.sqe.model.Floor;
import org.assertj.core.internal.bytebuddy.asm.Advice;
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
                for(int j = 0; j < NROFFLOORS; j++) {
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
        mockBuilding.mElevators[0].mTarget = 4;
        assertEquals(RETURNSUCCESS, ecc.update(mockBuilding));
        assertEquals(4, ecc.getBuilding().getElevator(0).getTarget());
    }

    @Test
    public void testInvalidElevatorTarget() {
        mockBuilding.mElevators[0].mTarget = 10;
        Exception exception  = assertThrows(IllegalArgumentException.class, () -> {
            ecc.update(mockBuilding);
        });

        String expectedMessage = "Target was not valid!";
        assertEquals(expectedMessage, exception.getMessage());
    }


    @Test
    public void testElevatorWeight() {
        mockBuilding.mElevators[0].mElevatorWeight = 100;
        assertEquals(RETURNSUCCESS, ecc.update(mockBuilding));
        assertEquals(100, ecc.getBuilding().getElevator(0).getWeight());
    }


}
