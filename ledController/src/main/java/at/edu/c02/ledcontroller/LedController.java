package at.edu.c02.ledcontroller;

import org.json.JSONArray;

import java.io.IOException;

public interface LedController {
    void demo() throws IOException;
    JSONArray getGroupLeds(String targetGroup) throws IOException;
    void getLight(int id) throws IOException;
}
