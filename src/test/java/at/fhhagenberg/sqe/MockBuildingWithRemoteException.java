package at.fhhagenberg.sqe;

import java.rmi.RemoteException;

public class MockBuildingWithRemoteException extends MockBuilding{


    public MockBuildingWithRemoteException(int nrOfElevators, int floors, int floorHeight) {
        super(nrOfElevators, floors, floorHeight);
    }

    private boolean throwExceptions = false;

    public void throwExceptionsOn(){
        throwExceptions = true;
    }

    public void throwExceptionsOff() {
        throwExceptions = true;
    }

    @Override
    public int getElevatorSpeed(int elevatornum) throws RemoteException {
        if(throwExceptions) {
            throw new RemoteException();
        }
        else {
            return super.getElevatorSpeed(elevatornum);
        }
    }
}
