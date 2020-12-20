package com.example.erion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;


public class LoginedActivity extends AppCompatActivity {
    private Button btn_qr_login;
    private TextView tv_nick;
    private TextView tv_point;
    private Button btn_detail;

    String id = "";
    String total_point = "";
    String nick_value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logined);

        btn_qr_login = (Button) findViewById(R.id.btn_qr_login);
        tv_nick = (TextView) findViewById(R.id.tv_nick);
        tv_point = (TextView) findViewById(R.id.tv_point);
        btn_detail = (Button) findViewById(R.id.btn_detail);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        pgsqlcon pgcon = new pgsqlcon();
        pgcon.execute();



        btn_qr_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateQR.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        btn_detail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("nick", nick_value);
                startActivity(intent);

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


                result = st.executeQuery(String.format("select nickname, total_point from recycle_ruser where rfid = '%s'", id));
                if (result.next()) {
                    nick_value = result.getString("nickname");
                    total_point = String.valueOf(result.getInt("total_point"));
                }

                new Thread()

                {

                    public void run()

                    {

                        Message msg = handler.obtainMessage();

                        handler.sendMessage(msg);

                    }

                }.start();


                st.close();

                conn.close();



            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }

            return null;
        }
    }

    final Handler handler = new Handler()

    {

        public void handleMessage(Message msg)

        {

            tv_nick.setText(String.format("%s님", nick_value));
            tv_point.setText(String.format("총 포인트 %s point", total_point));
        }

    };
}