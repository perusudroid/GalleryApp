package com.perusudroid.galleryapp.common;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.perusudroid.galleryapp.R;


public class ViewSwitcher extends android.widget.ViewSwitcher {
    public ViewSwitcher(Context paramContext) {
        super(paramContext);
        init();
    }

    public ViewSwitcher(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    private void init() {
        // Declare in and out animations and load them using AnimationUtils class
        setInAnimation(getContext(), R.anim.fast_fade_in);
        setOutAnimation(getContext(), R.anim.fast_fade_out);
    }

    public void setChildVisible() {
        Log.d("PreziViewSwitcher", "setChildVisible1: getDisplayedChild() "+ getDisplayedChild());

        if (getDisplayedChild() != 1) {
            setDisplayedChild(1);
        }
    }

    public void setParentVisible() {
        Log.d("PreziViewSwitcher", "setChildVisible2: getDisplayedChild() "+ getDisplayedChild());
        if (getDisplayedChild() != 0) {
            setDisplayedChild(0);
        }
    }
}



/* Location:           D:\ExtractApkHere\Prezi\Jar\classes2.jar

 * Qualified Name:     com.prezi.android.folders.view.PreziViewFlipper

 * JD-Core Version:    0.7.0.1

 */