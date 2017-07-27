package com.example.jaroslavh.myapplication1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    private ArrayList<User> ListUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        try {
            ListUser = GetUser();
        } catch (Exception e) {
            print(e.toString());
            Toast toast = Toast.makeText(this, "Дані по користувачам не звантажені", Toast.LENGTH_LONG );
            toast.show();
            return;
        }

        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, android.R.layout.simple_spinner_item, ListUser);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

    }

    public ArrayList<User> GetUser() throws Exception {

        Class.forName("org.firebirdsql.jdbc.FBDriver");

        Properties props = new Properties();
        props.setProperty("user", "SYSDBA");
        props.setProperty("password", "masterkey");
        props.setProperty("encoding", "UTF8");

        Connection connection = DriverManager.getConnection(
                "jdbc:firebirdsql:192.168.0.96/3050:C:/IBExpert/db/TEST1.FDB",
                props);

        Statement statement = null;
        String query = " SELECT * FROM users";

        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        ArrayList<User> users = new ArrayList<User>();
        while (resultSet.next()) {
            users.add(new User(resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getInt("PASSWD")));
        }

        return users;

    }

    public void Exit(View view) {
        finishAffinity();
    }

    public void In(View view) {

        int inPassInteger;

        EditText editText = (EditText) findViewById(R.id.editText);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.getSelectedItem();
        User user = (User) spinner.getSelectedItem();

        if (spinner.getSelectedItem() == null) {
            return;
        }

        int pass = user.pass;
        String inPass = editText.getText().toString();

        try {
            inPassInteger = Integer.parseInt(inPass);
        }
        catch (Exception e){
            print("В поле пароль введено літери");
            print(e.toString());
            return;
        }

        if (inPass.length() == 0) {
            Toast toast = Toast.makeText(this, "Пароль пустий", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        if (pass == inPassInteger) {
            Intent intent = new Intent(this, ListOperations.class);
            startActivity(intent);
        } else {
            print("Пароль не вірний");
        }

    }

    void print(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

}
