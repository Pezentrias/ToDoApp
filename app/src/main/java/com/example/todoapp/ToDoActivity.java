package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import model.ToDoItem;

public class ToDoActivity extends AppCompatActivity {

    private RadioGroup rgFontossag;
    private ToDoItem tdi;
    private EditText etNev;
    private EditText etEv;
    private EditText etHonap;
    private EditText etNap;
    private Intent intent;
   /* private RadioButton rbNagyFont;
    private RadioButton rbKicsitFont;
    private RadioButton rbNemFont;*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        rgFontossag = findViewById(R.id.rgFontossag);
        etEv = findViewById(R.id.etEv);
        etHonap = findViewById(R.id.etHonap);
        etNap = findViewById(R.id.etNap);
        etNev = findViewById(R.id.etNev);
        intent = getIntent();
       /* rbKicsitFont=findViewById(R.id.rbKicsitF);
        rbNagyFont=findViewById(R.id.rbNagyonF);
        rbNemFont=findViewById(R.id.rbNemF);*/


        tdi = (ToDoItem) intent.getSerializableExtra("tdi");
        if (tdi != null) {
            etNev.setText(tdi.getName());
            etEv.setText(tdi.getYear() + "");
            etHonap.setText(tdi.getMonth() + "");
            etNap.setText(tdi.getDay() + "");
          //  rbNemFont.setSelected(true);

         /* if (tdi.getUrgency().equals("0")){   // ezek itt nem jók!
            rbNagyFont.setSelected(true);
            }
          else if (tdi.getUrgency().equals("1")){
              rbKicsitFont.setSelected(true);
          }
          else if
          (tdi.getUrgency().equals("2")){
              rbNemFont.setSelected(true);
          }*/

        }

    }

    public void megsem(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void ment(View view) {
        if (tdi == null) {
            tdi = new ToDoItem();
        }

        tdi.setName(etNev.getText().toString());
            tdi.setYear(Integer.parseInt(etEv.getText().toString()));
            tdi.setMonth(Integer.parseInt(etHonap.getText().toString()));
            tdi.setDay(Integer.parseInt(etNap.getText().toString()));
            tdi.setIsCompleted(0);
            int fontossag = rgFontossag.getCheckedRadioButtonId();
            switch (fontossag) {
                case R.id.rbNagyonF:
                    tdi.setUrgency("0");
                    break;
                case R.id.rbKicsitF:
                    tdi.setUrgency("1");
                    break;
                case R.id.rbNemF:
                    tdi.setUrgency("2");
                    break;
            }
            intent.putExtra("tdi", tdi);
            setResult(RESULT_OK, intent);
            finish();

    }
}