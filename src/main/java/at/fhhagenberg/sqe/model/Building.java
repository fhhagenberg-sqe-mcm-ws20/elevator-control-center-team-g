package at.fhhagenberg.sqe.model;

import java.util.ArrayList;
import java.util.List;

public class Building {

	// Floors
	List<Floor> Floors = null;

	// Elevators
	List<Elevator> Elevators = null;

	// stored data that is the same for all floors/elevators
	private int Floorheight = 0;

	public Building(int nrOfFloors, int nrOfElevators, int floorheight) {
		Floors = new ArrayList<Floor>(nrOfFloors);
		Elevators = new ArrayList<Elevator>(nrOfElevators);
		Floorheight = floorheight;
	}

	/**
	 * @return the floorheight
	 */
	public int getFloorheight() {
		return Floorheight;
	}
	
	/**
	 * @param num of Floor you want to get
	 * @return Floor you want to get
	 */
	public Floor getFloor(int num) {
		return Floors.get(num);
	}

	/**
	 * @param num of elevator you want to get
	 * @return elevator you want to get
	 */
	public Elevator getElevator(int num) {
		return Elevators.get(num);
	}
	
	/**
	 * @return number of floors
	 */
	public int getNrOfFloors() {
		return Floors.size();
	}
	
	/**
	 * @return number of Elevators
	 */
	public int getNrOfElevators() {
		return Elevators.size();
	}
	
	
}
