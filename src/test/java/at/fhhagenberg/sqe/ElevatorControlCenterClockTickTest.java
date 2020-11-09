package at.fhhagenberg.sqe;

import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ElevatorControlCenterClockTickTest {

    @Test
    public void testClockTickChange() throws RemoteException {
        ElevatorControlCenter ecc = new ElevatorControlCenter();

        MockBuildingWithClockTick mockBuilding = new MockBuildingWithClockTick(3,3,500);

        mockBuilding.setServicesFloors(0,0,true);
        mockBuilding.setServicesFloors(0,1,true);
        mockBuilding.setServicesFloors(0,2,true);
        mockBuilding.setServicesFloors(1,0,true);
        mockBuilding.setServicesFloors(1,1,true);
        mockBuilding.setServicesFloors(1,2,true);
        mockBuilding.setServicesFloors(2,0,true);
        mockBuilding.setServicesFloors(2,1,true);
        mockBuilding.setServicesFloors(2,2,true);

        Exception exception = assertThrows(ClockTickChangeException.class, () -> ecc.update(mockBuilding));

        assertEquals("Clock Tick changed during operation!", exception.getMessage());
    }
}
