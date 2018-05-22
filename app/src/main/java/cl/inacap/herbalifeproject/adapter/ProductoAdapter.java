package cl.inacap.herbalifeproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import cl.inacap.herbalifeproject.R;
import cl.inacap.herbalifeproject.dto.Producto;
import cl.inacap.herbalifeproject.interfaces.Listeners.OnRemoveRowClickListener;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    private List<Producto> productos;
    private Context context;
    private OnRemoveRowClickListener listener;

    public ProductoAdapter(List<Producto> productos, Context context, OnRemoveRowClickListener listener) {
        this.productos = productos;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.layout_listar_productos, parent, false);
        return new ViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindProducto(productos.get(position), position);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public boolean isEmpty() {
        return productos.isEmpty();
    }

    public void clearItems(List<Producto> productos) {
        this.productos = productos;
        notifyDataSetChanged();
    }

    public void removeAt(int position) {
        productos.remove(position);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView productoTv, cantidadTv;
        ImageButton eliminarBtn;
        OnRemoveRowClickListener clickListener;

        String[] productosStr;

        public ViewHolder(View v, OnRemoveRowClickListener clickListener) {
            super(v);
            this.clickListener = clickListener;
            productoTv = v.findViewById(R.id.lp_producto);
            cantidadTv = v.findViewById(R.id.lp_cantidad);
            eliminarBtn = v.findViewById(R.id.lp_eliminar);
            productosStr = v.getContext().getResources().getStringArray(R.array.productos);
        }

        public void bindProducto(Producto p, final int position) {
            productoTv.setText(productosStr[p.getNombre()]);
            cantidadTv.setText(String.valueOf(p.getCantidad()));
            eliminarBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onRemove(position);
                }
            });
        }
    }
}
