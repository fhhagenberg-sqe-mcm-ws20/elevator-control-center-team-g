package at.fhhagenberg.sqe;

import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertThrows;
public class ElevatorControlCenterRemoteExceptionTest {

    @Test
    public void testRemoteException() {
        MockBuildingWithRemoteException mockBuilding = new MockBuildingWithRemoteException(3,3,500);
        ElevatorControlCenter ecc = new ElevatorControlCenter();

        mockBuilding.throwExceptionsOn();

        assertThrows(RemoteException.class, () -> ecc.update(mockBuilding));


    }
}
