package es.iessaladillo.pedrojoya.demorecyclerview.ui.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.Database;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Student;

// TOO: Make class extend ViewModel
public class MainActivityViewModel extends ViewModel {

    // TOO: Define data needs to be retained during configuration change (state)
/*    private List<Student> students = new ArrayList<>(Arrays.asList(
            new Student(1, "Baldo", 23),
            new Student(2, "German", 17)
    )); Se traslada a database*/

    private final Database database; //database
/*
    private List<Student> students;
*/
    private LiveData<List<Student>> students;

    public MainActivityViewModel(Database database) {
        this.database = database;
    }

/*    public List<Student> getStudents(boolean forceLoad) { //database
        if(students == null || forceLoad) {
            students = database.getStudents();
        }
        return students;
    }*/

/*
    public LiveData<List<Student>> getStudents(boolean forceLoad) { //database
        if(students == null || forceLoad) { ya no es necesario el forceload porque ya lo hace solo, si cuando hay un botón de refresco

*/
/*    public LiveData<List<Student>> getStudents() { //database
        if(students == null) {*/
    public LiveData<List<Student>> getStudents(boolean forceLoad) { //database
        if(students == null || forceLoad) {
            students = database.getStudents();
        }
        return students;
    }

    public void deleteStudent(Student student) {
        database.deleteStudent(student);
    }
}
