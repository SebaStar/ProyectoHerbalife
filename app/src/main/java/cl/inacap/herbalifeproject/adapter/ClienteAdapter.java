package cl.inacap.herbalifeproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cl.inacap.herbalifeproject.R;
import cl.inacap.herbalifeproject.dto.Cliente;
import cl.inacap.herbalifeproject.interfaces.Listeners.OnRowItemClickListener;

/**
 * Adaptador que manipula y muestra información en un contenedor.
 * @author Sebastián
 */

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder> implements Filterable {

    private List<Cliente> clientes;
    private List<Cliente> clientesFiltrados;
    private Context context;
    private OnRowItemClickListener listener;

    /**
     * Constructor de la clase.
     * @param clientes Lista de todos los clientes que están en el sistema.
     * @param context
     * @param listener Contiene los eventos para los controles que tiene una fila del contenedor.
     */
    public ClienteAdapter(List<Cliente> clientes, Context context, OnRowItemClickListener listener){
        this.clientes = clientes;
        this.clientesFiltrados = clientes;
        this.context = context;
        this.listener = listener;
    }

    /**
     * Crea e infla el diseño desde un archivo a la lista contenedora.
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.layout_listar_clientes, parent, false);
        return new ViewHolder(v, listener);
    }

    /**
     * Rellena los controles con datos
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindCliente(clientesFiltrados.get(position), position);
    }

    /**
     * Recibe el contador de la listra filtrada
     * @return cantidad de clientes que fueron filtrados
     */
    @Override
    public int getItemCount() {
        return clientesFiltrados.size();
    }

    /**
     * Revisa si la lista de clientes se encuentra vacía.
     * @return
     */
    public boolean isEmpty() {
        return clientesFiltrados.isEmpty();
    }

    /**
     * Retorna la lista filtrada a su estado inicial.
     */
    public void reset() {
        clientesFiltrados = clientes;
        notifyDataSetChanged();
    }

    /**
     * Actualiza la lista completa
     * @param cls Nueva lista para limpiar y re-añadir la lista.
     */
    public void update(List<Cliente> cls) {
        clientes.clear();
        clientes.addAll(cls);
        reset();
    }

    /**
     * Retorna un filtro que puede ser usado como restricción.
     * @return
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty())
                    clientesFiltrados = clientes;
                else {
                    List<Cliente> filtrados = new ArrayList<>();
                    for (Cliente c : clientes) {
                        if (c.getNombre().toLowerCase().contains(charString.toLowerCase()))
                            filtrados.add(c);
                    }
                    clientesFiltrados = filtrados;
                }
                FilterResults results = new FilterResults();
                results.values = clientesFiltrados;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clientesFiltrados = (ArrayList<Cliente>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView clienteIdTv, clienteNombreTv;
        ImageButton menuBtn;
        OnRowItemClickListener clickListener;

        public ViewHolder(View v, OnRowItemClickListener clickListener) {
            super(v);
            this.clickListener = clickListener;
            clienteIdTv = v.findViewById(R.id.lc_id);
            clienteNombreTv = v.findViewById(R.id.lc_nombre);
            menuBtn = v.findViewById(R.id.lc_menu);
        }

        public void bindCliente(Cliente c, final int position) {
            clienteIdTv.setText(String.valueOf(c.getId()));
            clienteNombreTv.setText(c.getNombre());
            menuBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu menu = new PopupMenu(context, v);
                    menu.inflate(R.menu.listar_clientes_context_menu);
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.lcc_editar:
                                    clickListener.onEditClick(position);
                                    return true;
                                case R.id.lcc_borrar:
                                    clickListener.onDeleteClick(position);
                                    return true;
                            }
                            return false;
                        }
                    });
                    menu.show();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(position);
                }
            });
        }
    }
}