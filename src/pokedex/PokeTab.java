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

class PokeTab extends Tab {

    private static int max;
    private Pokedex pokedex;
    private int i = 1;
    private BorderPane pane;
    private Text text;
    private TextField searchText;
    private Button left,right,mute,exit;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;

    PokeTab(Stage stage) throws Exception {
        pokedex = new Pokedex();
        max = Pokedex.max;
        start(stage);
    }

    private void start(Stage stage) throws Exception {
        setPane();
        pane.setStyle("-fx-background-color: dimgray;");

        musicSetup();
        pane.getChildren().add(mediaView);

        text = new Text();
        text.setFont(new Font(20));
        text.setFill(Color.WHITE);

        setIm(pokedex.identify_Pokemon(i).getDir());
        pane.setCenter(setIm(pokedex.identify_Pokemon(i).getDir()));

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
        mediaView = new MediaView(mediaPlayer);
    }

    private void keySetup() {
        pane.setOnKeyReleased(e-> {
            switch(e.getCode()){
                default: break;
                case  RIGHT:
                    if ((i == max)) {
                        i = 1;
                    } else {
                        i++;
                    }
                    break;
                case LEFT:
                    if ((i == 1)) {
                        i = max;
                    } else {
                        i--;
                    }
                    break;
                case ENTER:
                    try {
                        try{
                            if(Integer.parseInt(searchText.getText())>max){
                                searchText.setText("No es un número válido");
                            }else {
                                i = Integer.parseInt(searchText.getText());
                            }
                        }catch (Exception e1){
                            if(pokedex.write_Name(searchText.getText().toLowerCase())==0){
                                searchText.setText("No es un nombre válido");
                            }else {
                                i = pokedex.write_Name(searchText.getText().toLowerCase());
                            }
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    break;
                case ESCAPE:
                    Platform.exit();
                    break;
            }
            try {
                pane.setCenter(setIm(pokedex.identify_Pokemon(i).getDir()));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    private void buttonSetup() {
        left = new Button("Anterior");
        right = new Button("Siguiente");
        mute = new Button("Mutear");
        exit = new Button("Salir");

        left.setOnAction(e -> {
            if ((i == 1)) {
                i = max;
            } else {
                i--;
            }
            try {
                pane.setCenter(setIm(pokedex.identify_Pokemon(i).getDir()));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        right.setOnAction(e-> {
            if ((i == max)) {
                i = 1;
            } else {
                i++;
            }
            try {
                pane.setCenter(setIm(pokedex.identify_Pokemon(i).getDir()));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        mute.setOnAction(e->{
            if(mediaPlayer.isMute()){
                mediaPlayer.setMute(false);
                mute.setText("Mutear");
            }else{
                mediaPlayer.setMute(true);
                mute.setText("Desmutear");
            }
        });
        exit.setOnAction(e-> Platform.exit());
    }

    private ImageView setIm(String dir) throws Exception{
        FileInputStream inStream = new FileInputStream(dir);

        Image image = new Image(inStream);

        ImageView imView = new ImageView(image);

        imView.setX(600);
        imView.setY(300);
        imView.setPreserveRatio(true);

        Pokemon p = pokedex.identify_Pokemon(i);
        if(p==null) {
            throw new Exception();
        } else text.setText(i+"-> "+p.toString());
        pane.setTop(text);

        return imView;
    }

}