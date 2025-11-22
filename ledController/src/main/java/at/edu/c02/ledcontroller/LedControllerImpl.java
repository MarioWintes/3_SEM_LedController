package at.edu.c02.ledcontroller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * This class handles the actual logic
 */
public class LedControllerImpl implements LedController {
    private final ApiService apiService;

    public LedControllerImpl(ApiService apiService)
    {
        this.apiService = apiService;
    }

    @Override
    public void demo() throws IOException
    {
        // Call `getLights`, the response is a json object in the form `{ "lights": [ { ... }, { ... } ] }`
        JSONObject response = apiService.getLights();
        // get the "lights" array from the response
        JSONArray lights = response.getJSONArray("lights");
        // read the first json object of the lights array
        JSONObject firstLight = lights.getJSONObject(0);
        // read int and string properties of the light
        System.out.println("First light id is: " + firstLight.getInt("id"));
        System.out.println("First light color is: " + firstLight.getString("color"));
    }


    @Override
    public JSONArray getGroupLeds(String targetGroup) throws IOException {

        JSONObject response = apiService.getLights();

        JSONArray lights = response.getJSONArray("lights");

        JSONArray lightsPerGroup = new JSONArray();

        for (int i = 0; i < lights.length(); i++){
            JSONObject light = lights.getJSONObject(i);

            JSONObject groupObj = light.getJSONObject("groupByGroup");
            String groupName = groupObj.optString("name", "");

            if (targetGroup.equals(groupName)){
                lightsPerGroup.put(light);
            }
        }

        return lightsPerGroup;


    }

    @Override
    public void getLight(int id) throws IOException {
        JSONObject light = apiService.getLight(id);

        if (light.getBoolean("on")){
            System.out.println("LED " + id + " is currently on. Color: " + light.getString("color"));
        }else{
            System.out.println("LED " + id + " is not currently off. Color: " + light.getString("color"));
        }
    }

    @Override
    public void setColorAndState(int id) throws IOException {
        JSONObject light = apiService.getLight(id);
        apiService.setColorAndStateOfLight(light);
    }
}
