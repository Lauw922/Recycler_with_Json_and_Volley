package com.example.recyclerwithjsonandvolley;

import static com.example.recyclerwithjsonandvolley.Nodes.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterRecycle.MyOnItemClickListener {

    private RecyclerView recyclerView;

    private EditText etSearch;
    private Button btnSearch;
    private ArrayList<ModelItem> arrayList; // ArrayList basé.e sur notre model
    private String search; // string de la recherche dans l'editText
    private AdapterRecycle adapter; // gestion de notre adapter
    private RequestQueue requestQueue; // Gestion de volley

    // Initialisation des composants graphiques et autre composantd
    private void initUI(){
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // forme du recycler la plus simple

        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);

        arrayList = new ArrayList<>() ;

        requestQueue = Volley.newRequestQueue(this);

    }

    public void newSearch(View view){
        arrayList.clear();
        search = etSearch.getText().toString().trim();

        parseJSON(search);
    }

    private void parseJSON(String search){
        //https://pixabay.com/api/?key=40668740-412d06df4414c608a96232742&q=yellow+flowers&image_type=photo&pretty=true
        String pixabayKey = "40668714-289985bba921b53cabe9c92a7";
        String urlJsonFile = "https://pixabay.com/api/"
                + "?key="
                + pixabayKey
                + "&q="
                + search //ici la recherche
                + "&image_type=photo"
                + "&orientation=horizontal" + "&per_page=30" + "&pretty=true";

        //Log.i("TAG", "Json : " + urlJsonFile);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlJsonFile, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try
                {
                    Log.i("TAG", "onResponse: " + "JE PASSE");
                    JSONArray jsonArray = response.getJSONArray(JSON_ARRAY);

                    for(int i = 0 ; i < jsonArray.length() ; i++)
                    {
                        JSONObject myHit = jsonArray.getJSONObject(i);
                        String imageURL = myHit.getString(JSON_IMAGE_URL);
                        String creator = myHit.getString(JSON_USER);
                        int likes = myHit.getInt(JSON_LIKES);

                        arrayList.add(new ModelItem(imageURL,creator,likes));
                    }

                    adapter = new AdapterRecycle(MainActivity.this,arrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.setMyOnItemClickListener(MainActivity.this);

                } catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        parseJSON(search);
    }

    @Override
    public void onItemClick(int position) {

        Toast.makeText(MainActivity.this, "TOTO", Toast.LENGTH_SHORT).show();
    }
}