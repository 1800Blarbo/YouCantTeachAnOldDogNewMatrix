package com.mobiledev.topimpamatrix;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by larspmayrand on 5/4/16.
 */
public class DictionaryActivity extends Activity {

    public static final int ID_DEFINITION = 0;
    private static final String TAG = "Definition";
    private DictionaryRecyclerViewAdapter mAdapter;

    @Bind(R.id.definition_recycler)
    RecyclerView RecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionary_activity);

        ButterKnife.bind(this);

        LinearDictionary dictionary = new LinearDictionary();

        this.mAdapter = new DictionaryRecyclerViewAdapter(dictionary.getDefinitions(), new DictionaryRecyclerViewAdapter.DictionaryRowOnClickListener() {
            @Override
            public void onDictionaryRowClick(Definition definition) {

            }
        });

        RecyclerView.setLayoutManager(new LinearLayoutManager(this)); // NULL
        RecyclerView.setAdapter(this.mAdapter);

//        GridViewCustomAdapter mAdapter = new GridViewCustomAdapter(this, mMatrix);
//        mGridView.setAdapter(mAdapter);
//        mGridView.setNumColumns(MainActivity.columns);

    }

//    @Override
//    public void onDefinitionRowClick(Definition term) {
//        Intent intent = new Intent(DefinitionRecyclerView.this, PokemonDetailView.class);
//        intent.putExtra(DefinitionDetailView.ARG_DEFINITION, term);
//        this.startActivity(intent);
//    }

}
