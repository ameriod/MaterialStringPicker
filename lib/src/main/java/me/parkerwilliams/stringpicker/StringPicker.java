/*
 * Copyright (C) 2008 Parker Williams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.parkerwilliams.stringpicker;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    public interface OnValueChangeListener {
        void onValueChanged(StringPicker picker, String oldVal, String newValue);
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

    @Nullable
    public String getCurrentValue() {
        if (mValues != null) {
            return mValues[getCurrent()];
        }
        return null;
    }

    public void setValues(@NonNull String[] values) {
        mValues = values;
        setMaxValue(values.length - 1);
        setMinValue(0);

        setDisplayedValues(values);
    }

    public void setValues(@NonNull List<String> values) {
        mValues = values.toArray(new String[values.size()]);
        setValues(mValues);
    }

    public void setCurrent(@NonNull String currentValue) {
        if (mValues != null) {
            for (int i = 0, size = mValues.length; i < size; i++) {
                String pickerValue = mValues[i];
                if (TextUtils.equals(currentValue, pickerValue)) {
                    setCurrent(i);
                    break;
                }
            }
        }
    }
}
