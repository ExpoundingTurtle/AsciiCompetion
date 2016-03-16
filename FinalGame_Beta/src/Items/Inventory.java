package Items;

import java.io.Serializable;
import java.util.*;

public class Inventory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1060623638149583738L;
	public List<InventorySlot> inventoryList;
	
	public Inventory(){
		
		inventoryList = new ArrayList<InventorySlot>();
		//inventoryList.add();
		
		
	}
	
	public void addItem(Item item, int quantity){
		
		if(item.getStackable()){
			for(int i = 0; i<inventoryList.size(); i++){
				
				if(item.getId() == inventoryList.get(i).item.getId()){
					inventoryList.get(i).increaseQuantity(quantity);
					return;
				}
			}
			InventorySlot is = new InventorySlot(item, quantity);
			inventoryList.add(is);
		}else{
			InventorySlot is = new InventorySlot(item, 1);
			for(int i = 0; i < quantity; i++){inventoryList.add(is);}
		}
	}
	
	public void removeItem(Item item, int quantity){
		
	}
	
	public void removeItem(InventorySlot is){
		inventoryList.remove(is);
		System.out.println("you drop a " + is.item.getDisplayName());
	}
	
	public boolean isFull(){
		return false;
	}
	
	public boolean contains(Item item){
		for (InventorySlot i : inventoryList){
			if (i.item == item)
				return true;
		}
		return false;
	}
}

