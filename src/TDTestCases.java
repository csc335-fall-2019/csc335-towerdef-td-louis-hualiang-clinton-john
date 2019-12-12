import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import model.entity.*;
import model.*;

/**
 * Purpose: Test cases which aim to test all non-visual aspects.
 * 
 * @author Hualiang Qin
 * @author Louis Galluzzi
 * @author Clinton Kral
 * @author John Stockey
 */
class TDTestCases {
	/**
	 * Test the methods of entity that have not required javafx
	 */
	@Test
	void testEntity() {
		TDModel model = null;
		Entity tower0 = new Entity("tower0", model);
		Entity tower1 = new Entity("tower1", model);
		Entity tower2 = new Entity("tower2", model);
		Entity tower3 = new Entity("tower3", model);
		Entity tower4 = new Entity("tower4", model);
		Entity tower5 = new Entity("tower5", model);
		Entity zombie0 = new Entity("zombie0", model);
		Entity zombie1 = new Entity("zombie1", model);
		Entity zombie2 = new Entity("zombie2", model);
		Entity zombie3 = new Entity("zombie3", model);
		Entity zombie4 = new Entity("zombie4", model);
		Entity object0 = new Entity("object0", model);
		Entity object1 = new Entity("object1", model);
		Entity object2 = new Entity("object2", model);
		Entity object3 = new Entity("object3", model);
		Entity object4 = new Entity("object4", model);
		Entity object5 = new Entity("object5", model);
		Entity object6 = new Entity("object6", model);
		Entity object7 = new Entity("object7", model);
		Entity object8 = new Entity("object8", model);
		Entity object9 = new Entity("object9", model);
		Entity object10 = new Entity("object10", model);
		Entity object11 = new Entity("object11", model);
		Entity object12 = new Entity("object12", model);
		Entity object13 = new Entity("object13", model);
		Entity object14 = new Entity("object14", model);
		Entity object15 = new Entity("object15", model);
		assertTrue(zombie0.getIsValid());
		assertFalse(zombie4.getIsValid());
		
		zombie0.beAttacked(zombie0.getHealth()-1);
		zombie0.beAttacked(2);
		assertTrue(zombie0.isDead());
		
		assertEquals(zombie0.getType(), "zombie0");
		assertEquals(zombie0.getBase(), "zombie");
		assertEquals(zombie0.getAttack(), 5);
		assertEquals(zombie0.getPrice(), 0);
		assertEquals(zombie0.getSpeed(), 75);
		assertNotEquals(zombie0.getFrames(),50);
		assertEquals(tower0.getPrice(), 110);
		assertEquals(tower0.getSpeed(), 0);
		assertEquals(tower5.getFrames(), 7);
		
	}
	
	/**
	 * Test the TDModel's methods that have not required javafx
	 */
	@Test
	void testModel() {
		TDModel model = new TDModel(5,9);
		Entity zombie0 = new Entity("zombie0", model);
		assertFalse(model.addEntity(zombie0, 6, 9));
		assertTrue(model.addEntity(zombie0, 0, 0));
		assertFalse(model.removeEntity(zombie0, 6, 9, false));
		assertTrue(model.removeEntity(zombie0, 0, 0, false));
		assertTrue(model.removeEntity(zombie0, 0, 1, false));
		assertEquals(model.nextStep(), 1);
		
		Entity tower0 = new Entity("tower0", model);
		model.addEntity(tower0, 1, 1);
		assertTrue(model.removeEntity(tower0, 1, 1, true));
		
		Entity object0 = new Entity("object0", model);
		model.addEntity(object0, 2, 2);
		assertTrue(model.removeEntity(object0, 2, 2, false));
		
		Entity tower3 = new Entity("tower3", model);
		Entity tower5 = new Entity("tower5", model);
		Entity zombie1 = new Entity("zombie1", model);
		model.addEntity(tower3, 3, 3);
		model.addEntity(tower5, 4, 4);
		model.addEntity(zombie1, 1, 1);
		assertEquals(model.nextStep(), 0);
		model.updateSpot(1, 1, zombie1);
		model.incrTurn();
		
		TDModel tdmodel = new TDModel(2,2);
		assertEquals(tdmodel.getCols(), 2);
		assertEquals(tdmodel.getRows(), 2);
		assertEquals(tdmodel.getMoney(), 1000);
		assertEquals(tdmodel.getTurn(), 1);
		assertTrue(tdmodel.reset());
		tdmodel.setRoundStatus(0);
		assertEquals(tdmodel.getRoundStatus(), 0);
		
	}
	
	/**
	 * Test the TDController's methods that have not required javafx
	 */
	@Test
	void testController() {
		TDModel model = new TDModel(5,9);
		TDController controller = new TDController(model);
		assertTrue(controller.placeEntity("tower4", 0, 0));
		controller.placeEntity("tower4", 1, 1);
		assertTrue(controller.placeEntity("tower4", 2, 2));
		assertTrue(controller.removeEntity("tower4", 1, 1));
		
		TDModel model2 = new TDModel(5,9);
		TDController controller2 = new TDController(model2);
		assertTrue(controller2.buildRandomStage(5, 9));
		assertTrue(controller2.buildStage2());
		assertTrue(controller2.buildStage3(2));
		assertTrue(controller2.randomizeGravesColEnd(5, 9));
		assertTrue(controller2.randomizeTownCol0(3));
		assertTrue(controller2.reset());
		controller2.setModel(model);
		assertEquals(controller2.getModel(), model);
		assertEquals(controller2.getMoney(), 425);
		controller2.setGameSpeed(5);
		
		List<List<Entity>> troops = controller.queueUpEnemy(1);
		List<List<Entity>> big_troops = controller.queueUpEnemy(10);
		assertTrue(controller2.enemiesInQueue(troops));
		assertTrue(controller2.enemiesInQueue(big_troops));
		List<List<Entity>> troops2 = new ArrayList<List<Entity>>();
		assertFalse(controller2.enemiesInQueue(troops2));
		
	}
	
}
