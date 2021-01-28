package sample;

import com.dustinredmond.fxtrayicon.FXTrayIcon;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Controller {

    boolean startStop = true;
    boolean minimized = false;
    Timeline timeline;
    FXTrayIcon trayIcon;
    Stage stage;
    MenuItem open, exit;

    @FXML
    private Label brightnessLabel, temperatureInLabel, temperatureOutLabel, pressureLabel, altitudeLabel, humidityLabel;

    @FXML
    private Button refreshButton, closeButton, iconifyButton;

    @FXML
    public void closeWindow(ActionEvent event) {

        stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

        trayIcon = new FXTrayIcon(stage, getClass().getResource("../Pictures/Icon.png"));
        trayIcon.showMinimal();

        open = new MenuItem("Wetterstation");
        exit = new MenuItem("Exit");

        trayIcon.setOnAction(event1 -> {
            trayIcon.hide();
            stage.show();
            minimized = false;
        });

        open.setOnAction(event1 -> {
            stage.show();
            minimized = false;
            trayIcon.hide();
        });

        exit.setOnAction(event1 -> {
            stage.close();
            minimized = true;
            trayIcon.hide();
        });

        trayIcon.addMenuItem(open);
        trayIcon.addMenuItem(exit);
        minimized = true;

    }

    @FXML
    public void iconifyWindow(ActionEvent event) {

        stage = (Stage) closeButton.getScene().getWindow();
        stage.setIconified(true);

    }

    @FXML
    public void refresh(ActionEvent event)  {
        if (startStop) {
            refreshButton.setText("Start");
            bindToTime();
            startStop = false;
        } else {
            refreshButton.setText("Stop");
            timeline.stop();
            startStop = true;
        }
    }

    public void bindToTime() {

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        actionEvent -> {
                            try {
                                Api.connect();
                            } catch (IOException | ParseException e) {
                                e.printStackTrace();
                            }
                            if (minimized) {
                                trayIcon.setTrayIconTooltip("Brightness: " + Api.getBrightness() + " %\n" +
                                        "Temperature " + Api.getTemperatureOut() + " / " +
                                        Api.getTemperatureIn() + " °C\n" +
                                        "Pressure: " + Api.getPressure() + " hPa\n" +
                                        "Altitude: " + Api.getAltitude() + " m\n" +
                                        "Humidity: " + Api.getHumidity()+ " %\n");
                            } else {
                                brightnessLabel.setText(Api.getBrightness());
                                temperatureOutLabel.setText(Api.getTemperatureOut());
                                temperatureInLabel.setText(Api.getTemperatureIn());
                                pressureLabel.setText(Api.getPressure());
                                altitudeLabel.setText(Api.getAltitude());
                                humidityLabel.setText(Api.getHumidity());
                            }
                        }
                ),
                new KeyFrame(Duration.seconds(0.1))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }


}

