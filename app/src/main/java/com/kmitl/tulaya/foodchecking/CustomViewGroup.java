package com.kmitl.tulaya.foodchecking;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by phatipan on 19/4/2018 AD.
 */

public class CustomViewGroup extends BaseAdapter {

    Activity activity;
    List<UserModel> users;
    LayoutInflater inflater;

    //short to create constructer using command+n for mac & Alt+Insert for window


    public CustomViewGroup(Activity activity) {
        this.activity = activity;
    }

    public CustomViewGroup(Activity activity, List<UserModel> users) {
        this.activity = activity;
        this.users = users;

        inflater = activity.getLayoutInflater();
    }


    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        if (view == null) {

            view = inflater.inflate(R.layout.check_layout, viewGroup, false);

            holder = new ViewHolder();

            holder.tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
            holder.ivCheckBox = (ImageView) view.findViewById(R.id.iv_check_box);

            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        UserModel model = users.get(i);

        holder.tvUserName.setText(model.getUserName());

        if (model.isSelected())
            holder.ivCheckBox.setBackgroundResource(R.drawable.checked);

        else
            holder.ivCheckBox.setBackgroundResource(R.drawable.check);

        return view;

    }

    public void updateRecords(List<UserModel> users) {
        this.users = users;

        notifyDataSetChanged();
    }

    class ViewHolder {

        TextView tvUserName;
        ImageView ivCheckBox;

    }
}
