package com.example.rama.IndiaBuzzNews;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private int sum =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onButtonClick(View v)
    {
        EditText num1 = (EditText)findViewById(R.id.ETFirstNum);
        EditText num2 = (EditText)findViewById(R.id.ETSecondNum);
        sum = Integer.parseInt(num1.getText().toString())+Integer.parseInt(num2.getText().toString());
        TextView result = (TextView)findViewById(R.id.TVResult);
        result.setText(Integer.toString(sum));

        Toast.makeText(this,"Successful",Toast.LENGTH_SHORT).show();

    }

    public void onNewActivityButtonClick(View v)
    {
        Intent intent = new Intent(this,secondActivity.class);
        intent.putExtra("sum",sum);
        startActivity(intent);
    }

    public void onThreadActivityButtonClick(View v)
    {
        Intent intent = new Intent(this,FourthActivity_Threading.class);
        //intent.putExtra("sum",sum);
        startActivity(intent);
    }



}
