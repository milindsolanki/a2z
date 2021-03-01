package com.a2z.deliver.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.a2z.deliver.R;


@SuppressLint("AppCompatCustomView")
public class CustomTextview extends TextView {

	public CustomTextview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomTextview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomTextview(Context context) {
		super(context);
	}

	public void setTypeface(Typeface tf, int style) {
		if (style == Typeface.BOLD) {
			super.setTypeface(Typeface.createFromAsset(
					getContext().getAssets(), getResources().getString( R.string.font)),Typeface.BOLD);

		} else {
			super.setTypeface(Typeface.createFromAsset(
					getContext().getAssets(), getResources().getString(R.string.font)));
		}

	}
}