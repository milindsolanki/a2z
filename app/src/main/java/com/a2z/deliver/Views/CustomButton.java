package com.a2z.deliver.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.a2z.deliver.R;


@SuppressLint("AppCompatCustomView")
public class CustomButton extends Button {

    public CustomButton(Context context) {
        super( context );
        setFont( context );

    }

    public CustomButton(Context context, AttributeSet attrs) {
        super( context, attrs );
        setFont( context );
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super( context, attrs, defStyle );
        setFont( context );
    }


    private void setFont(Context context) {
        Typeface normal = Typeface.createFromAsset( context.getAssets(),
                getResources().getString( R.string.font ) );
        setTypeface( normal );

    }

    public void setTypeface(Typeface tf, int style) {
        if (style == Typeface.BOLD) {
            super.setTypeface( Typeface.createFromAsset(
                    getContext().getAssets(), getResources().getString( R.string.font ) ), Typeface.BOLD );

        } else {
            super.setTypeface( Typeface.createFromAsset(
                    getContext().getAssets(), getResources().getString( R.string.font ) ) );
        }

    }
}