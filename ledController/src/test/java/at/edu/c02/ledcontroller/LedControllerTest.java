package at.edu.c02.ledcontroller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class LedControllerTest {
    /**
     * This test is just here to check if tests get executed. Feel free to delete it when adding your own tests.
     * Take a look at the stack calculator tests again if you are unsure where to start.
     */



    @Test
    public void testApiService() throws Exception{
        ApiService apiService = mock(ApiService.class);


        JSONObject response = new JSONObject();
        response.put("lights",new JSONArray());

        when(apiService.getLights()).thenReturn(response);

        LedController ledController = new LedControllerImpl(apiService);

        ledController.getGroupLeds("A");
        verify(apiService).getLights();

        verifyNoMoreInteractions(apiService);
    }


    @Test
    public void testEndToEnd_2_1_Task()throws Exception{
        ApiService api = new ApiServiceImpl();

        int ledId = 61;
        boolean state = true;
        String color = "#0f0";


        api.setColorAndStateOfLight(ledId, state, color);

        JSONObject response = api.getLights();
        JSONArray lights = response.getJSONArray("lights");
        JSONObject led = lights.getJSONObject(0);

        boolean on = led.getBoolean("on");
        String cur_color = led.getString("color");

        assertEquals("LED status did not match!", state, on);
        assertEquals("LED color did not match!", color, cur_color);
    }

}
