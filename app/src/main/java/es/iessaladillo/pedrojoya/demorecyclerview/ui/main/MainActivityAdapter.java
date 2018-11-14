package es.iessaladillo.pedrojoya.demorecyclerview.ui.main;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.demorecyclerview.R;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Student;

// TOO: Make class extend RecyclerView.Adapter
/*
public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {
*/
public class MainActivityAdapter extends ListAdapter<Student, MainActivityAdapter.ViewHolder> {

    //El adaptador es el que se encarga de mostrar los elementos en la pantalla

/*
    private List<Student> data = new ArrayList<Student>(); //Se inicializa vacía para que nunca retorne un null
*/
    private OnStudentListener onStudentListener;

/*    public MainActivityAdapter(OnStudentListener onStudentListener) {
        this.onStudentListener = onStudentListener;
    }*/
    public MainActivityAdapter(OnStudentListener onStudentListener) {
        super(new DiffUtil.ItemCallback<Student>() {
            @Override
            public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) { //Entra si areItemTheSame return true
                return TextUtils.equals(oldItem.getName(), newItem.getName()) &&                    //Si retorna false lo repinta, sigue siendo elmismo estudiante
                        oldItem.getAge() == newItem.getAge();
            }
        });
        this.onStudentListener = onStudentListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_main_item, parent, false), onStudentListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { //Para pintar los datos en la vista
/*
        holder.bind(data.get(position)); //ListAdapter tiene un data
*/
        holder.bind(getItem(position));
    }

    /*@Override
    public int getItemCount() { //Con cuantos itemView trabaja el adaptador
        return data == null ? 0 : data.size();
    } No hay que sobrescribirlo porque ListAdapter lo tiene impolementando*/

    @Override
    public long getItemId(int position) {
/*
        return data.get(position).getId();
*/
        return getItem(position).getId();
    }

/*    public Student getItem(int position) {
        return data.get(position);
    } Ya lo tiene implementado*/

    @Override
    public Student getItem(int position) {
        return super.getItem(position);
    }

    //Se pueden pasar los datos a través del constructor, implica que antes de tener el adaptador ya se los datos y que nunca van a cambiar (No habitual)
    //submitList para pasar lo datos
/*    public void submitList(@NonNull List<Student> newData) { //Puede llegar un null
        data = newData; //Se modifican los datos pero el RecyclerView no se está enterando, el adaptador debe de avisar con notify
        notifyDataSetChanged();
    } Lo trae el ListAdapter implmentado*/

    // TODO: Implement RecyclerView.Adapter abstract methods.

    // TODO: Make a method to receive data.

    // TOD: Make class extend RecyclerView.ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView lblName;
        private final TextView lblAge;
        /*public final View lblName;
        public final View lblAge; Cutre, crear método bind()*/

        // TOO: Add itemView as parameter to constructor.
        public ViewHolder(View itemView, OnStudentListener onStudentListener) { //OnStudentListener interfaz de comunicación
            super(itemView);
            // TODO: findViews in itemView
            //lblName = itemView.findViewById() No para evitar nulos
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblAge = ViewCompat.requireViewById(itemView, R.id.lblAge);

            // TOO: Detect cliks and call listeners methods.
            itemView.setOnClickListener(v -> onStudentListener.OnItemClick(getAdapterPosition()));
        }

        public void bind(Student student) {
            lblName.setText(student.getName()); //Definir Student antes
            lblAge.setText(String.valueOf(student.getAge())); //Debería funcionar, pero getAge es de tipo entero
        }
    }

    // TODO: Define interfaces to communicate to Activity
    // TODO: Define listeners and setters.

}
