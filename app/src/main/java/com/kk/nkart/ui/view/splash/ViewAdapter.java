package com.kk.nkart.ui.view.splash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.kk.nkart.R;

import org.jetbrains.annotations.NotNull;

class ViewAdapter extends PagerAdapter {
    private android.view.LayoutInflater layoutInflater;

    public ViewAdapter(Context context) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object view) {
        container.removeView((View) view);
    }

    @NotNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        View layout = layoutInflater.inflate(R.layout.view_splash_pager, container, false);
        Button button = new Button(container.getContext());
        button.setText("asda ");
        container.addView(button);
        return button;
    }
}
