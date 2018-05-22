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

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder> implements Filterable {

    private List<Cliente> clientes;
    private List<Cliente> clientesFiltrados;
    private Context context;
    private OnRowItemClickListener listener;

    public ClienteAdapter(List<Cliente> clientes, Context context, OnRowItemClickListener listener){
        this.clientes = clientes;
        this.clientesFiltrados = clientes;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.layout_listar_clientes, parent, false);
        return new ViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindCliente(clientesFiltrados.get(position), position);
    }

    @Override
    public int getItemCount() {
        return clientesFiltrados.size();
    }

    public boolean isEmpty() {
        return clientesFiltrados.isEmpty();
    }

    public void reset() {
        clientesFiltrados = clientes;
        notifyDataSetChanged();
    }

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