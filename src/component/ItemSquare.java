package component;



import java.util.Stack;
import java.util.logging.Handler;

import entity.base.Item;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.GameLogic;

public class ItemSquare extends VBox {
	
	private Text text;
	private ImageView imageView;
	private Item item;
	private final String blankImagePath = "image/blank.png" ;
	
    public ItemSquare() {
        this.setPrefHeight(100);
        this.setPrefWidth(150);
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY,Insets.EMPTY)));
        this.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
        
        
        this.text = new Text();
        this.text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
        this.text.setWrappingWidth( 80 );
        this.text.setFill(Color.WHITE);
        
        
        String imgPath = ClassLoader.getSystemResource( blankImagePath ).toString();
        this.imageView = new ImageView(new Image(imgPath));
        this.imageView.setFitHeight(60);
        this.imageView.setFitWidth(60);
        
        
        
        this.getChildren().add(this.imageView);
        this.getChildren().add(this.text);
        
        this.setAlignment(Pos.CENTER);
        

        
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
		if(GameLogic.getInstance().stageClear() || GameLogic.getInstance().stageFail() ) {
			System.out.println("cant use item please row a dice");
			return;
		}
		System.out.println("use item "+this.item.getName());
		this.item.active();
		//System.out.println(this.item.getAmount());
		GameLogic.getInstance().getCombatController().getItemGridPane().updateState();
		GameLogic.getInstance().getCombatController().getCombatDisplay().updateCombatDisplay();

	}
	
	public void addItem(Item item){
		text.setText(item.getName() + " x " + item.getAmount());
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
