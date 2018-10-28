package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;
import java.util.List;


public class ViewManager {

    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768 ;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    private SpaceRunnerSubScene creditsSubScene;
    private SpaceRunnerSubScene helpSubScene;
    private SpaceRunnerSubScene scoreSubScene;
    private SpaceRunnerSubScene shipChooserSubScene;

    private SpaceRunnerSubScene sceneToHide;

    private final static int MENU_BUTTONS_START_X = 100;
    private final static int MENU_BUTTONS_START_Y = 150;

    List<SpaceRunnerButton> menuButtons;
    List<ShipPicker> shipsList;
    private SHIP chosenSHIP;

    public ViewManager() {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage =  new Stage();
        mainStage.setScene(mainScene);
        createSubScenes();
        createButtons();
        createBackground();
        createLogo();
    }

    private void showSubScene(SpaceRunnerSubScene subScene){
        if (sceneToHide != null){
            sceneToHide.moveSubScene();
        }

        subScene.moveSubScene();
        sceneToHide = subScene;
    }
    private void createSubScenes(){
        creditsSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(creditsSubScene);

        helpSubScene =  new SpaceRunnerSubScene();
        mainPane.getChildren().add(helpSubScene);

        scoreSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(scoreSubScene);

//        shipChooserSubScene = new SpaceRunnerSubScene();
//        mainPane.getChildren().add(shipChooserSubScene);

        createShipChooserSubScene();
    }

    private void createShipChooserSubScene() {
        shipChooserSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(shipChooserSubScene);

        InfoLabel chooseShipLabel = new InfoLabel("CHOOSE YOUR SHIP");
        chooseShipLabel.setLayoutX(110);
        chooseShipLabel.setLayoutY(25);

        shipChooserSubScene.getPane().getChildren().add(chooseShipLabel);
        shipChooserSubScene.getPane().getChildren().add(createShipsToChoose());
        shipChooserSubScene.getPane().getChildren().add(createButtonToStart());
    }

    private void addMenuButton(SpaceRunnerButton button){
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y+menuButtons.size()*100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    public Stage getMainStage(){
        return mainStage;
    }

    private HBox createShipsToChoose(){
        HBox box = new HBox();
        box.setSpacing(20);
        shipsList = new ArrayList<>();
        for (SHIP ship: SHIP.values()){
            ShipPicker shipToPick = new ShipPicker(ship);
            shipsList.add(shipToPick);
            box.getChildren().add(shipToPick);
            shipToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (ShipPicker ship : shipsList){
                        ship.setIsCircleChoosen(false);
                    }
                    shipToPick.setIsCircleChoosen(true);
                    chosenSHIP = shipToPick.getShip();
                }
            });
        }
        box.setLayoutX(300 - (118*2));
        box.setLayoutY(100);
        return box;
    }
    private SpaceRunnerButton createButtonToStart(){
        SpaceRunnerButton startButton = new SpaceRunnerButton("START");
        startButton.setLayoutX(350);
        startButton.setLayoutY(300);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (chosenSHIP != null){
                    GameViewManager gameManager = new GameViewManager();
                    gameManager.createNewGame(mainStage,chosenSHIP);
                }
            }
        });
        return startButton;
    }
    private void createButtons(){
        createStartButton();
        createScoreButton();
        createHelpButton();
        createCreditsButton();
        createExitButton();
    }
    private void createStartButton(){
        SpaceRunnerButton startButton = new SpaceRunnerButton("PLAY");
        addMenuButton(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(shipChooserSubScene);
            }
        });
    }
    private void createScoreButton(){
        SpaceRunnerButton scorebutton = new SpaceRunnerButton("SCORES");
        addMenuButton(scorebutton);

        scorebutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(scoreSubScene);
            }
        });
    }
    private void createHelpButton(){
        SpaceRunnerButton helpButton = new SpaceRunnerButton("HELP");
        addMenuButton(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(helpSubScene);
            }
        });
    }

    private void createCreditsButton(){
        SpaceRunnerButton creditsb = new SpaceRunnerButton("CREDITS");
        addMenuButton(creditsb);

        creditsb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(creditsSubScene);
            }
        });
    }

    private void createExitButton(){
        SpaceRunnerButton exitb = new SpaceRunnerButton("EXIT");
        addMenuButton(exitb);

        exitb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });
    }
    private void createBackground(){
        Image backimage = new Image("view/resources/blue.png",256,256,false,true);

        BackgroundImage background = new BackgroundImage(backimage, BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,null);

        mainPane.setBackground(new Background(background));
    }
    private void createLogo(){
        ImageView logo = new ImageView("view/resources/space_runner_logo.png");
        logo.setLayoutX(500);
        logo.setLayoutY(6);
        logo.setFitHeight(170);
        logo.setFitWidth(430);
        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(new DropShadow());
            }
        });

        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(null);
            }
        });

        mainPane.getChildren().add(logo);
    }
}
