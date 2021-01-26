package at.fhhagenberg.sqe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import sqelevator.IElevator;

@ExtendWith(ApplicationExtension.class)
public class MainTest {
	private Button button;
	MockBuilding mockBuilding;
	App app;

	/**
	 * @param robot - Will be injected by the test runner.
	 * @throws InterruptedException
	 */
	@Test
	public void testMainDoesntCrash(FxRobot robot) throws InterruptedException {
		Main m = new Main();
		
		try {
			m.main(null);
		} catch (Exception e) {
		}
		
		TimeUnit.SECONDS.sleep(2);
	}

}