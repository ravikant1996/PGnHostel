package com.example.testlogin.Admin_Profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testlogin.ApiClient;
import com.example.testlogin.ApiInterface;
import com.example.testlogin.Models.User;
import com.example.testlogin.R;
import com.example.testlogin.SessionManager;

import java.util.HashMap;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class manager_reg_frag extends Fragment {
    private EditText nameInput, phoneInput, emailInput, passwordInput;
    Button regBtn;
    int AdminId;
    Boolean Status;
    SessionManager session;

    public manager_reg_frag() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        getActivity().setTitle("Add Manager");
/*
        if (getArguments() != null) {
            AdminId = getArguments().getInt("id");
        }*/

        session = new SessionManager(getContext());

        HashMap<String, Integer> userID = session.getUserIDs();
        AdminId = userID.get(SessionManager.KEY_ID);


        View view =  inflater.inflate(R.layout.fragment_manager_reg_frag, container, false);
        nameInput = view.findViewById(R.id.nameInput);
        emailInput = view.findViewById(R.id.emailInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        phoneInput = view.findViewById(R.id.phoneInput);
        regBtn = view.findViewById(R.id.regBtn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });
        return view;
    }
    private void check() {
        if (emailInput.getText().toString().length() == 0) {

            Toast.makeText(getContext(),
                    "Email id cannot be Blank", Toast.LENGTH_LONG).show();
            emailInput.setError("Email cannot be Blank");

            return;
        }
        else if(nameInput.getText().toString().length() == 0) {

                Toast.makeText(getContext(),
                        "Name cannot be Blank", Toast.LENGTH_LONG).show();
                nameInput.setError("Name cannot be Blank");
                return;
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(
                emailInput.getText().toString()).matches()) {

            Toast.makeText(getContext(), "Invalid Email",
                    Toast.LENGTH_LONG).show();
            emailInput.setError("Invalid Email");
            return;
        }
        else if (passwordInput.getText().toString().length() == 0){
            Toast.makeText(getContext(),
                    "Password?", Toast.LENGTH_LONG).show();
            passwordInput.setError( "Password?");
            return;
        }
        else if (passwordInput.length() < 6){
            passwordInput.setError("Password  may be at least 6 characters long.");
        }
        else if (phoneInput.getText().toString().length() == 0){
            Toast.makeText(getContext(),
                    "Phone No?", Toast.LENGTH_LONG).show();
            phoneInput.setError("Phone No?");
            return;
        }
        else if (phoneInput.length() < 10){
            phoneInput.setError("Phone nubmer must be 10 digits");
        }
        else {
                regBtn.setEnabled(false);
                registerUser();
        }
   }

    public void registerUser() {
        int aid= AdminId;
        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String phone = phoneInput.getText().toString();
        Status= true;

        ApiInterface api = ApiClient.getApiService();
        Call<User> userCall = api.registerManager(name, email, password, phone, aid, Status);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getResponse().equals("inserted")) {
                    Log.e("response", response.body().getResponse());
                    nameInput.setText("");
                    emailInput.setText("");
                    passwordInput.setText("");
                    phoneInput.setText("");
                    regBtn.setEnabled(true);
                    Toast.makeText(getActivity(), "Registration Successful", Toast.LENGTH_LONG).show();
                   closeFragment();
                }else{
                    regBtn.setEnabled(true);
                    Toast.makeText(getActivity(), response.body().getResponse(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Error", Toast.LENGTH_LONG).show();
                regBtn.setEnabled(true);

            }
        });
    }
    private void closeFragment() {
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction().remove(manager_reg_frag.this).commit();
        }
    }

}
