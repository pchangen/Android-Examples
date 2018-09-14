package com.example.pchan.usermanagement;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ForgotPassword extends AppCompatActivity {
    private AutoCompleteTextView emailForgot;
    private EditText firstNameForgot, lastNameForgot;
    private LinearLayout forgotMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        forgotMainLayout = (LinearLayout) findViewById(R.id.forgotMainLayout);
        forgotMainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(ForgotPassword.this);
                return true;
            }
        });
        //Input User Information
        emailForgot = (AutoCompleteTextView) findViewById(R.id.forgotEmail);
        firstNameForgot = (EditText) findViewById(R.id.forgotFirstName);
        lastNameForgot = (EditText) findViewById(R.id.forgotLastName);
        Button resetPassword = (Button) findViewById(R.id.forgotResetPassword);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptForgotPassword();
            }
        });


    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }
    private void attemptForgotPassword() {
//        if (mAuthTask != null) {
//            return;
//        }

        // Reset errors.
        emailForgot.setError(null);
        firstNameForgot.setError(null);
        lastNameForgot.setError(null);

        // Store values at the time of the login attempt.
        String email = emailForgot.getText().toString();
        String firstName = firstNameForgot.getText().toString();
        String lastName = lastNameForgot.getText().toString();

        boolean cancel = false;
        View focusView = null;
        hideKeyboard(ForgotPassword.this);
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailForgot.setError(getString(R.string.error_field_required));
            focusView = emailForgot;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailForgot.setError(getString(R.string.error_invalid_email));
            focusView = emailForgot;
            cancel = true;
        }
        // Check for a valid first name
        if (TextUtils.isEmpty(firstName)) {
            firstNameForgot.setError(getString(R.string.error_field_required));
            focusView = firstNameForgot;
            cancel = true;
        }
        // Check for a valid last name
        if (TextUtils.isEmpty(lastName)) {
            lastNameForgot.setError(getString(R.string.error_field_required));
            focusView = lastNameForgot;
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
