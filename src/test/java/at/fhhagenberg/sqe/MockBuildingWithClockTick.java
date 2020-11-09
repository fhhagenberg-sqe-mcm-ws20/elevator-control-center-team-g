package at.fhhagenberg.sqe;

import java.rmi.RemoteException;

public class MockBuildingWithClockTick extends MockBuilding {

    private long mClockTick = 15;

    public MockBuildingWithClockTick(int nrOfElevators, int floors, int floorHeight) {
        super(nrOfElevators, floors, floorHeight);
    }

    @Override
    public long getClockTick() throws RemoteException {
        return mClockTick++;
    }
}
