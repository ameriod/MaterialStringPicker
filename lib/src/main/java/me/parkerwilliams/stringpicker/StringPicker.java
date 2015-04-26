package me.parkerwilliams.stringpicker;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;

import java.util.List;

/**
 * Created by parker on 4/26/15.
 */
public class StringPicker extends NumberPickerTextView {

    private String[] mValues;
    private OnValueChangeListener mValueChangeListener;

    public StringPicker(Context context) {
        super(context);
    }

    public StringPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StringPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static interface OnValueChangeListener {
        public void onValueChanged(StringPicker picker, String oldVal, String newValue);
    }

    public void setOnValueChangeListener(OnValueChangeListener listener) {
        mValueChangeListener = listener;
        NumberPickerTextView.OnValueChangeListener numPickerValueListener = new NumberPickerTextView
                .OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerTextView picker, int oldVal, int newVal) {
                if (mValueChangeListener != null && mValues != null) {
                    String oldValue = mValues[oldVal];
                    String newValue = mValues[newVal];
                    mValueChangeListener.onValueChanged(StringPicker.this, oldValue, newValue);
                }
            }
        };
        this.setOnValueChangedListener(numPickerValueListener);
    }

    public int getCurrent() {
        return getValue();
    }

    public void setCurrent(int current) {
        setValue(current);
    }

    public String getCurrentValue() {
        return mValues[getCurrent()];
    }

    public void setValues(final String[] values) {
        mValues = values;
        setMaxValue(values.length - 1);
        setMinValue(0);

        setDisplayedValues(values);
    }

    public void setValues(final List<String> values) {
        mValues = values.toArray(new String[values.size()]);
        setValues(mValues);
    }

    public void setCurrent(@NonNull String currentValue) {
        for (int i = 0, size = mValues.length; i < size; i++) {
            String pickerValue = mValues[i];
            if (TextUtils.equals(currentValue, pickerValue)) {
                setCurrent(i);
                break;
            }
        }
    }
}
