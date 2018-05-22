package cl.inacap.herbalifeproject.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cl.inacap.herbalifeproject.R;
import cl.inacap.herbalifeproject.utils.SystemUtils;

public class ViewablePasswordEditText extends AppCompatEditText implements View.OnTouchListener, View.OnFocusChangeListener, TextWatcher {

    private Drawable icono;
    private OnFocusChangeListener focusChangeListener;
    private OnTouchListener touchListener;
    private Context context;
    private Typeface fuente;
    private boolean isVisible = false;

    public ViewablePasswordEditText(final Context context) {
        super(context);
        initialize(context);
    }

    public ViewablePasswordEditText(final Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public ViewablePasswordEditText(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(final Context context) {
        this.context = context;
        fuente = getTypeface();
        icono = ContextCompat.getDrawable(context, R.drawable.ic_eye);
        icono.setBounds(0, 0, icono.getIntrinsicHeight(), icono.getIntrinsicHeight());
        setIconoVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    private void setIconoVisible(boolean visible) {
        icono.setVisible(visible, false);
        final Drawable[] drawables = getCompoundDrawablesRelative();
        setCompoundDrawablesRelative(drawables[0], drawables[1], visible ? icono : null, drawables[3]);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isFocused())
            setIconoVisible(s.length() > 0);
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        focusChangeListener = l;
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        touchListener = l;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        setIconoVisible(hasFocus && getText().length() > 0);
        if (focusChangeListener != null)
            focusChangeListener.onFocusChange(v, hasFocus);
        SystemUtils.getInstance().keyboard(context, v, !hasFocus);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (icono.isVisible() && (int)event.getX() > getWidth() - getPaddingRight() - icono.getIntrinsicWidth()) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (isVisible) {
                    setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                setTypeface(fuente);
                setSelection(getText().length());
                isVisible = !isVisible;
            }
            return true;
        }
        return touchListener != null && touchListener.onTouch(v, event);
    }
}
