package cl.inacap.herbalifeproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AProgramaNutricionalActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprograma_nutricional);
        context = this;
        initializeComponent();
    }

    private void initializeComponent() {

    }
}
