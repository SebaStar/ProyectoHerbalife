package cl.inacap.herbalifeproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cl.inacap.herbalifeproject.dao.HerbalifeDAO;
import cl.inacap.herbalifeproject.dto.Seguimiento;
import cl.inacap.herbalifeproject.utils.Solicitud;

public class HSeguimientoFragment extends Fragment {

    public static HSeguimientoFragment newInstance(int id) {
        HSeguimientoFragment fragment = new HSeguimientoFragment();
        Bundle args = new Bundle();
        args.putInt(Solicitud.SEGUIMIENTO_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hseguimiento, container, false);
        initializeComponent(v);
        return v;
    }

    private void initializeComponent(View v) {
        TextView fechaTv, pesoTv;

        HerbalifeDAO hdao = new HerbalifeDAO(v.getContext());
        Seguimiento s = hdao.buscarSeguimiento(getArguments().getInt(Solicitud.SEGUIMIENTO_ID));

        fechaTv = v.findViewById(R.id.hseguimiento_fecha);
        pesoTv = v.findViewById(R.id.hseguimiento_peso);

        fechaTv.setText(s.getFecha());
        pesoTv.setText("Peso: " + s.getPeso() + " Kg");
    }
}
