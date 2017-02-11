import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class FloodControl {
	
	private Stage stage;
	private Canvas canvas;
	private GraphicsContext graphicsContext;
	private long startNanoTime;
	
	private Image playingPieces;
	private Image backgroundScreen;
	private Image titleScreen;
	
	private GameAnimationTimer animationTimer;

	
	
    private class GameAnimationTimer extends AnimationTimer {
    	@Override
    	public void handle(long currentNanoTime)
        {
        	update(currentNanoTime);
        	draw(currentNanoTime);
        }
    }
    
    
    public FloodControl( Stage primaryStage)
    {	
    	stage = primaryStage;
    	stage.setTitle( "Flood Control" );
    	stage.getIcons().add(new Image("content/icons/Game.png"));
    	startNanoTime = System.nanoTime(); // czas rozpoczęcia gry    	
    }
    
    
    public void run() {
    	initialize();
    	loadContent();
    	stage.show();
    	animationTimer = new GameAnimationTimer();
        animationTimer.start();
    }
    
    
    private void initialize() {
    	Group root = new Group();
    	canvas = new Canvas( 800, 600 );
    	root.getChildren().add( canvas );
        
    	graphicsContext = canvas.getGraphicsContext2D();
    	
    	Scene scene = new Scene( root ); 
    	stage.setScene( scene );
    	stage.setResizable(false);
    	stage.sizeToScene();
    	stage.setOnCloseRequest(e->stage_CloseRequest(e));
    }
    
    
    private void stage_CloseRequest(WindowEvent windowEvent) {
    	windowEvent.consume();
    	
    	Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	if (AlertBox.showAndWait(
            			AlertType.CONFIRMATION, 
            			"Flood Control", 
            			"Do you want to stop the game?")
            			.orElse(ButtonType.CANCEL) == ButtonType.OK) {
					animationTimer.stop();
					unloadContent();
					stage.close();
				}
            }
    	} );
    }
    
    
    private void loadContent() {
        playingPieces = new Image( "content/textures/Tile_Sheet.png" );
        backgroundScreen = new Image( "content/textures/Background.png" );
        titleScreen = new Image( "content/textures/TitleScreen.png" );
    }
    private void unloadContent()  {
    }
    private void update(long currentNanoTime) {
    }
    private void draw(long currentNanoTime) {
    }
}
