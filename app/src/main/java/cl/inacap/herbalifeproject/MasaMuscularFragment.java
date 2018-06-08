package cl.inacap.herbalifeproject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.herbalifeproject.dao.HerbalifeDAO;
import cl.inacap.herbalifeproject.dto.Seguimiento;
import cl.inacap.herbalifeproject.utils.Preferences;
import cl.inacap.herbalifeproject.utils.Solicitud;

public class MasaMuscularFragment extends Fragment {

    private static MasaMuscularFragment instance;

    public static MasaMuscularFragment getInstance(Bundle bundle) {
        if (instance == null) {
            instance = new MasaMuscularFragment();
            instance.setArguments(bundle);
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_masa_muscular, container, false);
        initializeComponent(v);
        return v;
    }

    private void initializeComponent(View v) {
        int clienteId = getArguments().getInt(Solicitud.CLIENTE_ID), i = 0;
        int usuarioId = Preferences.getPreferenceInt(v.getContext(), Preferences.MAIN_PREF, Preferences.USUARIO_ID);

        LineChart grafico = v.findViewById(R.id.masa_grafico);
        HerbalifeDAO hdao = new HerbalifeDAO(v.getContext());
        List<Seguimiento> seguimientos = hdao.listarSeguimientos(clienteId, usuarioId);

        if (seguimientos.isEmpty())
            return;
        List<Entry> entries = new ArrayList<>();
        for (Seguimiento s : seguimientos) {
            entries.add(new Entry(i, s.getMuscular()));
            i++;
        }
        LineDataSet dataSet = new LineDataSet(entries, "Masa muscular");
        dataSet.setColor(Color.RED);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        LineData data = new LineData(dataSets);
        grafico.setData(data);
        grafico.getDescription().setText("Seguimientos/Masa muscular");
        grafico.invalidate();
    }
}
