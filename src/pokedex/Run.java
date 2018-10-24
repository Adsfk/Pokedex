package pokedex;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Run extends Application {

    private static Pokedex pokedex;

    public static void main(String[] args){
        pokedex = new Pokedex();
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);

        Text text = new Text();

        text.setFont(new Font(45));

        text.setText("HAS ABIERTO LA POKEDEX :D");

        HBox layout = new HBox();
        layout.setAlignment(Pos.BASELINE_CENTER);

        root.setStyle("-fx-background-color: darkred;");

        root.setSpacing(20);
        layout.setSpacing(10);

        Button name = new Button("Nombre");
        Button number = new Button("Número");
        Button show = new Button("Mostrar");
        Button exit = new Button("Salir");

        layout.getChildren().addAll(name, number, show, exit);

        exit.setOnAction(e->Platform.exit());

        root.getChildren().addAll(text,layout);

        Scene scene = new Scene(root,600,500);

        stage.setScene(scene);

        stage.setTitle("Pokedex");

        stage.show();
    }
}
