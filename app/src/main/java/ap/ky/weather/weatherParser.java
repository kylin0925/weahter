package ap.ky.weather;

import android.content.Context;
import android.util.Log;
import android.util.Xml;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by kylin25 on 2018/1/21.
 */

public class weatherParser {
    private String ns = "";
    String TAG = "weatherParser";
    //new StringReader ( "<foo>Hello World!</foo>" )


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

    private List readDaily(XmlPullParser parser)throws XmlPullParserException,IOException {
        List weatherList = new ArrayList();
        int eventType = 0;
        parser.require(XmlPullParser.START_TAG,ns,"cwbopendata");

        while ((eventType = parser.next()) !=XmlPullParser.END_DOCUMENT){

           // Log.e(TAG,"eventType " + eventType);
            if(eventType == XmlPullParser.START_DOCUMENT) {
                Log.e(TAG,"Start document");
            } else if(eventType == XmlPullParser.START_TAG) {
                Log.e(TAG,"Start tag "+parser.getName());
            } else if(eventType == XmlPullParser.END_TAG) {
                Log.e(TAG,"End tag "+parser.getName());
            } else if(eventType == XmlPullParser.TEXT) {
                Log.e(TAG,"Text "+parser.getText());
            }
        }
        return  weatherList;
    }
}
