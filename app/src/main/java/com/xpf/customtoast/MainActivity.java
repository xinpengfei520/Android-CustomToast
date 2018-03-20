package com.xpf.customtoast;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btnRedToast, btnGreenToast, btnYellowToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRedToast = findViewById(R.id.btnRedToast);
        btnGreenToast = findViewById(R.id.btnGreenToast);
        btnYellowToast = findViewById(R.id.btnYellowToast);
        btnRedToast.setOnClickListener(this);
        btnGreenToast.setOnClickListener(this);
        btnYellowToast.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRedToast:
                MyToastUtil.showRedToast(this, "我是红色顶部Toast！");
                break;
            case R.id.btnGreenToast:
                MyToastUtil.showGreenToast(this, "我是绿色顶部Toast！");
                break;
            case R.id.btnYellowToast:
                MyToastUtil.showYellowToast(this, "我是黄色顶部Toast！");
                break;
        }
    }
}
