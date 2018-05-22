package cl.inacap.herbalifeproject.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cl.inacap.herbalifeproject.R;
import cl.inacap.herbalifeproject.utils.SystemUtils;

public class ClearableEditText extends AppCompatEditText implements View.OnTouchListener, View.OnFocusChangeListener, TextWatcher {

    private Drawable icono;
    private OnFocusChangeListener focusListener;
    private OnTouchListener touchListener;
    private Context context;

    public ClearableEditText(final Context context) {
        super(context);
        initialize(context);
    }

    public ClearableEditText(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public ClearableEditText(final Context context, final AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(final Context context) {
        this.context = context;
        icono = ContextCompat.getDrawable(context, R.drawable.ic_clear);
        icono.setBounds(0, 0, icono.getIntrinsicHeight(), icono.getIntrinsicHeight());
        setIconoVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    private void setIconoVisible(final boolean visible) {
        icono.setVisible(visible, false);
        final Drawable[] drawables = getCompoundDrawablesRelative();
        setCompoundDrawablesRelative(drawables[0], drawables[1], visible ? icono : null, drawables[3]);
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        focusListener = l;
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        touchListener = l;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        setIconoVisible(hasFocus && getText().length() > 0);
        if (focusListener != null)
            focusListener.onFocusChange(v, hasFocus);
        SystemUtils.getInstance().keyboard(context, v, !hasFocus);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (icono.isVisible() && (int)event.getX() > getWidth() - getPaddingRight() - icono.getIntrinsicWidth()) {
            if (event.getAction() == MotionEvent.ACTION_UP)
                setText("");
            return true;
        }
        return touchListener != null && touchListener.onTouch(v, event);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isFocused())
            setIconoVisible(s.length() > 0);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
