package at.edu.c02.ledcontroller;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    /**
     * This is the main program entry point. TODO: add new commands when implementing additional features.
     */
    public static void main(String[] args) throws IOException {
        LedController ledController = new LedControllerImpl(new ApiServiceImpl());


        String input = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(!input.equalsIgnoreCase("exit"))
        {
            System.out.println("=== LED Controller ===");
            System.out.println("Enter 'demo' to send a demo request");
            System.out.println("Enter 'ledgroup' to get all Leds per group");
            System.out.println("Enter 'exit' to exit the program");
            input = reader.readLine();
            if(input.equalsIgnoreCase("demo"))
            {
                ledController.demo();
            } else if (input.equalsIgnoreCase("groupstatus")) {
                JSONArray lights = ledController.getGroupLeds("A");
                System.out.println(lights.toString(2));
            }else if(input.equalsIgnoreCase("status")) {
                System.out.print("Please specify LED ID: ");
                Scanner sc = new Scanner(System.in);
                int id = sc.nextInt();
                ledController.getLight(id);
            }else if(input.equalsIgnoreCase("turnon")) {
                System.out.println("Note - IDs of Group A from 54 to 61");
                System.out.print("Please enter ID of LED: ");
                Scanner sc = new Scanner(System.in);
                int id = sc.nextInt();
                ledController.setColorAndState(id);
            }
        }
    }
}
