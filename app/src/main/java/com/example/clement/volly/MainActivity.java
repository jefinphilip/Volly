package com.example.clement.volly;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

     Button button;
    Button button1;
     TextView textView;
     String msgFrmServer = "http://192.168.1.3/greety.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        button= (Button)findViewById(R.id.dwnld);
        button1= (Button)findViewById(R.id.dwnld1);
        textView=(TextView)findViewById(R.id.txt);

        //custom request Queue
        Cache cache= new DiskBasedCache(getCacheDir(),1024*1024);
        Network network= new BasicNetwork(new HurlStack());
        final RequestQueue requestQueue= new RequestQueue(cache,network);
        requestQueue.start();

         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {



                 StringRequest stringRequest= new StringRequest(Request.Method.POST, msgFrmServer,
                         new Response.Listener<String>() {
                             @Override
                             public void onResponse(String response) {

                                 textView.setText(response);
                                 requestQueue.stop();
                             }
                         }, new Response.ErrorListener() {
                     @Override
                     public void onErrorResponse(VolleyError error) {

                         textView.setText("some thing went wrong");
                         error.printStackTrace();
                         requestQueue.stop();

                     }
                 });

                            requestQueue.add(stringRequest);
             }
         });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);

                StringRequest stringRequest= new StringRequest(Request.Method.POST, msgFrmServer,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                textView.setText(response);
                                requestQueue.stop();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        textView.setText("some thing went wrong");
                        error.printStackTrace();
                        requestQueue.stop();

                    }
                });

                requestQueue.add(stringRequest);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
