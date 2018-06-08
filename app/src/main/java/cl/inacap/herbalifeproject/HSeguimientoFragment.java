package cl.inacap.herbalifeproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cl.inacap.herbalifeproject.dao.HerbalifeDAO;
import cl.inacap.herbalifeproject.dto.Cliente;
import cl.inacap.herbalifeproject.dto.Seguimiento;
import cl.inacap.herbalifeproject.utils.Solicitud;
import cl.inacap.herbalifeproject.utils.SystemUtils;

public class HSeguimientoFragment extends Fragment {

    public static HSeguimientoFragment newInstance(int segId, int clienteId) {
        HSeguimientoFragment fragment = new HSeguimientoFragment();
        Bundle args = new Bundle();
        args.putInt(Solicitud.SEGUIMIENTO_ID, segId);
        args.putInt(Solicitud.CLIENTE_ID, clienteId);
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
        TextView fechaTv, pesoTv, grasaTotalTv, masaOseaTv, aguaTv, masaMuscularTv, imcTv, edadMetabolicaTv, bmrTv, grasaViceralTv, cinturaTv;

        HerbalifeDAO hdao = new HerbalifeDAO(v.getContext());
        Seguimiento s = hdao.buscarSeguimiento(getArguments().getInt(Solicitud.SEGUIMIENTO_ID));
        Cliente c = hdao.buscarCliente(getArguments().getInt(Solicitud.CLIENTE_ID));

        fechaTv = v.findViewById(R.id.hseguimiento_fecha);
        pesoTv = v.findViewById(R.id.hseguimiento_peso);
        grasaTotalTv = v.findViewById(R.id.hseguimiento_grasaTotal);
        masaOseaTv = v.findViewById(R.id.hseguimiento_osea);
        aguaTv = v.findViewById(R.id.hseguimiento_agua);
        masaMuscularTv = v.findViewById(R.id.hseguimiento_muscular);
        imcTv = v.findViewById(R.id.hseguimiento_imc);
        edadMetabolicaTv = v.findViewById(R.id.hseguimiento_edad);
        bmrTv = v.findViewById(R.id.hseguimiento_bmr);
        grasaViceralTv = v.findViewById(R.id.hseguimiento_viceral);
        cinturaTv = v.findViewById(R.id.hseguimiento_cintura);

        fechaTv.setText(s.getFecha());
        pesoTv.setText(s.getPeso() + " Kg");
        grasaTotalTv.setText(s.getGrasaTotal() + "%");
        masaOseaTv.setText(s.getOsea() + "%");
        aguaTv.setText(s.getAgua() + "%");
        masaMuscularTv.setText(s.getMuscular() + "%");
        imcTv.setText(SystemUtils.getInstance().getImc(s.getPeso(), c.getAltura()) + " Kg/m²");
        edadMetabolicaTv.setText(s.getEdadMetabolica() + " años");
        bmrTv.setText(s.getBmr() + " Kcal");
        grasaViceralTv.setText(s.getGrasaViceral() + "%");
        cinturaTv.setText(s.getCintura() + " cm");
    }
}
