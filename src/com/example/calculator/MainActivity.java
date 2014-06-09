package com.example.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	// Constants
	private static final int READY_FOR_NEXT = 1039184;
	private static final int READY_TO_RESET = 2903039;
	private static final int READY_TO_CALC = 2342222;
	private static final int ENTERING_FIRST = 3209581;
	
	// Members
	private CalcModel mState;
	private TextView mTextBox; // textbox pointer
	private int mMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		mTextBox = (TextView) findViewById(R.id.calc_input);
		mMode = ENTERING_FIRST;
		mState = new CalcModel();

		// Listener for setting operation
		OnClickListener op_listener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				Button b = (Button) view;
				mState.setOp((String) b.getText(), (String) mTextBox.getText());
				mMode = READY_FOR_NEXT;
			}
		};
		((Button) findViewById(R.id.btn_add)).setOnClickListener(op_listener);
		((Button) findViewById(R.id.btn_subtract))
				.setOnClickListener(op_listener);
		((Button) findViewById(R.id.btn_mult)).setOnClickListener(op_listener);
		((Button) findViewById(R.id.btn_div)).setOnClickListener(op_listener);

		// Listener for entering digits
		OnClickListener num_listener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mMode == READY_TO_RESET) {
					mState.clearInput();
					mTextBox.setText("");
					mMode = ENTERING_FIRST;
				}
				if (mMode == READY_FOR_NEXT){
					mTextBox.setText("");
					mMode = READY_TO_CALC;
				}
				displayDigit(view);
			}
		};
		((Button) findViewById(R.id.btn_one)).setOnClickListener(num_listener);
		((Button) findViewById(R.id.btn_two)).setOnClickListener(num_listener);
		((Button) findViewById(R.id.btn_three))
				.setOnClickListener(num_listener);
		((Button) findViewById(R.id.btn_four)).setOnClickListener(num_listener);
		((Button) findViewById(R.id.btn_five)).setOnClickListener(num_listener);
		((Button) findViewById(R.id.btn_six)).setOnClickListener(num_listener);
		((Button) findViewById(R.id.btn_seven))
				.setOnClickListener(num_listener);
		((Button) findViewById(R.id.btn_eight))
				.setOnClickListener(num_listener);
		((Button) findViewById(R.id.btn_nine)).setOnClickListener(num_listener);
		((Button) findViewById(R.id.btn_zero)).setOnClickListener(num_listener);
		((Button) findViewById(R.id.btn_dec)).setOnClickListener(num_listener);

		// Listener for changing signs
		OnClickListener sign_listener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				changeSign(view);
			}
		};
		((Button) findViewById(R.id.btn_sign))
				.setOnClickListener(sign_listener);

		// Listener for clearing textbox
		OnClickListener clear_listener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				mState.clearInput();
				mTextBox.setText("");
				mMode = ENTERING_FIRST;
			}
		};
		((Button) findViewById(R.id.btn_clear))
				.setOnClickListener(clear_listener);

		// Listener for calculating
		OnClickListener calc_listener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				mTextBox.setText(mState.calculate((String)mTextBox.getText()));
				mMode = READY_TO_RESET;
			}
		};
		((Button) findViewById(R.id.btn_eq)).setOnClickListener(calc_listener);

	}

	/**
	 * Displays the digit clicked in text box. Add the digit to the end of the pre-displayed input.
	 */
	public void displayDigit(View view) {
		if (mTextBox.getText().length() < 10) {
			Button b = (Button) view;
			mTextBox.setText(mTextBox.getText() + b.getText().toString());
		}
	}

	/**
	 * Changes sign of preinput
	 */
	public void changeSign(View view) {
		if (mTextBox.getText().length() < 10) {
			if (((String) mTextBox.getText()).substring(0, 1).equals("-")) {
				mTextBox.setText(((String) mTextBox.getText()).substring(1));
			} else {
				mTextBox.setText("-" + mTextBox.getText());
			}
		}
	}

}
