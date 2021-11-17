package com.example.testlogin.Admin_Profile;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testlogin.ApiClient;
import com.example.testlogin.ApiInterface;
import com.example.testlogin.R;
import com.example.testlogin.SessionManager;

import java.io.IOException;
import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment {

	private EditText  edtOldPass, edtNewPass, edtConfirmPass;
	private Button btnChangePass;
	private String password;
	private int aid;
	SessionManager session;

	public ChangePasswordFragment() {
		super();
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_change_password, container,
				false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		session = new SessionManager(getActivity());
		getActivity().setTitle("Change Password");
		bindView();

		HashMap<String, String> user = session.getUserDetails();
		// password
		password= user.get(SessionManager.KEY_PASSWORD);

		HashMap<String, Integer> userID = session.getUserIDs();
		aid = userID.get(SessionManager.KEY_ID);

	}

	private void bindView() {

		edtOldPass= (EditText) getActivity().findViewById(R.id.edtoldPassword);
		edtNewPass = (EditText) getActivity().findViewById(R.id.edtNewPassword);

		edtConfirmPass = (EditText) getActivity().findViewById(R.id.edtConfirmPassword);
		btnChangePass = (Button) getActivity().findViewById(R.id.btnChangePassword);

		btnChangePass.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				check();
			}
		});

	}

	private void check(){
		if(password != null) {
			if (edtOldPass.getText().toString().length() == 0) {

				Toast.makeText(getActivity(),
						"Old Password cannot be Blank", Toast.LENGTH_LONG)
						.show();
				edtOldPass.setError("Old Password cannot be Blank");

				return;

			} else if (edtNewPass.getText().toString().length() == 0) {
				Toast.makeText(getActivity(),
						"New Password cannot be Blank", Toast.LENGTH_LONG)
						.show();
				edtNewPass.setError("New Password cannot be Blank");
				return;

			} else if (edtNewPass.getText().length() <= 7) {

				Toast.makeText(getActivity(),
						"Password must be 8 characters above",
						Toast.LENGTH_LONG).show();
				edtNewPass.setError("Password must be 8 characters above");

				return;

			} /*else if (!(password.equals(edtOldPass.getText().toString()))) {
				Toast.makeText(getActivity(), "Old Password Is Not Match.",
						Toast.LENGTH_SHORT).show();
				edtOldPass.setError("Old Password Is Not Match");

			}
		*/
		else if (!edtNewPass.getText().toString()
					.equals(edtConfirmPass.getText().toString())) {

				Toast.makeText(getActivity(),
						"Confirm Password Is Not Match.",
						Toast.LENGTH_SHORT).show();

				edtConfirmPass.setError("Confirm Password Is Not Match.");

			} else {
				// id= AdminId;
				updatePassword(aid, edtNewPass.getText().toString());
			}
		}else {
			Toast.makeText(getActivity(), "Please Login Again", Toast.LENGTH_LONG).show();
		}
	}

	private void updatePassword(int id, String newPassword) {

		final ProgressDialog dialog;
		/**
		 * Progress Dialog for User Interaction
		 */
		dialog = new ProgressDialog(getActivity());
		dialog.setTitle("Loding");
		dialog.setMessage("Please Wait...");
		dialog.show();

		//Creating an object of our api interface
		ApiInterface api = ApiClient.getApiService();
		Call<ResponseBody> call = api.updatePasswordUser(id,newPassword);

		call.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				if (response.isSuccessful()) {
					//Dismiss Dialog
					dialog.dismiss();
					try {
						Toast.makeText(getActivity(), response.body().string(), Toast.LENGTH_LONG).show();
							edtOldPass.setText("");
							edtNewPass.setText("");
							edtConfirmPass.setText("");
							String pass= edtNewPass.getText().toString();
						session.createPassSession(pass);
						Toast.makeText(getActivity(), "Update Successful", Toast.LENGTH_LONG).show();
						closeFragment();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
					Toast.makeText(getActivity(), "Unsuccessful", Toast.LENGTH_LONG).show();

				}
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				//Dismiss Dialog
				Toast.makeText(getActivity(), "failed" , Toast.LENGTH_LONG).show();
				dialog.dismiss();
			}
		});
	}
public void closeFragment() {
	if (getFragmentManager() != null) {
		getFragmentManager().beginTransaction().remove(ChangePasswordFragment.this).commit();
	}
}

}
