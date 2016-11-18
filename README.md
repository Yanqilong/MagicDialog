# MagicDialog
Magic dialog, a common dialog for adapter all situation, you only need to customize layout and logic.

### How to use
```sh
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
                        //Listener to click
                        magicDialog.setOnClickListener(R.id.btn_click_me, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "Hello again Wave", Toast.LENGTH_SHORT).show();
                                magicDialog.dismiss();
                            }
                        });
                    }
                })
                .build();
```



