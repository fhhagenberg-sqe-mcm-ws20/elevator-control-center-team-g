package at.fhhagenberg.sqe;

import java.rmi.RemoteException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IElevatorTest {
	@Test
    public void testAlwaysSucceeds() {}
	
	
	@Test
    public void testCreateOneElevatorGetElevatorNum() {
		int testedVar = 1;
		MockBuilding building = new MockBuilding(testedVar, 5, 10);
		
		try {
			int returnedVar = building.getElevatorNum();
			assertEquals(testedVar, returnedVar);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	
	@Test
    public void testCreateZeroElevatorsGetElevatorNum() {
		int testedVar = 0;
		MockBuilding building = new MockBuilding(testedVar, 5, 10);
		
		try {
			int returnedVar = building.getElevatorNum();
			assertEquals(testedVar, returnedVar);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	
	@Test
    public void testCreateTenElevatorsGetElevatorNum() {
		int testedVar = 10;
		MockBuilding building = new MockBuilding(testedVar, 5, 10);
		
		try {
			int returnedVar = building.getElevatorNum();
			assertEquals(testedVar, returnedVar);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	
	@Test
    public void testSetGetComittedDirectionUpOneElevator() {
		MockBuilding building = new MockBuilding(1, 5, 10);
		int setdirection = IElevator.ELEVATOR_DIRECTION_UP;
		int getdirection = -1;
		int elevatornum = 0;
		
		try {
			building.setCommittedDirection(elevatornum, setdirection);
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
		try {
			getdirection = building.getCommittedDirection(elevatornum);
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
		
		assertEquals(setdirection, getdirection);
	}
	
	@Test
    public void testSetGetComittedDirectionDownOneElevator() {
		MockBuilding building = new MockBuilding(1, 5, 10);
		int setdirection = IElevator.ELEVATOR_DIRECTION_DOWN;
		int getdirection = -1;
		int elevatornum = 0;
		
		try {
			building.setCommittedDirection(elevatornum, setdirection);
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
		try {
			getdirection = building.getCommittedDirection(elevatornum);
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
		
		assertEquals(setdirection, getdirection);
	}
	
	@Test
    public void testSetGetComittedDirectionUncommittedOneElevator() {
		MockBuilding building = new MockBuilding(1, 5, 10);
		int setdirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		int getdirection = -1;
		int elevatornum = 0;
		
		try {
			building.setCommittedDirection(elevatornum, setdirection);
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
		try {
			getdirection = building.getCommittedDirection(elevatornum);
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
		
		assertEquals(setdirection, getdirection);
	}
	
	@Test
    public void testSetGetComittedDirectionThreeElevators() {
		MockBuilding building = new MockBuilding(3, 5, 10);
		
		try {
			building.setCommittedDirection(0, IElevator.ELEVATOR_DIRECTION_UP);
			building.setCommittedDirection(1, IElevator.ELEVATOR_DIRECTION_DOWN);
			building.setCommittedDirection(2, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
		try {
			assertEquals(IElevator.ELEVATOR_DIRECTION_UP, building.getCommittedDirection(0));
			assertEquals(IElevator.ELEVATOR_DIRECTION_DOWN, building.getCommittedDirection(1));
			assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, building.getCommittedDirection(2));
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	
	@Test
    public void testGetElevatorAccel() {
		MockBuilding building = new MockBuilding(1, 5, 10);
		int setAccel = 5;
		int getAccel = 0;
		building.mElevators[0].mElevatorAccel = setAccel;
		
		try {
			getAccel = building.getElevatorAccel(0);
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}

		assertEquals(setAccel, getAccel);
	}
	
	@Test
    public void testSetGetServicesFloor() {
		MockBuilding building = new MockBuilding(1, 5, 10);
		
		try {
			building.getServicesFloors(0, 0);
			
			building.setServicesFloors(0, 0, true);

			assertTrue(building.getServicesFloors(0, 0));
			
			building.setServicesFloors(0, 0, false);

			assertFalse(building.getServicesFloors(0, 0));
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	
	@Test
    public void testGetFloorHeightZero() {
		MockBuilding building = new MockBuilding(1, 5, 0);
		
		try {
			assertEquals(0, building.getFloorHeight());
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	
	@Test
    public void testGetFloorHeight10() {
		MockBuilding building = new MockBuilding(1, 5, 10);
		
		try {
			assertEquals(10, building.getFloorHeight());
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	
	@Test
    public void testGetElevatorButtonNotPressed() {
		MockBuilding building = new MockBuilding(1, 5, 10);
		
		try {
			assertEquals(false, building.getElevatorButton(0, 0));
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	
	@Test
    public void testGetElevatorButtonPressed() {
		MockBuilding building = new MockBuilding(1, 5, 10);
		building.mElevators[0].MockFloorButtonPressed[0] = true;
		
		try {
			assertEquals(true, building.getElevatorButton(0, 0));
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	
	@Test
    public void testGetElevatorDoorStatus() {
		MockBuilding building = new MockBuilding(1, 5, 10);
		building.mElevators[0].mElevatorDoorStatus = IElevator.ELEVATOR_DOORS_OPEN;
		
		try {
			assertEquals(IElevator.ELEVATOR_DOORS_OPEN, building.getElevatorDoorStatus(0));
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	
	@Test
    public void testGetElevatorFloor() {
		MockBuilding building = new MockBuilding(1, 5, 10);
		building.mElevators[0].mElevatorFloor = 3;
		
		try {
			assertEquals(3, building.getElevatorFloor(0));
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	
	@Test
    public void testGetElevatorPosition() {
		MockBuilding building = new MockBuilding(1, 5, 10);
		building.mElevators[0].mElevatorPosition = 33;
		
		try {
			assertEquals(33, building.getElevatorPosition(0));
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	
	@Test
    public void testGetElevatorSpeed() {
		MockBuilding building = new MockBuilding(1, 5, 10);
		building.mElevators[0].mElevatorSpeed = 33;
		
		try {
			assertEquals(33, building.getElevatorSpeed(0));
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	
	@Test
    public void testGetElevatorWeight() {
		MockBuilding building = new MockBuilding(1, 5, 10);
		building.mElevators[0].mElevatorWeight = 33;
		
		try {
			assertEquals(33, building.getElevatorWeight(0));
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	
	@Test
    public void testGetElevatorCapacity() {
		MockBuilding building = new MockBuilding(1, 5, 10);
		building.mElevators[0].mElevatorCapacity = 33;
		
		try {
			assertEquals(33, building.getElevatorCapacity(0));
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	
	@Test
    public void testFloorButtons() {
		MockBuilding building = new MockBuilding(1, 5, 10);
		building.mFloors[1].mFloorButtonDOWN = true;
		building.mFloors[2].mFloorButtonUP = true;
		building.mFloors[3].mFloorButtonDOWN = true;
		building.mFloors[3].mFloorButtonUP = true;
		
		try {
			assertEquals(false, building.getFloorButtonDown(0));
			assertEquals(false, building.getFloorButtonUp(0));
			
			assertEquals(true, building.getFloorButtonDown(1));
			assertEquals(false, building.getFloorButtonUp(1));
			
			assertEquals(false, building.getFloorButtonDown(2));
			assertEquals(true, building.getFloorButtonUp(2));
			
			assertEquals(true, building.getFloorButtonDown(3));
			assertEquals(true, building.getFloorButtonUp(3));
			
			assertEquals(false, building.getFloorButtonDown(4));
			assertEquals(false, building.getFloorButtonUp(4));
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	
	@Test
    public void testGetFloorNumOne() {
		MockBuilding building = new MockBuilding(1, 1, 10);
		
		try {
			assertEquals(1, building.getFloorNum());
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	
	@Test
    public void testGetFloorNumTen() {
		MockBuilding building = new MockBuilding(1, 10, 10);
		
		try {
			assertEquals(10, building.getFloorNum());
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	
	@Test
    public void testGetFloorNumZero() {
		MockBuilding building = new MockBuilding(1, 0, 10);
		
		try {
			assertEquals(0, building.getFloorNum());
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	
	@Test
    public void testSetGetTarget() {
		MockBuilding building = new MockBuilding(1, 5, 10);
		try {
			building.setServicesFloors(0, 0, true);
			building.setServicesFloors(0, 1, true);
			building.setServicesFloors(0, 2, true);
			building.setServicesFloors(0, 3, true);
			building.setServicesFloors(0, 4, true);
		} catch (RemoteException e1) {
			e1.printStackTrace();
			fail("Exception during setup");
		}
		
		try {
			building.setTarget(0, 1);
			assertEquals(1, building.getTarget(0));
			building.setTarget(0, 4);
			assertEquals(4, building.getTarget(0));			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	
	
}
