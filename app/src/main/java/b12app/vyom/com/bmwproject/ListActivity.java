package b12app.vyom.com.bmwproject;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private static final String TAG = "list" ;
    String url  = "http://localsearch.azurewebsites.net/api/Locations";
    private Timestamp ts;




    List<LocationInfo> locationInfoList;
    ListView listLocationView;
    private android.support.v7.widget.Toolbar toolbar;
    private RequestQueue mQueue;
    private LocationInfo locationInfo;
    private TextView btnMenu;
    private LocationListAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        locationInfoList = new ArrayList<>();
        listLocationView = findViewById(R.id.listViewLocations);
        toolbar = findViewById(R.id.toolBar);
        btnMenu = findViewById(R.id.menu_filter);

        btnMenu.setOnClickListener(this);

        mQueue = Volley.newRequestQueue(this);
        fetchData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);


    }

    public void fetchData(){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
           @Override
           public void onResponse(JSONArray response) {


               for(int i = 0 ; i<response.length();i++){

                   try {
                       JSONObject locationDetails = response.getJSONObject(i);

                       int ID = locationDetails.getInt("ID");
                       String Name = locationDetails.getString("Name");
                       Double Latitude = locationDetails.getDouble("Latitude");
                       Double Longitude = locationDetails.getDouble("Longitude");
                       String Address = locationDetails.getString("Address");
                       String ArrivalTime = locationDetails.getString("ArrivalTime");



                       locationInfo = new LocationInfo(ID, Name, Latitude, Longitude,Address,ArrivalTime);
                        locationInfoList.add(locationInfo);

                   } catch (JSONException e) {
                       e.printStackTrace();
                   }


               }

               Collections.sort(locationInfoList, new NameComparator());
               adapter = new LocationListAdapter(locationInfoList,ListActivity.this);
               listLocationView.setAdapter(adapter);

                listLocationView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(ListActivity.this, MapActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("location_detail", (Serializable) locationInfoList.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

           }
       });
       mQueue.add(jsonArrayRequest);

   }


    public void showMenu(View v)
    {
        PopupMenu popup = new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);// to implement on click event on items of menu
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.filter_menu, popup.getMenu());
        popup.show();
    }

    @Override
    public void onClick(View v) {

        if(v==btnMenu){
            showMenu(v);
        }

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()){

            case R.id.filter_name:
                Collections.sort(locationInfoList, new NameComparator());
                adapter.notifyDataSetChanged();
                break;
            case R.id.filter_arrival:
                Collections.sort(locationInfoList, new TimeStampComparator());
                adapter.notifyDataSetChanged();
                break;
            case R.id.filter_distance:
                break;
        }

        return false;
    }


    public class NameComparator implements Comparator<LocationInfo> {// may be it would be Model


        @Override
        public int compare(LocationInfo o1, LocationInfo o2) {


            return o1.getName().compareTo(o2.getName());
        }
    }

    public class TimeStampComparator implements Comparator<LocationInfo> {// may be it would be Model


        @Override
        public int compare(LocationInfo o1, LocationInfo o2) {

            String time1 =  o1.getArrivalTime().replaceAll("T", " ");
            String time2 =  o2.getArrivalTime().replaceAll("T", " ");

            return Timestamp.valueOf(time1).compareTo(Timestamp.valueOf(time2));
        }
    }



}
