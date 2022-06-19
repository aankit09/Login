package com.test.login.signup;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.hbb20.CountryCodePicker;

import com.test.login.MainActivity;
import com.test.login.R;
import com.test.login.RegisterModelResponse;
import com.test.login.RetrofitClient;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpActivity extends AppCompatActivity {
    Button button_SignUp;
    EditText edit_Fullname, edit_PhoneNumber, edit_Email, edit_Password, edit_Confirm_Password;
    CountryCodePicker cp;
    ImageView ivMenu;
    CircleImageView image_Camera;
    CardView user_pic;
    TextView button_New_SignIn;
    String fullname, phonenumber, email, password, confirmpassword;
    CheckBox privacy_Terms;
    ProgressBar progress_bar;


    private CharSequence[] options = {"Camera", "Gallery", "Cancel"};

    public SignUpActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        button_SignUp = (Button) findViewById(R.id.button_SignUp);
        button_New_SignIn = findViewById(R.id.button_New_SignIn);
        ivMenu = (ImageView) findViewById(R.id.ivMenu);
        image_Camera = (CircleImageView) findViewById(R.id.image_Camera);
        user_pic = (CardView) findViewById(R.id.user_pic);
        edit_Fullname = (EditText) findViewById(R.id.edit_Fullname);
        edit_PhoneNumber = (EditText) findViewById(R.id.edit_PhoneNumber);
        edit_Email = (EditText) findViewById(R.id.edit_Email);
        edit_Password = (EditText) findViewById(R.id.edit_Password);
        edit_Confirm_Password = (EditText) findViewById(R.id.edit_Confirm_Password);
        privacy_Terms = (CheckBox) findViewById(R.id.privacy_Terms);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        cp = findViewById(R.id.cp);

        // requiredPermission();
        edit_Fullname.requestFocus();


        button_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress_bar.setVisibility(View.VISIBLE);
                fullname = edit_Fullname.getText().toString();
                phonenumber = edit_PhoneNumber.getText().toString();
                email = edit_Email.getText().toString();
                password = edit_Password.getText().toString();
                confirmpassword = edit_Confirm_Password.getText().toString();

                if (fullname.isEmpty()) {
                    edit_Fullname.requestFocus();
                    edit_Fullname.setError("please enter your name");
                    progress_bar.setVisibility(View.GONE);
                    return;
                }

                if (phonenumber.isEmpty()) {
                    edit_PhoneNumber.requestFocus();
                    edit_PhoneNumber.setError("please enter your phone number");
                    progress_bar.setVisibility(View.GONE);
                    return;
                }
                if (email.isEmpty()) {
                    edit_Email.requestFocus();
                    edit_Email.setError("please enter your email");
                    progress_bar.setVisibility(View.GONE);
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edit_Email.requestFocus();
                    edit_Email.setError("please enter correct email");
                    progress_bar.setVisibility(View.GONE);
                    return;
                }
                if (password.isEmpty()) {
                    edit_Password.requestFocus();
                    edit_Password.setError("please enter your password");
                    progress_bar.setVisibility(View.GONE);
                    return;
                }
                if (!password.equals(confirmpassword)) {
                    edit_Confirm_Password.requestFocus();
                    edit_Confirm_Password.setError("Password Not Match");
                    progress_bar.setVisibility(View.GONE);
                    return;
                }
                if (!privacy_Terms.isChecked()) {
                    privacy_Terms.requestFocus();
                    privacy_Terms.setError("please clicked button on Terms of service");
                    Toast.makeText(SignUpActivity.this, "please clicked button on Terms of service", Toast.LENGTH_SHORT).show();
                    progress_bar.setVisibility(View.GONE);
                    return;
                }
                Log.d("userdata", "onClick: " + email + password);
                Signupme();

            }
        });
        button_New_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alreadySignUp = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(alreadySignUp);
            }
        });

    }


    private void Signupme() {
        progress_bar.setVisibility(View.VISIBLE);
        Call<RegisterModelResponse> call = RetrofitClient
                .getInstance(this)
                .getApi()
                .getUserSignup(fullname, email, password, confirmpassword, phonenumber);
        call.enqueue(new Callback<RegisterModelResponse>() {
            @Override
            public void onResponse(Call<RegisterModelResponse> call, Response<RegisterModelResponse> response) {
                RegisterModelResponse registerModelResponse = response.body();
                progress_bar.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    Intent registerSuccessful = new Intent(SignUpActivity.this, MainActivity.class);
                    registerSuccessful.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    registerSuccessful.setType("image/*");
                    startActivityForResult(registerSuccessful, 0);
                    finish();
//                    Toast.makeText(SignUpActivity.this, registerModelResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    progress_bar.setVisibility(View.GONE);
                } else {
                    progress_bar.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this, registerModelResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterModelResponse> call, Throwable t) {
                Log.d("go", "onFailure: " + t.toString());
                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progress_bar.setVisibility(View.GONE);
            }
        });
    }
}