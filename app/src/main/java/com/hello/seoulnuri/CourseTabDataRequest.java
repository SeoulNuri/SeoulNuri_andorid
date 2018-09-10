package com.hello.seoulnuri;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shineeseo on 2018. 9. 7..
 */

public class CourseTabDataRequest extends AsyncTask<Void, Void, String> {

    private String url;
    private ContentValues values;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public CourseTabDataRequest(String url, ContentValues values) {

        this.url = url;
        this.values = values;
    }

    @Override
    protected String doInBackground(Void... params) {
        String result; // 요청 결과를 저장할 변수.
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.
        //String[][] parsedData = jsonParserList(result); // 받은 메시지를 json 파싱

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.

    }

    public String[][] jsonParserList(String pRecvServerPage) {

//        Log.i("서버에서 받은 전체 내용 : ", pRecvServerPage);

        try {

            JSONObject json = new JSONObject(pRecvServerPage);

            JSONArray jArr = json.getJSONArray("data");



            // 받아온 pRecvServerPage를 분석하는 부분

            String[] jsonName = {"visual", "hearing", "physical", "older"};

            String[][] parseredData = new String[jArr.length()][jsonName.length];

            for (int i = 0; i < jArr.length(); i++) {

                json = jArr.getJSONObject(i);

                if(json != null) {

                    for(int j = 0; j < jsonName.length; j++) {

                        parseredData[i][j] = json.getString(jsonName[j]);

                    }

                }

            }


            // 분해 된 데이터를 확인하기 위한 부분

//            for(int i=0; i<parseredData.length; i++){
//
//                Log.i("JSON을 분석한 데이터 "+i+" : ", parseredData[i][0]);
//
//                Log.i("JSON을 분석한 데이터 "+i+" : ", parseredData[i][1]);
//
//                Log.i("JSON을 분석한 데이터 "+i+" : ", parseredData[i][2]);
//
//            }

            return parseredData;

        } catch (JSONException e) {

            e.printStackTrace();

            return null;

        }

    }

}

