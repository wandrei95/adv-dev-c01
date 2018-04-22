package quiztador.com.quiztador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginTask.LoginListener {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvSignUp;
    private ProgressBar loading;

    private LoginTask loginTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvSignUp = findViewById(R.id.tv_sign_up);
        loading = findViewById(R.id.loading);

        btnLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);

        loginTask = new LoginTask();
        loginTask.setLoginListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String username = etUsername.getText().toString();
                String pass = etPassword.getText().toString();
                loginTask.setUsername(username);
                loginTask.setPassword(pass);
                loginTask.execute();
                break;
            case R.id.tv_sign_up:
                Intent registerActivityIntent = new Intent(this, RegisterActivity.class);
                startActivity(registerActivityIntent);
                break;
        }
    }

    @Override
    public void onLoginStarted() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoginSuccess(Auth auth) {
        loading.setVisibility(View.GONE);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.AUTH_TOKEN_KEY, auth.getToken());
        startActivity(intent);
    }

    @Override
    public void onLoginFailure() {
        loading.setVisibility(View.GONE);
    }
}