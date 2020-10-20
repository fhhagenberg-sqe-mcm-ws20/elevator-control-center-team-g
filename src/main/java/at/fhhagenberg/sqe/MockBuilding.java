package at.fhhagenberg.sqe;

import java.rmi.RemoteException;

public class MockBuilding implements IElevator {

    public MockElevator[] mElevators;
    public MockFloor[] mFloors;
    private int mFloorHeight;
    private int mClockTick = 15;

    public MockBuilding(int nrOfElevators, int floors, int floorHeight) {
        mElevators = new MockElevator[nrOfElevators];
        for (int i = 0; i < nrOfElevators; i++) {
        	mElevators[i] = new MockElevator();
        	mElevators[i].mServiceFloors = new MockFloor[floors];
		}
        mFloors = new MockFloor[floors];
        for (int i = 0; i < floors; i++) {
        	mFloors[i] = new MockFloor();
		}
        mFloorHeight = floorHeight;
    }

    @Override
    public int getCommittedDirection(int elevatorNumber) throws RemoteException {
        return mElevators[elevatorNumber].mCommittedDirection;
    }

    @Override
    public int getElevatorAccel(int elevatorNumber) throws RemoteException {
        return mElevators[elevatorNumber].mElevatorAccel;
    }

    @Override
    public boolean getElevatorButton(int elevatorNumber, int floor) throws RemoteException {
        return mElevators[elevatorNumber].getElevatorButton(floor);
    }

    @Override
    public int getElevatorDoorStatus(int elevatorNumber) throws RemoteException {
        return mElevators[elevatorNumber].mElevatorDoorStatus;
    }

    @Override
    public int getElevatorFloor(int elevatorNumber) throws RemoteException {
        return mElevators[elevatorNumber].mElevatorFloor;
    }

    @Override
    public int getElevatorNum() throws RemoteException {
        return mElevators.length;
    }

    @Override
    public int getElevatorPosition(int elevatorNumber) throws RemoteException {
        return mElevators[elevatorNumber].mElevatorPosition;
    }

    @Override
    public int getElevatorSpeed(int elevatorNumber) throws RemoteException {
        return mElevators[elevatorNumber].mElevatorSpeed;
    }

    @Override
    public int getElevatorWeight(int elevatorNumber) throws RemoteException {
        return mElevators[elevatorNumber].mElevatorWeight;
    }

    @Override
    public int getElevatorCapacity(int elevatorNumber) throws RemoteException {
        return mElevators[elevatorNumber].mElevatorCapacity;
    }

    @Override
    public boolean getFloorButtonDown(int floor) throws RemoteException {
        return mFloors[floor].mFloorButtonDOWN;
    }

    @Override
    public boolean getFloorButtonUp(int floor) throws RemoteException {
        return mFloors[floor].mFloorButtonUP;
    }

    @Override
    public int getFloorHeight() throws RemoteException {
        return mFloorHeight;
    }

    @Override
    public int getFloorNum() throws RemoteException {
        return mFloors.length;
    }

    @Override
    public boolean getServicesFloors(int elevatorNumber, int floor) throws RemoteException {
    	if(mElevators.length < elevatorNumber) {
    		return false;
    	}
    	if(mElevators[elevatorNumber].mServiceFloors.length < floor) {
    		return false;
    	}
    	
        return mElevators[elevatorNumber].mServiceFloors[floor] != null;
    }

    @Override
    public int getTarget(int elevatorNumber) throws RemoteException {
        return mElevators[elevatorNumber].mTarget;
    }

    @Override
    public void setCommittedDirection(int elevatorNumber, int direction) throws RemoteException {
    	if(mElevators == null) {
    		return;
    	}
    	if(mElevators.length == 0) {
    		return;
    	}
    	if(mElevators[elevatorNumber] == null) {
    		return;
    	}
    	
        mElevators[elevatorNumber].mCommittedDirection = direction;
    }

    @Override
    public void setServicesFloors(int elevatorNumber, int floor, boolean service) throws RemoteException {
        if(service) {
            mElevators[elevatorNumber].mServiceFloors[floor] = mFloors[floor];
        } else {
            mElevators[elevatorNumber].mServiceFloors[floor] = null;
        }
    }

    @Override
    public void setTarget(int elevatorNumber, int target) throws RemoteException {
        mElevators[elevatorNumber].mTarget = target;
    }

    @Override
    public long getClockTick() throws RemoteException {
        return mClockTick;
    }

    public class MockFloor {
        public boolean mFloorButtonUP = false;
        public boolean mFloorButtonDOWN = false;
    }

    public class MockElevator {

        public int mCommittedDirection = ELEVATOR_DIRECTION_UP;
        public int mElevatorAccel = 0;
        public int mElevatorDoorStatus = ELEVATOR_DOORS_CLOSED;
        public int mElevatorFloor = 0;
        public int mElevatorPosition = 0;
        public int mElevatorSpeed = 0;
        public int mElevatorWeight = 0;
        public int mElevatorCapacity = 15;
        public int mTarget = 0;

        public MockFloor[] mServiceFloors;


        public boolean getElevatorButton(int floor){
            return true;
        }
    }
}
