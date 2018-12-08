package com.calculator.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by yingyaopeng on 2018/12/8.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button bt_0,bt_1,bt_2,bt_3,bt_4,bt_5,bt_6,bt_7,bt_8,bt_9,bt_multiply,bt_divide,bt_plus
            ,bt_subtraction,bt_point,bt_del,bt_equal,bt_clear,bt_superplus;
    TextView text_input;
    boolean clear_flag; //清空标识

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_0 = (Button)findViewById(R.id.zero);
        bt_1 = (Button)findViewById(R.id.one);
        bt_2 = (Button)findViewById(R.id.two);
        bt_3 = (Button)findViewById(R.id.three);
        bt_4 = (Button)findViewById(R.id.four);
        bt_5 = (Button)findViewById(R.id.five);
        bt_6 = (Button)findViewById(R.id.six);
        bt_7 = (Button)findViewById(R.id.seven);
        bt_8 = (Button)findViewById(R.id.eight);
        bt_9 = (Button)findViewById(R.id.nine);
        bt_multiply = (Button)findViewById(R.id.multiply);
        bt_divide = (Button)findViewById(R.id.divide);
        bt_plus = (Button)findViewById(R.id.plus);
        bt_subtraction = (Button)findViewById(R.id.subtraction);
        bt_point = (Button)findViewById(R.id.point);
        bt_del = (Button)findViewById(R.id.delete);
        bt_equal = (Button)findViewById(R.id.equal);
        bt_clear = (Button)findViewById(R.id.clear);
        bt_superplus = (Button)findViewById(R.id.superplus);

        text_input = (TextView)findViewById(R.id.input_result);

        bt_0.setOnClickListener(this);
        bt_1.setOnClickListener(this);
        bt_2.setOnClickListener(this);
        bt_3.setOnClickListener(this);
        bt_4.setOnClickListener(this);
        bt_5.setOnClickListener(this);
        bt_6.setOnClickListener(this);
        bt_7.setOnClickListener(this);
        bt_8.setOnClickListener(this);
        bt_9.setOnClickListener(this);
        bt_subtraction.setOnClickListener(this);
        bt_multiply.setOnClickListener(this);
        bt_del.setOnClickListener(this);
        bt_divide.setOnClickListener(this);
        bt_point.setOnClickListener(this);
        bt_plus.setOnClickListener(this);
        bt_equal.setOnClickListener(this);
        bt_clear.setOnClickListener(this);
        bt_superplus.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String str = text_input.getText().toString();
        switch (view.getId()){
            case R.id.zero:
            case R.id.one:
            case R.id.two:
            case R.id.three:
            case R.id.four:
            case R.id.five:
            case R.id.six:
            case R.id.seven:
            case R.id.eight:
            case R.id.nine:
            case R.id.point:
                if(clear_flag){
                    clear_flag = false;
                    str = "";
                    text_input.setText("");
                }
                text_input.setText(str+((Button)view).getText());
                break;

            case R.id.plus:
            case R.id.subtraction:
            case R.id.multiply:
            case R.id.divide:
            case R.id.superplus:
                if(clear_flag){
                    clear_flag = false;
                    text_input.setText("");
                }
                text_input.setText(str+" "+((Button)view).getText()+" ");  //在每个运算符 前 后 各加一个 空格
                break;

            case R.id.delete:
                if(clear_flag){
                    clear_flag = false;
                    str = "";
                    text_input.setText("");
                }else if(str != null && !str.equals("")){
                    text_input.setText(str.substring(0, str.length()-1));
                }
                break;

            case R.id.clear:
                clear_flag = false;
                str = "";
                text_input.setText("");
                break;

            case R.id.equal:
                getResult();
                break;
        }
    }

    private void getResult(){
        String exp = text_input.getText().toString();
        if(exp == null || exp.equals("")){
            return;
        }
        if(!exp.contains(" ")){
            return;
        }
        if(clear_flag){
            clear_flag = false;
            return;
        }
        clear_flag = true;
        String str_1 = exp.substring(0, exp.indexOf(" ")); // 运算符前面的字符
        String str_op = exp.substring(exp.indexOf(" ") + 1, exp.indexOf(" ") + 2); //获取到运算符
        String str_2 = exp.substring(exp.indexOf(" ") + 3);   //运算符后面的数字

        double result = 0;
        if(!str_1.equals("")&&!str_2.equals("")){
            double num_1 = Double.parseDouble(str_1);   //先将str_1、str_1强制转化为double类型
            double num_2 = Double.parseDouble(str_2);

            if (str_op.equals("+")){
                result = num_1 + num_2;
            }else if(str_op.equals("%")) {
                result = num_1 % num_2;
            }else if (str_op.equals("-")){
                result = num_1 - num_2;
            }else if (str_op.equals("×")){
                result = num_1 * num_2;
            }else if (str_op.equals("÷")){
                if(num_2 == 0){
                    result = 0;
                }else {
                    result = num_1 / num_2;
                }
            }
            if(!str_1.contains(".")&&!str_2.contains(".")&&!str_op.equals("÷")){
                int r = (int) result;
                text_input.setText(r+"");
            }else{
                text_input.setText(result+"");
            }
        }else if(!str_1.equals("")&&str_2.equals("")){
            text_input.setText(exp);
        }else if(str_1.equals("")&&!str_2.equals("")) {
            double num_2 = Double.parseDouble(str_2);
            if (str_op.equals("+")){
                result = 0 + num_2;
            }else if (str_op.equals("-")){
                result = 0 - num_2;
            }else if (str_op.equals("×")){
                result = 0;
            }else if (str_op.equals("÷")){
                result = 0;
            }else if (str_op.equals("%")){
                result = 0;
            }
            if(!str_2.contains(".")){
                int r = (int) result;
                text_input.setText(r+"");
            }else{
                text_input.setText(result+"");
            }
        }else{
            text_input.setText("");
        }
    }
}
