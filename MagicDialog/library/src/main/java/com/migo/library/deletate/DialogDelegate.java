package com.migo.library.deletate;

import com.migo.library.MagicDialog;

/**
 * Author：wave
 * Date：2016/10/17
 * Description：
 */

public interface DialogDelegate {

    int getDialogViewLayoutId();

    void onCreate(MagicDialog magicDialog);

    void onStart();
}
