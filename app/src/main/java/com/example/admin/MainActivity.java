package com.example.admin;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private EditText output = null;
    private EditText input = null;
    private Button btn0 = null;
    private Button btn1 = null;
    private Button btn2 = null;
    private Button btn3 = null;
    private Button btn4 = null;
    private Button btn5 = null;
    private Button btn6 = null;
    private Button btn7 = null;
    private Button btn8 = null;
    private Button btn9 = null;
    private Button btnadd = null;
    private Button btnsubtract = null;
    private Button btnmultiply = null;
    private Button btndivide = null;
    private Button btnclear = null;
    private Button btnresult = null;
    private Button btndot = null;

    private EditText errorzero = null;

    private EditText resultText = null;
    private Button writeButton = null;
    private Button readButton = null;
    private CheckBox appendBox = null;
    private EditText textView = null;
    private EditText displayView = null;
    public String FILE_NAME = "fileDemo.txt";


    private String str = "";//保存数字
    private String strold = "";//原数字
    private char act = ' ';//记录“加减乘除等于”符号
    private int count = 0;//判断要计算的次数，如果超过一个符号，先算出来一部分
    private Float result = null;//计算的输出结果
    private Boolean errBoolean = false;//有错误的时候为true，无错为false
    private Boolean flagBoolean = false;//一个标志，如果为true，可以响应运算消息，如果为false，不响应运算消息，只有前面是数字才可以响应运算消息
    private Boolean flagDot = false; //小数点标志位



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = findViewById(R.id.output);
        input = findViewById(R.id.input);

        errorzero = findViewById(R.id.errorzero);
        resultText = findViewById(R.id.resultText);
        writeButton = findViewById(R.id.writeButton);
        readButton = findViewById(R.id.readButton);
        textView = findViewById(R.id.textView);
        displayView = findViewById(R.id.displayView);
        appendBox = findViewById(R.id.appendBox);

        btn0 = findViewById(R.id.zero);
        btn1 = findViewById(R.id.one);
        btn2 = findViewById(R.id.two);
        btn3 = findViewById(R.id.three);
        btn4 = findViewById(R.id.four);
        btn5 = findViewById(R.id.five);
        btn6 = findViewById(R.id.six);
        btn7 = findViewById(R.id.seven);
        btn8 = findViewById(R.id.eight);
        btn9 = findViewById(R.id.nine);
        btnadd = findViewById(R.id.add);
        btnsubtract = findViewById(R.id.subtract);
        btnmultiply = findViewById(R.id.multiply);
        btndivide = findViewById(R.id.divide);
        btnclear = findViewById(R.id.clear);
        btnresult = findViewById(R.id.result);
        btndot = findViewById(R.id.dot);
        //设置按钮侦听事件
        btn0.setOnClickListener(listener);
        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
        btn5.setOnClickListener(listener);
        btn6.setOnClickListener(listener);
        btn7.setOnClickListener(listener);
        btn8.setOnClickListener(listener);
        btn9.setOnClickListener(listener);
        //执行运算
        btnadd.setOnClickListener(listener);
        btnsubtract.setOnClickListener(listener);
        btnmultiply.setOnClickListener(listener);
        btndivide.setOnClickListener(listener);
        btnclear.setOnClickListener(listener);
        btnresult.setOnClickListener(listener);

        btndot.setOnClickListener(listener);

        writeButton.setOnClickListener(writelistener);
        readButton.setOnClickListener(readlistener);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }


    private OnClickListener listener = new OnClickListener() {

        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                //输入数字
                case R.id.zero:
                    num(0);
                    break;
                case R.id.one:
                    num(1);
                    break;
                case R.id.two:
                    num(2);
                    break;
                case R.id.three:
                    num(3);
                    break;
                case R.id.four:
                    num(4);
                    break;
                case R.id.five:
                    num(5);
                    break;
                case R.id.six:
                    num(6);
                    break;
                case R.id.seven:
                    num(7);
                    break;
                case R.id.eight:
                    num(8);
                    break;
                case R.id.nine:
                    num(9);
                    break;

                case R.id.dot:
                    dot();
                    break;
                //执行运算
                case R.id.add:
                    add();
                    break;
                case R.id.subtract:
                    sub();
                    break;
                case R.id.multiply:
                    multiply();
                    break;
                case R.id.divide:
                    divide();
                    break;
                case R.id.clear:
                    clear();
                    break;
                //计算结果
                case R.id.result:
                    result();
                    if (!errBoolean && flagBoolean) {
                        output.setText(String.valueOf(result));
                    }
                    resultText.setText(strold + act + str + "=" + result+"                          ");
                    break;

                default:
                    break;

            }
            input.setText(strold + act + str);
            output.setText(String.valueOf(result));


        }
    };

    private OnClickListener writelistener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            //textView.setText("");

            FileOutputStream fos = null;
            try {
                if (appendBox.isChecked()) {
                    fos = openFileOutput(FILE_NAME, Context.MODE_APPEND);
                } else {
                    fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                }
                String text = resultText.getText().toString();
                fos.write(text.getBytes());
                textView.setText("文件写入成功，写入长度：" + text.length());
                //resultText.setText("");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (fos != null)
                    try {
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    };
    private OnClickListener readlistener = new OnClickListener() {
        @Override
        public void onClick(View view) {

            displayView.setText("");
            FileInputStream fis = null;
            try {
                fis = openFileInput(FILE_NAME);
                if (fis.available() == 0) {
                    return;
                }
                byte[] readBytes = new byte[fis.available()];
                while (fis.read(readBytes) != -1) {

                }
                String text = new String(readBytes);
                displayView.setText(text);
                textView.setText("文件读取成功，写入长度：" + text.length());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    private void dot() {
        // TODO Auto-generated method stub

        if (!flagDot) {
            str = str + ".";
            flagBoolean = false;
            flagDot = true;
        }
    }

    private void clear() {
        // TODO Auto-generated method stub
        str = strold = "";
        count = 0;
        act = ' ';
        result = null;
        flagBoolean = false;
        flagDot = false;
        input.setText(strold + act + str);
        output.setText("");
        errorzero.setText("");
        displayView.setText("");
        textView.setText("");
        resultText.setText("");
    }

    private void divide() {
        // TODO Auto-generated method stub
        if (flagBoolean) {
            check();
            act = '/';
            flagBoolean = false;
        }
    }

    private void multiply() {
        // TODO Auto-generated method stub
        if (flagBoolean) {
            check();
            act = '*';
            flagBoolean = false;
        }
    }

    private void sub() {
        // TODO Auto-generated method stub
        if (flagBoolean) {
            check();
            act = '-';
            flagBoolean = false;
        }
    }

    private void add() {
        // TODO Auto-generated method stub
        if (flagBoolean) {
            check();
            act = '+';
            flagBoolean = false;
        }
    }

    private void check() {
        // TODO Auto-generated method stub
        if (count >= 1) {
            result();
            str = String.valueOf(result);
        }
        strold = str;
        str = "";
        count++;
        flagDot = false;
        errorzero.setText("");
    }

    //计算输出结果
    private void result() {
        // TODO Auto-generated method stub
        if (flagBoolean) {
            Float a, b;

            a = Float.parseFloat(strold);
            b = Float.parseFloat(str);

            if (b == 0 && act == '/') {
                clear();
                errorzero.setText("除数不能为零!");
                //output.setText("除数不能为零!");


                //errBoolean=true;
            }


            if (!errBoolean) {
                switch (act) {
                    case '+':
                        result = a + b;
                        break;
                    case '-':
                        result = a - b;
                        break;
                    case '*':
                        result = a * b;
                        break;
                    case '/':
                        result = a / b;
                        break;

                    default:
                        break;
                }
            }


        }
    }

    private void num(int i) {
        // TODO Auto-generated method stub
        str = str + String.valueOf(i);
        flagBoolean = true;
        errorzero.setText("");
    }

}