package com.example.pchan.usermanagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.DataFormatException;

public class SignUp extends AppCompatActivity {

    // UI references.
    private EditText birthDateSignUp,lastNameSignUp, firstNameSignUp, passwordSighUp, passwordRepeatSignUp;
    private AutoCompleteTextView  emailSignUp;
    private Spinner genderSignUp;
    private LinearLayout signUpMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Hide Keyboard
        signUpMainLayout = (LinearLayout) findViewById(R.id.signUpMainLayout);
        signUpMainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(SignUp.this);
                return true;
            }
        });

        //Input User Information
        emailSignUp = (AutoCompleteTextView) findViewById(R.id.signUpEmail);
        passwordSighUp = (EditText) findViewById(R.id.signUpPassword);
        passwordRepeatSignUp = (EditText) findViewById(R.id.signUpRepeatPassword);
        firstNameSignUp = (EditText) findViewById(R.id.signUpFirstName);
        lastNameSignUp = (EditText) findViewById(R.id.signUpLastName);
        birthDateSignUp = (EditText) findViewById(R.id.signUpBirth);
        birthDateSignUp.setText(getCurrentDate());
        birthDateSignUp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean focusOn) {
                        if (focusOn){
                            showDatePickerDialog(view);
                    hideKeyboard(SignUp.this);
                }
            }
        });
        //Spinner for gender
        genderSignUp = (Spinner) findViewById(R.id.signUpGender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        genderSignUp.setAdapter(adapter);

        Button completeSignUpButton = (Button) findViewById(R.id.signUpComplete);
        completeSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignUp();
            }
        });
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    protected String getCurrentDate(){
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        return formatter.format(today);
    }
    protected int getTimestamp(){
        String birth = String.valueOf(birthDateSignUp.getText());
        try{
            String[] pieces = birth.split("/");
            Calendar birthCalendar = Calendar.getInstance();
            birthCalendar.set(Integer.parseInt(pieces[2]),Integer.parseInt(pieces[0]),Integer.parseInt(pieces[1]),0,0,0);
            int birthTimestamp = (int)((birthCalendar.getTimeInMillis()-25200000)/1000);
            return birthTimestamp;
        }catch (Exception e ){
            return 0;
        }

    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    private boolean isPasswordsEqual(String password, String repeatPassword) {
        //TODO: Replace this with your own logic
        return password.equals(repeatPassword);
    }



    private void attemptSignUp() {
//        if (mAuthTask != null) {
//            return;
//        }

        // Reset errors.
        emailSignUp.setError(null);
        passwordSighUp.setError(null);
        passwordRepeatSignUp.setError(null);
        firstNameSignUp.setError(null);
        lastNameSignUp.setError(null);
        firstNameSignUp.setError(null);
        birthDateSignUp.setError(null);

        // Store values at the time of the login attempt.
        String email = emailSignUp.getText().toString();
        String password = passwordSighUp.getText().toString();
        String repeatPassword = passwordRepeatSignUp.getText().toString();
        String firstName = firstNameSignUp.getText().toString();
        String lastName = lastNameSignUp.getText().toString();
        String birthDate = birthDateSignUp.getText().toString();

        boolean cancel = false;
        View focusView = null;
        hideKeyboard(SignUp.this);
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailSignUp.setError(getString(R.string.error_field_required));
            focusView = emailSignUp;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailSignUp.setError(getString(R.string.error_invalid_email));
            focusView = emailSignUp;
            cancel = true;
        }
        // Check for a valid password
        if (TextUtils.isEmpty(password)) {
            passwordSighUp.setError(getString(R.string.error_field_required));
            focusView = passwordSighUp;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            passwordSighUp.setError(getString(R.string.error_invalid_password));
            focusView = passwordSighUp;
            cancel = true;
        }
        // Check for a valid repeat password
        if (TextUtils.isEmpty(repeatPassword)) {
            passwordRepeatSignUp.setError(getString(R.string.error_field_required));
            focusView = passwordRepeatSignUp;
            cancel = true;
        } else if (!isPasswordsEqual(password, repeatPassword)) {
            passwordRepeatSignUp.setError(getString(R.string.error_invalid_repeat_password));
            focusView = passwordRepeatSignUp;
            cancel = true;
        }
        // Check for a valid first name
        if (TextUtils.isEmpty(firstName)) {
            firstNameSignUp.setError(getString(R.string.error_field_required));
            focusView = firstNameSignUp;
            cancel = true;
        }
        // Check for a valid last name
        if (TextUtils.isEmpty(lastName)) {
            lastNameSignUp.setError(getString(R.string.error_field_required));
            focusView = lastNameSignUp;
            cancel = true;
        }
        // Check for a valid birthDate
        if (TextUtils.isEmpty(birthDate)) {
            birthDateSignUp.setError(getString(R.string.error_field_required));
            focusView = birthDateSignUp;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new LoginActivity.UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
        }
    }
    private void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

