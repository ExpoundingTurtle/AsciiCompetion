package Items;

import java.io.Serializable;

public class InventorySlot implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1060623638149583738L;

	public Item item;
	
	public int quantity;
	
	public InventorySlot(Item item, int q){
		this.item = item;
		quantity  = q;
		
		
	}
	
	public void increaseQuantity(int x){
		
		this.quantity += x;
		
	}

}
