package com.example.pchan.usermanagement;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    protected String selectYear, selectMonth, selectDay;
    EditText birthDateSignUp;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker

        birthDateSignUp = (EditText) getActivity().findViewById(R.id.signUpBirth);
        // Create a new instance of DatePickerDialog and return it
        int txtDate[] = getDate();
        return new DatePickerDialog(getActivity(), this, txtDate[0], txtDate[1]-1, txtDate[2]);
    }

    public int[] getDate(){
        String birth = String.valueOf(birthDateSignUp.getText());
        String[] pieces = birth.split("/");
        return new int[]{Integer.parseInt(pieces[2]), Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1])};

    }
    public String getDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return simpleDateFormat.format(date);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user

        Calendar birth = Calendar.getInstance();
        birth.set(year,month,day,0,0,0);
        Date date = birth.getTime();

//        (birth.getTimeInMillis()-25200000)/1000);

        birthDateSignUp.setText(getDate(date));
        hideSoftKeyboard(view);
    }
    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

}
