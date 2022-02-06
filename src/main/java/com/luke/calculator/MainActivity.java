package com.luke.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final String CLEAR_INPUT_TEXT = "0"; // 변하지않는 값 상수 지정, 초기화 버튼에 사용

    boolean isFirstInput = true; // 입력값이 처음 입력되는가를 체크한다. 입력을 하게 되면 False 를 줘서
    // 처음 입력이 아니니 뒤에 계속 숫자를 붙여서 나열할 수 있도록 역할을 함.
    int resultNumber = 0; // 숫자를 입력하고 연산을 누르게 되면, 그 숫자를 저장할수있도록 정수형 변수 만듬.
    char operator = '+';  // 그럼 당연히 연산자를 저장하는 부분도 필요함, 초기값 +(plus) 왜냐 10 + 하고 -5 하면 결과값 5가 저장됨.
    // 문자형 변수에 값을 저장할때는 '' 작은따옴표 사용



    TextView resultText;
    // Main activity 가 실행되면 위 코드들을 실행하고 onCreate 가 실행된다.
    // 그래서, 바로 UI 에서 Textview 값 가져와서 초기화 하는건 불가,

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 이게 activity_main.xml 실행한다는소리, 화면에 우리가만든 UI 보여준다는것.
        // 위 코드 실행된 후에 UI의 TextView 값 가져올수있다.

        resultText = findViewById(R.id.result_text); // findViewById 로 UI의 텍스트뷰 ID result_text 를 불러와서 resultText 에 값을 저장을 한다.
    }

    // AC,CE,BS 버튼을 눌렀을때(OnClick) 해당하는 코딩 부분.
    public void buttonClick(View view) {

        // AC,CE,BS 버튼 눌렀을때 코딩 (Switch 문.ver)
        switch (view.getId()) {

            // AC 버튼 눌렀을 때 코딩 (전체 초기화)
            case R.id.all_clear_button:
                resultNumber = 0; // 이것도 똑같이, 숫자누르고 연산 눌렀을때 저장된 숫자 결과값을 다시 0으로 초기화.
                operator = '+'; // 연산자 저장하는 문자 결과값도 다시 +로 초기화
                setClearText(CLEAR_INPUT_TEXT); //
                break; // 초기화 하고 난 다음 빠져나감

            // CE 버튼 눌렀을 때 코딩 (방금 눌렀던 버튼만 초기화)
            case R.id.clear_entry_button:
                setClearText(CLEAR_INPUT_TEXT); //
                break;

            // BS 버튼 눌렀을 때 코딩 (백 스페이스, 누를 경우 뒤부터 숫자 하나씩 지워지고 한개남았을땐 0으로 바뀌는 구조)
            case R.id.back_space_button:
                if (resultText.getText().toString().length() > 1) { // 문자의 길이가 1보다 크냐 ~?
                    String getResultText = resultText.getText().toString(); // getResultText 에 resultText 에 입력된 문자열을 받아온다.
                    String subString = getResultText.substring(0, getResultText.length() - 1); // subString 에 getResultText 가 가져온 문자열에서 하나를 빼고가져온것
                    resultText.setText(subString); // 그 값(substring)을 resultText 에 저장.
                } else { // 마지막 숫자 하나도 다 지워서 하나도 안남으면
                    setClearText(CLEAR_INPUT_TEXT); //
                }

                break;

            // decimal(소수점.) 버튼 눌렀을 때 코딩
            case R.id.decimal_button:
                Log.e("buttonClick", " decimal_button 이 클릭되었습니다. ");
                // decimal 버튼은 정수형계산기라 구현안하고 로그캣만 찍음.
                break;

        }
    }

    // 입력된 숫자 초기화에 사용되는 메소드 코딩 (중복처리)
    public void setClearText(String clearText) {
        isFirstInput = true; // 만약에 true, 처음 계산기 입력이면
        resultText.setTextColor(0xFF666666); // 숫자 1 눌렀을때, 글자 검정색으로 바뀌게 설정
        resultText.setText(clearText); // clearText 를 받아서 resultText 에 바로 넣어줌.
    }

    // 계산기 숫자 버튼(0~9) 눌렀을 때 실행되는 메소드 코딩 (중복처리)
    public void numButtonClick(View view) {
        Button getButton = findViewById(view.getId()); // 이 버튼이 실행될때마다 위의 view 내용이 getButton 에 들어와서 등록됨
        if (isFirstInput) { // 만약에 true, 처음 계산기 입력이면
            resultText.setTextColor(0xFF000000); // 숫자 1 눌렀을때, 글자 검정색으로 바뀌게 설정
            resultText.setText(getButton.getText().toString());
            isFirstInput = false; // 위 코드에서 방금 처음 실행이 됬으니 false 를 바꿔주는것.
        } else { // 처음 입력이 아니면
                if(resultText.getText().toString().equals("0")){ // 예외처리) 계산기 결과값이 01 02 이런식으로 0으로 시작 못하게 하는 코드
                    Toast.makeText(getApplicationContext(), " 0으로 시작하는 정수는 없습니다. ", Toast.LENGTH_SHORT).show();
                    setClearText(CLEAR_INPUT_TEXT);
                } else {
                    resultText.append(getButton.getText().toString()); // append (뒤에 숫자를 붙이도록) 해라
                }
        }
    }

    // 연산자 버튼이 클릭되었을때 사칙연산인지 = 인지 구분하여 그에 맞는 메소드 실행 코딩 (중복처리)
    public void operatorClick(View view) {
        Button getButton = findViewById(view.getId()); // 이 버튼이 실행될때마다 위의 view 내용이 getButton 에 들어와서 등록됨

        if (view.getId() == R.id.result_button) {
            if(isFirstInput){
                resultNumber = 0; // 이것도 똑같이, 숫자누르고 연산 눌렀을때 저장된 숫자 결과값을 다시 0으로 초기화.
                operator = '+'; // 연산자 저장하는 문자 결과값도 다시 +로 초기화
                setClearText("0");
                // TODO: 2022-02-06 다음에 실수형 계산기 만들때 윈도우 계산기 처럼 =을 두번 이상 누를때 실행 방법과 같이 구현할 것. 
            } else {
                resultNumber = intCal(resultNumber, Integer.parseInt(resultText.getText().toString()), operator); // (중복처리-3)
                // = 결과값이기때문에 operator 는 필요 없다.
                resultText.setText(String.valueOf(resultNumber));
                isFirstInput = true;
            }

        } else {
            if(isFirstInput){
                operator = getButton.getText().toString().charAt(0);
            }else{
                int lastNum = Integer.parseInt(resultText.getText().toString()); // 만약, 숫자 11을 하고 +를 누르면, 그 11 문자열을 정수로 바꿔 LastNum 에 저장
                // lastNum 대신 Integer.parseInt(resultText.getText().toString()) 로 바로 값을 불러올수도 있다.
                resultNumber = intCal(resultNumber, lastNum, operator);
                operator = getButton.getText().toString().charAt(0); // 사칙연산 문자 저장, 이걸 바꿔줌으로써 사칙연산마다 중복코딩 없이 다 사용 가능.
                resultText.setText(String.valueOf(resultNumber));
                isFirstInput = true;
            }

        }

    }

    // 사칙연산을 해서 반환해주는것에 사용되는 메소드 코딩 (중복처리)
    public int intCal(int result, int lastNum, char operator) {
        if (operator == '+') {
            result += lastNum; // result = result + lastNum;
        } else if (operator == '-') {
            result -= lastNum;
        } else if (operator == '/') {
            result /= lastNum;
        } else if (operator == '*') {
            result *= lastNum;
        }
        return result;
    }


}
