package com.example.doorbell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class light_ldr extends AppCompatActivity {

    List<ExampleModel> list;
    RecyclerView recyclerView;
    RecyclerAdapter radapter;
    User user;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_ldr);

        Animation a= AnimationUtils.loadAnimation(this,R.anim.scale);
        findViewById(R.id.textView7).startAnimation(a);


        user = SharedPrefManager.getInstance(this).getUser();
        uid = String.valueOf(user.getLogin_id());

        recyclerView=findViewById(R.id.rv_light_histrory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        list=new ArrayList<>();
        radapter = new RecyclerAdapter(getAllData(),this);
        recyclerView.setAdapter(radapter);


    }


    private List<ExampleModel> getAllData()
    {

        final List<ExampleModel> data=new ArrayList<>();

        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        // JSONObject userJson = obj.getJSONObject("vehicle");

                        JSONArray jsonArray=obj.getJSONArray("LDR");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String ldr_value = jsonObject1.getString("LDR_value");
                            String time = jsonObject1.getString("timestamp");


                            ExampleModel current = new ExampleModel();
                            current.ldr_value = ldr_value;
                            current.time = time;
                            data.add(current);
                        }


                        radapter.notifyDataSetChanged();

                    } else {

                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        // Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();



                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("uid",uid);
                return requestHandler.sendPostRequest(URLs.URL_LDR, params);

                //returing the response
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();




        return data;
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyviewHolder> {


        List<ExampleModel> list;
        Context context;

        public RecyclerAdapter(List<ExampleModel> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @NonNull
        @Override
        public RecyclerAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history,parent,false);
            return new RecyclerAdapter.MyviewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull RecyclerAdapter.MyviewHolder holder, @SuppressLint("RecyclerView") final int position) {

            ExampleModel current = list.get(position);

            holder.ldrval.setText("LIGHT VALUE: "+ current.getLdr_value());
            holder.dtime.setText("TIME: "+ current.getTime());

        }



        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyviewHolder extends RecyclerView.ViewHolder {

            private TextView ldrid , sensid , did , ldrval , dtime;


            public MyviewHolder(@NonNull View itemView) {
                super(itemView);

                ldrval = itemView.findViewById(R.id.ldr_value);
                dtime = itemView.findViewById(R.id.time);
            }
        }
    }


}