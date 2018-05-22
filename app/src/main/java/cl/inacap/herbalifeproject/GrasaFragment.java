package cl.inacap.herbalifeproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GrasaFragment extends Fragment {

    private static GrasaFragment instance;

    public static GrasaFragment getInstance() {
        if (instance == null)
            instance = new GrasaFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grasa, container, false);
    }

}
