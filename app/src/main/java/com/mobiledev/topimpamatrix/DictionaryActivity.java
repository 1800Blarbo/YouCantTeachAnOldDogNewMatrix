//package com.mobiledev.topimpamatrix;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//
///**
// * Created by larspmayrand on 5/4/16.
// */
//public class DictionaryActivity extends AppCompatActivity implements DictionaryRecyclerViewAdapter.OnDefinitionRowClickListener {
//
//    public static final int ID_DEFINITION = 0;
//    private static final String TAG = "Definition";
//    private RecyclerView RecyclerView;
//    private LinearDictionary dictionary;
//    private DictionaryRecyclerViewAdapter Adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dictionary_activity);
//
//        RecyclerView = (RecyclerView) findViewById(R.id.definition_recycler);
//        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        dictionary = new LinearDictionary();
//        Adapter = new DictionaryRecyclerViewAdapter(dictionary.getDefinitions(), this);
//        RecyclerView.setAdapter(Adapter);
//    }
//
//    @Override
//    public void onDefinitionRowClick(Definition term) {
////        Intent intent = new Intent(DefinitionRecyclerView.this, PokemonDetailView.class);
////        intent.putExtra(DefinitionDetailView.ARG_DEFINITION, term);
////        this.startActivity(intent);
//    }
//
//}
