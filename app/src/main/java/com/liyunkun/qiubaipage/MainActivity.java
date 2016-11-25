package com.liyunkun.qiubaipage;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView ivQiuBai;
    private List<User> userList;
    private List<User> usersList;
    private List<Boolean> isGood;
    private List<Boolean> isNoGood;
    private List<Boolean> isComments;
    private List<Boolean> isShareMore;
    private List<Boolean> isGoodes;
    private List<Boolean> isNoGoodes;
    private List<Boolean> isCommentses;
    private List<Boolean> isShareMorees;
    private Button bt;
    private int countPage=1;
    private int start;
    private int startTop;
    private SQLiteDatabase db;
    private String HTTPURL="http://m2.qiushibaike.com/article/list/suggest?" +
            "page=%d&type=refresh&count=30&readarticles=[115869149,115801176," +
            "115861770,115862983,115861264,115803971,115805607,115868733,115861796" +
            ",115861269,115810782,115853306,115804094,115807385,115805858,115810996" +
            ",115805223,115809747,115860087]&rqcnt=16&r=d0dc8ad41460029471175";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivQiuBai= (ListView) findViewById(R.id.lv_qiu_bai);
        bt= (Button) findViewById(R.id.bt_show);
        usersList=new ArrayList<>();
        isGoodes=new ArrayList<>();
        isNoGoodes=new ArrayList<>();
        isCommentses=new ArrayList<>();
        isShareMorees=new ArrayList<>();
        DBHelper dbHelper=new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        String urlPath=String.format(HTTPURL,countPage);
        MyTask task=new MyTask();
        task.execute(urlPath);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start=ivQiuBai.getFirstVisiblePosition();
                startTop=ivQiuBai.getChildAt(0).getTop();
                new MyTask().execute(String.format(HTTPURL,++countPage));
            }
        });
    }
    private class MyTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... params) {
            String json=HttpUtils.getJSONString(params[0]);
            return json;
        }
        @Override
        protected void onPostExecute(String s) {
            getData(s);
            usersList.addAll(userList);
            isGoodes.addAll(isGood);
            isNoGoodes.addAll(isNoGood);
            isShareMorees.addAll(isShareMore);
            isCommentses.addAll(isComments);
            MyAdapter adapter=new MyAdapter(MainActivity.this,usersList,isGoodes,isNoGoodes,isCommentses,isShareMorees);
            ivQiuBai.setAdapter(adapter);
            ivQiuBai.setSelectionFromTop(start,startTop);
            ivQiuBai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    User user2=usersList.get(position);
                    Intent intent=new Intent(MainActivity.this,TwoActivity.class);
                    intent.putExtra("user",user2);
                    startActivity(intent);
                }
            });
            ivQiuBai.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if((firstVisibleItem+visibleItemCount)==totalItemCount){
                        bt.setVisibility(View.VISIBLE);
                    }else{
                        bt.setVisibility(View.GONE);
                        ivQiuBai.requestLayout();
                    }
                }
            });
        }
        private void getData(String json){
            userList= new ArrayList<>();
            isGood=new ArrayList<>();
            isNoGood=new ArrayList<>();
            isComments=new ArrayList<>();
            isShareMore=new ArrayList<>();
            try {
                JSONArray items= new JSONObject(json).optJSONArray("items");
                for (int i = 0; i < items.length(); i++) {
                    JSONObject item=items.optJSONObject(i);
                    JSONObject votes=item.optJSONObject("votes");
                    String image=item.optString("image");
                    JSONObject user = null;
                    try {
                        user = item.optJSONObject("user");
                    } catch (Exception e) {
                        user=null;
                    }
                    String userImg="";
                    String userName="";
                    if(user!=null){
                        String icon=user.optString("icon");
                        String id=user.optString("id");
                        userName=user.optString("login");
                        String idTop=id.substring(0,id.length()-4);
                        userImg=String.format("http://pic.qiushibaike.com/system/avtnew/%s/%s/thumb/%s",idTop,id,icon);
                    }
                    String userId=item.optString("id");
                    String contentImg;
                    if(!image.equals("null")){
                        int count = image.lastIndexOf(".");
                        String imageTop=image.substring(0,count);
                        String num="";
                        for(int j=0;j<imageTop.length();j++){
                            char temp=imageTop.charAt(j);
                            if(temp>='0'&&temp<='9'){
                                num+=(String.valueOf(temp));
                            }
                        }
                        String numTop=num.substring(0,num.length()-4);
                        contentImg=String.format("http://pic.qiushibaike.com/system/pictures/%s/%s/%s/%s",numTop,num,"small",image);
                    }else{
                        contentImg="";
                    }
                    String content=item.optString("content");
                    String type= null;
                    if (item.has("type")) {
                        type = item.optString("type");
                    }
                    int up=Integer.parseInt(votes.optString("up"));
                    int down=Integer.parseInt(votes.optString("down"));
                    String comments_count=item.optString("comments_count");
                    String share=item.optString("share_count");
                    User users=new User(userId,userImg,userName,content,contentImg,type,up,down,comments_count,share);
                    putDataToSQLite(users);
                    userList.add(users);
                    isGood.add(false);
                    isNoGood.add(false);
                    isComments.add(false);
                    isShareMore.add(false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void putDataToSQLite(User users) {
        ContentValues values=new ContentValues();
        values.put("userid",users.getUserId());
        values.put("userimg",users.getUserImg());
        values.put("USERNAME",users.getUserName());
        values.put("CONTENT",users.getContent());
        values.put("CONTENTIMG",users.getContentImg());
        values.put("TYPE",users.getType());
        values.put("UP",users.getUp());
        values.put("down",users.getDown());
        values.put("COMMENTS_COUNT",users.getComments_count());
        values.put("SHARE",users.getShare());
        db.insert(DBHelper.TABLENAME,"userid",values);
    }
}
