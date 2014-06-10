package com.example.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	// Constants
	private static final int READY_FOR_NEXT = 1039184; // ready for second
														// number, textbox not
														// yet refreshed
	private static final int READY_TO_RESET = 2903039; // AC
	private static final int READY_TO_CALC = 2342222; // current input is the
														// second number
	private static final int ENTERING_FIRST = 3209581; // current input is the
														// first number

	// Members
	private CalcModel mState;
	private TextView mTextBox; // textbox pointer
	private int mMode;
	private boolean mDecimalize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		mTextBox = (TextView) findViewById(R.id.calc_input);
		mMode = ENTERING_FIRST;
		mDecimalize = false;
		mState = new CalcModel();
		mTextBox.setText(mState.getCurrent().toString());

		// Listener for setting operation
		OnClickListener op_listener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				Button b = (Button) view;
				if (mMode == READY_TO_CALC) {
					displayResult();
				}
				mState.setOp(b.getText().toString());
				mMode = READY_FOR_NEXT;
				mDecimalize = false;
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
					reset();
				}
				if (mMode == READY_FOR_NEXT) {
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

		// Listener for adding a decimal point
		OnClickListener point_listener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mMode == READY_TO_RESET) {
					reset();
				}
				addPoint(view);
			}
		};
		((Button) findViewById(R.id.btn_dec))
				.setOnClickListener(point_listener);

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
				reset();
			}
		};
		((Button) findViewById(R.id.btn_clear))
				.setOnClickListener(clear_listener);

		// Listener for calculating
		OnClickListener calc_listener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				displayResult();
			}
		};
		((Button) findViewById(R.id.btn_eq)).setOnClickListener(calc_listener);

	}

	/**
	 * Displays the digit clicked in text box. Add the digit to the end of the
	 * pre-displayed input.
	 */
	public void displayDigit(View view) {
		if (mTextBox.getText().length() < 10) {
			Button b = (Button) view;
			if (!mDecimalize) {
				mState.updateDigit(Integer.parseInt(b.getText().toString()));
			} else {
				mState.updateDecimal(Integer.parseInt(b.getText().toString()));
			}
			mTextBox.setText(mState.getCurrent().toString());
		}
	}

	/**
	 * Changes sign of preinput
	 */
	public void changeSign(View view) {
		if (mTextBox.getText().length() < 10) {
			mState.updateSign();
			mTextBox.setText(mState.getCurrent().toString());

		}
	}

	/** Add decimal point */
	public void addPoint(View view) {
		if (!mDecimalize) {
			mTextBox.setText(mState.getCurrent().toString() + ".");
			mDecimalize = true;
		}
	}

	/** AC */
	public void reset() {
		mState.clearInput();
		mTextBox.setText(mState.getCurrent().toString());
		mDecimalize = false;
		mMode = ENTERING_FIRST;
	}
	
	/** Calculates and displays result */
	public void displayResult(){
		if (mState.calculate()) {
			if (mState.getCurrent().toPlainString().length() > 10) {
				mTextBox.setText(mState.getCurrent().toString());
			} else {
				mTextBox.setText(mState.getCurrent().toPlainString());
			}
			}else{
				mTextBox.setText("Zero Division Error");
			}
		mMode = READY_TO_RESET;
	}
}
