package component;



import java.util.logging.Handler;

import entity.base.Item;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logic.GameLogic;

public class ItemSquare extends Pane {
	
	private Text text;
	private ImageView imageView;
	private Item item;
	private final String blankImagePath = "image/overwatch-logo.png";
	
	
    public ItemSquare() {
        this.setPrefHeight(100);
        this.setPrefWidth(100);
        this.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
        this.text = new Text();
        String imgPath = ClassLoader.getSystemResource( blankImagePath ).toString();
        this.imageView = new ImageView(new Image(imgPath));
        this.imageView.setFitHeight(100);
        this.imageView.setFitWidth(100);
        this.getChildren().add(this.imageView);
        this.getChildren().add(this.text);
        this.text.setWrappingWidth(BASELINE_OFFSET_SAME_AS_HEIGHT);
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
		System.out.println("use item");
		this.item.active();
		System.out.println(this.item.getAmount());
		GameLogic.getInstance().getCombatController().getItemGridPane().updateState();
		GameLogic.getInstance().getCombatController().getCombatDisplay().updateCombatDisplay();

	}
	
	public void addItem(Item item){
		text.setText(item.getName() + "x " + item.getAmount());
		String imgPath = ClassLoader.getSystemResource( item.getImagePath() ).toString();
		this.imageView.setImage( new Image(imgPath) );
		this.item = item;
	}
	
	public void removeItem() {
		this.text.setText("");
		String imgPath = ClassLoader.getSystemResource( blankImagePath ).toString();
		this.imageView.setImage(new Image(imgPath)); 
		this.item = null;
		
		
	}

}
