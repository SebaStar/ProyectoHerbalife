package cl.inacap.herbalifeproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class UnusedActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView experimentText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unused);

        toolbar = findViewById(R.id.unused_toolbar);
        experimentText = findViewById(R.id.display_text);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Unused Activity");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        experimentText.setText(getIntent().getStringExtra("display_text"));
    }
}
