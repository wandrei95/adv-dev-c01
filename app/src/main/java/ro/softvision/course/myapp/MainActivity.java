package ro.softvision.course.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view_test);
        textView.setText("New text");
        editText = findViewById(R.id.et_input);

        Button button = findViewById(R.id.btn);
        button.setOnClickListener(this);
        Button btnToast = findViewById(R.id.btn_show_toast);
        btnToast.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                ((Button) v).setText("new btn text");
                Random random = new Random();
                textView.setText("changed " + random.nextInt());
                break;
            case R.id.btn_show_toast:
                Toast.makeText(this, editText.getText().toString(), Toast.LENGTH_LONG).show();
                break;
            default:
        }
    }
}
