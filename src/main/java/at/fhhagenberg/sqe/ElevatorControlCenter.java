package at.fhhagenberg.sqe;

import at.fhhagenberg.sqe.model.Building;
import at.fhhagenberg.sqe.util.ClockTickChangeException;
import sqelevator.IElevator;

import java.rmi.RemoteException;

/**
 * <p>ElevatorControlCenter class.</p>
 *
 * @author Simon Bergmaier
 * @version $Id: $Id
 */
public class ElevatorControlCenter {

	// for getting data from actual elevator
	// we don't need that for now
	// private IElevator elevatorApi;

	// Model that contains all the data
	private Building mBuilding;


	/**
	 * <p>update.</p>
	 *
	 * @param ElevatorSystem a {@link sqelevator.IElevator} object.
	 * @throws java.rmi.RemoteException if any.
	 * @throws at.fhhagenberg.sqe.util.ClockTickChangeException if any.
	 */
	public void update(IElevator ElevatorSystem) throws RemoteException, ClockTickChangeException {
		long currentTick = ElevatorSystem.getClockTick();

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
			for(int j = 0; j < newbuilding.getNrOfFloors(); j++) {
				if(ElevatorSystem.getServicesFloors(i,j)) newbuilding.getElevator(i).AddServicedFloor(newbuilding.getFloor(j));
				if(ElevatorSystem.getElevatorButton(i, j)) newbuilding.getElevator(i).AddPressedButton(j);
			}

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
			mBuilding = newbuilding;

		}else {
			throw new ClockTickChangeException("Clock Tick changed during operation!");
		}

	}

	/**
	 * <p>Getter for the field <code>building</code>.</p>
	 *
	 * @return a {@link at.fhhagenberg.sqe.model.Building} object.
	 */
	public Building getBuilding() {
		return mBuilding;
	}

}
