package aaa.bbb.ccc09;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Random;

public class RouletteActivity extends AppCompatActivity {

    EditText etNumber;
    Button btn_twist;
    int SLOTS_COUNT = 38;
    CardView roulette;
    Spinner spColor;
    Spinner spParity;
    RadioGroup rg;
    LinearLayout llEtNumber;
    LinearLayout llSpColor;
    LinearLayout llSpParity;
    String colorStr;
    String parityStr;
    ImageView ivInfo;
    String[] value= {
            "black", "red" };
    String[] valueTwo = { "even", "odd" };
    int[] mass = {0, 2, 14, 35, 23, 4, 16, 33, 21, 6, 18, 31, 19, 8, 12, 29, 25, 10, 27, 0, 1, 13, 36, 24, 3, 15, 34,
    22, 5, 17, 32, 20, 7, 11, 30, 26, 9, 28};
    String[] color = {"green", "black", "red", "black", "red", "black", "red", "black", "red", "black", "red", "black", "red",
            "black", "red", "black", "red" ,"black", "red", "green", "black", "red", "black", "red", "black", "red", "black", "red",
            "black", "red", "black", "red", "black", "red", "black", "red", "black", "red"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette);

        btn_twist = (Button) findViewById(R.id.btn_twist);
        roulette = (CardView) findViewById(R.id.roulette);
        etNumber = (EditText) findViewById(R.id.et_number);
        llEtNumber = (LinearLayout) findViewById(R.id.ll_et_number);
        llSpColor = (LinearLayout) findViewById(R.id.ll_sp_color);
        llSpParity = (LinearLayout) findViewById(R.id.ll_sp_parity);
        ivInfo = (ImageView) findViewById(R.id.iv_info);
        etNumber.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        spColor= (Spinner) findViewById(R.id.spColor);
        spParity= (Spinner) findViewById(R.id.spParity);
        rg = (RadioGroup) findViewById(R.id.rg);
        btn_twist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinRoulette();
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, value);
        spColor.setAdapter(adapter);
        spColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                colorStr = item;

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, valueTwo);
        spParity.setAdapter(adapter1);
        spParity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                parityStr = item;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        switch (rg.getCheckedRadioButtonId()) {
            case R.id.rb_one:
                break;
            case R.id.rb_two:
                break;
            case R.id.rb_three:
                break;
            default:
                break;
        }

        ivInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RouletteActivity.this);
                builder.setCancelable(true);
                builder.setMessage(Html.fromHtml("<font color='#808080'>" + getResources().getString(R.string.rules) + "</font>"));
                builder.setPositiveButton(Html.fromHtml("<font color='#4990e2'>" + getResources().getString(R.string.ok) + "</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

    }

    private void spinRoulette() {


        int corner = 360 / SLOTS_COUNT;
        int randPosition = corner * new Random().nextInt(SLOTS_COUNT);
        int MIN = 5;
        int MAX = 9;
        long TIME_IN_WHEEL = 1000;
        int randRotation = MIN + new Random().nextInt(MAX - MIN);
        int truePosition = randRotation * 360 + randPosition;
        long totalTime = TIME_IN_WHEEL * randRotation + (TIME_IN_WHEEL / 360) * randPosition;
        Log.d("myLogs", "randomPosition: " + randPosition + " randRotation: " + randRotation + "totalTime: " + totalTime + " truePosition: " + truePosition);
        final int value = (randPosition * 38) / 360;
        final int result = mass[value];
        final String str_result = String.valueOf(result);
        final String parity;
        int resultParity = result%2;
        if(resultParity == 0) {
            parity = "even";
        } else {
            parity = "odd";
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(roulette, "rotation", 0f, (float) truePosition);
        animator.setDuration(totalTime);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                switch (rg.getCheckedRadioButtonId()) {
                    case R.id.rb_one:
                        if(!etNumber.getText().toString().isEmpty()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RouletteActivity.this);
                            builder.setCancelable(true);
                            if(str_result.equals(etNumber.getText().toString())) {
                                builder.setMessage(Html.fromHtml("<font color='#808080'>" + getResources().getString(R.string.congratulations) + "</font>"));
                            } else {
                                builder.setMessage(Html.fromHtml("<font color='#808080'>" + getResources().getString(R.string.lost) + " " + str_result + "</font>"));
                            }
                            builder.setPositiveButton(Html.fromHtml("<font color='#4990e2'>" + getResources().getString(R.string.ok) + "</font>"), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.setNegativeButton(Html.fromHtml("<font color='#4990e2'>" + getResources().getString(R.string.cancel)+ "</font>"), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        }
                        break;
                    case R.id.rb_two:
                            AlertDialog.Builder builder = new AlertDialog.Builder(RouletteActivity.this);
                            builder.setCancelable(true);
                            if(colorStr.equals(color[value])) {
                                builder.setMessage(Html.fromHtml("<font color='#808080'>" + getResources().getString(R.string.congratulations) + "</font>"));
                            } else {
                                builder.setMessage(Html.fromHtml("<font color='#808080'>" + getResources().getString(R.string.lost) + " " + color[value] + "</font>"));
                            }
                            builder.setPositiveButton(Html.fromHtml("<font color='#4990e2'>" + getResources().getString(R.string.ok) + "</font>"), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.setNegativeButton(Html.fromHtml("<font color='#4990e2'>" + getResources().getString(R.string.cancel)+ "</font>"), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        break;
                    case R.id.rb_three:
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(RouletteActivity.this);
                        builder1.setCancelable(true);
                        if(parityStr.equals(parity)) {
                            builder1.setMessage(Html.fromHtml("<font color='#808080'>" + getResources().getString(R.string.congratulations) + "</font>"));
                        } else {
                            builder1.setMessage(Html.fromHtml("<font color='#808080'>" + getResources().getString(R.string.lost) + " " + parity + "</font>"));
                        }
                        builder1.setPositiveButton(Html.fromHtml("<font color='#4990e2'>" + getResources().getString(R.string.ok) + "</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder1.setNegativeButton(Html.fromHtml("<font color='#4990e2'>" + getResources().getString(R.string.cancel)+ "</font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder1.show();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

}
