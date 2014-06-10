package com.example.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import android.util.Log;

public class CalcModel {

	// Constants
	private static final int ADD = 262561;
	private static final int SUBTRACT = 263652;
	private static final int MULTIPLY = 352656;
	private static final int DIVIDE = 487959;
	private static final int RESET = 0;

	// Members
	private BigDecimal mCurrent; // input that is currently in the textbox
	private BigDecimal mLastInput; // input that was
									// last in the
									// textbox before
									// the textbox was
									// cleared
	private double mOp; // operation to be executed
	private int mExp;

	/** Constructor */
	public CalcModel() {
		mCurrent = new BigDecimal(RESET);
		mLastInput = new BigDecimal(RESET);
		mOp = RESET;
		mExp = -1;
	}

	public BigDecimal getCurrent() {
		return mCurrent;
	}

	public void updateDigit(int nextDigit) {
		mCurrent = mCurrent.multiply(BigDecimal.TEN).add(
				new BigDecimal(nextDigit));
	}

	public void updateDecimal(int nextDigit) {
		mCurrent = mCurrent.add((new BigDecimal(nextDigit))
				.scaleByPowerOfTen(mExp));
		mExp--;
	}

	public void updateSign() {
		mCurrent = mCurrent.multiply(BigDecimal.ONE.negate());
	}

	/** Clears mCurrent. */
	public void clearInput() {
		mCurrent = new BigDecimal(RESET);
		mOp = RESET;
		mLastInput = new BigDecimal(RESET);
		mExp = -1;

	}

	/** Sets fields for add operation */
	public void setOp(String op) {
		if (op.equals("-")) {
			mOp = SUBTRACT;
		} else if (op.equals("+")) {
			mOp = ADD;
		} else if (op.equals("*")) {
			mOp = MULTIPLY;
		} else {
			mOp = DIVIDE;
		}
		mLastInput = mCurrent;
		mCurrent = new BigDecimal(RESET);
		mExp = -1;
	}

	/**
	 * Calculates the final value. Sets mCurrent to the final value. Displays
	 * final value in textbox.
	 */
	public BigDecimal calculate() {
		if (mOp == ADD) {
			mCurrent = mLastInput.add(mCurrent).stripTrailingZeros();
		} else if (mOp == SUBTRACT) {
			mCurrent = mLastInput.subtract(mCurrent).stripTrailingZeros();
		} else if (mOp == MULTIPLY) {
			mCurrent = mLastInput.multiply(mCurrent).stripTrailingZeros();
		} else if (mOp == DIVIDE) {
			mCurrent = (mLastInput.divide(mCurrent, 20, RoundingMode.HALF_UP))
					.stripTrailingZeros();
		}
		mOp = RESET;
		mLastInput = new BigDecimal(RESET);
		mExp = -1;
		return mCurrent;
	}

}
