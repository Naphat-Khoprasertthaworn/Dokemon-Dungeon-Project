package component;

import java.util.ArrayList;

import entity.base.Item;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import logic.GameLogic;

public class ItemGridPane extends GridPane{
	
	static final int MAX_COL = 3;
	static final int MAX_ROW = 2;
	private ArrayList<Item> inventory;
	//private Item[][] inventory;
	
	public ItemGridPane() {
		
		for(int i = 0;i<MAX_COL;i++) {
			for(int j = 0;j<MAX_ROW;j++) {
				ItemSquare itemSquare = new ItemSquare();
				this.add(itemSquare, i, j);
			}
		}
		//inventory = new Item[MAX_COL][MAX_ROW];
		inventory = GameLogic.getInstance().getInventory().getInventory();
	}
	
	public void updateState(Item item) {
		int col,row;
		for(int i = 0;i<inventory.size();i++) {
			row = i/MAX_COL;
			col = i-(row*MAX_COL);
			Node node = this.getChildren().get(i);
			if( node instanceof ItemSquare ) {
				((ItemSquare)node).addItem( inventory.get(i) );
			}
			
		}
	}
	
	
	
}
