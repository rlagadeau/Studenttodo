package com.example.studenttodo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.List;

public class HoursAdapter extends ArrayAdapter<RegistrationData> {

    Context context;
    int listLayoutRes;
    List<RegistrationData> registrationDataList;
    SQLiteDatabase sqLiteDatabase;

    public HoursAdapter(Context context, int listLayoutRes, List<RegistrationData> registrationDataList, SQLiteDatabase sqLiteDatabase){
        super(context, listLayoutRes, registrationDataList);

        this.context = context;
        this.listLayoutRes = listLayoutRes;
        this.registrationDataList = registrationDataList;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("ViewHolder") View view = inflater.inflate(listLayoutRes, null);

        final RegistrationData registrationData = registrationDataList.get(position);

        //getting views
        TextView textViewHours = view.findViewById(R.id.textViewHours);
        TextView textViewSchool = view.findViewById(R.id.textViewSchool);
        TextView textViewLocation = view.findViewById(R.id.textViewLocation);
        TextView textViewSubject = view.findViewById(R.id.textViewSubject);
        TextView textViewAmountStuds = view.findViewById(R.id.textViewAmountStuds);
        TextView textViewRegistrationDate = view.findViewById(R.id.textViewRegistrationDate);

        //adding data to views
        textViewHours.setText("Hours: " + registrationData.getHours());
        textViewSchool.setText("School: " + registrationData.getSchool());
        textViewLocation.setText("Location: " + registrationData.getLocation());
        textViewSubject.setText("Subject: " + registrationData.getSubject());
        textViewAmountStuds.setText("Amount students: " + registrationData.getAmountstuds());
        textViewRegistrationDate.setText("Date: " + registrationData.getRegdate());

        //we will use these ImageViews later for update and delete operation
        ImageView btnDelete = view.findViewById(R.id.buttonDeleteHours);
        ImageView btnUpdate = view.findViewById(R.id.buttonEditHours);

        //adding a clicklistener to button
        btnUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                updateHours(registrationData);
            }
        });

        //the delete operation
        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure you want to delete this item?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String sql = "DELETE FROM hours WHERE id = ?";
                                sqLiteDatabase.execSQL(sql, new Integer[]{registrationData.getId()});
                                reloadHoursFromDatabase();
                            }
                        }
                );
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }
                );
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    private void updateHours(final RegistrationData registrationData){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_update_hours, null);
        builder.setView(view);

        final EditText editTextHours = view.findViewById(R.id.editTextHours);
        final EditText editTextSchool = view.findViewById(R.id.editTextSchool);
        final EditText editTextLocation = view.findViewById(R.id.editTextLocation);
        final EditText editTextSubject = view.findViewById(R.id.editTextSubject);
        final EditText editTextAmountStuds = view.findViewById(R.id.editTextAmountStuds);

        editTextHours.setText(registrationData.getHours());
        editTextSchool.setText(registrationData.getSchool());
        editTextLocation.setText(registrationData.getLocation());
        editTextSubject.setText(registrationData.getSubject());
        editTextAmountStuds.setText(registrationData.getAmountstuds());

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.buttonUpdateHours).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String Hours = editTextHours.getText().toString().trim();
                String School = editTextSchool.getText().toString().trim();
                String Location = editTextLocation.getText().toString().trim();
                String Subject = editTextSubject.getText().toString().trim();
                String AmountStuds = editTextAmountStuds.getText().toString().trim();

                if(Hours.isEmpty()){
                    editTextHours.setError("Hours field can't be blank!");
                    editTextHours.requestFocus();
                    return;
                }

                if(School.isEmpty()){
                    editTextSchool.setError("School field can't be empty!");
                    editTextSchool.requestFocus();
                    return;
                }

                if(Location.isEmpty()){
                    editTextLocation.setError("Location field can't be empty!");
                    editTextLocation.requestFocus();
                    return;
                }

                if(Subject.isEmpty()){
                    editTextSubject.setError("Subject field can't be empty!");
                    editTextSubject.requestFocus();
                    return;
                }

                if(AmountStuds.isEmpty()){
                    editTextAmountStuds.setError("Amount students field can't be empty!");
                    editTextAmountStuds.requestFocus();
                    return;
                }

                String sql = "UPDATE hours " +
                        "SET hours = ?, " +
                        "school = ?, " +
                        "location = ?, " +
                        "subject = ?, " +
                        "amountstuds = ? " +
                        "WHERE id = ?; ";

                sqLiteDatabase.execSQL(sql, new String[]{Hours, School, Location, Subject, AmountStuds, String.valueOf(registrationData.getId())});
                Toast.makeText(context, "Item Updated", Toast.LENGTH_SHORT).show();
                reloadHoursFromDatabase();

                dialog.dismiss();
            }
        });
    }


    private void reloadHoursFromDatabase(){
        Cursor cursorHours = sqLiteDatabase.rawQuery("SELECT * FROM hours", null);
        if(cursorHours.moveToFirst()){
            registrationDataList.clear();
            do{
                registrationDataList.add(new RegistrationData(
                        cursorHours.getString(1),
                        cursorHours.getString(2),
                        cursorHours.getString(3),
                        cursorHours.getString(4),
                        cursorHours.getString(5),
                        cursorHours.getString(6),
                        cursorHours.getInt(0)
                ));
            } while(cursorHours.moveToNext());
        }
        cursorHours.close();
        notifyDataSetChanged();
    }

}
