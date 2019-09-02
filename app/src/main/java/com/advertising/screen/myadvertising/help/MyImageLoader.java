package com.advertising.screen.myadvertising.help;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * 作者：罗发新
 * 时间：2019/9/2 0002    星期一
 * 邮件：424533553@qq.com
 * 说明：图片加载类
 */
public class MyImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context.getApplicationContext()).load(path).into(imageView);
//            //Glide 加载图片简单用法
//            Glide.with(context).load(path).into(imageView);
    }
}
