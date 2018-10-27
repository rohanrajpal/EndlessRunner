package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.SpaceRunnerButton;
import model.SpaceRunnerSubScene;

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
    private SpaceRunnerSubScene shipchooserSubScene;

    private final static int MENU_BUTTONS_START_X = 100;
    private final static int MENU_BUTTONS_START_Y = 150;

    List<SpaceRunnerButton> menuButtons;


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

    private void createSubScenes(){
        creditsSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(creditsSubScene);

        helpSubScene =  new SpaceRunnerSubScene();
        mainPane.getChildren().add(helpSubScene);

        scoreSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(scoreSubScene);

        shipchooserSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(shipchooserSubScene);


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
                shipchooserSubScene.moveSubScene();
            }
        });
    }
    private void createScoreButton(){
        SpaceRunnerButton scorebutton = new SpaceRunnerButton("SCORES");
        addMenuButton(scorebutton);

        scorebutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scoreSubScene.moveSubScene();
            }
        });
    }
    private void createHelpButton(){
        SpaceRunnerButton helpButton = new SpaceRunnerButton("HELP");
        addMenuButton(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                helpSubScene.moveSubScene();
            }
        });
    }

    private void createCreditsButton(){
        SpaceRunnerButton creditsb = new SpaceRunnerButton("CREDITS");
        addMenuButton(creditsb);

        creditsb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                creditsSubScene.moveSubScene();
            }
        });
    }

    private void createExitButton(){
        SpaceRunnerButton exitb = new SpaceRunnerButton("EXIT");
        addMenuButton(exitb);
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
