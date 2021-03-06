package com.example.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	// Constants
	private int ADD = 1;
	private int SUBTRACT = 2;
	private int MULTIPLY = 3;
	private int DIVIDE = 4;
	private int RESET = 0;

	// Members
	private String mPreInput = ""; // input that is currently in the textbox
	private BigDecimal mLastInput = new BigDecimal(RESET); // input that was
															// last in the
															// textbox before
															// the textbox was
															// cleared
	private double mOp = RESET; // operation to be executed
	private TextView mTextBox; // textbox pointer
	private boolean mNewInput = true; // if true, clear screen before entering
										// new digits

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		mTextBox = (TextView) findViewById(R.id.calc_input);

		// Listener for setting operation
		OnClickListener op_listener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				setOp(view);
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
				clearInput(view);
			}
		};
		((Button) findViewById(R.id.btn_clear))
				.setOnClickListener(clear_listener);

		// Listener for calculating
		OnClickListener calc_listener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				calculate(view);
			}
		};
		((Button) findViewById(R.id.btn_eq)).setOnClickListener(calc_listener);

	}

	/**
	 * Displays the digit clicked in text box. If digits are already displayed
	 * in the text box, add the digit to the end of the pre-displayed input.
	 */
	public void displayDigit(View view) {
		if (mNewInput) {
			clearInput(view);
			mNewInput = false;
		}
		if (mPreInput.length() < 10) {
			Button b = (Button) view;
			String new_digit = b.getText().toString();
			String display_num = mPreInput + new_digit;
			mPreInput = display_num;
			mTextBox.setText(mPreInput);
		}
	}

	/**
	 * Clears preinput.
	 */
	public void clearInput(View view) {
		mPreInput = "";
		mTextBox.setText(mPreInput);
		mOp = RESET;
		mLastInput = new BigDecimal(RESET);

	}

	/**
	 * Changes sign of preinput
	 */
	public void changeSign(View view) {
		if (mPreInput.length() > 0) {
			if (mPreInput.substring(0, 1).equals("-")) {
				mPreInput = mPreInput.substring(1);
			} else {
				mPreInput = "-" + mPreInput;
			}
			mTextBox.setText(mPreInput);
		}
	}

	/**
	 * Sets fields for add operation
	 */
	public void setOp(View view) {
		if (mNewInput) {
			mNewInput = false;
		}
		Button b = (Button) view;
		String btnText = (String) b.getText();
		if (btnText.equals("-")) {
			mOp = SUBTRACT;
		} else if (btnText.equals("+")) {
			mOp = ADD;
		} else if (btnText.equals("*")) {
			mOp = MULTIPLY;
		} else {
			mOp = DIVIDE;
		}
		if (mPreInput.length() > 0) {
			mLastInput = new BigDecimal(mPreInput);
			mPreInput = "";
		}
	}

	/**
	 * Calculates the final value using operation op, lastinput, and preinput.
	 * Sets preinput to the final value. Displays final value in textbox.
	 */
	public void calculate(View view) {
		if (mOp == ADD) {
			mPreInput = (mLastInput.add(new BigDecimal(mPreInput)))
					.stripTrailingZeros() + "";
		} else if (mOp == SUBTRACT) {
			mPreInput = (mLastInput.subtract(new BigDecimal(mPreInput)))
					.stripTrailingZeros() + "";
		} else if (mOp == MULTIPLY) {
			mPreInput = (mLastInput.multiply(new BigDecimal(mPreInput)))
					.stripTrailingZeros() + "";
		} else if (mOp == DIVIDE) {
			try {
				mPreInput = (mLastInput.divide(new BigDecimal(mPreInput),
						5, RoundingMode.HALF_UP)).stripTrailingZeros() + "";
			} catch (ArithmeticException ae) {
					mPreInput = "Zero Division Error";
			}
		}
		mNewInput = true;
		mOp = RESET;
		mLastInput = new BigDecimal(RESET);
		mTextBox.setText(mPreInput);
	}

}
