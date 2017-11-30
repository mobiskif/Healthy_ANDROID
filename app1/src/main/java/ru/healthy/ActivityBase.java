package ru.healthy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityBase extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, View.OnClickListener {
    int id_content = R.layout.activity_base;
    int spinner_id = 0;
    String top_text_value = "*";
    String FIO_value = "*";
    String btn_text = "*";
    String spinner_arr = "def_arr";
    String card_arr = "def_arr";
    String list_arr = "def_arr";
    String TAG=getClass().getSimpleName()+" jop";

    void config_ToolbarAndMenu() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Нажата FloatingActionButton", Snackbar.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.textview).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
    }

    void init_Visiblity() {
        findViewById(R.id.label1).setVisibility(View.GONE);
        findViewById(R.id.label2).setVisibility(View.GONE);
        findViewById(R.id.label3).setVisibility(View.GONE);
        findViewById(R.id.text).setVisibility(View.GONE);
        findViewById(R.id.recycler).setVisibility(View.GONE);
        findViewById(R.id.tv).setVisibility(View.GONE);
    }

    void attach_Adapters() {
        Spinner spinner = findViewById(R.id.spinner);
        if (spinner.getVisibility()==View.VISIBLE) {
            spinner.setOnItemSelectedListener(this);
            DataAdapter spinner_adapter = new DataAdapter (this, R.layout.item_spinner, spinner_arr);
            spinner.setAdapter(spinner_adapter);
        }

        ListView listView = findViewById(R.id.list);
        if (listView.getVisibility()==View.VISIBLE) {
            listView.setOnItemClickListener(this);
            DataAdapter list_adapter = new DataAdapter(this, android.R.layout.simple_list_item_1, list_arr);
            listView.setAdapter(list_adapter);
        }

        RecyclerView mRecyclerView = findViewById(R.id.recycler);
        if (mRecyclerView.getVisibility()==View.VISIBLE) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            CardAdapter card_adapter = new CardAdapter(card_arr, this, btn_text);
            mRecyclerView.setAdapter(card_adapter);
        }

    }

    void restore_Values() {
        top_text_value = Storage.restore(this, "top_text_value");
        FIO_value = Storage.restore(this, "FIO_value");
        spinner_id = Integer.valueOf(Storage.restore(this, spinner_arr+"_pos"));
        //if (((Spinner)findViewById(R.id.spinner)).getAdapter().getCount() >= spinner_id) ((Spinner) findViewById(R.id.spinner)).setSelection(spinner_id);
        ((Spinner) findViewById(R.id.spinner)).setSelection(spinner_id);
    }

    void init_Values() {
        ((Button) findViewById(R.id.button)).setText(R.string.button);
        ((TextView) findViewById(R.id.text)).setText(FIO_value);
        ((TextView) findViewById(R.id.textview)).setText(top_text_value);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(id_content);
        config_ToolbarAndMenu();

        init_Visiblity();
        attach_Adapters();
        restore_Values();
        init_Values();
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(getApplicationContext(), "onClick", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getApplicationContext(), "onItemClick", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getApplicationContext(), "onItemSelected", Toast.LENGTH_LONG).show();
        if (((DataAdapter) parent.getAdapter()).loaded) {
            Storage.store(this, spinner_arr + "_pos", String.valueOf(position));
            if (view!=null) {
                String s = ((TextView) view).getText().toString();
                Storage.store(this, spinner_arr + "_str", s);
                Storage.store(this, "top_text_value", s);
            }

            if (spinner_arr.equals("GetLPUList")) {
                //DataAdapter da = new DataAdapter(this, 666,"CheckPatient");
            }

            ListView listView = findViewById(R.id.list);
            if (listView.getVisibility()==View.VISIBLE) {
                IDataAdapter ladapter = (IDataAdapter) listView.getAdapter();
                //ladapter.update();
            }

            RecyclerView mRecyclerView = findViewById(R.id.recycler);
            if (mRecyclerView.getVisibility()==View.VISIBLE) {
                IDataAdapter radapter = (IDataAdapter) mRecyclerView.getAdapter();
                //radapter.update();
            }


        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Toast.makeText(this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
        }

    }

}
