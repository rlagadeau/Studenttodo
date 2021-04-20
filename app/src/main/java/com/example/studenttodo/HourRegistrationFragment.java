package com.example.studenttodo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HourRegistrationFragment extends Fragment {

    EditText e_hour;
    EditText e_school;
    EditText e_location;
    EditText e_subject;
    EditText e_amountstuds;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_hour_registration, container,false);
        e_hour = (EditText) v.findViewById(R.id.hour);
        e_school = (EditText) v.findViewById(R.id.school);
        e_location = (EditText) v.findViewById(R.id.location);
        e_subject = (EditText) v.findViewById(R.id.subject);
        e_amountstuds = (EditText) v.findViewById(R.id.amountstuds);

        final Button loginButton = (Button) v.findViewById(R.id.submitbtn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                SaveHourRegInfo(v);
            }
        });

        return v;
    }

    public void SaveHourRegInfo(View view){
        String hour = e_hour.getText().toString();
        String school = e_school.getText().toString();
        String location = e_location.getText().toString();
        String subject = e_subject.getText().toString();
        String amountstuds = e_amountstuds.getText().toString();

        if(hour.isEmpty() && school.isEmpty() && location.isEmpty() && subject.isEmpty() && amountstuds.isEmpty()){
            Toast.makeText(getActivity(), "The fields are empty. Please fill them in!", Toast.LENGTH_LONG).show();
            return;
        }

        if(hour.isEmpty()){
            Toast.makeText(getActivity(), "The hour field is empty. Please fill this in!", Toast.LENGTH_LONG).show();
            return;
        }

        if(school.isEmpty()){
            Toast.makeText(getActivity(), "The school field is empty. Please fill this in!", Toast.LENGTH_LONG).show();
            return;
        }

        if(location.isEmpty()){
            Toast.makeText(getActivity(), "The location field is empty. Please fill this in!", Toast.LENGTH_LONG).show();
            return;
        }

        if(subject.isEmpty()){
            Toast.makeText(getActivity(), "The subject field is empty. Please fill this in!", Toast.LENGTH_LONG).show();
            return;
        }

        if(amountstuds.isEmpty()){
            Toast.makeText(getActivity(), "The amount students field is empty. Please fill this in!", Toast.LENGTH_LONG).show();
            return;
        }

        String regdate = (String) android.text.format.DateFormat.format("yyyy-MM-dd kk:mm:ss", new java.util.Date());

        e_hour.getText().clear();
        e_school.getText().clear();
        e_location.getText().clear();
        e_subject.getText().clear();
        e_amountstuds.getText().clear();

        BackgroundTask backgroundTask = new BackgroundTask(getActivity());
        backgroundTask.execute("add", hour, school, location, subject, amountstuds, regdate);
    }

}
