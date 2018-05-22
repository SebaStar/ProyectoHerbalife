package cl.inacap.herbalifeproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PesoFragment extends Fragment {

    private static PesoFragment instance;

    public static PesoFragment getInstance() {
        if (instance == null)
            instance = new PesoFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_peso, container, false);
    }
}
