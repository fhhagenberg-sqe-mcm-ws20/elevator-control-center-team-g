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
	List<Floor> Floors = null;

	// Elevators
	List<Elevator> Elevators = null;

	// stored data that is the same for all floors/elevators
	private int Floorheight = 0;

	/**
	 * <p>Constructor for Building.</p>
	 *
	 * @param nrOfFloors a int.
	 * @param nrOfElevators a int.
	 * @param floorheight a int.
	 */
	public Building(int nrOfFloors, int nrOfElevators, int floorheight) {
		Floors = new ArrayList<Floor>(nrOfFloors);
		Elevators = new ArrayList<Elevator>(nrOfElevators);
		Floorheight = floorheight;

		for(int i = 0; i < nrOfElevators; i++) {
			Elevators.add(new Elevator());
		}
		for(int i = 0; i < nrOfFloors; i++) {
			Floors.add(new Floor());
		}
	}

	/**
	 * <p>getFloorheight.</p>
	 *
	 * @return the floorheight
	 */
	public int getFloorheight() {
		return Floorheight;
	}
	
	/**
	 * <p>getFloor.</p>
	 *
	 * @param num of Floor you want to get
	 * @return Floor you want to get
	 */
	public Floor getFloor(int num) {
		return Floors.get(num);
	}

	/**
	 * <p>getElevator.</p>
	 *
	 * @param num of elevator you want to get
	 * @return elevator you want to get
	 */
	public Elevator getElevator(int num) {
		return Elevators.get(num);
	}
	
	/**
	 * <p>getNrOfFloors.</p>
	 *
	 * @return number of floors
	 */
	public int getNrOfFloors() {
		return Floors.size();
	}
	
	/**
	 * <p>getNrOfElevators.</p>
	 *
	 * @return number of Elevators
	 */
	public int getNrOfElevators() {
		return Elevators.size();
	}
	
	
}
