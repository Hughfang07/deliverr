package deliverr;

import static org.junit.Assert.*;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;
/*
 * Input: { apple: 3,orange : 3}, [{ name: owd, inventory: { apple: 0}}]
 *   
 * Output: [{owd: { apple: 0}}]
 */
public class InventoryAllocatorTest3 {
	static InventoryAllocator ivta;

	@BeforeClass
	public static void buildInventories() {
		ivta = new InventoryAllocator();
		Map<String, Map<String, Integer>> invertories = new LinkedHashMap<>();
		invertories.put("owd", new LinkedHashMap<String, Integer>());
		invertories.get("owd").put("apple", 0);
		ivta.inputWarehouses(invertories);
	}
	
	@Test
	public void testShipments() {
		Map<String, Integer> demand = new LinkedHashMap<>();
		demand.put("apple",3);
		demand.put("orange",3);
		ivta.demandList(demand);
		
		Map<String, Map<String, Integer>>  expected = new LinkedHashMap<>();
		expected.put("owd", new LinkedHashMap<String, Integer>());
		expected.get("owd").put("apple", 0);
		Map<String, Map<String, Integer>>  actual = ivta.shipments();
	
		assertEquals(expected, actual);
	}
}
