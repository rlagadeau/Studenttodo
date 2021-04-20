package com.example.studenttodo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class RegistrationViewFragment extends Fragment {

    List<RegistrationData> registrationDataList;
    SQLiteDatabase sqLiteDatabase;
    ListView listViewHours;
    HoursAdapter hoursAdapter;

    private static final String TABLE_HOURS = "hours";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registration_view, container, false);
        listViewHours = (ListView) v.findViewById(R.id.listViewHours);
        registrationDataList = new ArrayList<>();

        //opening the database
        sqLiteDatabase = getActivity().openOrCreateDatabase("androiddb", MODE_PRIVATE, null);

        //this method will display the employees in the list
        showHoursFromDatabase();

        return v;
    }

    private void showHoursFromDatabase(){

        Cursor cursorHours = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_HOURS, null);

        if(cursorHours.moveToFirst()){
            //looping through all the records
            do{
                //pushing each record in the hours list
                registrationDataList.add(new RegistrationData(cursorHours.getString(1),
                        cursorHours.getString(2),
                        cursorHours.getString(3),
                        cursorHours.getString(4),
                        cursorHours.getString(5),
                        cursorHours.getString(6),
                        cursorHours.getInt(0)
                ));
            } while(cursorHours.moveToNext());
        }

        //closing the cursor
        cursorHours.close();

        //creating the adapter object
        hoursAdapter = new HoursAdapter(getActivity(), R.layout.list_layout_hours, registrationDataList, sqLiteDatabase);

        listViewHours.setAdapter(hoursAdapter);
    }
}
