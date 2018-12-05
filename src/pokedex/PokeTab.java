package pokedex;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

class PokeTab extends Tab {

    private final int max;
    private final Pokedex pokedex;
    private int currentPokemon = 1;
    private BorderPane pane;
    private Text text;
    private TextField searchText;
    private Button left,right,mute,exit;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;

    public PokeTab(Stage stage) {
        pokedex = new Pokedex();
        max = pokedex.getMax();
        start(stage);
    }

    private void start(Stage stage) {
        setPane();
        pane.setStyle("-fx-background-color: dimgray;");

        musicSetup();
        pane.getChildren().add(mediaView);

        text = new Text();
        text.setFont(new Font(20));
        text.setFill(Color.WHITE);

        setImage(pokedex.identifyPokemon(currentPokemon).getDir());
        pane.setCenter(setImage(pokedex.identifyPokemon(currentPokemon).getDir()));

        Scene vScene = new Scene(pane,600,600);

        stage.setScene(vScene);
        stage.centerOnScreen();
    }

    private void setPane() {
        pane = new BorderPane();

        HBox hbox = new HBox();
        hbox.setStyle("-fx-background-color: darkred;");
        hbox.setSpacing(20);
        hbox.setPrefHeight(35);
        hbox.setAlignment(Pos.CENTER);

        searchText = new TextField();
        searchText.setPrefWidth(150);
        searchText.setText("Inserta nombre o número");
        searchText.setOnMouseClicked(e->searchText.setText(""));

        buttonSetup();
        hbox.getChildren().addAll(left,right,searchText,mute,exit);
        keySetup();
        pane.setBottom(hbox);
    }

    private void musicSetup() {
        String path = ".\\Pokemon\\music.mp3";
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
        mediaPlayer.setVolume(0.05);
        mediaView = new MediaView(mediaPlayer);
    }

    private void keySetup() {
        pane.setOnKeyReleased(e-> {
            switch(e.getCode()){
                default: break;
                case  RIGHT:
                    if ((currentPokemon == max)) {
                        currentPokemon = 1;
                    } else {
                        currentPokemon++;
                    }
                    break;
                case LEFT:
                    if ((currentPokemon == 1)) {
                        currentPokemon = max;
                    } else {
                        currentPokemon--;
                    }
                    break;
                case ENTER:
                    enterKey();
                    break;
                case ESCAPE:
                    Platform.exit();
                    break;
            }
            Pokemon p = pokedex.identifyPokemon(currentPokemon);
            if(p != null) pane.setCenter(setImage(p.getDir()));
        });
    }

    private void enterKey() {
        try {
            if (Integer.parseInt(searchText.getText()) > max) {
                searchText.setText("No es un número válido");
            } else {
                currentPokemon = Integer.parseInt(searchText.getText());
            }
        }catch (Exception e){
            if(pokedex.writeName(searchText.getText().toLowerCase())==0){
                searchText.setText("No es un nombre válido");
            }else {
                currentPokemon = pokedex.writeName(searchText.getText().toLowerCase());
            }
        }
    }

    private void buttonSetup() {
        left = new Button("Anterior");
        right = new Button("Siguiente");
        mute = new Button("Mutear");
        exit = new Button("Salir");
        leftButtonSetup();
        rightButtonSetup();
        muteButtonSetup();

        exit.setOnAction(e-> Platform.exit());
    }

    private void muteButtonSetup() {
        mute.setOnAction(e->{
            if(mediaPlayer.isMute()){
                mediaPlayer.setMute(false);
                mute.setText("Mutear");
            }else{
                mediaPlayer.setMute(true);
                mute.setText("Desmutear");
            }
        });
    }

    private void rightButtonSetup() {
        right.setOnAction(e-> {
            if ((currentPokemon == max)) {
                currentPokemon = 1;
            } else {
                currentPokemon++;
            }
            Pokemon p = pokedex.identifyPokemon(currentPokemon);
            if(p != null) pane.setCenter(setImage(p.getDir()));
        });
    }

    private void leftButtonSetup() {
        left.setOnAction(e -> {
            if ((currentPokemon == 1)) {
                currentPokemon = max;
            } else {
                currentPokemon--;
            }
            Pokemon p = pokedex.identifyPokemon(currentPokemon);
            if(p != null) pane.setCenter(setImage(p.getDir()));
        });
    }

    private ImageView setImage(String dir){
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(dir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Image image = new Image(inStream);

        ImageView imView = new ImageView(image);

        imView.setX(600);
        imView.setY(300);
        imView.setPreserveRatio(true);

        Pokemon p = pokedex.identifyPokemon(currentPokemon);
        if(p!=null) text.setText(currentPokemon +"-> "+p.toString());
        pane.setTop(text);

        return imView;
    }

}