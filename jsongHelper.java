package com.example.myapplication;

import android.content.res.Resources;
import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class jsongHelper {
    public static jsongHelper JsongHelper;


    private JSONArray jsonArray;

    ///making it singleton class
    private jsongHelper(JSONArray jsonArray){
        this.jsonArray=jsonArray;
    }

    //builder for basic template of ticket all objects empty
    private static JSONArray jsonBuilder(){
        JSONArray jsonTicketArray=null;
        try {
            jsonTicketArray=new JSONArray();
            JSONObject tmpObject=new JSONObject();
            JSONArray dishArray=new JSONArray();

            jsonTicketArray.put(tmpObject.put("orderid",""));
            jsonTicketArray.put(tmpObject.put("customername",""));
            jsonTicketArray.put(tmpObject.put("tablename",""));
            jsonTicketArray.put(tmpObject.put("waiter",""));
            jsonTicketArray.put(tmpObject.put("guests",""));
            jsonTicketArray.put(tmpObject.put("saletype",""));
            jsonTicketArray.put(tmpObject.put("time",""));
            jsonTicketArray.put(tmpObject.put("ticketnum",""));
            jsonTicketArray.put(tmpObject.put("ordertotal",0));
            jsonTicketArray.put(tmpObject.put("deliveryaddress",""));
            jsonTicketArray.put(tmpObject.put("dishes",dishArray));

        }catch (Exception j){
            System.out.println("couldn't create jsonArray");
        }

            return jsonTicketArray;


    }

    //by it u can jsonHelper object
    public static jsongHelper getInstance(){
        if(JsongHelper!=null) return JsongHelper;
        JSONArray jsonArray1=jsonBuilder();
        return JsongHelper=new jsongHelper(jsonArray1);
    }

    //u can set an jsonObject using key/value pair
    public void jsonSetter(JSONArray array,String key,Object replace){
        for(int i=0;i<array.length();i++){
            try {
                JSONObject jsonObject=array.getJSONObject(i);
                if(jsonObject.has(key)){
                    jsonObject.putOpt(key,replace);
                    return;
                }
            }catch (JSONException j){
                System.out.println(j.toString());
            }

        }

    }

    //u can get java object from jsonObject
    public Object jsonGetter(JSONArray array,String key){


        for (int i=0;i<array.length();i++){
            try{

                JSONObject jsonObject=array.getJSONObject(i);
                if(jsonObject.has(key))return jsonObject.get(key);

            }catch (JSONException j){System.out.println(j.toString());}
        }

        return null;
    }


    //return arrayList of strings from jsonFile
    public ArrayList<String> readJson() throws IOException,JSONException {
        ArrayList<String> jsonArrayList=new ArrayList<>();

        ///step1 -> open that json file from local storage
        Resources resources=Resources.getSystem();
        InputStream in_s=resources.openRawResource(R.raw.jsonFile);
        byte[] b=new byte[in_s.available()];

        //step 2 reading it
        StringBuilder stringBuffer=new StringBuilder();
        while(in_s.read(b)!=-1){
            stringBuffer.append((char)in_s.read());
        }

        String fileTxt=stringBuffer.toString();

        //step3->storing it in arraylist
        JSONArray jsonArray=new JSONArray(fileTxt);
        for(int i=0;i<jsonArray.length();i++){
            String values=(String) jsonArray.get(i);
            jsonArrayList.add(values);
        }


        return jsonArrayList;
    }


    ///removing jsonObject
    //for sdk < 4.4
    public static JSONArray removeJSONArray(JSONArray inJSONArray, int pos) {
        JSONArray newJsonArray = new JSONArray();
        try {
            for (int i=0; i<inJSONArray.length(); i++) {
                if (i != pos)
                    newJsonArray.put(inJSONArray.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newJsonArray;
    }

}
