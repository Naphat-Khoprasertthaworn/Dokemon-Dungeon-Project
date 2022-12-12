package component;



import entity.base.Monster;
import entity.base.Unit;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import logic.GameLogic;

public class UnitCard extends VBox {
	
	private final String pointerImageURL = "image/pointer.png";
	private final String blankUnitURL = "image/overwatch-logo.png";
	private Unit unit;
	private ProgressBar healthBar;
	private ImageView unitImage;
	//Image pointerImage = new Image(ClassLoader.getSystemResource("image/pointer.png").toString(), 20,20, false, true);
	Image blankImage = new Image(ClassLoader.getSystemResource("image/overwatch-logo.png").toString(), 50,50, false, true);
	
	public UnitCard() {
		

		unitImage = new ImageView(new Image(blankUnitURL));
		
		ImageView pointerImageView = new ImageView(pointerImageURL);
		pointerImageView.setFitWidth(50);
		pointerImageView.setFitHeight(50);
		healthBar = new ProgressBar(100);
		
//		this.setPrefHeight(100);
		this.setPrefWidth(100);
		this.setPrefHeight(200);
		
		this.getChildren().add(pointerImageView);
		this.getChildren().add(unitImage);
		this.getChildren().add(healthBar);
		this.setAlignment(Pos.BOTTOM_CENTER);
		
		unitImage.setFitWidth(100);
		
		//pointerImageView.setA
		//healthBar.ali
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				onClickHandler();
			}
		});
		this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
		
	}
	
	public void update(Unit u) {
		this.unit = u;
		//System.out.println( u );
		String imgPath = ClassLoader.getSystemResource(u.getImagePath()).toString();
		
		unitImage.setImage(new Image(imgPath)); 
		
		
	}
	
	public void updateHealthBar() {
		if(unit == null) {
			return;
		}
		healthBar.setProgress( (unit.getHealth()*100) /unit.getMaxHealth() );
	}
	
	
	public void onClickHandler() {
		if(unit instanceof Monster) {
			GameLogic.getInstance().setTargetedMonster(this.unit);
		}else if(unit instanceof Unit) {
			GameLogic.getInstance().setTargetedHero(this.unit);
		}
	}
	
}
