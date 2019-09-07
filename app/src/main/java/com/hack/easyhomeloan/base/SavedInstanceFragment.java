package com.hack.easyhomeloan.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

/**
 * A neat trick to avoid TransactionTooLargeException while saving our instance state
 */

public class SavedInstanceFragment extends Fragment {

    private static final String TAG = "SavedInstanceFragment";
    private Bundle mInstanceBundle = null;

    public SavedInstanceFragment() { // This will only be called once be cause of setRetainInstance()
        super();
        setRetainInstance(true);
    }

    public static SavedInstanceFragment getInstance(FragmentManager fragmentManager) {
        SavedInstanceFragment out = (SavedInstanceFragment) fragmentManager.findFragmentByTag(TAG);

        if (out == null) {
            out = new SavedInstanceFragment();
            fragmentManager.beginTransaction().add(out, TAG).commit();
        }
        return out;
    }

    public void pushData(Bundle instanceState) {
        if (this.mInstanceBundle == null) {
            this.mInstanceBundle = instanceState;
        } else {
            this.mInstanceBundle.putAll(instanceState);
        }
    }

    public Bundle popData() {
        Bundle out = this.mInstanceBundle;
        this.mInstanceBundle = null;
        return out;
    }
}