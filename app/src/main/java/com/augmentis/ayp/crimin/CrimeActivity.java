package com.augmentis.ayp.crimin;

import android.support.v4.app.Fragment;

public class CrimeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment onCreateFragment() {
        return new CrimeFragment();
    }
}
