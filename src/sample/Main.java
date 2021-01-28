package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    double xOffset, yOffset;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Wetterstation");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.getIcons().add(new Image("Pictures/Icon.png"));
        primaryStage.show();

        Controller c = loader.getController();
        c.bindToTime();

        root.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });

        root.setOnMouseDragged(mouseEvent -> {
            primaryStage.setX(mouseEvent.getScreenX() - xOffset);
            primaryStage.setY(mouseEvent.getScreenY() - yOffset);
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
