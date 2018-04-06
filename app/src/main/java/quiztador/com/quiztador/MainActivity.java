package quiztador.com.quiztador;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView tvResponse;
    private TextView tvId;
    private TextView tvToken;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResponse = findViewById(R.id.tv_response);
        tvId = findViewById(R.id.tv_id);
        tvToken = findViewById(R.id.tv_token);
        loading = findViewById(R.id.loading);

        new NetworkExampleAsyncTask().execute();
    }

    private class NetworkExampleAsyncTask extends AsyncTask<Void, Void, Auth> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Auth doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String stringUri = "http://192.168.0.100/svcourse2018/users/login";
            try {
                URL url = new URL(stringUri);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setConnectTimeout(5000);

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("username", "wandrei");
                    jsonObject.put("password", "1234567");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                httpURLConnection.setRequestProperty("Content-Type", "application/json");

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(jsonObject.toString().getBytes());
                outputStream.flush();
                outputStream.close();


                int responseCode = httpURLConnection.getResponseCode();
                Log.e("AAAAAAAAAAAAAAAAAAAAAA", responseCode + " ");

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";

                StringBuilder stringBuilder = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }

                String responseString = stringBuilder.toString();
                JSONObject responseJson = new JSONObject(responseString);

                Log.e("AAAAAAAAAAAAAAAAAAAAAA", responseString);

                Auth auth = new Auth();
                auth.setId(responseJson.getString("userId"));
                auth.setToken(responseJson.getString("authorizationToken"));

                return auth;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Auth s) {
            super.onPostExecute(s);

            loading.setVisibility(View.GONE);
            if (s == null) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                return;
            }

            tvId.setText(s.getId());
            tvToken.setText(s.getToken());
        }
    }
}
