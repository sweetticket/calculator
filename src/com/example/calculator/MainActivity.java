package com.example.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	// Members
	private CalcModel mState;
	private TextView mTextBox; // textbox pointer
	private boolean mReset = true; // if true, clear screen before entering
										// new digits
	private boolean mReadyForNext = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		mTextBox = (TextView) findViewById(R.id.calc_input);
		mState = new CalcModel();

		// Listener for setting operation
		OnClickListener op_listener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				Button b = (Button) view;
				mState.setOp((String) b.getText(), (String) mTextBox.getText());
				mReadyForNext = true;
				mReset = false;
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
			}
		};
		((Button) findViewById(R.id.btn_clear))
				.setOnClickListener(clear_listener);

		// Listener for calculating
		OnClickListener calc_listener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				mReset = true;
				mTextBox.setText(mState.calculate((String)mTextBox.getText()));
			}
		};
		((Button) findViewById(R.id.btn_eq)).setOnClickListener(calc_listener);

	}

	/**
	 * Displays the digit clicked in text box. If digits are already displayed
	 * in the text box, add the digit to the end of the pre-displayed input.
	 */
	public void displayDigit(View view) {
		if (mReset) {
			mState.clearInput();
			mReset = false;
		}
		if (mReadyForNext){
			mTextBox.setText("");
			mReadyForNext = false;
		}

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
