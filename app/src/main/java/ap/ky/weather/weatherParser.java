package ap.ky.weather;

import android.content.Context;
import android.util.Log;
import android.util.Xml;


import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * Created by kylin25 on 2018/1/21.
 */

public class weatherParser {
    private String ns = "";
    String TAG = "weatherParser";
    //new StringReader ( "<foo>Hello World!</foo>" )

    SAXParserFactory factory=SAXParserFactory.newInstance();


    public List parserDaily(Reader in) throws XmlPullParserException,IOException{
        try{
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser.setInput(in);
            parser.nextTag();
            return readDaily(parser);
        }finally {
            in.close();
        }


    }
    //if location
    // read location
    //  read weatherElement list
    //      read time list
    //
    private List readDaily(XmlPullParser parser)throws XmlPullParserException,IOException {
        List weatherList = new ArrayList();
        int eventType = 0;
        parser.require(XmlPullParser.START_TAG,ns,"cwbopendata");
        List<Location> weatherLocation = new ArrayList<>();;

        while ((eventType = parser.next()) !=XmlPullParser.END_DOCUMENT){

           // Log.e(TAG,"eventType " + eventType);
            if(eventType == XmlPullParser.START_DOCUMENT) {
                //Log.e(TAG,"Start document");
            } else if(eventType == XmlPullParser.START_TAG) {
                String tagName = parser.getName();


                if(tagName.equals("location")){
                    Log.e(TAG,"Start location tag "+tagName);
                    Location location = readLocation(parser);
                    weatherList.add(location);

                }

            } else if(eventType == XmlPullParser.END_TAG) {
                //Log.e(TAG,"End tag "+parser.getName());
            } else if(eventType == XmlPullParser.TEXT) {
                //Log.e(TAG,"Text "+parser.getText());
            }
        }
        return  weatherList;
    }
    Location readLocation(XmlPullParser parser)throws XmlPullParserException,IOException{
        Location location =new Location();
        location.weatherElement = new ArrayList<>();
        int eventType = 0;
        while ((eventType = parser.next()) !=XmlPullParser.END_DOCUMENT) {

            if(eventType == XmlPullParser.START_TAG) {
                String tagName = parser.getName();
                Log.e(TAG,"tagName  :" + parser.getName());
                if(tagName.equals("locationName")){
                   eventType = parser.next();
                   location.locationName = parser.getText();
                   Log.e(TAG,"location Name :" + location.locationName);
                   //eventType = parser.next(); // end tag
                   // Log.e(TAG,"tag  :" + parser.getName());
                }

                if(tagName.equals("weatherElement")){
                   WeatherElement weatherElement = readWeatherElement(parser);
                   location.weatherElement.add(weatherElement);
                }

            } else if(eventType == XmlPullParser.END_TAG) {

                String tagName = parser.getName();
                Log.e(TAG,"End tag "+tagName);
                if(tagName.equals("location"))
                     break;
            } else if(eventType == XmlPullParser.TEXT) {
                Log.e(TAG,"Text "+parser.getText());
            }
        }
        return  location;
    }
    WeatherElement readWeatherElement(XmlPullParser parser)throws XmlPullParserException,IOException{
        WeatherElement weatherElement = new WeatherElement();
        weatherElement.time = new ArrayList<>();

        int eventType = 0;
        while ((eventType = parser.next()) !=XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG) {
                String tagName = parser.getName();
                if(tagName.equals("elementName")){
                    eventType = parser.next();
                    weatherElement.elementName = parser.getText();
                    eventType = parser.next(); //end tag
                    Log.e(TAG,"readWeatherElement " +  weatherElement.elementName);
                }
                if(tagName.equals("time")){
                    eventType = parser.next();
                    //weatherElement.elementName = parser.getText();
                    WeatherTime time = readWeatherTime(parser);
                    weatherElement.time.add(time);
                }

            } else if(eventType == XmlPullParser.END_TAG) {

                String tagName = parser.getName();
                //Log.e(TAG,"End tag "+tagName);
                if(tagName.equals("weatherElement"))
                    break;
            }
//            else if(eventType == XmlPullParser.TEXT) {
//                 Log.e(TAG,"readWeatherElement Text "+parser.getText());
//            }
        }
        return weatherElement;
    }

    WeatherTime readWeatherTime(XmlPullParser parser)throws XmlPullParserException,IOException{
        WeatherTime time = new WeatherTime();
        int eventType = 0;
        while ((eventType = parser.next()) !=XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG) {
                String tagName = parser.getName();
                Log.e(TAG,"readWeatherTime START_TAG " + tagName);
                if(tagName.equals("startTime")){
                    eventType = parser.next();
                    //weatherElement.elementName = parser.getText();
                    Log.e(TAG,"time Text "+parser.getText());
                    //eventType = parser.next(); //end tag
                    time.startTime = parser.getText();
                }
                if(tagName.equals("endTime")){
                    eventType = parser.next();
                    Log.e(TAG,"time Text "+parser.getText());
                    //eventType = parser.next(); //end tag
                    time.endTime = parser.getText();
                }
                if(tagName.equals("parameter")){
                    eventType = parser.next();
                    //weatherElement.elementName = parser.getText();
                    Parameter parameter = readParameter(parser);
                    time.parameter = parameter;
                    Log.e(TAG,"parameter " + parameter.parameterName + " " + parameter.parameterValue);

                }
            } else if(eventType == XmlPullParser.END_TAG) {

                String tagName = parser.getName();
                Log.e(TAG, "time End tag " + tagName);
                if (tagName.equals("time"))
                    break;
            }
//           else if(eventType == XmlPullParser.TEXT) {
//                Log.e(TAG,"time Text "+parser.getText() );
//            }
        }
        return time;
    }

    Parameter readParameter(XmlPullParser parser)throws XmlPullParserException,IOException{
        Parameter parameter = new Parameter();
        int eventType = 0;
        while ((eventType = parser.next()) !=XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG) {
                String tagName = parser.getName();
                if(tagName.equals("parameterName")){
                    eventType = parser.next();
                    parameter.parameterName = parser.getText();
                }
                if(tagName.equals("parameterValue")){
                    eventType = parser.next();
                    parameter.parameterValue = parser.getText();
                }
                if(tagName.equals("parameterUnit")){
                    eventType = parser.next();
                    //parameter.parameterUnit = parser.getText();
                }

            }
            else if(eventType == XmlPullParser.END_TAG) {

                String tagName = parser.getName();
                Log.e(TAG, "parameter End tag " + tagName);
                if (tagName.equals("parameter"))
                    break;
            }
        }

        return parameter;
    }
}

