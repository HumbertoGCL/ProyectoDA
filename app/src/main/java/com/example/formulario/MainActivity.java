package com.example.formulario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DBHelper myDb;
    EditText editName, editP1, editP2, editP3;
    Button btnSaveData;
    Button btnViewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DBHelper(this);

        editName = (EditText) findViewById(R.id.editText_name);
        editP1 = (EditText) findViewById(R.id.editText_p1);
        editP2 = (EditText) findViewById(R.id.editText_p2);
        editP3 = (EditText) findViewById(R.id.editText_p3);
        btnSaveData = (Button) findViewById(R.id.save_data);
        btnViewData = (Button) findViewById(R.id.view_data);
        SaveData();
        viewData();

    }

    public void SaveData() {
        btnSaveData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editName.getText().toString(),
                                editP1.getText().toString(),
                                editP2.getText().toString(),
                                editP3.getText().toString()
                        );
                        if (isInserted = true){
                            Toast.makeText(MainActivity.this, "Datos guardados, gracias!", Toast.LENGTH_LONG).show();
                            editName.setText("");
                            editP1.setText("");
                            editP2.setText("");
                            editP3.setText("");
                        }
                        else
                            Toast.makeText(MainActivity.this, "Datos no ingresados", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewData() {
        btnViewData.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            showMessage("Error","No se encontro");

                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id :" + res.getString(0) + "\n");
                            buffer.append("Nombre del alumno :" + res.getString(1) + "\n");
                            buffer.append("Grado de satisfacción :" + res.getString(2) + "\n");
                            buffer.append("Utilidad en el aprendizaje :" + res.getString(3) + "\n");
                            buffer.append("Comentarios :" + res.getString(4) + "\n\n");
                        }
                        showMessage("Datos guardados",buffer.toString());
                    }
                }
        );
    }


    public void showMessage(String title,String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }
}