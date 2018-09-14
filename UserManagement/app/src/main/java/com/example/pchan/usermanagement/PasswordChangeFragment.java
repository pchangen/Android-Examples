package com.example.pchan.usermanagement;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PasswordChangeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PasswordChangeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PasswordChangeFragment extends Fragment {
     // UI references.
    private Context context;
    private EditText currentPasswordChange, newPasswordChange, repeatNewPasswordChange;
    private AutoCompleteTextView  emailPasswordChange;
    private LinearLayout mainLayoutPasswordChange;

    private OnFragmentInteractionListener mListener;

    public PasswordChangeFragment() {
        // Required empty public constructor
    }

    public static PasswordChangeFragment newInstance(Context context) {
        PasswordChangeFragment fragment = new PasswordChangeFragment();
        fragment.context =context;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Resource match
        View layout = inflater.inflate(R.layout.fragment_password_change, container, false);
        emailPasswordChange = (AutoCompleteTextView) layout.findViewById(R.id.passwordEmail);
        currentPasswordChange = (EditText) layout.findViewById(R.id.passwordCurreuntPassword);
        newPasswordChange = (EditText) layout.findViewById(R.id.passwordNewPassword);
        repeatNewPasswordChange = (EditText) layout.findViewById(R.id.passwordRepeatNewPassword);
        mainLayoutPasswordChange = (LinearLayout) layout.findViewById(R.id.passwordChangeMainLayout);

        Button passwordChangeButton= (Button) layout.findViewById(R.id.passwordButton);
        passwordChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptPasswordChange();
            }
        });

        return layout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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

    private void attemptPasswordChange() {
//        if (mAuthTask != null) {
//            return;
//        }
        // Reset errors.
        emailPasswordChange.setError(null);
        currentPasswordChange.setError(null);
        newPasswordChange.setError(null);
        repeatNewPasswordChange.setError(null);

        // Store values at the time of the login attempt.
        String email = emailPasswordChange.getText().toString();
        String currentPassword = currentPasswordChange.getText().toString();
        String newPassword = newPasswordChange.getText().toString();
        String repeatNewPassword = repeatNewPasswordChange.getText().toString();

        boolean cancel = false;
        View focusView = null;
        hideKeyboard();
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailPasswordChange.setError(getString(R.string.error_field_required));
            focusView = emailPasswordChange;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailPasswordChange.setError(getString(R.string.error_invalid_email));
            focusView = emailPasswordChange;
            cancel = true;
        }
        // Check for a valid current password
        if (TextUtils.isEmpty(currentPassword)) {
            currentPasswordChange.setError(getString(R.string.error_field_required));
            focusView = currentPasswordChange;
            cancel = true;
        } else if (!isPasswordValid(currentPassword)) {
            currentPasswordChange.setError(getString(R.string.error_invalid_password));
            focusView = currentPasswordChange;
            cancel = true;
        }
        // Check for a valid new password
        if (TextUtils.isEmpty(newPassword)) {
            newPasswordChange.setError(getString(R.string.error_field_required));
            focusView = newPasswordChange;
            cancel = true;
        } else if (!isPasswordsEqual(currentPassword, newPassword)) {
            newPasswordChange.setError(getString(R.string.error_invalid_new_password));
            focusView = newPasswordChange;
            cancel = true;
        }
        // Check for a valid repeat new password
        if (TextUtils.isEmpty(repeatNewPassword)) {
            repeatNewPasswordChange.setError(getString(R.string.error_field_required));
            focusView = repeatNewPasswordChange;
            cancel = true;
        } else if (!isPasswordsEqual(newPassword, repeatNewPassword)) {
            repeatNewPasswordChange.setError(getString(R.string.error_invalid_repeat_password));
            focusView = repeatNewPasswordChange;
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

    private void hideKeyboard() {
        View view = getActivity().findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
