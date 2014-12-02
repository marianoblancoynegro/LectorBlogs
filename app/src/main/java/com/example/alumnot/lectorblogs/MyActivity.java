package com.example.alumnot.lectorblogs;

import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class MyActivity extends ListActivity
{
    public static final int NUMBER_OF_POST=20;
    public static final String TAG=MyActivity.class.getSimpleName();
    public static final String JSON_URL="http://minoviomecontrola.blogspot.com.es/feeds/posts/default?alt=json";

    private class GetBlogPostTask extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object[] params)
        {
            int responseCode=-1;
            try
            {
                URL blogFeedUrl = new URL(JSON_URL);
                try
                {
                    HttpURLConnection connection = (HttpURLConnection) blogFeedUrl.openConnection();
                    try
                    {
                        connection.connect();
                        responseCode = connection.getResponseCode();
                        Log.i(TAG, "Code: " + responseCode);
                    } catch(Exception e3){}
                }catch(IOException e2)
                {
                    Log.e(TAG, "Exception caught:", e2);
                }

            }catch(MalformedURLException e)
            {
                Log.e(TAG, "Exception caught:", e);
            }
            return ("Code: "+ responseCode);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        String[] mblogPostTitles;
        Resources recursos;
        recursos=getResources();

        if (isNetworkAvailable())
        {
            GetBlogPostTask getBlogPostTask = new GetBlogPostTask();

            getBlogPostTask.execute();

        }
        else
        {
            Toast.makeText(this,"La red no esta disponible",Toast.LENGTH_LONG).show();
        }
        //mblogPostTitles=recursos.getStringArray(R.array.nombres);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mblogPostTitles);
        //setListAdapter(adapter);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

    }
    public boolean isNetworkAvailable() {
        boolean isAvailable = false;

        ConnectivityManager manager =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if((networkInfo!=null)&&(networkInfo.isConnected()==true))
        {
            isAvailable=true;
        }
        return (isAvailable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
