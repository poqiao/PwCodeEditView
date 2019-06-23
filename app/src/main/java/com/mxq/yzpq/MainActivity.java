package com.mxq.yzpq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mxq.pq.PwCodeEditView;

public class MainActivity extends AppCompatActivity {
    private Button btn1;
    private PwCodeEditView editPw;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.btn_1);
        editPw = (PwCodeEditView) findViewById(R.id.edit_pw);
        editPw.setPwCodeEditListener (new PwCodeEditView.PwCodeEditListener () {
            @Override
            public void finishInput(String pwStr) {
            }
        });
        btn1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                editPw.clear();
            }
        });
    }
}
