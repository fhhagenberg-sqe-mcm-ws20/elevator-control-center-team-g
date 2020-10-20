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
	
}
