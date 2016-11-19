# MagicDialog
Magic dialog, a common dialog for adapter all situation, you only need to customize layout and logic.

#Demonstration

[![IMAGE ALT TEXT](https://github.com/Yanqilong/MagicDialog/blob/master/ScreenShot/Screenshot_2016-11-19-19-24-28-493_com.migo.magic.png)](https://github.com/Yanqilong/MagicDialog/blob/master/ScreenShot/ScreenRecord_2016-11-19-19-15-27.mp4 "Video Title")

# How to use
```sh
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

                        magicDialog.setOnClickListener(R.id.btn_click_me, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "默认弹窗一", Toast.LENGTH_SHORT).show();
                                magicDialog.dismiss();
                            }
                        });
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

                        magicDialog.setOnClickListener(R.id.btn_click_me, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "弹窗二", Toast.LENGTH_SHORT).show();
                                magicDialog.dismiss();
                            }
                        });
                    }
                })
                .build();
```


#Customizable methods
| method        | explain       |
| ------------------------------- |:--------------------:|
| setWidth(float width)           | width                |
| setHeight(float height)         | height               |
| setGravity(int gravity)         | gravity              |
| setDimAmount(float dimAmount)   | dimAmount            |
| setAlpha(float alpha)           | alpha                |
| setRelativeOffsetX(int relativeOffsetX)                    | relativeOffsetX            |
| setRelativeOffsetY(int relativeOffsetY)           | relativeOffsetY                |
| setBackgroundDrawableResource(int backgroundDrawableResource)                      | backgroundDrawableResource              |
| etDialogDelegate(DialogDelegate dialogDelegate)                    | dialog delegate            |
