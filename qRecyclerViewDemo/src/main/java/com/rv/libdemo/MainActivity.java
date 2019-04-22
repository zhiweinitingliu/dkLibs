package com.rv.libdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn_rv_load_more;
    private Button btn_rv_refresh_load_more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_rv_load_more = findViewById(R.id.btn_rv_load_more);
        btn_rv_refresh_load_more = findViewById(R.id.btn_rv_refresh_load_more);
        initListener();
    }

    private void initListener() {
        btn_rv_load_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewLoadMoreActivity.class);
                startActivity(intent);
            }
        });

        btn_rv_refresh_load_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RefreshLoadMoreActivity.class);
                startActivity(intent);
            }
        });
    }
}
