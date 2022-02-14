package com.kk.nkart.base.core;

import com.kk.nkart.ui.view.adapter.ProductItemAdapter;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;

class A {

    void streamToString(InputStream stream){
        try {
            byte buffer[] = new byte[stream.available()];

            String data = new String(buffer);
        } catch (Exception e) {
        }
    }
}
