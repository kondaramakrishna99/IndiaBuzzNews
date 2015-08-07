package com.example.rama.IndiaBuzzNews;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class secondActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        int sum = getIntent().getIntExtra("sum",-1);
        TextView tv_sum = (TextView)findViewById(R.id.textView_sum);
        //tv_sum.setText("Result add : "+sum);

        final String[] newsPapers = {"TheHindu","TimesOfIndia","LiveMint","BusinessChronicle","ET","Outlook"};
        /*final String[] newsPaperList = {"The Hindu", "Times Of India",
                                        "DD News",
        "Live Mint",
                "Economic Times",
                "Business Standard",
                "Indian Express",
                "CNNIBN",
                "DNA",
                "NDTV",
                "BBC"
        };*/
        final String[] newsPaperList = {"The Hindu", "Times Of India",
                "DD News",
                "Live Mint",
                "Economic Times",
                "Indian Express",
                "DNA",
                "NDTV",
                "BBC"
        };
        String[] newsDescription = {"Hindu","TOI","Mint","BC","ET","OT"};
        int images[] = {R.drawable.hindu,R.drawable.toi,R.drawable.mint,R.drawable.businessstandard,R.drawable.economictimes,R.drawable.outlook};

        final ListView lv_news = (ListView)findViewById(R.id.listView_newsPapers);

       ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,newsPaperList);

        //NewsAdapter adapter = new NewsAdapter(this,images,newsPapers,newsDescription);

        lv_news.setAdapter(adapter1);
        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = lv_news.getItemAtPosition(position).toString();
                Toast.makeText(secondActivity.this, item+"",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(secondActivity.this,FourthActivity_Threading.class);
                i.putExtra("newsPaper",newsPaperList[position]);
                startActivity(i);
            }
        });



    }

class NewsAdapter extends ArrayAdapter
{
    Context c;
    int[] images;
    String[] newsPapers;
    String[] newsDescriptions;
    NewsAdapter(Context con, int[] imgs, String[] news, String[] descript)
    {
        super(con,R.layout.layout_row,R.id.textView_row,news);
        this.c = con;
        this.newsDescriptions = descript;
        this.newsPapers = news;
        images=imgs;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(R.layout.layout_row,parent,false);
        ImageView img = (ImageView)row.findViewById(R.id.imageView);
        TextView textView_news = (TextView)row.findViewById(R.id.textView_row);
        TextView textView_descr =(TextView)row.findViewById(R.id.textView_description);

        img.setImageResource(images[position]);
        textView_news.setText(newsPapers[position]);
        textView_descr.setText(newsDescriptions[position]);

        return row;
    }
}






}
