package com.example.erion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import org.postgresql.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    private Button login_btn;
    private Button password_btn;
    private EditText edit_id;
    private EditText edit_pass;

    String message = null;
    String conmsg = null;
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_btn = (Button) findViewById(R.id.btn_login);
        password_btn = (Button) findViewById(R.id.btn_pass);
        edit_id = (EditText) findViewById(R.id.edt_id);
        edit_pass = (EditText) findViewById(R.id.edt_password);

        login_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pgsqlcon pgcon = new pgsqlcon();
                pgcon.execute();

            }
        });

        password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Join.class);
                startActivity(intent);
            }
        });



    }

    public String Login() throws SQLException {
        Connection con = getRemoteConnection();
        Statement st = null;
        st = con.createStatement();

        String rfid = "";

        Editable id_value = edit_id.getText();
        Editable pass_value = edit_pass.getText();

        ResultSet result = null;
                result = st.executeQuery("select * from recycle_ruser");

        if (result.next())

        {
            if(id_value.equals(result.getString("rfid")) == true && pass_value.equals(result.getString("pw")) == true){
                // 입력한 id, pass가 데베의 id와 pass와 일치할 때, 연결 끊고, intent로 id 값과 보내면서 메인페이지 이동
                rfid = result.getString("rfid");
                Intent intent = new Intent(this, Main.class);
                intent.putExtra("id", rfid);
                startActivity(intent);
            }else if(id_value.equals(result.getString("rfid")) == true && pass_value.equals(result.getString("pw")) == false){
                // 입력된 값 지우고, 비번 잘못 입력 다시 입력 토스트
                Toast.makeText(getApplicationContext(), "비밀번호가 잘못 입력되었습니다", Toast.LENGTH_SHORT).show();
            }

        } else{
            // db에 값 없을 때
            Toast.makeText(getApplicationContext(), "회원정보가 없습니다. 회원가입을 먼저 해주세요", Toast.LENGTH_SHORT).show();
        }

        st.close();
        con.close();
        return rfid;
    }
    private static Connection getRemoteConnection() {

        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            String dbName = System.getenv("recycledb");
            String userName = System.getenv("team05");
            String password = System.getenv("adminyes");
            String hostname = System.getenv("recycledb.c8eedu3otduy.us-east-1.rds.amazonaws.com");
            String port = System.getenv("5432");
            String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
            Log.d("hahahahah", "Getting remote connection with connection string from environment variables.");
            con = DriverManager.getConnection(jdbcUrl);
            Log.d("hahahahah", "Remote connection successful.");
            return con;
        } catch (ClassNotFoundException e) {
            Log.d("classnotfound", e.toString());
        } catch (SQLException e) {
            Log.d("Exception", e.toString());
        }

        return con;
    }

    public void real_login(){
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
            message = "Connecting to database...";
            conn = DriverManager.getConnection("jdbc:postgresql://recycledb.c8eedu3otduy.us-east-1.rds.amazonaws.com:5432/recycledb","team05","adminyes");
            if (conn != null) {
                Log.d("succes","You made it, take control your database now!");
            } else {
                System.out.println("Failed to make connection!");
            }

            st = conn.createStatement();

            String rfid = "";

            Editable id_value = edit_id.getText();
            Editable pass_value = edit_pass.getText();

            result = st.executeQuery("select * from recycle_ruser");

            if (result.next())

            {
                if(id_value.equals(result.getString("rfid")) == true && pass_value.equals(result.getString("pw")) == true){
                    // 입력한 id, pass가 데베의 id와 pass와 일치할 때, 연결 끊고, intent로 id 값과 보내면서 메인페이지 이동
                    rfid = result.getString("rfid");
                        Intent intent = new Intent(this, Main.class);
                        intent.putExtra("id", rfid);
                        startActivity(intent);
                }else if(id_value.equals(result.getString("rfid")) == true && pass_value.equals(result.getString("pw")) == false){
                    // 입력된 값 지우고, 비번 잘못 입력 다시 입력 토스트

                }

            } else{
                // db에 값 없을 때
            }

            st.close();
            conn.close();
    } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }}


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
                message = "Connecting to database...";
                conn = DriverManager.getConnection("jdbc:postgresql://recycledb.c8eedu3otduy.us-east-1.rds.amazonaws.com:5432/recycledb","team05","adminyes");
                if (conn != null) {
                    Log.d("succes","You made it, take control your database now!");
                } else {
                    System.out.println("Failed to make connection!");
                }

                st = conn.createStatement();

                String rfid = "";

                String id_value = String.valueOf(edit_id.getText());
                String pass_value = String.valueOf(edit_pass.getText());

                result = st.executeQuery("select * from recycle_ruser");

                while (result.next())

                {
                    if(id_value.equals(result.getString("rfid")) == true && pass_value.equals(result.getString("pw")) == true){
                        // 입력한 id, pass가 데베의 id와 pass와 일치할 때, 연결 끊고, intent로 id 값과 보내면서 메인페이지 이동
                        rfid = result.getString("rfid");
                        Log.d("id", rfid);
                        Intent intent = new Intent(getApplicationContext(), LoginedActivity.class);
                        intent.putExtra("id", rfid);
                        startActivity(intent);
                    }else if(id_value.equals(result.getString("rfid")) == true && pass_value.equals(result.getString("pw")) == false){
                        // 입력된 값 지우고, 비번 잘못 입력 다시 입력 토스트

                    }

                }

                st.close();
                conn.close();

//                result = st.executeQuery("select * from recycle_ruser");
//
//                while (result.next())
//
//                {
//
//                    String rfid = result.getString("rfid");
//                    System.out.println("rfid: " + rfid);
//
//                }
//
//                st.close();
//
//                conn.close();


//                String insertRow1 = "INSERT INTO Beanstalk (Resource) VALUES ('EC2 Instance');";
//
//                setupStatement.addBatch(insertRow1);
//                setupStatement.executeBatch();
//                setupStatement.close();


//                c.setAutoCommit(false);
//                System.out.println("Opened database successfully");
//                stmt = c.createStatement();
//                String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
//                        + "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
//                stmt.executeUpdate(sql);
//                sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
//                        + "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );";
//                stmt.executeUpdate(sql);
//                sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
//                        + "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );";
//                stmt.executeUpdate(sql);
//                sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
//                        + "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );";
//                stmt.executeUpdate(sql);
//                stmt.close();
//                c.commit();
//                c.close();


            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }

            return null;
        }
    }

    //using quote because code formatting doesn't work anymore for me xD
    private void setAsyncText(final TextView text, final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (value == null)
                    text.setText("null");
                else
                    text.setText(value);
            }
        });
    }
}