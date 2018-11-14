package es.iessaladillo.pedrojoya.demorecyclerview.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import java.util.Observer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.demorecyclerview.R;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.Database;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Student;
import es.iessaladillo.pedrojoya.demorecyclerview.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;
    //Para recompilar ViewDataBinding, luego se sustituye por el nombre del layout + Binding
    //que lo habrá autogenerado al hacer rebuild project
    private MainActivityViewModel viewModel;
    private MainActivityAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TOO: Use setContentView from data binding library and save the binding as field.
        //setContentView(R.layout.activity_main); se cambia por el de abajo
        b = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // TOO: Obtain viewModel (better using MainActivityViewModelFactory). Primero definir ViewModel y ViewModelFactory
        /*viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory()).get(MainActivityViewModel.class);*/
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(new Database())).get(MainActivityViewModel.class);

        // TODO: Give viewModel to binding.
        // TODO: Give lifecycle to binding.

        setupViews();
        /*List<Student> students = viewModel.getStudents(false);
        // TOO: Observe data from viewModel, giving them to adapter
        listAdapter.submitList(students);
        // TOO: Observe emptyView visibility state.
        b.lblEmptyView.setVisibility(students.size() == 0 ? View.VISIBLE : View.INVISIBLE);*/
        observeStudents();
    }

    private void observeStudents() {
        viewModel.getStudents(false).observe(this, students -> {
            listAdapter.submitList(students);
            b.lblEmptyView.setVisibility(students.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void setupViews() {
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        /*listAdapter = new MainActivityAdapter(position -> showStudent(listAdapter.getItem(position)));*/
/*
        listAdapter = new MainActivityAdapter(position -> deleteStudent(listAdapter.getItem(position))); //Getitem es protected, cambiar a public
*/
        listAdapter = new MainActivityAdapter(position -> showStudent(listAdapter.getItem(position))); //Getitem es protected, cambiar a public

        // TOO: Create adapter.
        // TODO: Set listeners of adapter.
        // TOO: Setup recyclerView. Give adapter to recyclerview.
        b.lstStudents.setHasFixedSize(true); //Asegurar que los elementos no van a cambiar de altura
        b.lstStudents.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.main_lstStudents_columns))); //Rejilla, contexto y columnas

        b.lstStudents.setItemAnimator(new DefaultItemAnimator());
        b.lstStudents.setAdapter(listAdapter);
//cardView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.END) { //End solo detecte el swiped hacia la derecha
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) { //Cuando un elemento se ha posicionado donde estaba otro
                return false; //Se ejecuta cada vez que invade el espacio de otro item
            }

            @Override
/*            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) { //solo implementamos este
                viewModel.deleteStudent(listAdapter.getItem(viewHolder.getAdapterPosition()));
            } //Cuando haga swiped borra el estudiante*/
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) { //las dos direcciones, no es un buen ejemplo
                if(direction == ItemTouchHelper.END) {
                    viewModel.deleteStudent(listAdapter.getItem(viewHolder.getAdapterPosition()));
                } else {
                    showStudent(listAdapter.getItem(viewHolder.getAdapterPosition()));
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(b.lstStudents);
    }

/*    private void showStudent(Student student) {
        Toast.makeText(this, student.getName(), Toast.LENGTH_SHORT).show();
    }*/

/*    private void deleteStudent(Student student) {
        //El viewModel es la base de datos, modificar DataBase
        viewModel.deleteStudent(student);
       *//* List<Student> students = viewModel.getStudents(true);
        listAdapter.submitList(students);
        b.lblEmptyView.setVisibility(students.size() == 0 ? View.VISIBLE : View.INVISIBLE);*//*
    } Renombrar para no repetir funcionalidad*/
    private void showStudent(Student student) { //showStudent
        //El viewModel es la base de datos, modificar DataBase
        /*viewModel.deleteStudent(student);*/
           /* List<Student> students = viewModel.getStudents(true);
            listAdapter.submitList(students);
            b.lblEmptyView.setVisibility(students.size() == 0 ? View.VISIBLE : View.INVISIBLE);*/
        Toast.makeText(this, student.getName(), Toast.LENGTH_SHORT).show();

    }

}
