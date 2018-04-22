package quiztador.com.quiztador;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String AUTH_TOKEN_KEY = "auth_token";
    public static final String AUTH_TOKEN_SAVED_KEY = "auth_token_save";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null && bundle.containsKey(AUTH_TOKEN_KEY)) {
            String token = bundle.getString(AUTH_TOKEN_KEY);

            SharedPreferences sharedPreferences = getSharedPreferences("MainActivity", MODE_PRIVATE);

            String savedToken = sharedPreferences.getString(AUTH_TOKEN_SAVED_KEY, "default");
            Log.e("AAAAAAAAAAAAA", "saved string " + savedToken);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(AUTH_TOKEN_SAVED_KEY, token);
            editor.apply();
        }
    }
}
