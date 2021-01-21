package at.fhhagenberg.sqe;

import sqelevator.IElevator;

import java.rmi.RemoteException;

public class MockBuilding implements IElevator {

    public MockElevator[] mElevators;
    public MockFloor[] mFloors;
    private final int mFloorHeight;

    private final long mClockTick = 15;

    public MockBuilding(int nrOfElevators, int floors, int floorHeight) {
        mElevators = new MockElevator[nrOfElevators];
        for (int i = 0; i < nrOfElevators; i++) {
        	mElevators[i] = new MockElevator();
        	mElevators[i].mServiceFloors = new MockFloor[floors];
        	mElevators[i].MockFloorButtonPressed = new boolean[floors];
        	for (int j = 0; j < floors; j++) {
        		mElevators[i].MockFloorButtonPressed[j]  = false;
			}
        	mElevators[i].mCommittedDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		}
        mFloors = new MockFloor[floors];
        for (int i = 0; i < floors; i++) {
        	mFloors[i] = new MockFloor();
		}
        mFloorHeight = floorHeight;
    }

    @Override
    public int getCommittedDirection(int elevatorNumber) {
        return mElevators[elevatorNumber].mCommittedDirection;
    }

    @Override
    public int getElevatorAccel(int elevatorNumber) {
        return mElevators[elevatorNumber].mElevatorAccel;
    }

    @Override
    public boolean getElevatorButton(int elevatorNumber, int floor) {
        return mElevators[elevatorNumber].getElevatorButton(floor);
    }

    @Override
    public int getElevatorDoorStatus(int elevatorNumber) {
        return mElevators[elevatorNumber].mElevatorDoorStatus;
    }

    @Override
    public int getElevatorFloor(int elevatorNumber) {
        return mElevators[elevatorNumber].mElevatorFloor;
    }

    @Override
    public int getElevatorNum() {
        return mElevators.length;
    }

    @Override
    public int getElevatorPosition(int elevatorNumber) {
        return mElevators[elevatorNumber].mElevatorPosition;
    }

    @Override
    public int getElevatorSpeed(int elevatorNumber) throws RemoteException {
        return mElevators[elevatorNumber].mElevatorSpeed;
    }

    @Override
    public int getElevatorWeight(int elevatorNumber) {
        return mElevators[elevatorNumber].mElevatorWeight;
    }

    @Override
    public int getElevatorCapacity(int elevatorNumber) {
        return mElevators[elevatorNumber].mElevatorCapacity;
    }

    @Override
    public boolean getFloorButtonDown(int floor) {
        return mFloors[floor].mFloorButtonDOWN;
    }

    @Override
    public boolean getFloorButtonUp(int floor) {
        return mFloors[floor].mFloorButtonUP;
    }

    @Override
    public int getFloorHeight() {
        return mFloorHeight;
    }

    @Override
    public int getFloorNum(){
        return mFloors.length;
    }

    @Override
    public boolean getServicesFloors(int elevatorNumber, int floor) {
        return mElevators[elevatorNumber].mServiceFloors[floor] != null;
    }

    @Override
    public int getTarget(int elevatorNumber) {
        return mElevators[elevatorNumber].mTarget;
    }

    @Override
    public void setCommittedDirection(int elevatorNumber, int direction) {
        mElevators[elevatorNumber].mCommittedDirection = direction;
    }

    @Override
    public void setServicesFloors(int elevatorNumber, int floor, boolean service) {
        if(service) {
            mElevators[elevatorNumber].mServiceFloors[floor] = mFloors[floor];
        } else {
            mElevators[elevatorNumber].mServiceFloors[floor] = null;
        }
    }

    @Override
    public void setTarget(int elevatorNumber, int target) {
        mElevators[elevatorNumber].mTarget = target;
    }

    @Override
    public long getClockTick() {
        return mClockTick;
    }


    public static class MockFloor {
        public boolean mFloorButtonUP = false;
        public boolean mFloorButtonDOWN = false;
    }

    public static class MockElevator {
        public int mCommittedDirection = ELEVATOR_DIRECTION_UP;
        public int mElevatorAccel = 0;
        public int mElevatorDoorStatus = ELEVATOR_DOORS_CLOSED;
        public int mElevatorFloor = 0;
        public int mElevatorPosition = 0;
        public int mElevatorSpeed = 0;
        public int mElevatorWeight = 0;
        public int mElevatorCapacity = 15;
        public int mTarget = -1;

        public MockFloor[] mServiceFloors;
        public boolean[] MockFloorButtonPressed;

        public boolean getElevatorButton(int floor){
            return MockFloorButtonPressed[floor];
        }
    }
}

