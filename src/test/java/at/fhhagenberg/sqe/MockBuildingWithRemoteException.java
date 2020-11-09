package at.fhhagenberg.sqe;

import java.rmi.RemoteException;

public class MockBuildingWithRemoteException extends MockBuilding{


    public MockBuildingWithRemoteException(int nrOfElevators, int floors, int floorHeight) {
        super(nrOfElevators, floors, floorHeight);
    }

    @Override
    public int getFloorNum() throws RemoteException {
        throw new RemoteException();
    }
}
