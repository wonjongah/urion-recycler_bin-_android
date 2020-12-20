package com.example.erion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;



public class DetailActivity extends AppCompatActivity {

    private RecyclerAdapter adapter;
    private TextView tv_detail;

    String id = "";
    String nick = "";
    Integer bottle_id = 0;
    Integer bottle_point = 0;
    String image_url = "";
    Timestamp date;
    List<String> listTitle;
    List<String> listContent;
    List<String> listKind =  new ArrayList<String>();
    List<Integer> listPoint = new ArrayList<Integer>();
    List<Timestamp> listDate =  new ArrayList<Timestamp>();
    List<String> listImage = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv_detail = (TextView) findViewById(R.id.tv_detail_info);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        nick = intent.getStringExtra("nick");

        init();
        pgsqlcon pgcon = new pgsqlcon();
        pgcon.execute();
        tv_detail.setText(String.format("%s님의 포인트 내역입니다", nick));

//        getData();
    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.r_detail);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),
                LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getApplicationContext().getResources().getDrawable(R.drawable.devider));

        //recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }


    private void getData() {

        for (int i = 0; i < listKind.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            Data data = new Data();
            data.setBottleKind(listKind.get(i));
            data.setBottlePoint(listPoint.get(i));
            data.setResId(listImage.get(i));
//            data.setResId(listResId.get(i));
            data.setDate(listDate.get(i));
            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(data);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
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


                result = st.executeQuery(String.format("select image, bottle_class, Datetime from recycle_bottle where user_rfid = '%s'", id));
                while (result.next()) {

//                    for(int i = 0; i < result.getFetchSize(); i++){
                        Integer kind = result.getInt("bottle_class");
                        switch (kind){
                            case 0:
                                listPoint.add(0);
                                listKind.add("생활 유리\n(재활용 불가)");
                                break;
                            case 1:
                                listPoint.add(130);
                                listKind.add("맥주병\n(재사용)");
                                break;
                            case 2:
                                listPoint.add(0);
                                listKind.add("음료병\n(재활용)");
                                break;
                            case 3:
                                listPoint.add(0);
                                listKind.add("이물질 찬 병\n(재활용 불가)");
                                break;
                            case 4:
                                listPoint.add(10);
                                listKind.add("유리병\n(재활용)");
                                break;
                            case 5:
                                listPoint.add(0);
                                listKind.add("이물질 찬 병\n(재활용 불가)");
                                break;
                            case 7:
                                listPoint.add(0);
                                listKind.add("생활 유리\n(재활용 불가)");
                                break;
                            case 8:
                                listPoint.add(0);
                                listKind.add("이물질 찬 병\n(재활용 불가)");
                                break;
                            case 9:
                                listPoint.add(5);
                                listKind.add("음료병\n(재활용)");
                                break;
                            case 10:
                                listPoint.add(0);
                                listKind.add("이물질 찬 병\n(재활용 불가)");
                                break;
                            case 11:
                                listPoint.add(0);
                                listKind.add("향수병\n(재활용 불가)");
                                break;
                            case 12:
                                listPoint.add(100);
                                listKind.add("소주병\n(재사용)");
                                break;
                            case 13:
                                listPoint.add(5);
                                listKind.add("음료병\n(재활용)");
                                break;
                            case 14:
                                listPoint.add(5);
                                listKind.add("와인병\n(재활용)");
                                break;
                            case 15:
                                listPoint.add(0);
                                listKind.add("이물질 찬 병\n(재활용 불가)");
                                break;
                            default:
                                System.out.println("default");
                                break;

                        }
//                        image, bottle_class, Datetime
                        image_url = result.getString("image");
                        date = result.getTimestamp("Datetime");

                        listImage.add(image_url);
                        listDate.add(date);
//                    }
//                    nick_value = result.getString("nickname");
//                    id = result.getDate()
//                    total_point = String.valueOf(result.getInt("total_point"));
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

            getData();
        }

    };
}