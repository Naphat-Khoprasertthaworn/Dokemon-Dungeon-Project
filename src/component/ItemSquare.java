package component;



import java.util.logging.Handler;

import entity.base.Item;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import logic.GameLogic;

public class ItemSquare extends Pane {
	
	private Text text;
	private ImageView imageView;
	private Item item;
	private final String blankImagePath = "blankItem.png";
	
	
	public ItemSquare() {
		this.setPrefHeight(100);
		this.setPrefWidth(100);
		
		this.text = new Text();
		String imgPath = ClassLoader.getSystemResource( blankImagePath ).toString();
		this.imageView = new ImageView(new Image(imgPath));
		
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				onClickHandler();
			}
		});
		
		
		
	}
	
	public void onClickHandler() {
		if(this.item==null) {
			return;
		}
		this.item.active();
		if(this.item.getAmount() <= 0) {
			this.removeItem();
		}
	}
	
	public void addItem(Item item){
		text.setText(item.getName());
		String imgPath = ClassLoader.getSystemResource( item.getImagePath() ).toString();
		this.imageView.setImage( new Image(imgPath) );
		this.item = item;
	}
	
	public void removeItem() {
		this.text.setText("");
		String imgPath = ClassLoader.getSystemResource( blankImagePath ).toString();
		this.imageView = new ImageView(new Image(imgPath));
		this.item = null;
		
		
	}

}