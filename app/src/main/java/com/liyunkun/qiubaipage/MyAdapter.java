package com.liyunkun.qiubaipage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.lenve.customshapeimageview.CustomShapeImageView;

import java.util.List;

/**
 * Created by liyunkun on 2016/9/3 0003.
 */
public class MyAdapter extends BaseAdapter{

    private Context context;
    private List<User> list;
    private List<Boolean> isGood;
    private List<Boolean> isNoGood;
    private List<Boolean> isComments;
    private List<Boolean> isShareMore;
    private LayoutInflater inflater;

    public MyAdapter(Context context, List<User> list, List<Boolean> isGood, List<Boolean> isNoGood, List<Boolean> isComments, List<Boolean> isShareMore) {
        this.context = context;
        this.list = list;
        this.isGood = isGood;
        this.isNoGood = isNoGood;
        this.isComments = isComments;
        this.isShareMore = isShareMore;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_layout,parent,false);
            holder =new ViewHolder();
            holder.ivUserImg= (CustomShapeImageView) convertView.findViewById(R.id.iv_userImg);
            holder.ivComments= (ImageView) convertView.findViewById(R.id.iv_comments);
            holder.ivTypeImg= (ImageView) convertView.findViewById(R.id.iv_type);
            holder.ivContentImg= (ImageView) convertView.findViewById(R.id.iv_contentImg);
            holder.ivGood= (ImageView) convertView.findViewById(R.id.iv_good);
            holder.ivNoGood= (ImageView) convertView.findViewById(R.id.iv_nogood);
            holder.ivShareMore= (ImageView) convertView.findViewById(R.id.iv_share_more);
            holder.tvUserName= (TextView) convertView.findViewById(R.id.tv_userName);
            holder.tvType= (TextView) convertView.findViewById(R.id.tv_type);
            holder.tvContent= (TextView) convertView.findViewById(R.id.tv_content);
            holder.tvLike= (TextView) convertView.findViewById(R.id.tv_like);
            holder.tvComments= (TextView) convertView.findViewById(R.id.tv_comments);
            holder.tvShare= (TextView) convertView.findViewById(R.id.tv_share);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        //获取一个User对象
        final User user = list.get(position);
        //设置用户名
        holder.tvUserName.setText(user.getUserName());
        //设置类型
        if (user.getType()!=null) {
            if(user.getType().equals("hot")){
                holder.ivTypeImg.setImageResource(R.drawable.jingxuan);
                holder.tvType.setText("热门");
            }else{
                holder.ivTypeImg.setVisibility(View.GONE);
                holder.tvType.setVisibility(View.GONE);
            }
        }else{
            holder.ivTypeImg.setVisibility(View.GONE);
            holder.tvType.setVisibility(View.GONE);
        }
        //设置文本
        holder.tvContent.setText(user.getContent());
        //设置好笑
        holder.tvLike.setText(String.valueOf((user.getUp()+user.getDown())));
        //设置分享
        holder.tvShare.setText(user.getShare());
        //设置评论
        holder.tvComments.setText(user.getComments_count());
        //设置用户图片
        if(user.getUserImg().equals("")){
            holder.ivUserImg.setImageResource(R.drawable.jingxuan);
        }else{
            Picasso.with(context).load(user.getUserImg()).into(holder.ivUserImg);
        }
        //设置是否有内容图片
        if(!user.getContentImg().equals("")){
            holder.ivContentImg.setVisibility(View.VISIBLE);
            Picasso.with(context).load(user.getContentImg()).into(holder.ivContentImg);
            holder.ivContentImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,ImageActivity.class);
                    intent.putExtra("img",user.getContentImg());
                    context.startActivity(intent);
                }
            });
        }
        //Good的点击事件
        holder.ivGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isGood.set(position,true);
                notifyDataSetChanged();
            }

        });
        //NoGood的点击事件
        holder.ivNoGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNoGood.set(position,true);
                notifyDataSetChanged();
            }
        });
        //评论的点击事件
        holder.ivComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isComments.set(position,true);
            }
        });

        //链接的点击事件
        holder.ivShareMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               isShareMore.set(position,true);
                notifyDataSetChanged();
            }
        });
        if(isGood.get(position)){
            holder.ivGood.setImageResource(R.drawable.good_press);
            holder.ivNoGood.setEnabled(false);
            holder.ivComments.setEnabled(false);
        }else{
            holder.ivNoGood.setEnabled(true);
            holder.ivComments.setEnabled(true);
            holder.ivGood.setImageResource(R.drawable.good);
        }
        if(isNoGood.get(position)){
            holder.ivNoGood.setImageResource(R.drawable.nogood_press);
            holder.ivComments.setEnabled(false);
            holder.ivGood.setEnabled(false);
        }else {
            holder.ivGood.setEnabled(true);
            holder.ivComments.setEnabled(true);
            holder.ivNoGood.setImageResource(R.drawable.nogood);
        }
        if(isComments.get(position)){
            holder.ivComments.setImageResource(R.drawable.night);
            holder.ivGood.setEnabled(false);
            holder.ivNoGood.setEnabled(false);
        }else{
            holder.ivNoGood.setEnabled(true);
            holder.ivGood.setEnabled(true);
            holder.ivComments.setImageResource(R.drawable.comments);
        }
        if(isShareMore.get(position)){
            holder.ivShareMore.setImageResource(R.drawable.share_more_night);
        }else{
            holder.ivShareMore.setImageResource(R.drawable.share_more);
        }
        return convertView;
    }
    private class ViewHolder{
        CustomShapeImageView ivUserImg;//用户头像
        ImageView ivTypeImg;//类型图片
        ImageView ivContentImg;//内容图片
        ImageView ivGood;//好评图片
        ImageView ivNoGood;//差评图片
        ImageView ivComments;//评论图片
        ImageView ivShareMore;//链接图片
        TextView tvUserName;//用户名
        TextView tvType;//类型
        TextView tvContent;//内容
        TextView tvLike;//好笑
        TextView tvComments;//评论
        TextView tvShare;//分享
    }
}
