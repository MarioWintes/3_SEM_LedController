package at.edu.c02.ledcontroller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class should handle all HTTP communication with the server.
 * Each method here should correspond to an API call, accept the correct parameters and return the response.
 * Do not implement any other logic here - the ApiService will be mocked to unit test the logic without needing a server.
 */
public class ApiServiceImpl implements ApiService {
    /**
     * This method calls the `GET /getLights` endpoint and returns the response.
     * TODO: When adding additional API calls, refactor this method. Extract/Create at least one private method that
     * handles the API call + JSON conversion (so that you do not have duplicate code across multiple API calls)
     *
     * @return `getLights` response JSON object
     * @throws IOException Throws if the request could not be completed successfully
     */
    @Override
    public JSONObject getLights() throws IOException
    {
        // Get String response
        String response = createResponse();

        return new JSONObject(response);
    }

    @Override
    public JSONObject getLight(int id) throws IOException {
        JSONObject jsonObject = getLights();


        JSONArray lights = jsonObject.getJSONArray("lights");
        for(int i=0; i<lights.length(); i++){
            JSONObject light = lights.getJSONObject(i);
            if(light.get("id").equals(id)){
                return light;
            }
        }
        return null;
    }

    @Override
    public void setColorAndStateOfLight(int id, boolean state, String color) throws IOException{
        HttpURLConnection con = getConnection("PUT", "vs44kgGGmVV","https://balanced-civet-91.hasura.app/api/rest/setLight");
        String jsonInputString = "{\"id\":" + id + ",\"state\": " + state + ",\"color\": \"" + color + "\"}";
        con.setDoOutput(true);

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Read the response code
        int responseCode = con.getResponseCode();
        if(responseCode != HttpURLConnection.HTTP_OK) {
            // Something went wrong with the request
            throw new IOException("Error: getLights request failed with response code " + responseCode);
        }

        // The request was successful, read the response
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        // Save the response in this StringBuilder
        StringBuilder sb = new StringBuilder();

        int character;
        // Read the response, character by character. The response ends when we read -1.
        while((character = reader.read()) != -1) {
            sb.append((char) character);
        }

        System.out.println(sb.toString());
    }

    private String createResponse() throws IOException {
        HttpURLConnection connection = getConnection("GET", "Todo","https://balanced-civet-91.hasura.app/api/rest/getLights");
        // Read the response code
        int responseCode = connection.getResponseCode();
        if(responseCode != HttpURLConnection.HTTP_OK) {
            // Something went wrong with the request
            throw new IOException("Error: getLights request failed with response code " + responseCode);
        }

        // The request was successful, read the response
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        // Save the response in this StringBuilder
        StringBuilder sb = new StringBuilder();

        int character;
        // Read the response, character by character. The response ends when we read -1.
        while((character = reader.read()) != -1) {
            sb.append((char) character);
        }

       return sb.toString();
    }

    private static HttpURLConnection getConnection(String requestMethod, String prop, String urlLocal) throws IOException {
        // Connect to the server
        URL url = new URL(urlLocal);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // and send a GET request
        connection.setRequestMethod(requestMethod);
        connection.setRequestProperty("X-Hasura-Group-ID", prop);
        return connection;
    }
}
