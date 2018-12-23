package com.calculator.android;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * Created by yingyaopeng on 2018/12/8.
 * 还有一些字符串处理方面的小bug,多多包涵
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button bt_0,bt_1,bt_2,bt_3,bt_4,bt_5,bt_6,bt_7,bt_8,bt_9,bt_multiply,bt_divide,bt_plus
            ,bt_subtraction,bt_point,bt_del,bt_equal,bt_clear,bt_superplus,bt_zuokuohao,bt_youkuohao;
    TextView text_input;
    boolean clear_flag = false;
    boolean numberInputed = false;//标记是否有数字输入
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getTitle().toString().equals("帮助")){
                    Intent it = new Intent(MainActivity.this,HelpActivity.class);
                    startActivity(it);
                }else if(menuItem.getTitle().toString().equals("关于")){
                    Intent it = new Intent(MainActivity.this,AboutActivity.class);
                    startActivity(it);
                }
                return true;
            }
        });

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

        }
        return true;
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
                numberInputed = true;
                if(clear_flag){
                    clear_flag = false;
                    str = "";
                    text_input.setText("");
                }
                if(!str.equals("") && isOperator(String.valueOf(str.charAt(str.length()- 1))))
                    text_input.setText(str + " " + ((Button)view).getText());
                else {
                    text_input.setText(str + ((Button) view).getText());
                }
                break;

            case R.id.point:
                if(numberInputed){
                    text_input.setText(str + ((Button) view).getText());
                }
                break;

            case R.id.zuokuohao:
            case R.id.youkuohao:
                if(clear_flag){
                    clear_flag = false;
                    text_input.setText("");
                }
                text_input.setText(str+ " " +((Button)view).getText() + " ");
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
                if(!str.equals("") &&//当字符串最后一个字符是数字时，置数字输入标志为true
                    (str.charAt(str.length()- 1) >= '0' && str.charAt(str.length()- 1) <= '9' )){
                    numberInputed = true;
                }
                if(numberInputed || !str.equals("")&&//当字符串最后倒数第二个字符（即符号）不是符号时才可以输入运算符号
                        !isOperator(String.valueOf(str.charAt(str.length() - 2)))) {
                    numberInputed = false;
                    text_input.setText(str + " " + ((Button) view).getText() + " ");
                }
                break;

            case R.id.delete:
                if(clear_flag){
                    clear_flag = false;
                    str = "";
                    text_input.setText("");
                }else if(!str.equals("")) {
                    if(str.charAt(str.length() - 1) == ' '){
                        numberInputed = false;
                    }
                    else
                        numberInputed = true;
                    if(str.length() >= 2 && (isOperator(String.valueOf(str.charAt(str.length() - 2)))
                            || (str.charAt(str.length() - 2) == ')' || str.charAt(str.length() - 2) == ')'))) {
                        text_input.setText(str.substring(0, str.length() - 3));
                    }
                    else if(str.charAt(str.length() - 1) >= '0' && str.charAt(str.length() - 1) <= '9'
                            || str.charAt(str.length() - 1) == '.'){
                        text_input.setText(str.substring(0, str.length() - 1));
                    }
                }
                if(str.equals(""))
                    numberInputed = false;
                break;

            case R.id.clear:
                clear_flag = false;
                numberInputed = false;
                str = "";
                text_input.setText("");
                break;

            case R.id.equal:
                String expressions = text_input.getText().toString();
                if(numberInputed) {
                    String postFix = covertToPostFix(expressions);
                    String result = getResult(postFix);
                    text_input.setText(result);
                    clear_flag = true;
                }
                break;
        }
    }

    public String covertToPostFix(String expressions) {
        Stack<String> stOperator = new Stack<String>();
        expressions += " ";
        String postFix = "";
        String ac = "";
        String c = "";
        char temp;
        for (int i=0; i<expressions.length()&&expressions != "";i++){
            temp = expressions.charAt(i);
            if(temp != ' '){
                c += temp;
            }
            if(temp == ' ') {
                switch (c) {
                    case "(":
                        stOperator.push(c);
                        break;
                    case ")":
                        ac = stOperator.pop();
                        while (!ac.equals("(")) {
                            postFix += ac + " ";
                            ac = stOperator.pop();
                        }
                    case "+":
                    case "-":
                    case "×":
                    case "÷":
                    case "%":
                        while (!stOperator.isEmpty()) {
                            ac = stOperator.peek();//取出栈顶元素而不弹出
                            if (priority(ac) >= priority(c)) {
                                stOperator.pop();
                                postFix += ac + " ";
                            } else {
                                break;
                            }
                        }
                        if(!c.equals(")"))
                            stOperator.push(c);
                        break;
                    default:
                        postFix += c + " ";
                }
                c = "";
            }
        }
        while (!stOperator.isEmpty()){
            ac = stOperator.pop();
            postFix += ac + " ";
        }
        return postFix;
    }

    public String getResult(String postFix){
        Stack<String> st = new Stack<String>();
        String c = "";
        char temp;
        for (int i =0; i < postFix.length();i++){
            temp = postFix.charAt(i);
            if(temp != ' '){
                c += temp;
            }
            if (temp == ' '){
                st.push(c.toString());
                c = "";
                if (st.contains(" ")||st.contains("")){
                    st.pop();
                }
            }
            if (isOperator(c)){
                BigDecimal d2 = new BigDecimal(st.pop().toString());
                BigDecimal d1 = new BigDecimal(st.pop().toString());
                double result = 0;
                switch (c){
                    case "+":
                        result=d1.add(d2).doubleValue();
                        break;
                    case "-":
                        result=d1.subtract(d2).doubleValue();
                        break;
                    case "×":
                        result=d1.multiply(d2).doubleValue();
                        break;
                    case "÷":
                        if (d2.doubleValue() == 0){
                            break;
                        }
                        result=d1.divide(d2,6,BigDecimal.ROUND_HALF_DOWN).doubleValue();
                        break;
                    case "%":
                        if (d2.doubleValue() == 0){
                            break;
                        }
                        result=d1.divideAndRemainder(d2)[1].doubleValue();
                    default:
                        break;
                }
                String value = String.valueOf(result);
                if (value.indexOf(".")>0){
                    value = value.replaceAll("0+?$", "");//去掉多余的0
                    value = value.replaceAll("[.]$", "");//如最后一位是.则去掉
                }
                if(d2.doubleValue() == 0 && (c.equals("÷")||c.equals("%")))
                    st.push("Error");
                else
                    st.push(value);
                c = "";
            }
        }
        if(st.peek().equals("Error")){
            st.pop();
            return "Error";
        } else
            return st.pop();
    }

    private int priority(String c){
        switch (c){
            case "+":
            case "-":
                return 1;
            case "×":
            case "÷":
            case "%":
                return 2;
        }
        return 0;
    }
    private boolean isOperator(String c){
        if (c.equals("+")||c.equals("-")||c.equals("÷")||c.equals("×")||c.equals("%")){
            return true;
        }
        return false;
    }
}
