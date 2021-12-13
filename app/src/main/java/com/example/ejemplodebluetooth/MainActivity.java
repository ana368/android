package com.example.ejemplodebluetooth;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
CheckBox enable_bt, visible_bt;
ImageView search_bt;
TextView name_bt;
ListView listView;
private BluetoothAdapter BA;
private Set<BluetoothDevice> pairedDevice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enable_bt=(CheckBox)findViewById(R.id.enable_bt);
        visible_bt=findViewById(R.id.visible_bt);
        search_bt=findViewById(R.id.search_bt);
        listView=findViewById(R.id.list_view);
        name_bt=findViewById(R.id.name_bt);
        BA= BluetoothAdapter.getDefaultAdapter();
        name_bt.setText(getLocaBluetoothName());
        if(BA==null){
            Toast.makeText(this, "Bluetooth no responde", Toast.LENGTH_SHORT).show();
            finish(); }
        if(BA.isEnabled()){
            enable_bt.setChecked(true);
        }
        enable_bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) {
                    BA.disable();
                    Toast.makeText(MainActivity.this, "Apagado", Toast.LENGTH_SHORT).show();
                }}});
        visible_bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Intent getVisible=new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(getVisible, 0);
                    Toast.makeText(MainActivity.this, "Visible por 2 minutos", Toast.LENGTH_SHORT).show();
            }
        }});
        search_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list();
            }
        });
                }

    private void list() {
        pairedDevice=BA.getBondedDevices();
        ArrayList list= new ArrayList();
        for (BluetoothDevice bt : pairedDevice ){
                list.add(bt.getName());
            }
            Toast.makeText(this, "Mostrando dispositivos", Toast.LENGTH_SHORT).show();
            ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);
        }
        public String getLocaBluetoothName(){
        if(BA==null){
            BA=BluetoothAdapter.getDefaultAdapter();
        }

String name= BA.getName();
    if(name==null){
        name=BA.getAddress();
    }
    return name;
}}