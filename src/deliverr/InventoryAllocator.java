package deliverr;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
/**
* this class is the collections all inventories. I have implemented inventories input, shipment output 
* and check the remainder of inventories
* @author Hugh fang
* @Time 2020-08-25
*/
public class InventoryAllocator {
	private Map<String, Map<String, Integer>> invertories;// the storage 
	private Map<String, Integer> demand; // a map of items that are being ordered and the number of them are ordered.
	
	public InventoryAllocator() {
		this.invertories = new LinkedHashMap<>();// warehouses is pre-sort
		this.demand = new LinkedHashMap<>();
	}
	
	/* 
	 * check remainder in warehouse
	 */
	public Map<String, Map<String, Integer>> getWarehouse() {
		return this.invertories;
	}
	
	/*
	 * input all warehouses to store
	 */
	public void inputWarehouses(Map<String, Map<String, Integer>> list) {
		try {
			for(Map.Entry<String, Map<String, Integer>> entry : list.entrySet()) {
				inputOneWarehouse(entry.getKey(), entry.getValue());
			}
		}catch(Exception e) {
			System.out.println("list of Warehouse goods issue");
		}
	}
	
	/*
	 *input demand to plan
	 */
	public void demandList(Map<String,Integer> list) {
		for(Map.Entry<String, Integer> entry : list.entrySet()) {
			demand.put(entry.getKey(), entry.getValue());
		}
	}
	
	/*
	 * input specific warehouse to store. 
	 */
 	public void inputOneWarehouse(String name, Map<String, Integer> inventory) {
		if(invertories.containsKey(name)) {
			Map<String, Integer> curr = invertories.get(name);
			for(Map.Entry<String, Integer> entry : inventory.entrySet()) {
				curr.put(entry.getKey(), curr.getOrDefault(entry.getKey(), 0) + entry.getValue());
			}
			invertories.replace(name,curr);
		}else {
			invertories.put(name, inventory);
		}
	}
 	
	/*
	 * output a shipment
	 */
 	public Map<String, Map<String, Integer>> shipments() {
 		 Map<String, Map<String, Integer>> res = new LinkedHashMap<>();
 		 if(demand.isEmpty())return res;
 		 Iterator<Map.Entry<String, Map<String, Integer>>> eachInver = invertories.entrySet().iterator();
 		 
 		 while(eachInver.hasNext()) {// search goods in each of pre-order warehouse 
 			 Map.Entry<String, Map<String, Integer>> entry = eachInver.next();
 			 String name = entry.getKey();// the name of warehouse
 			 Map<String, Integer> goods = entry.getValue();// the inventory of warehouse
 			 Map<String, Integer> pick = new LinkedHashMap<>();// the collections of choosing goods in the warehouse
 			 Iterator<Entry<String, Integer>> itr = demand.entrySet().iterator();
 			 while(itr.hasNext()) {
 				 Map.Entry<String, Integer> curr = itr.next();
 				 if(goods.containsKey(curr.getKey())) {
 					 int take = Math.min(curr.getValue(), goods.get(curr.getKey()));// choose elements from the specific warehouse
 					 if(take == curr.getValue()) {
 						 itr.remove();// finish pick up the goods
 					 }else {
 						 curr.setValue(curr.getValue() - goods.get(curr.getKey()));
 					 }
 					 goods.replace(curr.getKey(), goods.get(curr.getKey()) - take);// update inventory
 					 pick.put(curr.getKey(), take);// record the name of goods and number of taking
 				 }
 			 }
 			 res.put(name, pick);
 			 if(demand.isEmpty()) {// finish the list of shipment
 				 break;
 			 }
 			 
 		 }

 		 return res;
 	}
	
}
