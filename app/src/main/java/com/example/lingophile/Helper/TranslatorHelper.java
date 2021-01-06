package com.example.lingophile.Helper;

import androidx.annotation.NonNull;

import com.example.lingophile.Database.DataCenter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;

public class TranslatorHelper {
    DataCenter dc = DataCenter.getInstance();

    public static TranslatorHelper getInstance() {
        return TranslatorHelper.SingletonHolder.INSTANCE;
    }

    public void downloadTranslator(final TranslateListener translateListener) {

        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();

        dc.getEnglishToVietnameseTranslator().downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
    }

    public void translateText(String text, final TranslateListener listener) {
        dc.getEnglishToVietnameseTranslator().translate(text)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                listener.onListenTranslateSuccess(translatedText);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                listener.onFail();
                            }
                        });
    }

    private static class SingletonHolder {
        private static final TranslatorHelper INSTANCE = new TranslatorHelper();
    }
}
