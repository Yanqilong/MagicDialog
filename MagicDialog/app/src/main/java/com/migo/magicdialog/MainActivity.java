package com.migo.magicdialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.migo.library.MagicDialog;
import com.migo.library.deletate.DialogDelegate;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnDefaultDialog;

    private Button btnChangeDialog;

    private MagicDialog defaultMagicDialog;

    private MagicDialog changeMagicDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDefaultDialog = (Button) findViewById(R.id.btn_default_dialog);
        btnChangeDialog = (Button) findViewById(R.id.btn_change_dialog);
        btnDefaultDialog.setOnClickListener(this);
        btnChangeDialog.setOnClickListener(this);

        //default`s dialog
        defaultMagicDialog = new MagicDialog.Builder(this)
                .setDialogDelegate(new DialogDelegate() {
                    @Override
                    public int getDialogViewLayoutId() {
                        return R.layout.dialog_default;
                    }

                    @Override
                    public void onCreate(final MagicDialog magicDialog) {
                        //Change text color
                        magicDialog.setTextColorRes(R.id.btn_click_me, R.color.colorAccent);
                        //Change text size
                        magicDialog.setTextSize(R.id.btn_click_me, 16);

                        magicDialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(v.getId() == R.id.btn_click_me) {
                                    Toast.makeText(MainActivity.this, "默认弹窗一", Toast.LENGTH_SHORT).show();
                                    magicDialog.dismiss();
                                }
                            }
                        }, R.id.btn_click_me);
                    }

                    @Override
                    public void onStart() {

                    }
                })
                .build();


        //change attribute`s dialog
        changeMagicDialog = new MagicDialog.Builder(this)
                .setWidth(0.7f)
                .setBackgroundDrawableResource(R.drawable.border_corner_pink)
                .setGravity(Gravity.BOTTOM)
                .setRelativeOffsetY(150)
                .setDialogDelegate(new DialogDelegate() {
                    @Override
                    public int getDialogViewLayoutId() {
                        return R.layout.dialog_default;
                    }

                    @Override
                    public void onCreate(final MagicDialog magicDialog) {
                        //Change text color
                        magicDialog.setTextColorRes(R.id.btn_click_me, R.color.colorPrimary);
                        //Change text size
                        magicDialog.setTextSize(R.id.btn_click_me, 16);

                        magicDialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(v.getId() == R.id.btn_click_me) {
                                    Toast.makeText(MainActivity.this, "弹窗二", Toast.LENGTH_SHORT).show();
                                    magicDialog.dismiss();
                                }
                            }
                        }, R.id.btn_click_me);
                    }

                    @Override
                    public void onStart() {

                    }
                })
                .build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_default_dialog:
                defaultMagicDialog.show();
                break;
            case R.id.btn_change_dialog:
                changeMagicDialog.show();
                break;
        }
    }
}
