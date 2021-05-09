package com.example.android_testsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android_testsqlite.entity.Student;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Button bttAdd,bttRemove,bttCancel;
    DatabaseHandler databaseHandler;
    TextInputEditText ietName;
    int index=-1;
    List<Student> students= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHandler = new DatabaseHandler(this);
        listView = findViewById(R.id.lviDatabase);
//        databaseHandler.addStudent(new Student("Truong"));
//        databaseHandler.addStudent(new Student("Cong"));
//        databaseHandler.addStudent(new Student("Cuong"));
        students = databaseHandler.getListStudent();
        ArrayAdapter<Student> arrayAdapter = new ArrayAdapter<Student>(this, android.R.layout.simple_list_item_1,databaseHandler.getListStudent());
        listView.setAdapter(arrayAdapter);

        bttAdd = findViewById(R.id.bttAdd);
        bttRemove = findViewById(R.id.bttRemove);
        bttCancel = findViewById(R.id.bttCancel);
        ietName = findViewById(R.id.ietName);

        bttAdd.setText("Add");
        bttRemove.setText("Remove");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
            }
        });

        bttAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtName = ietName.getText().toString();
                databaseHandler.addStudent(new Student(txtName));

                ArrayAdapter<Student> arrayAdapter1 = new ArrayAdapter<Student>(MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        databaseHandler.getListStudent());
                listView.setAdapter(arrayAdapter1);
                students = databaseHandler.getListStudent();
            }
        });
        bttRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(index>=0){
                   databaseHandler.removeStudent(students.get(index).getId());
                   ArrayAdapter<Student> arrayAdapter1 = new ArrayAdapter<Student>(MainActivity.this,
                           android.R.layout.simple_list_item_1,
                           databaseHandler.getListStudent());
                   listView.setAdapter(arrayAdapter1);
                   students = databaseHandler.getListStudent();
                   index = -1;
               }
            }
        });
        bttCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}