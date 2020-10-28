package at.fhhagenberg.sqe;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import at.fhhagenberg.sqe.model.*;

/**
 * <p>ElevatorControlCenter class.</p>
 *
 * @author Simon Bergmaier
 * @version $Id: $Id
 */
public class ElevatorControlCenter {

	// for getting data from actual elevator
	private IElevator elevatorApi;

	// Model that contains all the data
	private Building building;

	// return values
	final int ReturnSuccess = 0;
	final int ErrorRemoteException = -1;
	final int ErrorTickChange = -2;

	// init the ElevatorControlCenter
	// this does not have to happen in the same clock tick because the number of
	// floors and elevators is assumed to not change during runtime
	/**
	 * <p>init.</p>
	 *
	 * @param ElevatorSystem a {@link at.fhhagenberg.sqe.IElevator} object.
	 * @return a int.
	 */
	public int init(IElevator ElevatorSystem) {
		// get data from Elevator system and create model classes out of it
		int nrOfFloors = 0;
		int nrOfElevators = 0;
		int Floorheight = 0;

		try {
			nrOfFloors = ElevatorSystem.getFloorNum();
		} catch (RemoteException e) {
			return ErrorRemoteException;
		}

		// create elevators
		try {
			nrOfElevators = ElevatorSystem.getElevatorNum();
		} catch (RemoteException e) {
			return ErrorRemoteException;
		}

		// get floor height
		try {
			Floorheight = ElevatorSystem.getFloorHeight();
		} catch (RemoteException e) {
			return ErrorRemoteException;
		}

		// create building
		building = new Building(nrOfFloors, nrOfElevators, Floorheight);

		// set floor numbers
		for (int i = 0; i < building.getNrOfFloors(); i++) {
			building.getFloor(i).setFloorNumber(i);
		}

		return ReturnSuccess;
	}

	/**
	 * <p>update.</p>
	 *
	 * @param ElevatorSystem a {@link at.fhhagenberg.sqe.IElevator} object.
	 * @return a int.
	 */
	public int update(IElevator ElevatorSystem) {
		try {
			// Get Tick
			long currentTick = 0;
			currentTick = ElevatorSystem.getClockTick();

			// create new building
			Building newbuilding = new Building(ElevatorSystem.getFloorNum(), ElevatorSystem.getElevatorNum(), ElevatorSystem.getFloorHeight());

			// set floor numbers
			for (int i = 0; i < newbuilding.getNrOfFloors(); i++) {
				newbuilding.getFloor(i).setFloorNumber(i);
			}

			// Update Model
			// Floors
			for (int i = 0; i < newbuilding.getNrOfFloors(); i++) {
				newbuilding.getFloor(i).setButtonDownPressed(ElevatorSystem.getFloorButtonDown(i));
				newbuilding.getFloor(i).setButtonUpPressed(ElevatorSystem.getFloorButtonUp(i));
			}
			// Elevators
			for (int i = 0; i < newbuilding.getNrOfElevators(); i++) {
				newbuilding.getElevator(i).setDirection(ElevatorSystem.getCommittedDirection(i));
				newbuilding.getElevator(i).setAcceleration(ElevatorSystem.getElevatorAccel(i));
				newbuilding.getElevator(i).setDoorStatus(ElevatorSystem.getElevatorDoorStatus(i));
				newbuilding.getElevator(i).setCurrentFloor(ElevatorSystem.getElevatorFloor(i));
				newbuilding.getElevator(i).setPositionFeet(ElevatorSystem.getElevatorPosition(i));
				newbuilding.getElevator(i).setSpeed(ElevatorSystem.getElevatorSpeed(i));
				newbuilding.getElevator(i).setWeight(ElevatorSystem.getElevatorWeight(i));		
				newbuilding.getElevator(i).setCapacity(ElevatorSystem.getElevatorCapacity(i));
				newbuilding.getElevator(i).setTarget(ElevatorSystem.getTarget(i));
			}

			// Check if everything was refreshed in one tick
			// throw everything away if it was not
			if (currentTick == ElevatorSystem.getClockTick()) {
				building = newbuilding;
				return ReturnSuccess;
			}else {
				return ErrorTickChange;
			}
		} catch (RemoteException e) {
			return ErrorRemoteException;
		}

	}

}
