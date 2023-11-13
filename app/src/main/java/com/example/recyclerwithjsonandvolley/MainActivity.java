package com.example.recyclerwithjsonandvolley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }
}