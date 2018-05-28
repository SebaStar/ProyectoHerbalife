package cl.inacap.herbalifeproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class RESeguimientoActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reseguimiento);
        context = this;
        initializeComponent();
    }

    private void initializeComponent() {

    }
}
