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
            ,bt_subtraction,bt_point,bt_del,bt_equal,bt_clear,bt_superplus;
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
                String postFix = transform(expressions);
                getResult(postFix);
                break;
        }
    }

    //private void getResult(){
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

    public String transform(String expressions) {
        Stack<Character> st = new Stack<Character>(); //运算符栈
        expressions = expressions+"#";  //最后一位添加“#”以表示运算结束
        String postFix = ""; //后缀表达式
        String c2 = "";
        for (int i=0; i<expressions.length()&&expressions !=null;i++){
            char c= expressions.charAt(i);
            if (c != ' '){

                if (isOperator(c)){  //符号判断
                    /**
                     * 1、如果栈为空，直接进栈，如果栈非空，需要将栈顶的运算符的优先级和要入栈的运算符优先级比较
                     * 2、将栈中比入栈的运算符优先级高的出栈，（添加到后缀表达式）然后将该运算符入栈，
                     */
                    isInStack =true; //进栈  boolean值为true
                    if (isInStack ==true || c == '#'){ //如果进栈了，如果是#号，就不进行下面的判断，表示已经到最后一个数了。
                        postFix +=c2+" ";  //将先前存储的值添加到后缀式后面
                        c2 ="";     //将该值清楚，以便进行下次存储
                        isInStack =false;
                    }
                    if (!st.isEmpty()&& c!='#'){  //如果栈非空，则需要判断
                        Character ac = st.pop();
                        while (ac !=null
                                &&priority(ac.charValue())>priority(c) ){  //判断运算符优先级
                            postFix +=ac;
                            ac = null;
                            //先取出来后判断，如果要跳出循环，将ac值设为null
                        }
                        if(ac!=null){
                            st.push(ac);  //如果没有进行前面的运算符优先级判断，则要将取出的运算符重新压入栈中。
                        }
                    }
                    st.push(c);//运算符入栈
                }
                else {
                    Character c1 = (Character) c;
                    c2 +=c1 ;  //暂时存储该数
                    //postFix +=c;
                }
            }
        }
        while (!st.isEmpty()){
            if (calculateOne ==true){  //将最后的‘#’号出栈，因为涉及到每次更新数据以后都会产生一个‘#’号，所以需要设置boolean值来将‘#号移除栈’
                st.pop();
                calculateOne = false;
            }
            postFix +=" "+st.pop().toString(); //如果栈非空，需要将栈中所有运算符串联到后缀表达式的末尾
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
                    case '*':
                        result=d1*d2;
                        break;
                    case '/':
                        if (d2 == 0){
                            text_input.setText("error");
                        }
                        result=d1/d2;

                        break;
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
            case '#':
                return 0;
            case '+':
            case  '-':
            case  ')':
                return 1;
            case '×':
            case '÷':
            case '%':
                return 2;
            case '(':
                return 3;
        }
        return 0;
    }
    private boolean isOperator(char c){
        if ('+' ==c||'-' == c||'/'==c||'*'==c || '#' ==c){
            return true;
        }
        return false;
    }
}
