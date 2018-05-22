package cl.inacap.herbalifeproject.interfaces;

public class Listeners {

    public interface OnRowItemClickListener {
        void onItemClick(int position);
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    public interface OnRemoveRowClickListener {
        void onRemove(int position);
    }
}
