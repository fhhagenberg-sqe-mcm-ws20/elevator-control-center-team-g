package at.fhhagenberg.sqe.model;

/**
 * <p>Floor class.</p>
 *
 * @author Simon Bergmaier
 * @version $Id: $Id
 */
public class Floor {
	
	private boolean isButtonDownPressed = false;
	private boolean isButtonUpPressed = false;
	private int FloorNumber = Integer.MAX_VALUE;
	
	
	/**
	 * <p>isButtonDownPressed.</p>
	 *
	 * @return the isButtonDownPressed
	 */
	public boolean isButtonDownPressed() {
		return isButtonDownPressed;
	}
	/**
	 * <p>setButtonDownPressed.</p>
	 *
	 * @param ButtonStatus a boolean.
	 */
	public void setButtonDownPressed(boolean ButtonStatus) {
		this.isButtonDownPressed = ButtonStatus;
	}
	/**
	 * <p>isButtonUpPressed.</p>
	 *
	 * @return the isButtonUpPressed
	 */
	public boolean isButtonUpPressed() {
		return isButtonUpPressed;
	}
	/**
	 * <p>setButtonUpPressed.</p>
	 *
	 * @param ButtonStatus a boolean.
	 */
	public void setButtonUpPressed(boolean ButtonStatus) {
		this.isButtonUpPressed = ButtonStatus;
	}
	/**
	 * <p>getFloorNumber.</p>
	 *
	 * @return the floorNumber
	 */
	public int getFloorNumber() {
		return FloorNumber;
	}
	/**
	 * <p>setFloorNumber.</p>
	 *
	 * @param floorNumber the floorNumber to set
	 */
	public void setFloorNumber(int floorNumber) {
		FloorNumber = floorNumber;
	}

}
