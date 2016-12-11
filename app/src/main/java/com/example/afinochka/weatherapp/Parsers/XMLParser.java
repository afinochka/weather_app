package com.example.afinochka.weatherapp.Parsers;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.example.afinochka.weatherapp.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class XMLParser {

    private Context context;
    private String countryName = "";
    private String countryCod;
    private boolean isFound = false;

    public XMLParser(Context context, String countryCod)
            throws ExecutionException, InterruptedException, IOException, SQLException {
        this.context = context;
        this.countryCod = countryCod;
        XmlPullParser xmlPullParser = prepareXpp();
        parseFile(xmlPullParser);
    }

    private void parseFile(XmlPullParser file) {
        try {
            while (file.getEventType() != XmlPullParser.END_DOCUMENT) {
                if(!isFound) {
                    if (file.getEventType() == XmlPullParser.START_TAG) {
                        switch (file.getName()) {
                            case "english":
                                file.next();
                                countryName = file.getText();
                                break;
                            case "alpha2":
                                file.next();
                                if (file.getText().equals(countryCod))
                                    isFound = true;
                                break;
                        }
                    }
                    file.next();
                }else
                    break;
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }

    }

    public String getCountryName(){
        return countryName;
    }


    private XmlPullParser prepareXpp() {
        return context.getResources().getXml(R.xml.countries);
    }
}
