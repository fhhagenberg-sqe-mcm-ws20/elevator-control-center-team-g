package at.fhhagenberg.sqe.model;

public class Floor {
	
	private boolean isButtonDownPressed = false;
	private boolean isButtonUpPressed = false;
	private int FloorNumber = Integer.MAX_VALUE;
	
	
	/**
	 * @return the isButtonDownPressed
	 */
	public boolean isButtonDownPressed() {
		return isButtonDownPressed;
	}
	/**
	 * @param isButtonDownPressed the isButtonDownPressed to set
	 */
	public void setButtonDownPressed(boolean ButtonStatus) {
		this.isButtonDownPressed = ButtonStatus;
	}
	/**
	 * @return the isButtonUpPressed
	 */
	public boolean isButtonUpPressed() {
		return isButtonUpPressed;
	}
	/**
	 * @param isButtonUpPressed the isButtonUpPressed to set
	 */
	public void setButtonUpPressed(boolean ButtonStatus) {
		this.isButtonUpPressed = ButtonStatus;
	}
	/**
	 * @return the floorNumber
	 */
	public int getFloorNumber() {
		return FloorNumber;
	}
	/**
	 * @param floorNumber the floorNumber to set
	 */
	public void setFloorNumber(int floorNumber) {
		FloorNumber = floorNumber;
	}

}
