package component;

import java.util.ArrayList;

import entity.base.Item;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import logic.GameLogic;

public class ItemGridPane extends GridPane{
	
	static final int MAX_COL = 3;
	static final int MAX_ROW = 2;
	private ArrayList<Item> inventory;
	//private Item[][] inventory;
	
	public ItemGridPane() {
		
		for(int i = 0;i<MAX_ROW;i++) {
			for(int j = 0;j<MAX_COL;j++) {
				ItemSquare itemSquare = new ItemSquare();
				this.add(itemSquare, j, i);
			}
		}
		//inventory = new Item[MAX_COL][MAX_ROW];
		this.setAlignment(Pos.CENTER);
		inventory = GameLogic.getInstance().getInventory();
	}
	
	public void updateState() {
		int col,row;
		System.out.println(inventory);
		
		for(int i = 0;i<this.getChildren().size();i++) {
			Node node = this.getChildren().get(i);
			if( node instanceof ItemSquare ) {
				((ItemSquare)node).removeItem();
			}
		}
		
		for(int i = 0;i<inventory.size();i++) {
			row = i/MAX_COL;
			col = i-(row*MAX_COL);
			Node node = this.getChildren().get(i);
			if( node instanceof ItemSquare ) {
				//System.out.println("it work");
				((ItemSquare)node).addItem( inventory.get(i) );
			}
			
		}
	}
	
	
	
}
