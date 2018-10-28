package model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static javafx.scene.layout.BackgroundRepeat.NO_REPEAT;
import static javafx.scene.text.Font.*;

public class InfoLabel extends Label {

    public static final String FONT_PATH = "src/model/resources/kenvector_future.ttf";

    public final static String BACKGROUND_IMAGE = "view/resources/yellow_small_panel.png";

    public InfoLabel(String text) {
        super(text);

        setPrefWidth(380);
        setPrefHeight(49);
        setText(text);
        setWrapText(true);
        setLabelFont();
        setAlignment(Pos.CENTER);

        BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE,380,49,
                false,
                true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);

        setBackground(new Background(backgroundImage));
    }

    private void setLabelFont(){
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),23));
        } catch (FileNotFoundException e) {
            setFont(font("Verdana",23));
        }
    }
}
