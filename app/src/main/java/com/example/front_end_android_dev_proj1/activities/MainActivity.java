package com.example.front_end_android_dev_proj1.activities;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.front_end_android_dev_proj1.R;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private char operation ;
    private Boolean displayRes, start;
    private Double storedNum, ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        display = findViewById(R.id.textViewResult);
        display.setText("0");
        operation = 'e';
        displayRes = false;
        start = true;
        storedNum = 0.0;
        ans = 0.0;


    }

    public void numFunction(View view){

        Button numBtn = (Button) view;
        String btnText = numBtn.getText().toString();

        if (displayRes){
            display.setText(btnText);
            displayRes = false;
            ans = 0.0;
            return;
        }

        if (start){
            display.setText(btnText);
            start = false;
            return;
        }
        display.append(btnText);


    }

    public void operationStore(View view){

        if (start)
            return;
        Button opBtn = (Button) view;

        String requestedOp = opBtn.getText().toString();
        char op_char = requestedOp.charAt(0);


        if (displayRes) {
            if(ans != 0.0)
                storedNum = ans;
            operation = op_char;
            display.setText(requestedOp);
            return;
        }

        if (storedNum != 0.0)
            this.compute();
        else
            storedNum = Double.parseDouble(display.getText().toString());

        operation = op_char;
        display.setText(requestedOp);
        displayRes = true;

    }

    public void compute(){
        String screenVal = display.getText().toString();
        if(!Character.isDigit(screenVal.charAt(0)))
            return;

        double secondNum = Double.parseDouble(display.getText().toString());
        switch (operation){
            case '+':
                storedNum += secondNum;
                ans = storedNum;
                break;
            case '-':
                storedNum -= secondNum;
                ans = storedNum;
                break;
            case '×':
                storedNum *= secondNum;
                ans = storedNum;
                break;
            case '÷':
                if (secondNum == 0.0){
                    display.setText("Error");
                    storedNum=0.0;
                    ans = 0.0;
                    start=true;
                    return;
                }
                storedNum/= secondNum;
                ans = storedNum;
                break;
        }
    }

    public void result(View view){
        this.compute();
        if (operation!='e' && !start) {
            String disTxt = "";
            Integer ansInt;
            if(ans%1==0) {//Checks if the number has a reminder.
                ansInt = (Integer) ans.intValue();
                disTxt = disTxt + ansInt;
            }
            else
                disTxt = disTxt + ans;
            
            Log.d("number check", "The number is "+ disTxt);
            display.setText(disTxt);
            displayRes = true;
            storedNum = 0.0;
            operation = 'e';
        }
    }

    public void clearScreen(View view){
        display.setText("0");
        operation = 'e';
        displayRes = false;
        start = true;
        storedNum = 0.0;
        ans = 0.0;
    }

}