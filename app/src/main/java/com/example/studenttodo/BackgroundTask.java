package com.example.studenttodo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

@SuppressWarnings("ALL")
public class BackgroundTask extends AsyncTask<String, Void, String> {

    @SuppressLint("StaticFieldLeak")
    Context context;

    BackgroundTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... voids) {
        String method = voids[0];

        DatabaseHandler databaseHandler = new DatabaseHandler(context);

        if(method.equals("add")){
            String hour = voids[1];
            String school = voids[2];
            String location = voids[3];
            String subject = voids[4];
            String amountstuds = voids[5];
            String regdate = voids[6];

            SQLiteDatabase db = databaseHandler.getWritableDatabase();
            databaseHandler.addHourRegInformation(db, hour, school, location, subject, amountstuds, regdate);
            return "Successfully added record!";
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
