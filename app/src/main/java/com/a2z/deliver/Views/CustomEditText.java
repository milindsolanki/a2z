package com.a2z.deliver.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.a2z.deliver.R;


public class CustomEditText extends AppCompatEditText implements CharSequence {

	public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		super.setText(text, type);
	}

	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CustomEditText(Context context) {
		super(context);
		init();
	}


	private void init() {

		Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
				getResources().getString( R.string.font));
		setTypeface(tf);

	}

	@Override
	public char charAt(int index) {
		return 0;
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return null;
	}
}