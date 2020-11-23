package at.fhhagenberg.sqe.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Building class.</p>
 *
 * @author Simon Bergmaier
 * @version $Id: $Id
 */
public class Building {

	// Floors
	private final List<Floor> mFloors;

	// Elevators
	private final List<Elevator> mElevators;

	// stored data that is the same for all floors/elevators
	private final int mFloorheight;

	/**
	 * <p>Constructor for Building.</p>
	 *
	 * @param nrOfFloors a int.
	 * @param nrOfElevators a int.
	 * @param floorheight a int.
	 */
	public Building(int nrOfFloors, int nrOfElevators, int floorheight) {
		mFloors = new ArrayList<>(nrOfFloors);
		mElevators = new ArrayList<>(nrOfElevators);
		mFloorheight = floorheight;

		for(int i = 0; i < nrOfElevators; i++) {
			mElevators.add(new Elevator());
		}
		for(int i = 0; i < nrOfFloors; i++) {
			mFloors.add(new Floor());
		}
	}

	/**
	 * <p>getFloorheight.</p>
	 *
	 * @return the floorheight
	 */
	public int getFloorheight() {
		return mFloorheight;
	}
	
	/**
	 * <p>getFloor.</p>
	 *
	 * @param num of Floor you want to get
	 * @return Floor you want to get
	 */
	public Floor getFloor(int num) {
		return mFloors.get(num);
	}

	/**
	 * <p>getElevator.</p>
	 *
	 * @param num of elevator you want to get
	 * @return elevator you want to get
	 */
	public Elevator getElevator(int num) {
		return mElevators.get(num);
	}
	
	/**
	 * <p>getNrOfFloors.</p>
	 *
	 * @return number of floors
	 */
	public int getNrOfFloors() {
		return mFloors.size();
	}
	
	/**
	 * <p>getNrOfElevators.</p>
	 *
	 * @return number of Elevators
	 */
	public int getNrOfElevators() {
		return mElevators.size();
	}
	
	
}
