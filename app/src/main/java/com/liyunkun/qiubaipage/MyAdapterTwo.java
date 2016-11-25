package com.liyunkun.qiubaipage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by liyunkun on 2016/9/3 0003.
 */
public class MyAdapterTwo extends BaseAdapter{
    private List<OtherUser> list;
    private Context context;
    private LayoutInflater inflater;

    public MyAdapterTwo(List<OtherUser> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_other_layout,parent,false);
            holder=new ViewHolder();
            holder.iv= (ImageView) convertView.findViewById(R.id.iv);
            holder.tvName= (TextView) convertView.findViewById(R.id.tv);
            holder.tvContent= (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        OtherUser otherUser = list.get(position);
        holder.tvName.setText(otherUser.getName());
        holder.tvContent.setText(otherUser.getContent());
        Picasso.with(context).load(otherUser.getImg()).into(holder.iv);
        return convertView;
    }
    private class ViewHolder{
        ImageView iv;
        TextView tvName;
        TextView tvContent;
    }
}
