package com.example.nsutassignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nsutassignment.adapter.ListAdapter;
import com.example.nsutassignment.clickListeners.SetClickListeners;
import com.example.nsutassignment.items.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    ArrayList<Item> list = new ArrayList<>();
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerViewList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ListAdapter adapter;
    ImageView nsut_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setupSharedPreferences();
        recyclerViewList = findViewById(R.id.recyclerViewList);
        setRecyclerView();
        setBindView();
        setLogo();

    }

    private void setLogo() {
        nsut_logo.setAlpha(0.2f);
    }

    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PreferenceConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        String check_list = sharedPreferences.getString(PreferenceConstant.ITEM_LIST, "");
        if(check_list != ""){
            Gson gson = new Gson();
            list = gson.fromJson(check_list, new TypeToken<List<Item>>(){}.getType());
        }
    }

    private void setBindView() {

        nsut_logo = (ImageView) findViewById(R.id.nsut_logo_img);
        floatingActionButton = findViewById(R.id.fabButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialogueBox();
            }
        });
    }

    private void opendialogueBox() {
        View view = LayoutInflater.from(ListActivity.this).inflate(R.layout.add_item_dialog_box, null);
        EditText editTextname = view.findViewById(R.id.editTextName);
        EditText editTextprice = view.findViewById(R.id.editTextPrice);
        Button submitButton = view.findViewById(R.id.submitButton);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();
        dialog.show();

        dialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                }
                return true;
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextname.getText().toString();
                String price = editTextprice.getText().toString();
                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(price)){
                    Item item = new Item();
                    item.setName(name);
                    item.setPrice(price);
                    Date currentTime = Calendar.getInstance().getTime();
                    DateFormat currenFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                    String currentTimeString = currenFormat.format(currentTime);
                    item.setDate(currentTimeString);
                    list.add(item);

                    updateList();

                }else{
                    Toast.makeText(ListActivity.this, "Item Name or Price can't be empty", Toast.LENGTH_SHORT).show();
                }

                setRecyclerView();

                dialog.hide();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(submitButton.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        });

    }

    private void updateList() {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        editor = sharedPreferences.edit();
        editor.remove(PreferenceConstant.ITEM_LIST).commit();
        editor.putString(PreferenceConstant.ITEM_LIST, json);
        editor.commit();

    }

    private void setRecyclerView() {

        SetClickListeners setClickListeners = new SetClickListeners() {
            @Override
            public void onClickToDelete(int position) {
                View view = LayoutInflater.from(ListActivity.this).inflate(R.layout.delete_item_layout, null);
                Button buttonDelete = view.findViewById(R.id.deleteButton);
                AlertDialog dialog = new AlertDialog.Builder(ListActivity.this)
                    .setView(view)
                    .setCancelable(true)
                    .create();
                dialog.show();

                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.v("Aneesh 2", Integer.toString(position));
                        adapter.deleteItem(position);
                        updateList();
                        dialog.hide();
                    }
                });
            }
        };

        adapter = new ListAdapter(list, setClickListeners);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewList.setLayoutManager(linearLayoutManager);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewList.getContext(), linearLayoutManager.getOrientation());
//        recyclerViewList.addItemDecoration(dividerItemDecoration);
        recyclerViewList.setAdapter(adapter);
    }
}