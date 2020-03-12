    package com.example.foodhygiene;

    import android.os.AsyncTask;
    import android.widget.EditText;
    import android.widget.TextView;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.net.MalformedURLException;
    import java.net.URL;
    import java.net.URLConnection;
    import java.util.ArrayList;
    import java.util.List;

    public class HygieneWebServiceClient {
        TextView output;
        public HygieneWebServiceClient(TextView target) {
            super();
            this.output = target;
        }

        public class AsyncTask02 extends AsyncTask<URL , Void , List<Restaurant>>{

            TextView output;

            public AsyncTask02(TextView target) {
                super();
                this.output = target;
            }

            ArrayList<Restaurant> info = new ArrayList<>();
            @Override
            protected List<Restaurant> doInBackground(URL... url) {

    //            url = ("http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?op=search_postcode&postcode=");
                URLConnection tc = null;
                try {
                    tc = url[0].openConnection();
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

                        Restaurant restaurant = new Restaurant();
                        restaurant.setHygieneRate(Integer.valueOf(jo.getString("RatingValue")));
                        restaurant.setName(jo.getString("BusinessName"));
                        restaurant.setAddress(jo.getString("AddressLine1") + "\n"
                                +"                                    "
                        + jo.getString("AddressLine2") + "\n"
                                +"                                    "
                                + jo.getString("AddressLine3"));
                        restaurant.setPostCode(jo.getString("PostCode"));
                        restaurant.setRatingDate(jo.getString("RatingDate"));
                        info.add(restaurant);
                    }


                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return info;
            }

            @Override
            protected void onPostExecute(List<Restaurant> restaurants) {
                super.onPostExecute(restaurants);
                String display = "";
                for (Restaurant r: restaurants
                     ) {
                    //display += r.getName() + " " + r.getHygieneRate() + "\n";
                      display+= "Business name:      " + r.getName() + "\n"
                              +"\n"
                            + "Business Address: "+r.getAddress() + "\n"
                            + "Postcode:                 " +r.getPostCode() + "\n"
                              +"\n"
                              + "Rating:                       "+ r.getHygieneRate() + "\n"
                              +"Rating Date:             " + r.getRatingDate()
                            + "\n________________________________________________\n";

                }
                output.setText(display);
            }
        }

        public void getRatingByPostcode(String postcode) {
            URL url = null;
            try {
                url = new URL("http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?op=search_postcode&postcode="+postcode);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            new AsyncTask02(output).execute(url);
        }
        public void  getRatingByLocation (double lat, double lng){
            URL url = null;
            try {
                url = new URL("http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?op=search_location&lat=" + lat+ "&long=" + lng);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            new AsyncTask02(output).execute(url);
        }


    }
