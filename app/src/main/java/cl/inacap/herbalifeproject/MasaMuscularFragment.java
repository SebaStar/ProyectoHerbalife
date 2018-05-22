package cl.inacap.herbalifeproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MasaMuscularFragment extends Fragment {

    private static MasaMuscularFragment instance;

    public static MasaMuscularFragment getInstance() {
        if (instance == null)
            instance = new MasaMuscularFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_masa_muscular, container, false);
    }

}
