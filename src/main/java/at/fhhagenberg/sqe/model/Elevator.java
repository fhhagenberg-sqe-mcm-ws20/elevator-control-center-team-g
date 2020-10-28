package at.fhhagenberg.sqe.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Elevator {
	
	// Properties
	private int direction = Integer.MAX_VALUE;
	private int acceleration = Integer.MAX_VALUE;
	private int doorStatus = Integer.MAX_VALUE;
	private int currentFloor = Integer.MAX_VALUE;
	private int positionFeet = Integer.MAX_VALUE;
	private int speed = Integer.MAX_VALUE;
	private int weight = Integer.MAX_VALUE;
	private int capacity = Integer.MAX_VALUE;
	private int target = Integer.MAX_VALUE;
	
	// List of serviced floors
	private HashSet<Floor> ServicedFloors = new HashSet<Floor>();
	
	// List of pressed buttons
	private HashSet<Integer> PressedButtons = new HashSet<Integer>();
	
	/**
	 * @param fl Nr of button that was pressed
	 */
	public void AddPressedButton(int fl) {
		PressedButtons.add(fl);
	}

	/**
	 * @param fl Nr of button that was canceled
	 */
	public void RemovePressedButton(int fl) {
		PressedButtons.remove(fl);
	}
	
	/**
	 * @param fl Nr of button to check for pressed status
	 * @return true if Button was pressed
	 */
	public boolean IsPressedButton(int fl) {
		return PressedButtons.contains(fl);
	}
	
	/**
	 * @param fl pointer to floor to add
	 */
	public void AddServicedFloor(Floor fl) {
		ServicedFloors.add(fl);
	}

	/**
	 * @param fl pointer to floor to remove
	 */
	public void RemoveServicedFloor(Floor fl) {
		ServicedFloors.remove(fl);
	}
	
	/**
	 * @param fl pointer to floor to check if its being serviced by this elevator
	 * @return true if the elevator services this floor
	 */
	public boolean IsServicedFloor(Floor fl) {
		return ServicedFloors.contains(fl);
	}
	
	/**
	 * @return the direction
	 */
	public int getDirection() {
		return direction;
	}
	/**
	 * @param direction the direction to set
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}
	/**
	 * @return the acceleration
	 */
	public int getAcceleration() {
		return acceleration;
	}
	/**
	 * @param acceleration the acceleration to set
	 */
	public void setAcceleration(int acceleration) {
		this.acceleration = acceleration;
	}

	/**
	 * @return the doorStatus
	 */
	public int getDoorStatus() {
		return doorStatus;
	}

	/**
	 * @param doorStatus the doorStatus to set
	 */
	public void setDoorStatus(int doorStatus) {
		this.doorStatus = doorStatus;
	}

	/**
	 * @return the currentFloor
	 */
	public int getCurrentFloor() {
		return currentFloor;
	}

	/**
	 * @param currentFloor the currentFloor to set
	 */
	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	/**
	 * @return the positionFeet
	 */
	public int getPositionFeet() {
		return positionFeet;
	}

	/**
	 * @param positionFeet the positionFeet to set
	 */
	public void setPositionFeet(int positionFeet) {
		this.positionFeet = positionFeet;
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * @return the target
	 */
	public int getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(int target) {
		this.target = target;
	}


	
	
	
}
