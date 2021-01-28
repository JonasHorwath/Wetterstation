package sample;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Api {

    private static double brightness, temperatureOut, temperatureIn, pressure, altitude, humidity;
    private static JSONObject jsonObject;

    public static void connect() throws IOException, ParseException {
        // Create a neat value object to hold the URL
        URL url = new URL("http://10.0.0.103:8000/weatherstation");

        // Open a connection(?) on the URL(??) and cast the response(???)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // This line makes the request
        InputStream responseStream = connection.getInputStream();

        JSONParser jsonParser = new JSONParser();
        jsonObject = (JSONObject)jsonParser.parse(
                new InputStreamReader(responseStream, StandardCharsets.UTF_8));
    }

    public static String getBrightness() {
        return jsonObject.get("brightness").toString();

    }

    public static String getTemperatureOut() {
        return jsonObject.get("temperatureout").toString();

    }

    public static String getTemperatureIn() {
        return jsonObject.get("temperaturein").toString();

    }

    public static String getPressure() {
        return jsonObject.get("pressure").toString();

    }

    public static String getAltitude() {
        return jsonObject.get("altitude").toString();

    }

    public static String getHumidity() {
        return jsonObject.get("humidity").toString();
    }

}