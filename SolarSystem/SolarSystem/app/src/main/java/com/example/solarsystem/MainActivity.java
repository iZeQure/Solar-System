package com.example.solarsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import tech.gusavila92.websocketclient.WebSocketClient;

public class MainActivity extends AppCompatActivity {

    private static ArrayList<Planet> planets = new ArrayList<Planet>(){};
    private WebSocketClient webSocketClient;

    Switch mercury;
    Switch venus;
    Switch earth;
    Switch mars ;
    Switch jupiter;
    Switch saturn ;
    Switch uranus ;
    Switch neptune ;
    Switch pluto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CreateWebSocketClient();
        AddPlanets();

         mercury = (Switch) findViewById(R.id.merkurShitch);
         venus = (Switch) findViewById(R.id.venusSwitch);
         earth = (Switch) findViewById(R.id.jordenSwitch);
         mars = (Switch) findViewById(R.id.marsSwitch);
         jupiter = (Switch) findViewById(R.id.jupiterSwitch);
         saturn = (Switch) findViewById(R.id.saturnSwitch);
         uranus = (Switch) findViewById(R.id.uranusSwitch);
         neptune = (Switch) findViewById(R.id.neptuneSwitch);
         pluto = (Switch) findViewById(R.id.plutoSwitch);

         mercury.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 webSocketClient.send("21");
             }
         });

        venus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webSocketClient.send("22");
            }
        });

        earth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webSocketClient.send("23");
            }
        });

        mars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webSocketClient.send("24");
            }
        });

        jupiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webSocketClient.send("25");
            }
        });

        saturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webSocketClient.send("26");
            }
        });

        uranus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webSocketClient.send("27");
            }
        });

        neptune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webSocketClient.send("28");
            }
        });

        pluto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webSocketClient.send("29");
            }
        });
    }

    private void AddPlanets() {
        planets.add(new Planet("Merkur",R.drawable.merkur));
        planets.add(new Planet("Venus",R.drawable.venus));
        planets.add(new Planet("Jorden",R.drawable.jorden));
        planets.add(new Planet("Mars",R.drawable.mars));
        planets.add(new Planet("Jupiter",R.drawable.jupiter));
        planets.add(new Planet("Saturn",R.drawable.saturn));
        planets.add(new Planet("Uranus",R.drawable.uranus));
        planets.add(new Planet("Neptun",R.drawable.neptun));
        planets.add(new Planet("Pluto",R.drawable.pluto));
    }
    
    

    private void CreateWebSocketClient() {
        URI uri;
        try {
            // Connect to local host
            uri = new URI("ws://10.108.131.152/ws");
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                Log.i("WebSocket", "Session is starting");

                // Command 3
                webSocketClient.send("3");

            }
            @Override
            public void onTextReceived(String s) {
                Log.i("WebSocket", "Message received");
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Log.e("WebSocket",message);

                            int command = Integer.parseInt(message.substring(0,1));
                            String content = message.substring(2, message.length());



                            switch (command){
                                case 1:
                                    JSONObject usernameReader = new JSONObject(content);

                                    Toast.makeText(MainActivity.this, "Bruger Oprettet : " + usernameReader.getString("Username"), Toast.LENGTH_SHORT).show();
                                    break;
                                case 2:
                                    JSONObject stateReader = new JSONObject(content);

                                    //Toast.makeText(MainActivity.this, "Ændret Planet : " + stateReader.getString("State"), Toast.LENGTH_SHORT).show();
                                    planets.get(stateReader.getInt("PlanetNumber")-1).setLightIsOn(stateReader.getBoolean("State"));

                                    switch (stateReader.getInt("PlanetNumber")-1){
                                        case 0:
                                            mercury.setChecked(planets.get(0).getLightIsOn());
                                            break;
                                        case 1:
                                            venus.setChecked(planets.get(1).getLightIsOn());
                                            break;
                                        case 2:
                                            earth.setChecked(planets.get(2).getLightIsOn());
                                            break;
                                        case 3:
                                            mars.setChecked(planets.get(3).getLightIsOn());
                                            break;
                                        case 4:
                                            jupiter.setChecked(planets.get(4).getLightIsOn());
                                            break;
                                        case 5:
                                            saturn.setChecked(planets.get(5).getLightIsOn());
                                            break;
                                        case 6:
                                            uranus.setChecked(planets.get(6).getLightIsOn());
                                            break;
                                        case 7:
                                            neptune.setChecked(planets.get(7).getLightIsOn());
                                            break;
                                        case 8:
                                            pluto.setChecked(planets.get(8).getLightIsOn());
                                            break;
                                    }

                                    break;
                                case 3:
                                    JSONArray reader = new JSONArray(content);

                                    for (int i =0; i < reader.length(); i++){
                                        JSONObject planetJson = reader.getJSONObject(i);
                                        Log.e("Hello",planetJson.toString());

                                        Log.e("GET","ID:" + (planetJson.getInt("PlanetNumber")-1));
                                        planets.get(planetJson.getInt("PlanetNumber")-1).setLightIsOn(planetJson.getBoolean("State"));

                                        switch (planetJson.getInt("PlanetNumber")-1){
                                            case 0:
                                                mercury.setChecked(planets.get(0).getLightIsOn());
                                                break;
                                            case 1:
                                                venus.setChecked(planets.get(1).getLightIsOn());
                                                break;
                                            case 2:
                                                earth.setChecked(planets.get(2).getLightIsOn());
                                                break;
                                            case 3:
                                                mars.setChecked(planets.get(3).getLightIsOn());
                                                break;
                                            case 4:
                                                jupiter.setChecked(planets.get(4).getLightIsOn());
                                                break;
                                            case 5:
                                                saturn.setChecked(planets.get(5).getLightIsOn());
                                                break;
                                            case 6:
                                                uranus.setChecked(planets.get(6).getLightIsOn());
                                                break;
                                            case 7:
                                                neptune.setChecked(planets.get(7).getLightIsOn());
                                                break;
                                            case 8:
                                                pluto.setChecked(planets.get(8).getLightIsOn());
                                                break;
                                        }

                                    }

                                    break;
                                default:
                                    break;
                            }

                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
            @Override
            public void onBinaryReceived(byte[] data) {
            }
            @Override
            public void onPingReceived(byte[] data) {
            }
            @Override
            public void onPongReceived(byte[] data) {
            }
            @Override
            public void onException(Exception e) {
                System.out.println(e.getMessage());
            }
            @Override
            public void onCloseReceived() {
                Log.i("WebSocket", "Closed ");
                System.out.println("onCloseReceived");
            }
        };
        webSocketClient.setConnectTimeout(0);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(0);
        webSocketClient.connect();
    }
}
