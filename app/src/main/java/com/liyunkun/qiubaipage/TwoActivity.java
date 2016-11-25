package com.liyunkun.qiubaipage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lenve.customshapeimageview.CustomShapeImageView;

import java.util.ArrayList;
import java.util.List;

public class TwoActivity extends AppCompatActivity {
    private CustomShapeImageView ivUserImg;
    private ImageView ivComments;
    private ImageView ivTypeImg;
    private ImageView ivContentImg;
    private ImageView ivGood;
    private ImageView ivNoGood;
    private ImageView ivShareMore;
    private TextView tvUserName;
    private TextView tvType;
    private TextView tvContent;
    private TextView tvLike;
    private TextView tvComments;
    private TextView tvShare;
    private ListView lvTwo;
    private List<OtherUser> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        findView();
        Intent intent=getIntent();
        User user= (User) intent.getSerializableExtra("user");
        String id=user.getUserId();
        setTop(user);
        String urlPath=String.format("http://m2.qiushibaike.com/article/%s/comments?page=1&count=50&rqcnt=19&r=d0dc8ad41456830331669",id);
        MyTask task=new MyTask();
        task.execute(urlPath);
    }
    private class MyTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            return HttpUtils.getJSONString(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            getData(s);
            MyAdapterTwo adapterTwo=new MyAdapterTwo(arr,TwoActivity.this);
            lvTwo.setAdapter(adapterTwo);
        }
    }
    private void getData(String json){
        arr=new ArrayList<>();
        try {
            JSONArray jsonArray=new JSONObject(json).optJSONArray("items");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject items=jsonArray.optJSONObject(i);
                String content=items.optString("content");
                JSONObject users=items.optJSONObject("user");
                String id=users.optString("id");
                String icon=users.optString("icon");
                String login=users.optString("login");
                String idTop=id.substring(0,id.length()-4);
                String otherImg=String.format("http://pic.qiushibaike.com/system/avtnew/%s/%s/thumb/%s",idTop,id,icon);
                arr.add(new OtherUser(otherImg,content,login));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void setTop(User user){
        //设置用户名
        tvUserName.setText(user.getUserName());
        //设置类型
        if (user.getType()!=null) {
            if(user.getType().equals("hot")){
                ivTypeImg.setImageResource(R.drawable.jingxuan);
                tvType.setText("热门");
            }else{
                ivTypeImg.setVisibility(View.GONE);
                tvType.setVisibility(View.GONE);
            }
        }else{
            ivTypeImg.setVisibility(View.GONE);
            tvType.setVisibility(View.GONE);
        }
        //设置文本
        tvContent.setText(user.getContent());
        //设置好笑
        tvLike.setText(String.valueOf((user.getUp()+user.getDown())));
        //设置分享
        tvShare.setText(user.getShare());
        //设置评论
        tvComments.setText(user.getComments_count());
        //设置用户图片
        if(user.getUserImg().equals("")){
            ivUserImg.setImageResource(R.drawable.jingxuan);
        }else{
            Picasso.with(this).load(user.getUserImg()).into(ivUserImg);
        }
        //设置是否有内容图片
        if(!(user.getContentImg().equals(""))){
            ivContentImg.setVisibility(View.VISIBLE);
            Picasso.with(this).load(user.getContentImg()).into(ivContentImg);
        }
    }
    private void findView(){
        ivUserImg= (CustomShapeImageView) findViewById(R.id.iv_userImg);
        ivComments= (ImageView)findViewById(R.id.iv_comments);
        ivTypeImg= (ImageView)findViewById(R.id.iv_type);
        ivContentImg= (ImageView)findViewById(R.id.iv_contentImg);
        ivGood= (ImageView)findViewById(R.id.iv_good);
        ivNoGood= (ImageView)findViewById(R.id.iv_nogood);
        ivShareMore= (ImageView)findViewById(R.id.iv_share_more);
        tvUserName= (TextView)findViewById(R.id.tv_userName);
        tvType= (TextView)findViewById(R.id.tv_type);
        tvContent= (TextView)findViewById(R.id.tv_content);
        tvLike= (TextView)findViewById(R.id.tv_like);
        tvComments= (TextView)findViewById(R.id.tv_comments);
        tvShare= (TextView)findViewById(R.id.tv_share);
        lvTwo= (ListView) findViewById(R.id.lv_two);
    }
}
