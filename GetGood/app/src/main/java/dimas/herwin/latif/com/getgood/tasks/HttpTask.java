package dimas.herwin.latif.com.getgood.tasks;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpTask extends AsyncTask<String, Void, String> {

    AsyncTaskListener asyncTaskListener;

    public HttpTask(AsyncTaskListener listener){
        asyncTaskListener = listener;
    }

    @Override
    protected String doInBackground(String... urls) {
        try{
            String method = urls[1];
            String params = urls[2];
            String target = (method.equals("GET"))? urls[0] + "?" + params : urls[0];

            URL url = new URL(target);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod(method);
            conn.setDoInput(true);

            if(method.equals("POST")){
                conn.setDoOutput(true);
                DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
                writer.writeBytes(params);
                writer.flush();
                writer.close();
            }

            int responseCode = conn.getResponseCode();

            InputStream inputStream = (responseCode == 200 || responseCode == 201)? conn.getInputStream() : conn.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            return response.toString();
        }
        catch(IOException e){
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String response) {
        asyncTaskListener.onTaskCompleted(response);
    }

}
