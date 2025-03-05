package com.u063.todo;

import static android.view.View.GONE;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ArrayList<Task> t = new ArrayList<>();
    Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        init();
    }
    void init(){
        Task.setConstraintLayout(findViewById(R.id.scroll));
        c = this;
        File f = new File(this.getFilesDir()+"/tasks");
        try {
            f.createNewFile();
        } catch (IOException ignored) {}
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(openFileInput(f.getName())));
            String s;
            while((s = bf.readLine())!=null){
                if(!s.equals("\n")) {
                    t.add(new Task(s, this));
                }
            }
            bf.close();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
        if(!t.isEmpty()){
            Log.i("size",""+t.size());
            noRecords();
        }
        save();
    }
    void save(){
        File f = new File(this.getFilesDir()+"/tasks");
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        for(int i = 0; i<t.size(); i++){
                            try {
                                BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(openFileOutput(f.getName(),MODE_PRIVATE)));
                                String s = t.get(i).getName();
                                bf.write(s+"\n");
                                Log.e("Hi",s);
                                bf.close();
                            } catch (IOException ignored1) {}
                        }
                    }
                };
                timer.schedule(timerTask,0,1000);
            }
        });
        th.start();
    }
    void noRecords(){
        LinearLayout linearLayout = findViewById(R.id.notyeat);
        linearLayout.setVisibility(GONE);
    }
    public void addTask(View w){
        t.add(new Task(" ",c));
        noRecords();
    }
}