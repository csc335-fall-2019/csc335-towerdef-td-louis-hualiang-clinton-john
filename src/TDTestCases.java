import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import model.entity.*;
import model.*;

class TDTestCases {

	@Test
	void testPlacement() {
		TDModel model = new TDModel(9,9);
		TDController ctrl = new TDController(model);
		assertTrue(ctrl.placeEntity("tower3", 6,6));
		assertTrue(ctrl.placeEntity("tower1", 8,8));
		assertFalse(ctrl.placeEntity("tower2", 8, 8));
	}
	
	@Test
	void testRow() {
		Row r = new Row(6);
		assertTrue(r.getCols() == 6);
	}
	
	@Test
	void testModel() {
		TDModel mod = new TDModel(9,9);
		Entity entity = new Entity("Knave");
		assertTrue(mod.addEntity(entity, 5, 5));
		assertFalse(mod.addEntity(entity, 10, 10));
	}

}
