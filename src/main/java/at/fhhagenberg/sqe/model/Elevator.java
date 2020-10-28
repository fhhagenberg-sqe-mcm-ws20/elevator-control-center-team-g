package at.fhhagenberg.sqe.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>Elevator class.</p>
 *
 * @author Simon Bergmaier
 * @version $Id: $Id
 */
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
	 * <p>AddPressedButton.</p>
	 *
	 * @param fl Nr of button that was pressed
	 */
	public void AddPressedButton(int fl) {
		PressedButtons.add(fl);
	}

	/**
	 * <p>RemovePressedButton.</p>
	 *
	 * @param fl Nr of button that was canceled
	 */
	public void RemovePressedButton(int fl) {
		PressedButtons.remove(fl);
	}
	
	/**
	 * <p>IsPressedButton.</p>
	 *
	 * @param fl Nr of button to check for pressed status
	 * @return true if Button was pressed
	 */
	public boolean IsPressedButton(int fl) {
		return PressedButtons.contains(fl);
	}
	
	/**
	 * <p>AddServicedFloor.</p>
	 *
	 * @param fl pointer to floor to add
	 */
	public void AddServicedFloor(Floor fl) {
		ServicedFloors.add(fl);
	}

	/**
	 * <p>RemoveServicedFloor.</p>
	 *
	 * @param fl pointer to floor to remove
	 */
	public void RemoveServicedFloor(Floor fl) {
		ServicedFloors.remove(fl);
	}
	
	/**
	 * <p>IsServicedFloor.</p>
	 *
	 * @param fl pointer to floor to check if its being serviced by this elevator
	 * @return true if the elevator services this floor
	 */
	public boolean IsServicedFloor(Floor fl) {
		return ServicedFloors.contains(fl);
	}

	/**
	 * <p>IsServicedFloor.</p>
	 *
	 * @param fl a int.
	 * @return a boolean.
	 */
	public boolean IsServicedFloor(int fl) {
		return ServicedFloors.stream().anyMatch(floor -> floor.getFloorNumber() == fl);
	}
	
	/**
	 * <p>Getter for the field <code>direction</code>.</p>
	 *
	 * @return the direction
	 */
	public int getDirection() {
		return direction;
	}
	/**
	 * <p>Setter for the field <code>direction</code>.</p>
	 *
	 * @param direction the direction to set
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}
	/**
	 * <p>Getter for the field <code>acceleration</code>.</p>
	 *
	 * @return the acceleration
	 */
	public int getAcceleration() {
		return acceleration;
	}
	/**
	 * <p>Setter for the field <code>acceleration</code>.</p>
	 *
	 * @param acceleration the acceleration to set
	 */
	public void setAcceleration(int acceleration) {
		this.acceleration = acceleration;
	}

	/**
	 * <p>Getter for the field <code>doorStatus</code>.</p>
	 *
	 * @return the doorStatus
	 */
	public int getDoorStatus() {
		return doorStatus;
	}

	/**
	 * <p>Setter for the field <code>doorStatus</code>.</p>
	 *
	 * @param doorStatus the doorStatus to set
	 */
	public void setDoorStatus(int doorStatus) {
		this.doorStatus = doorStatus;
	}

	/**
	 * <p>Getter for the field <code>currentFloor</code>.</p>
	 *
	 * @return the currentFloor
	 */
	public int getCurrentFloor() {
		return currentFloor;
	}

	/**
	 * <p>Setter for the field <code>currentFloor</code>.</p>
	 *
	 * @param currentFloor the currentFloor to set
	 */
	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	/**
	 * <p>Getter for the field <code>positionFeet</code>.</p>
	 *
	 * @return the positionFeet
	 */
	public int getPositionFeet() {
		return positionFeet;
	}

	/**
	 * <p>Setter for the field <code>positionFeet</code>.</p>
	 *
	 * @param positionFeet the positionFeet to set
	 */
	public void setPositionFeet(int positionFeet) {
		this.positionFeet = positionFeet;
	}

	/**
	 * <p>Getter for the field <code>speed</code>.</p>
	 *
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * <p>Setter for the field <code>speed</code>.</p>
	 *
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * <p>Getter for the field <code>weight</code>.</p>
	 *
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * <p>Setter for the field <code>weight</code>.</p>
	 *
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * <p>Getter for the field <code>capacity</code>.</p>
	 *
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * <p>Setter for the field <code>capacity</code>.</p>
	 *
	 * @param capacity the capacity to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * <p>Getter for the field <code>target</code>.</p>
	 *
	 * @return the target
	 */
	public int getTarget() {
		return target;
	}

	/**
	 * <p>Setter for the field <code>target</code>.</p>
	 *
	 * @param target the target to set
	 */
	public void setTarget(int target) {
		if(IsServicedFloor(target)) this.target = target;
		else throw new IllegalArgumentException("Target was not valid!");
	}


	
	
	
}
