package at.fhhagenberg.sqe;

public class MockBuildingWithClockTick extends MockBuilding {

    private long mClockTick = 15;

    public MockBuildingWithClockTick(int nrOfElevators, int floors, int floorHeight) {
        super(nrOfElevators, floors, floorHeight);
    }

    @Override
    public long getClockTick() {
        return mClockTick++;
    }
}
