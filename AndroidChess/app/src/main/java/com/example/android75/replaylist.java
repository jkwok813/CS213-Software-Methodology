package com.example.android75;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.android75.databinding.ReplaylistDetailsBinding;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

public class replaylist extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ReplaylistDetailsBinding binding;

    private ListView listView;
    String[] replays = new String[10000];
    String[] titles = new String[10000];
    String[] dates = new String[10000];
    String[] titlelist;
    //DateFormat[] datelist;
    String[] finaldate;
    String[] datelist;
    String[] listviewholder;
    ArrayAdapter<String> adapter;
    int counter = 0;
    boolean datesort;
    public static final String TITLE_NAME = "title_name";
    public static final String TITLE_DATE = "title_date";
    public static final String MOVES = "moves";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replaylist_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int num = 0;
        int gameno = 0;
        String line;
        line = readFromFile();
        String line2;
        int i = -1;
        int j = 1;
        while(j!= line.length()+1){
            if(i < 0){
                i++;
            }
            line2 = line.substring(j-1, j);
            //System.out.println("line2 = " + line2);
            if(line2.equalsIgnoreCase("|")){
                if(num == 0) { //It's a title
                    System.out.println(line.substring(i, j-1) + " added to titles. i = " + i + " j = " + j);
                    titles[gameno] = line.substring(i, j-1);
                    num++;
                    i = j;
                }
                else if(num == 1) {//It's a date
                    System.out.println(line.substring(i, j-1) + " added to dates. i = " + i + " j = " + j);
                    dates[gameno] = line.substring(i, j-1);
                    num++;
                    i = j;
                }
                else {//It's a move

                }
            }
            if(line2.equalsIgnoreCase("*")){
                replays[gameno] = line.substring(i, j-1);
                num = 0;
                i = j;
                System.out.println("Game added. Title = " + titles[gameno] + " date = " + dates[gameno] + " moves = " + replays[gameno]);
                gameno++;
                counter++;
            }
            j++;
        }
        titlelist = new String[counter];
        System.out.println("counter = " + counter);
        for(int z = 0; z < counter; z++) {
            titlelist[z] = titles[z];
            System.out.println(titlelist[z] + " = titlelist[z]");
        }
        /*datelist = new DateFormat[counter];
        for(int x = 0; x < counter; x++) {
            DateFormat dateFormat = new SimpleDateFormat(dates[x]);
            datelist[x] = dateFormat;
        }*/
        datelist = new String[counter];
        for(int y = 0; y < counter; y++){
            datelist[y] = dates[y];
        }
        Arrays.sort(titlelist);
        Arrays.sort(datelist);
        datesort = false;
        listviewholder = new String[counter];
        for(int y = 0; y < counter; y++){
            listviewholder[y] = titlelist[y];
        }
        System.out.println(titles[0]);
        adapter = new ArrayAdapter<>(this, R.layout.replay, listviewholder);
        listView = findViewById(R.id.replays_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                if(!datesort) {
                    //Need to find the item at titles 3 and find where it equals titlelist 3
                    int wowza = 0;
                    for (int x = 0; x < titlelist.length; x++) {
                        System.out.println(titlelist[x] + " and " + titles[x]);
                        if (titlelist[position].equals(titles[x])) {
                            wowza = x;
                            break;
                        }
                    }
                    position = wowza;
                    playreplay(position);
                }
                else{
                    playreplay(position);
                }
            }
        });
    }

    private void playreplay(int pos){
        Bundle bundle = new Bundle();
        //Save string here
        bundle.putString(TITLE_NAME, titles[pos]);
        bundle.putString(TITLE_DATE, dates[pos]);
        bundle.putString(MOVES, replays[pos]);
        Intent intent = new Intent(this, PlayReplay.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public String readFromFile()
    {
        Context context=getApplicationContext();
        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("gamerecording.dat");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public void datesort(View view){
        datesort = true;
        for(int x = 0; x < counter; x++){
            //System.out.println(listviewholder[x]);
            //System.out.println(titles[x]);
            listviewholder[x] = titles[x];
        }
        for(int x = 0; x < counter; x++){
            System.out.println(listviewholder[x]);
            System.out.println(titles[x]);
        }
        adapter.notifyDataSetChanged();
    }
    public void titlesort(View view){
        datesort = false;
        for(int y = 0; y < counter; y++){
            listviewholder[y] = titlelist[y];
        }
        for(int x = 0; x < counter; x++){
            System.out.println(listviewholder[x]);
            System.out.println(titlelist[x]);
        }
        adapter.notifyDataSetChanged();
    }
}