package com.example.debug;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SimpleCalc";

    private Calculator mCalculator;

    private EditText mOperandOneEditText;
    private EditText mOperandTwoEditText;
    private TextView mResultTextView;

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

        mCalculator = new Calculator();
        mResultTextView = findViewById(R.id.tvResult);
        mOperandOneEditText = findViewById(R.id.etNum1);
        mOperandTwoEditText = findViewById(R.id.etNum2);
    }

    public void onAdd(View view) {
        compute(Calculator.Operator.ADD);
    }

    public void onSub(View view) {
        compute(Calculator.Operator.SUB);
    }

    public void onDiv(View view) {
        compute(Calculator.Operator.DIV);
    }

    public void onMul(View view) {
        compute(Calculator.Operator.MUL);
    }

    private void compute(Calculator.Operator operator) {
        double operandOne;
        double operandTwo;

        try {
            operandOne = getOperand(mOperandOneEditText);
            operandTwo = getOperand(mOperandTwoEditText);
        } catch (NumberFormatException nfe) {
            Log.e(TAG, "NumberFormatException", nfe);
            mResultTextView.setText(R.string.invalid_input);
            Toast.makeText(this, getString(R.string.please_enter_both_numbers), Toast.LENGTH_SHORT).show();
            return;
        }

        double result;
        switch (operator) {
            case ADD:
                result = mCalculator.add(operandOne, operandTwo);
                break;
            case SUB:
                result = mCalculator.sub(operandOne, operandTwo);
                break;
            case DIV:
                if (operandTwo == 0) {
                    mResultTextView.setText(R.string.cannot_divide_by_zero);
                    return;
                }
                result = mCalculator.div(operandOne, operandTwo);
                break;
            case MUL:
                result = mCalculator.mul(operandOne, operandTwo);
                break;
            default:
                result = 0;
                break;
        }

        if (result == (long) result) {
            mResultTextView.setText(String.format(Locale.US, "%d", (long) result));
        } else {
            mResultTextView.setText(String.valueOf(result));
        }
    }

    private static double getOperand(EditText operandEditText) {
        String operandText = getOperandText(operandEditText);
        return Double.parseDouble(operandText);
    }

    private static String getOperandText(EditText operandEditText) {
        return operandEditText.getText().toString();
    }
}