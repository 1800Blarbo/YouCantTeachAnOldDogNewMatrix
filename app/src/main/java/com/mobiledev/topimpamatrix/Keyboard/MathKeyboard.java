package com.mobiledev.topimpamatrix.Keyboard;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.mobiledev.topimpamatrix.R;

/**
 * Created by maiaphoebedylansamerjan on 4/13/16.
 */
public class MathKeyboard extends Keyboard {

    private Key mEnterKey;
    private Key mSpaceKey;

    private Key mModeChangeKey;

    private Key mLanguageSwitchKey;

    private Key mSavedModeChangeKey;

    public MathKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    private Key mSavedLanguageSwitchKey;

    private MathKeyboardView mKeyboardView;
    private Activity mHostActivity;

    public MathKeyboard(Context context, int layoutTemplateResId,
                        CharSequence characters, int columns, int horizontalPadding) {
        super(context, layoutTemplateResId, characters, columns, horizontalPadding);
    }


    public MathKeyboard(Activity host, int viewid, int layoutid) {
        super(host, viewid, layoutid);
        mHostActivity = host;
        mKeyboardView = (MathKeyboardView) mHostActivity.findViewById(viewid);
        mKeyboardView.setKeyboard(new Keyboard(mHostActivity, layoutid));
        mKeyboardView.setPreviewEnabled(false); // NOTE Do not show the preview balloons
        mKeyboardView.setOnKeyboardActionListener(new SimpleIME());
        // Hide the standard keyboard initially
        mHostActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    @Override
    protected Key createKeyFromXml(Resources res, Row parent, int x, int y,
                                   XmlResourceParser parser) {
        Key key = new MathKey(res, parent, x, y, parser);
        if (key.codes[0] == 10) {
            mEnterKey = key;
        } else if (key.codes[0] == ' ') {
            mSpaceKey = key;
        } else if (key.codes[0] == Keyboard.KEYCODE_MODE_CHANGE) {
            mModeChangeKey = key;
            mSavedModeChangeKey = new MathKey(res, parent, x, y, parser);
        } else if (key.codes[0] == MathKeyboardView.KEYCODE_LANGUAGE_SWITCH) {
            mLanguageSwitchKey = key;
            mSavedLanguageSwitchKey = new MathKey(res, parent, x, y, parser);
        }
        return key;
    }


    void setLanguageSwitchKeyVisibility(boolean visible) {
        if (visible) {

            mModeChangeKey.width = mSavedModeChangeKey.width;
            mModeChangeKey.x = mSavedModeChangeKey.x;
            mLanguageSwitchKey.width = mSavedLanguageSwitchKey.width;
            mLanguageSwitchKey.icon = mSavedLanguageSwitchKey.icon;
            mLanguageSwitchKey.iconPreview = mSavedLanguageSwitchKey.iconPreview;
        } else {

            mModeChangeKey.width = mSavedModeChangeKey.width + mSavedLanguageSwitchKey.width;
            mLanguageSwitchKey.width = 0;
            mLanguageSwitchKey.icon = null;
            mLanguageSwitchKey.iconPreview = null;
        }
    }

    void setImeOptions(Resources res, int options) {
        if (mEnterKey == null) {
            return;
        }
        switch (options & (EditorInfo.IME_MASK_ACTION | EditorInfo.IME_FLAG_NO_ENTER_ACTION)) {
            case EditorInfo.IME_ACTION_DONE:
                mEnterKey.iconPreview = null;
                mEnterKey.icon = null;
                mEnterKey.label = res.getText(R.string.label_done_key);
                break;
            case EditorInfo.IME_ACTION_NEXT:
                mEnterKey.iconPreview = null;
                mEnterKey.icon = null;
                mEnterKey.label = (CharSequence) res.getDrawable(R.drawable.keyboard_arrow_right);
                mEnterKey.label = (CharSequence) res.getDrawable(R.drawable.keyboard_arrow_left);
                mEnterKey.label = (CharSequence) res.getDrawable(R.drawable.keyboard_arrow_down);
                mEnterKey.label = (CharSequence) res.getDrawable(R.drawable.keyboard_arrow_up);
                break;
        }
    }

    public boolean isMathKeyboardVisible() {
        return mKeyboardView.getVisibility() == View.VISIBLE;
    }

    public void onShowMathKeyboard(View v) {
        mKeyboardView.setVisibility(View.VISIBLE);
        mKeyboardView.setEnabled(true);
        if (v != null)
            ((InputMethodManager) mHostActivity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void hideMathKeyboard() {
        mKeyboardView.setVisibility(View.GONE);
        mKeyboardView.setEnabled(false);
    }

    public void registerEditText(int resid) {
        EditText edittext = (EditText) mHostActivity.findViewById(resid);
        edittext.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) onShowMathKeyboard(v);
                else hideMathKeyboard();
            }
        });
        edittext.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onShowMathKeyboard(v);
            }
        });

        edittext.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                edittext.setInputType(inType);              // Restore input type
                return true; // Consume touch event
            }
        });
        edittext.setInputType(edittext.getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }

    public void setSpaceIcon(final Drawable icon) {
        if (mSpaceKey != null) {
            mSpaceKey.icon = icon;
        }
    }

    static class MathKey extends Keyboard.Key {

        public MathKey(Resources res, Keyboard.Row parent, int x, int y,
                       XmlResourceParser parser) {
            super(res, parent, x, y, parser);
        }

        @Override
        public boolean isInside(int x, int y) {
            return super.isInside(x, codes[0] == KEYCODE_CANCEL ? y - 10 : y);
        }
    }

}
