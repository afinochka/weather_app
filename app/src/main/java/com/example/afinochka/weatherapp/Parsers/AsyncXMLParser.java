package com.example.afinochka.weatherapp.Parsers;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class AsyncXMLParser extends AsyncTask<String, Void, String> {

    private Context mContext;
    XMLParser xmlParser;

    public AsyncXMLParser(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(String countryName) {
        super.onPostExecute(countryName);
    }

    @Override
    protected String doInBackground(String... args) {
        try {
            xmlParser = new XMLParser(mContext, args[0]);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return xmlParser.getCountryName();
    }
}
