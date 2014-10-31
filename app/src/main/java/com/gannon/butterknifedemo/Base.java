package com.gannon.butterknifedemo;

import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;

/**
 * Activity Base
 *
 * @author Gannon McGibbon
 */
public abstract class Base extends Activity
{
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);

        // auto inject after content view is set

        ButterKnife.inject(this);
    }
}
