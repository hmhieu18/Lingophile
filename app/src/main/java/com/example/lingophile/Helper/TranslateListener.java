package com.example.lingophile.Helper;

public interface TranslateListener {
    void onStart();

    void onFinish();

    void onFail();

    void updateUI();

    void onListenTranslateSuccess(String str);
}
