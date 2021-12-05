package com.example.calc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import javax.script.*;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tbResult;
    ScriptEngine engine;

    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;

    double number;
    String operation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        engine = new ScriptEngineManager().getEngineByName("rhino");
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        tbResult = findViewById(R.id.tbResult);
    }

    public void isZero(int number){
        if(tbResult.getText().toString().equals("0")){
            tbResult.setText(String.valueOf(number));
            this.number = number;
        }
        else{
            tbResult.append(String.valueOf(number));
            this.number = (this.number * 10) + number;
        }
    }

    public void onNumberClick(View view){
        Button button = (Button) view;
        if(operation.equals(""))
            isZero(Integer.parseInt(button.getText().toString()));
        else tbResult.append(button.getText());
    }

    public void onOperationClick(View view){
        char symbol = tbResult.getText().charAt(tbResult.length() - 1);

        if(!operation.equals("") && (symbol == '+' || symbol == '-' || symbol == '*' || symbol == '/'))
            tbResult.setText(tbResult.getText().toString().substring(0,tbResult.length() - 1));

        Button button = (Button) view;
        tbResult.append(button.getText());
        String equation = tbResult.getText().toString();

        if (button.getText().toString().equals("+"))
            operation = "+";
        else if (button.getText().toString().equals("-"))
        operation = "-";
        else if (button.getText().toString().equals("*"))
            operation = "*";
        else if (button.getText().toString().equals("/"))
            operation = "/";
    }
    public void onClearClick(View view){
        tbResult.setText("0");
        number = 0;
        operation = "";
    }
    public void onEqualsClick(View view){
        try{
            String res = engine.eval(tbResult.getText().toString()).toString();
            tbResult.setText(res);
        } catch (ScriptException e){
            tbResult.setText("Что-то пошло не так");
        }
    }

    public double helper(){
        String math = tbResult.getText().toString();
        tbResult.setText(math.replaceAll("^-?\\d*\\.*\\d*$|\\d*\\.*\\d*$",""));
        math = math.substring(math.lastIndexOf('+') + 1);
        if (math.charAt(0) != '-'){
            math = math.substring(math.lastIndexOf('-') + 1);
        }
        math = math.substring(math.lastIndexOf('/') + 1);
        math = math.substring(math.lastIndexOf('*') + 1);
        return Double.parseDouble(math);
    }

    public void onModuleClick(View view){
        char lastChar = tbResult.getText().charAt(tbResult.length() - 1);
        if(lastChar != '+' && lastChar != '-' && lastChar != '*' && lastChar != '/')
                tbResult.append(String.valueOf(Math.abs(helper())));
    }

    public void onSqrtClick(View view){
        char lastChar = tbResult.getText().charAt(tbResult.length() - 1);
        if(lastChar != '+' && lastChar != '-' && lastChar != '*' && lastChar != '/')
            tbResult.append(String.valueOf(Math.sqrt(helper())));
    }

    public void onSquareClick(View view){
        char lastChar = tbResult.getText().charAt(tbResult.length() - 1);
        if(lastChar != '+' && lastChar != '-' && lastChar != '*' && lastChar != '/')
            tbResult.append(String.valueOf(Math.pow(helper(), 2)));
    }

    public void onPlusMinusClick(View view){
        char lastChar = tbResult.getText().charAt(tbResult.length() - 1);
        if(lastChar != '+' && lastChar != '-' && lastChar != '*' && lastChar != '/')
            tbResult.append(String.valueOf(-1 * helper()));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("expression",tbResult.getText().toString());
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        tbResult.setText(savedInstanceState.getString("expression"));
    }
}