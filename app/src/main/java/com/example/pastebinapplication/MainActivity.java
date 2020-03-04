package com.example.pastebinapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button btnSendPaste;
    EditText pasteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSendPaste = findViewById(R.id.buttonSendPaste);

        pasteText = findViewById(R.id.editTextPasteText);

        btnSendPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gets the paste and converts to a string
                String pasteString = pasteText.getText().toString();
                new sendPasteAsync().execute(pasteString);
            }
        });
    }

    public static String makePaste(String pasteText) {
        // The is now local scope
        String line, response = "";

        try {
            URL url = new URL("https://pastebin.com/api/api_post.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Connection configuration

            // 5 seconds - times are in milisecond
            connection.setReadTimeout(5000);

            // Establing connecting for the server - longer than 5 seconds send error
            // message
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("POST");

            // We are sending server something
            connection.setDoOutput(true);

            // We are recieving some date bag - respone
            connection.setDoInput(true);

            // Lets you send things
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            String reqParams =  AppConstants.APIKEY + "&api_option=paste&api_paste_code=" + pasteText;

            writer.write(reqParams);

            writer.flush();
            writer.close();

            os.close();

            // Was the connection successful
            int responseCode = connection.getConnectTimeout();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                // Gives you abit of extra time so it can load
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                // Use the buffered reader - reade the line and store it in the brackets - Keep
                // doing it its not equal to null
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {

                // Gives you abit of extra time so it can load
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                // Use the buffered reader - reade the line and store it in the brackets - Keep
                // doing it its not equal to null
                while ((line = br.readLine()) != null) {
                    response += line;

                }
                System.out.println();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response;
    }

                                //<ARGUMENT, UPDATE, RETURN TYPE>
 class sendPasteAsync extends AsyncTask<String, Void, String>
{
    @Override
    //That string above is the same string below
    protected String doInBackground(String... strings) {

        String pasteString = strings[0];
        //Making a string
        return makePaste(pasteString);
    }

    @Override
    //String is now put into resp
    protected void onPostExecute (String resp) {

        //Print out resp
        System.out.println(resp);
    }
}



}
