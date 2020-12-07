package com.example.sketchbookapp;

import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.Array;
import java.sql.Timestamp;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import javax.xml.validation.TypeInfoProvider;

public class fetchData extends AsyncTask<Void,Void,Void> {
    String data="",dataParsed="",singleParsed="";

    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    private static final String OUTPUT_FORMAT = "%-20s:%s";
    public String[] arrayList;

    public static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            //URL url = new URL("https://comicvine.gamespot.com/api/issues/?api_key=bdb3d1587b59134f566307e4fdcc3ea9877cd091&format=json");'
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            Long tsLong = ts.getTime();
            String tsstring = tsLong+"";
            //byte[] tsByte =tsstring.getBytes();
            String pubKey = "e69dcd94db40f1b333bb5e4e1ff97c39";
           // byte[] pubByte = pubKey.getBytes();
            String priKey = "8e2d4ef49997614f06f215fa8201d5ad3d4076e4";
           // byte[] priByte = priKey.getBytes();

            String preHash = tsLong+pubKey+priKey;
            byte[] hashable = preHash.getBytes();

            /*byte[] hashable = new byte[tsByte.length+pubByte.length*//*+priByte.length*//*];
            System.arraycopy(tsByte,0,hashable,0,tsByte.length);
            System.arraycopy(pubByte,0,hashable,tsByte.length,pubByte.length);
            System.arraycopy(priByte,0,hashable,(tsByte.length+pubByte.length),priByte.length);*/

            byte[] hash = MessageDigest.getInstance("MD5").digest(hashable);

            //String address = "http://gateway.marvel.com:433/v1/public/comics?ts="+tsstring+"&apikey="+pubKey+"&hash="+toHexString(hash);
            String address = "https://api.shortboxed.com/comics/v1/future";
            URL url = new URL(address);
            //System.out.println(address);
            //http://gateway.marvel.com/v1/public/comics?ts=1&apikey=e69dcd94db40f1b333bb5e4e1ff97c39&hash=ffd275c5130566a2916217b101f26150

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            //System.out.println(httpURLConnection.getResponseCode());

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = " ";
            while(line != null){
                line = bufferedReader.readLine();
                data += line;
                //System.out.println("Data: "+data);
            }

            JSONObject response = new JSONObject(data);
            JSONArray ja = new JSONArray(response.getString("comics"));
            //JSONArray ja = new JSONArray(data);
            for(int i=0; i<ja.length();i++){

                JSONObject jo = (JSONObject)ja.get(i);
                String publisher = jo.get("publisher").toString();
                //System.out.println(publisher.trim());
                if(publisher.trim().equals("DC COMICS") || publisher.trim().equals("MARVEL COMICS")) {
                    String title = jo.get("title").toString();
                    String price = jo.get("price").toString();
                    if(!title.contains("VAR")&&!title.contains("VOL")&&!title.contains("OMNIBUS")&&!title.contains("NEW PTG")) {
                        singleParsed = title + "\n" + price + ";";
                        dataParsed += singleParsed;
                    }
                }

            }
            //System.out.println("DATA:"+dataParsed);
            arrayList = dataParsed.split(";");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        /*String[] array = dataParsed.split(";");
        Releases.listItems = new ArrayList<String>(Arrays.asList(array));*/
    }
}
