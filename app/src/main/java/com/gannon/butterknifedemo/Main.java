package com.gannon.butterknifedemo;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnLongClick;


/**
 * Main Activity
 *
 * More on ButterKnife here: https://github.com/JakeWharton/butterknife
 *
 * @see com.gannon.butterknifedemo.Base
 *
 * @author Gannon McGibbon
 */
public class Main extends Base
    implements SeekBar.OnSeekBarChangeListener
{
    private int counter;

    @InjectView(R.id.click_button)  // say goodbye to findViewById!
    protected Button clickButton;

    @InjectView(R.id.color_spinner) // set this spinner to specified ID on inject
    protected Spinner colorSpinner;

    @InjectView(R.id.number_seek)   // you may also use @InjectViews() for view collections
    protected SeekBar numberSeek;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState); // super injects here ***

        // populate spinner

        String[] colors = getResources().getStringArray(R.array.colors);

        colorSpinner.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, colors
        ));

        // set injected seek bar listener

        numberSeek.setOnSeekBarChangeListener(this);
    }

    // handlers

    @OnClick(R.id.hello_text)
    protected void helloTextLongClick() // all you need is a view ID to add a listener
    {
        String text = getResources().getString(R.string.hello_text);

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.click_button)
    protected void cickButtonClicked() // names of these methods do not matter, annotations do all the work
    {
        String plural = counter != 0 ? "s" : ""; // 0 to account for pre increment

        String text   = String.format("Clicked %d time%s!", ++counter, plural);

        clickButton.setText(text);
    }

    @OnItemSelected(R.id.color_spinner)
    protected void colorSpinnerSelected() // the return type of handler methods must match the callback type
    {
        String text = String.format("%s is selected!", colorSpinner.getSelectedItem());

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @OnLongClick({ R.id.hello_text, R.id.click_button })
    protected boolean sharedViewLongClicked() // you can bind multiple IDs to a single handler
    {
        if (clickButton.isPressed())
        {
            clickButton.setText("Long Clicked!");
        }

        Toast.makeText(this, "Long Click Triggered", Toast.LENGTH_SHORT).show();

        return true;
    }

    // views with more complex listeners still need implemented interface methods
    // however, the views themselves are still injectable

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        clickButton.setText(String.valueOf(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {
        seekBar.setBackgroundColor(Color.parseColor(colorSpinner.getSelectedItem().toString()));
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {
        seekBar.setBackgroundColor(Color.TRANSPARENT);
    }
}
