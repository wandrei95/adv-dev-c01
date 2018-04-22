package quiztador.com.quiztador;

import android.os.AsyncTask;
import android.util.Log;

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

public class LoginTask extends AsyncTask<Void, Void, Auth> {
    private String username;
    private String password;
    private LoginListener loginListener;

    public LoginTask() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void removeListener() {
        loginListener = null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (loginListener != null) {
            loginListener.onLoginStarted();
        }
    }

    @Override
    protected Auth doInBackground(Void... voids) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String stringUri = "http://192.168.13.31/svcourse2018/users/login";
        try {
            URL url = new URL(stringUri);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(5000);

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", username);
                jsonObject.put("password", password);
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
    protected void onPostExecute(Auth auth) {
        super.onPostExecute(auth);

        if (loginListener != null) {
            if (auth == null) {
                loginListener.onLoginFailure();
            } else {
                loginListener.onLoginSuccess(auth);
            }
        }
    }

    public interface LoginListener {
        void onLoginStarted();

        void onLoginSuccess(Auth auth);

        void onLoginFailure();
    }
}
