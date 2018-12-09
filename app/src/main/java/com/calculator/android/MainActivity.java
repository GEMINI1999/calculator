package com.calculator.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;

/**
 * Created by yingyaopeng on 2018/12/8.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button bt_0,bt_1,bt_2,bt_3,bt_4,bt_5,bt_6,bt_7,bt_8,bt_9,bt_multiply,bt_divide,bt_plus
            ,bt_subtraction,bt_point,bt_del,bt_equal,bt_clear,bt_superplus,bt_zuokuohao,bt_youkuohao;
    TextView text_input;
    boolean clear_flag; //清空标识
    private boolean isInStack = false;
    private boolean calculateOne = true;
    Stack<String> st = new Stack<String>();

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
        bt_zuokuohao = (Button)findViewById(R.id.zuokuohao);
        bt_youkuohao = (Button)findViewById(R.id.youkuohao);

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
        bt_youkuohao.setOnClickListener(this);
        bt_zuokuohao.setOnClickListener(this);
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
            case R.id.zuokuohao:
            case R.id.youkuohao:
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
                text_input.setText(str+((Button)view).getText());  //在每个运算符 前 后 各加一个 空格
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
                String expressions = text_input.getText().toString();
                String postFix = covertToPostFix(expressions);
                text_input.setText(postFix);
                break;
        }
    }
//        private void getResult(){
//        String exp = text_input.getText().toString();
//        if(exp == null || exp.equals("")){
//            return;
//        }
//        if(!exp.contains(" ")){
//            return;
//        }
//        if(clear_flag){
//            clear_flag = false;
//            return;
//        }
//        clear_flag = true;
//        String str_1 = exp.substring(0, exp.indexOf(" ")); // 运算符前面的字符
//        String str_op = exp.substring(exp.indexOf(" ") + 1, exp.indexOf(" ") + 2); //获取到运算符
//        String str_2 = exp.substring(exp.indexOf(" ") + 3);   //运算符后面的数字
//
//        double result = 0;
//        if(!str_1.equals("")&&!str_2.equals("")){
//            double num_1 = Double.parseDouble(str_1);   //先将str_1、str_1强制转化为double类型
//            double num_2 = Double.parseDouble(str_2);
//
//            if (str_op.equals("+")){
//                result = num_1 + num_2;
//            }else if(str_op.equals("%")) {
//                result = num_1 % num_2;
//            }else if (str_op.equals("-")){
//                result = num_1 - num_2;
//            }else if (str_op.equals("×")){
//                result = num_1 * num_2;
//            }else if (str_op.equals("÷")){
//                if(num_2 == 0){
//                    text_input.setText("错误");
//                }else {
//                    result = num_1 / num_2;
//                }
//            }
//            if(!str_1.contains(".")&&!str_2.contains(".")&&!str_op.equals("÷")){
//                int r = (int) result;
//                text_input.setText(r+"");
//            }else{
//                text_input.setText(result+"");
//            }
//        }else if(!str_1.equals("")&&str_2.equals("")){
//            text_input.setText(exp);
//        }else if(str_1.equals("")&&!str_2.equals("")) {
//            double num_2 = Double.parseDouble(str_2);
//            if (str_op.equals("+")){
//                result = 0 + num_2;
//            }else if (str_op.equals("-")){
//                result = 0 - num_2;
//            }else if (str_op.equals("×")){
//                result = 0;
//            }else if (str_op.equals("÷")){
//                result = 0;
//            }else if (str_op.equals("%")){
//                result = 0;
//            }
//            if(!str_2.contains(".")){
//                int r = (int) result;
//                text_input.setText(r+"");
//            }else{
//                text_input.setText(result+"");
//            }
//        }else{
//            text_input.setText("");
//        }
  //  }

    public String covertToPostFix(String expressions) {
        Stack<Character> st = new Stack<Character>(); //运算符栈
        String postFix = "";
        char ac;
        for (int i=0; i<expressions.length()&&expressions != null;i++){
            char c = expressions.charAt(i);
            switch (c){
                case '(':
                    st.push(c);
                    break;
                case ')':
                    ac = st.pop();
                    while (ac != '('){
                        postFix += ac;
                        ac = st.pop();
                    }
                case '+':
                case '-':
                case '×':
                case '÷':
                case '%':
                    while (!st.isEmpty()){//当栈非空
                        ac = st.pop();
                        if(priority(ac) >= priority(c)){//将栈顶元素与当前字符元素进行优先级比较，弹出优先级更高的元素
                            postFix += ac;
                            ac = st.pop();
                        }else{
                            break;
                        }
                    }
                    st.push(c);
                    break;
                default: postFix += c;
            }
        }

        while (!st.isEmpty()){
            ac = st.pop();
            postFix += ac;
        }
        return postFix;  //返回后缀表达式
    }

    public String getResult(String postFix){
        String c5 = "";
        for (int i =0;i<postFix.length();i++){
            char c= postFix.charAt(i);
            if (c == ' '){
                st.push(c5.toString()); //数字入栈
                c5 ="";
                if (st.contains(" ")){
                    st.pop();
                }
            }
            if (isOperator(c)){

                double d2 = Double.valueOf(st.pop().toString());
                double d1 = Double.valueOf(st.pop().toString());
               // Log.i(SERVICE,"d2"+d2);
              //  Log.i(SERVICE,"d1"+d1);
                double result=0;
                switch (c){
                    case '+':
                        result=d1+d2;
                        break;
                    case '-':
                        result=d1-d2;
                        break;
                    case '×':
                        result=d1*d2;
                        break;
                    case '÷':
                        if (d2 == 0){
                            text_input.setText("error");
                        }
                        result=d1/d2;
                        break;
                    case '%':
                        result=d1%d2;
                    default:
                        break;
                }
                String value =String.valueOf(result);
                if (value.indexOf(".")>0){
                    value = value.replaceAll("0+?$", "");//去掉多余的0
                    value = value.replaceAll("[.]$", "");//如最后一位是.则去掉
                }
                st.push(value);  //操作后的结果入栈
            }
            else {
                Character c4 = (Character) c;
                c5 += c4;
                // st.push(String.valueOf(c)); //数字入栈

            }
        }
        return st.pop();
    }

    private int priority(char c){
        switch (c){
            case '+':
            case '-':
                return 1;
            case '×':
            case '÷':
            case '%':
                return 2;
        }
        return 0;
    }
    private boolean isOperator(char c){
        if ('+' ==c||'-' == c||'÷'==c||'×'==c|| '%' == c){
            return true;
        }
        return false;
    }
}
