package com.example.foodhygiene;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText input;
    TextView bName;
    Button reset;
   // ScrollView scroll;

    //String businessInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        input = findViewById(R.id.input);
        bName = findViewById(R.id.bName);
        reset = findViewById(R.id.reset);
       // scroll = findViewById(R.id.scroll);


    }

    public void on_click (View v)
    {
        ArrayList<String> Name = new ArrayList<String>();
       // String names = getResources().getString(Name.get(0));
        ArrayList<String> Rate = new ArrayList<String>();


        try {

            URL url = new URL("http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?op=search_postcode&postcode=" + input.getText());
            URLConnection tc = url.openConnection();
            InputStreamReader isr = new InputStreamReader(tc.getInputStream());
            BufferedReader in = new BufferedReader(isr);

            String line = "" , rb= "" ,businessInfo = "";

            while ((line = in.readLine()) != null) {
                rb = rb + line;
            }
                JSONArray ja = new JSONArray(rb);

                for (int i = 0; i < ja.length(); i ++)
                {
                    JSONObject jo = (JSONObject) ja.get(i);
                    Name.add(jo.getString("BusinessName"));
                   // bName.setText(Name.get(0));

                   // bName.append(Name.get(0));
                    businessInfo += "Business name : " + jo.get("BusinessName") + "\n"
                            + "Business Address : " +jo.get("AddressLine2" ) + "\n"
                            + "Postcode : " +jo.get("PostCode" ) + "\n"
                            + "Rating : "+ jo.get("RatingValue" ) + "\n"
                            + "\n ______________________ \n";

                }
                bName.setText(businessInfo);

               // in.close();
               /* for (int i = 0; i < ja.length(); i ++)
                {
                    JSONObject jo = (JSONObject) ja.get(i);
                    Rate.add(jo.getString("RatingValue"));
                  //  bRate.setText(Rate.get(0));
                    bRate.append(Rate.get(0) );

                }*/


            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public  void reset (View v){
        bName.setText("");
        input.setText("");

    }

}
