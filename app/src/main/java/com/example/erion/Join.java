package com.example.erion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Join extends AppCompatActivity {

    private Button btn_join;
    private Button btn_cancel;
    private EditText edt_join_id;
    private EditText edt_join_pass;
    private EditText edt_join_nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        btn_join = (Button)findViewById(R.id.btn_join);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);
        edt_join_id = (EditText)findViewById(R.id.edt_join_id);
        edt_join_pass = (EditText)findViewById(R.id.edt_join_pass);
        edt_join_nick = (EditText)findViewById(R.id.edt_join_nick);

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgsqlcon pgcon = new pgsqlcon();
                pgcon.execute();
                finish();


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });


    }

    private class pgsqlcon extends AsyncTask<Void, Void, Void> {

        public pgsqlcon() {
            super();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Connection conn = null;
            Statement st = null;
            Statement setupStatement = null;
            ResultSet result = null;

            try {
                //STEP 2: Register JDBC driver\
                Class.forName("org.postgresql.Driver");
                //DriverManager.registerDriver(new org.postgresql.Driver());
                //DriverManager.registerDriver(new Driver());
                //STEP 3: Open a connection
                System.out.println("Connecting to database...");
                conn = DriverManager.getConnection("jdbc:postgresql://recycledb.c8eedu3otduy.us-east-1.rds.amazonaws.com:5432/recycledb","team05","adminyes");
                if (conn != null) {
                    Log.d("succes","You made it, take control your database now!");
                } else {
                    System.out.println("Failed to make connection!");
                }

                st = conn.createStatement();

                String id_value = String.valueOf(edt_join_id.getText());
                String pass_value = String.valueOf(edt_join_pass.getText());
                String nick_value = String.valueOf(edt_join_nick.getText());
                int total_point = 0;


                String insertRow1 = String.format("INSERT INTO recycle_ruser (rfid,nickname,pw,total_point) VALUES ('%s','%s','%s',%d);", id_value, nick_value, pass_value, total_point);

//                stmt.executeUpdate(sql);
//                stmt.close();
//                c.commit();
//                c.close();
//                st.addBatch(insertRow1);
//                st.executeBatch();
                st.executeUpdate(insertRow1);
                st.close();
                //                c.commit();
                conn.commit();
                conn.close();



            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }

            return null;
        }
    }
}