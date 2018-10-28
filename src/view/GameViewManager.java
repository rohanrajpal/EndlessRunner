package view;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.SHIP;
import model.SmallInfoLabel;

import java.security.Key;
import java.util.Random;

public class GameViewManager {

    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 800;

    private Stage menuStage;
    private ImageView ship;

    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private int angle;
    private AnimationTimer gameTimer;

    private GridPane gridPane1;
    private GridPane gridPane2;
    private final static String BACKGROUND_IMAGE = "view/resources/blue.png";


    private final static String METEOR_BROWN_IMAGE = "view/resources/meteorBrown_small1.png";
    private final static String METEOR_GREY_IMAGE = "view/resources/meteorGrey_small1.png";

    private ImageView[] brownMeteors;
    private ImageView[] greyMeteors;
    Random randomPositionGenerator;

    private ImageView star;
    private SmallInfoLabel pointsLabel;
    private ImageView[] playerLifes;
    private int playerLife;
    private int points;
    private final static String GOLD_STAR_IMAGE = "view/resources/star_gold.png";

    private final static int STAR_RADIUS = 12;
    private final static int SHIP_RADIUS = 27;
    private final static int METEOR_RADIUS = 20;


    public GameViewManager(){
        initializeStage();
        createKeyListeners();
        randomPositionGenerator =  new Random();
    }

    private void createKeyListeners() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT){
                    isLeftKeyPressed = true;
                }else if (event.getCode() == KeyCode.RIGHT){
                    isRightKeyPressed = true;
                }
            }
        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT){
                    isLeftKeyPressed = false;
                }else if (event.getCode() == KeyCode.RIGHT){
                    isRightKeyPressed = false;
                }
            }
        });
    }

    private void initializeStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane,GAME_WIDTH,GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    public void createNewGame(Stage menuStage, SHIP choosenShip){
        this.menuStage = menuStage;
        this.menuStage.hide();
        createBackround();
        createShip(choosenShip);
        createGameElements(choosenShip);
        createGameLoop();
        gameStage.show();
    }

    private void createGameElements(SHIP choosenShip){
        playerLife = 2;
        star = new ImageView(GOLD_STAR_IMAGE);
        setNewElementsPosition(star);
        gamePane.getChildren().add(star);
        pointsLabel = new SmallInfoLabel("POINTS : 00");
        pointsLabel.setLayoutX(460);
        pointsLabel.setLayoutY(20);

        gamePane.getChildren().add(pointsLabel);

        playerLifes = new ImageView[3];

        for (int i=0;i<playerLifes.length;i++){
            playerLifes[i] = new ImageView(choosenShip.getUrlLife());
            playerLifes[i].setLayoutX(455 + (i*50));
            playerLifes[i].setLayoutY(80);
            gamePane.getChildren().add(playerLifes[i]);
        }
        brownMeteors = new ImageView[3];
        for (int i=0;i<brownMeteors.length;i++){
            brownMeteors[i] = new ImageView(METEOR_BROWN_IMAGE);
            setNewElementsPosition(brownMeteors[i]);
            gamePane.getChildren().add(brownMeteors[i]);
        }

        greyMeteors = new ImageView[3];
        for (int i=0;i<greyMeteors.length;i++){
            greyMeteors[i] = new ImageView(METEOR_GREY_IMAGE);
            setNewElementsPosition(greyMeteors[i]);
            gamePane.getChildren().add(greyMeteors[i]);
        }

    }

    private void moveGameElements(){

        star.setLayoutY(star.getLayoutY() + 5);

        for (int i=0;i<brownMeteors.length;i++){
            brownMeteors[i].setLayoutY(brownMeteors[i].getLayoutY()+7);
            brownMeteors[i].setRotate(brownMeteors[i].getRotate() + 4);
        }

        for (int i=0;i<greyMeteors.length;i++){
            greyMeteors[i].setLayoutY(greyMeteors[i].getLayoutY()+7);
            greyMeteors[i].setRotate(greyMeteors[i].getRotate() + 4);
        }
    }

    private void checkIfElementsAreBehindTheShipAndRelocate(){
        if (star.getLayoutY() > 1200){
            setNewElementsPosition(star);
        }

        for (int i=0;i<brownMeteors.length;i++){
            if (brownMeteors[i].getLayoutY()>900){
                setNewElementsPosition(brownMeteors[i]);
            }
        }

        for (int i=0;i<greyMeteors.length;i++){
            if (greyMeteors[i].getLayoutY()>900){
                setNewElementsPosition(greyMeteors[i]);
            }
        }
    }
    private void setNewElementsPosition(ImageView image){
        image.setLayoutX(randomPositionGenerator.nextInt(370));
        image.setLayoutY(-randomPositionGenerator.nextInt(3200)+600);
    }
    private void createShip(SHIP chosenSHIP){
        ship = new ImageView(chosenSHIP.getUrlShip());
        ship.setLayoutX(GAME_WIDTH/2);
        ship.setLayoutY(GAME_HEIGHT-90);

        gamePane.getChildren().add(ship);
    }

    private void createGameLoop(){
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveShip();
                moveBackground();
                checkIfElementsAreBehindTheShipAndRelocate();
                moveGameElements();
                checkIfElementsCollide();
            }
        };

        gameTimer.start();
    }

    private void moveShip(){
//        System.out.println(angle);
        if (isLeftKeyPressed && !isRightKeyPressed){
            if (angle > -30){
                angle -= 5;
            }
            ship.setRotate(angle);
            if (ship.getLayoutX() > -20){
                ship.setLayoutX(ship.getLayoutX() - 3);
            }
        }

        if (isRightKeyPressed && !isLeftKeyPressed){
            if (angle < 30){
                angle += 5;
            }
            ship.setRotate(angle);

            if (ship.getLayoutX()<522){
                ship.setLayoutX(ship.getLayoutX()+3);
            }
        }

        if (!isLeftKeyPressed && !isRightKeyPressed){
            if (angle<0){
                angle +=5;
            }
            else if (angle>0){
                angle -=5;
            }
            ship.setRotate(angle);
        }

        if (isLeftKeyPressed && isRightKeyPressed){
            if (angle<0){
                angle +=5;
            }
            else if (angle>0){
                angle -=5;
            }
            ship.setRotate(angle);
        }
    }

    private void createBackround(){
        gridPane1 = new GridPane();
        gridPane2 = new GridPane();

        for (int i = 0;i<12;i++){
            ImageView backgroundImage1 = new ImageView(BACKGROUND_IMAGE);
            ImageView backgroundImage2 = new ImageView(BACKGROUND_IMAGE);

            GridPane.setConstraints(backgroundImage1,i%3,i/3);
            GridPane.setConstraints(backgroundImage2,i%3,i/3);

            gridPane2.getChildren().add(backgroundImage2);
            gridPane1.getChildren().add(backgroundImage1);
        }

        gridPane2.setLayoutY(-1024);
        gamePane.getChildren().addAll(gridPane1, gridPane2);
    }

    private void moveBackground(){
        gridPane1.setLayoutY(gridPane1.getLayoutY() + 0.5);
        gridPane2.setLayoutY(gridPane2.getLayoutY() + 0.5);

        if (gridPane1.getLayoutY()>=1024){
            gridPane1.setLayoutY(-1024);
        }

        if (gridPane2.getLayoutY()>=1024){
            gridPane2.setLayoutY(-1024);
        }
    }
    private void checkIfElementsCollide(){
        if (SHIP_RADIUS+STAR_RADIUS > calcculateDistance(ship.getLayoutX()+49,star.getLayoutX()+15,
                ship.getLayoutY()+37,star.getLayoutY()+15)){
            setNewElementsPosition(star);
            points++;
            String textToSet = "POINTS : ";
            if (points < 10){
                textToSet = textToSet + "0";
            }
            pointsLabel.setText(textToSet + points);
        }

        for (int i=0;i<brownMeteors.length;i++){
            if (METEOR_RADIUS+SHIP_RADIUS > calcculateDistance(ship.getLayoutX()+49,brownMeteors[i].getLayoutX()+20,
                    ship.getLayoutY()+37,brownMeteors[i].getLayoutY()+20)){
                removeLife();
                setNewElementsPosition(brownMeteors[i]);
            }
        }

        for (int i=0;i<greyMeteors.length;i++){
            if (METEOR_RADIUS+SHIP_RADIUS > calcculateDistance(ship.getLayoutX()+49,greyMeteors[i].getLayoutX()+20,
                    ship.getLayoutY()+37,greyMeteors[i].getLayoutY()+20)){
                removeLife();
                setNewElementsPosition(greyMeteors[i]);
            }
        }
    }
    private void removeLife(){

        gamePane.getChildren().remove(playerLifes[playerLife]);
        playerLife--;
        if (playerLife<0){
            gameStage.close();
            gameTimer.stop();
            menuStage.show();
        }
    }
    private double calcculateDistance(double x1,double x2,double y1,double y2){
        return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }
}
