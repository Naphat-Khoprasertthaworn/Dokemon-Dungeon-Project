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
	
	private ImageView pointerImageView;
	

	public UnitCard() {
		

		unitImage = new ImageView(new Image(blankUnitURL));
		unitImage.setFitHeight(100);
		unitImage.setFitWidth(100);
		pointerImageView = new ImageView(pointerImageURL);
		pointerImageView.setVisible(false);
		
		pointerImageView.setFitHeight(50);
		pointerImageView.setFitWidth(50);
		healthBar = new ProgressBar(100);
		healthBar.setPrefHeight(10);
		healthBar.setPrefWidth(100);
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

		String imgPath = ClassLoader.getSystemResource(u.getImagePath()).toString();
		
		unitImage.setImage(new Image(imgPath)); 
		
		
	}
	
	public void updateHealthBar() {
		if(unit == null) {
			return;
		}

		healthBar.setProgress( ((float)unit.getHealth()) /(float)unit.getMaxHealth() );
	}
	
	
	
	public void onClickHandler() {
		if(unit instanceof Monster) {
			//System.out.println(this.unit);
			GameLogic.getInstance().setTargetedMonster(this.unit);
			//System.out.println( GameLogic.getInstance().getTargetedMonster() );
			System.out.println(this.unit.isTargeted());
		}else if(unit instanceof Unit) {
			GameLogic.getInstance().setTargetedHero(this.unit);
		}
		GameLogic.getInstance().getCombatController().getCombatDisplay().updatePointer();
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public ImageView getPointerImageView() {
		return pointerImageView;
	}

	public void setPointerImageView(ImageView pointerImageView) {
		this.pointerImageView = pointerImageView;
	}
}
