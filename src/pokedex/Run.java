package pokedex;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Run extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: darkred;");

        Button startBut = new Button("Abrir Pokedex");
        startButtonSetup(startBut, stage);


        Text text = new Text("Pokedex Primera Generación");
        text.setFont(new Font(30));
        ImageView iv = setImage();

        root.getChildren().addAll(text,iv,startBut);
        root.setSpacing(50);
        Scene scene = new Scene(root,600,600);

        stage.setScene(scene);
        stage.setTitle("Pokedex");
        try {
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/Icon/icon.png")));
        }catch (Exception e1){
            e1.printStackTrace();
        }
        stage.show();
    }

    private void startButtonSetup(Button startBut, Stage stage) {
        startBut.setOnAction(e-> {
            try {
                new PokeTab(stage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    private ImageView setImage() throws Exception{
        FileInputStream inStream = new FileInputStream(".\\Pokemon\\pokedex.jpg");

        Image image = new Image(inStream);

        ImageView imView = new ImageView(image);

        imView.setFitHeight(400);
        imView.setPreserveRatio(true);

        return imView;
    }
}
