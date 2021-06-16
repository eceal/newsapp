package com.example.apcentece;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apcentece.adapter.DownloadImageFromInternet;
import com.example.apcentece.adapter.NewsListAdapter;
import com.example.apcentece.model.Articles;
import com.example.apcentece.util.GenericUtils;

public class DetailActivity extends AppCompatActivity {

    Button shareBtn, browserBtn;
    TextView title, detail;
    ImageView image;
    Articles article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();
        getPageData();
        fillPage();
    }

    private void initViews(){

    shareBtn = findViewById(R.id.share_btn);
    title = findViewById(R.id.article_title_tv);
    detail = findViewById(R.id.article_tv);
    image = findViewById(R.id.article_img);
    browserBtn = findViewById(R.id.open_btn);

    shareBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GenericUtils.share(DetailActivity.this, article.getUrl());
        }
    });

    browserBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("articleURL", article.getUrl());
            Intent intent = new Intent(DetailActivity.this, WebViewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    });
    }

    private void getPageData(){
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null && intent.getExtras().containsKey("articleBody")){
            article = (Articles) intent.getExtras().getSerializable("articleBody");
        }
    }

    private void fillPage(){
        if(article != null){
            title.setText(article.getTitle());
            detail.setText(article.getDescription());
            new DownloadImageFromInternet(image).execute(article.getUrlToImage());

        }else{
            finish();
        }
    }
}
