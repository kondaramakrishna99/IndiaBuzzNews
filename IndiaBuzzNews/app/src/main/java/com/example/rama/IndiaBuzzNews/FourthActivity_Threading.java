package com.example.rama.IndiaBuzzNews;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class FourthActivity_Threading extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_activity__threading);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        MyHttpConnection threadConn=new MyHttpConnection();
        String theHindu = "http://www.thehindu.com/?service=rss";
        String liveMint = "http://www.livemint.com/rss/homepage";
        String timesOfIndia="http://timesofindia.feedsportal.com/c/33039/f/533965/index.rss";
        String ddNews="http://www.ddinews.gov.in/rssNews/Pages/rssnews.aspx";
        String economicTimes = "http://economictimes.indiatimes.com/rssfeedsdefault.cms";
        String businessStandard = "http://www.business-standard.com/rss/latest.rss";
        String indianExpress = "http://indianexpress.com/section/india/feed/";
        String cnnIBN="http://www.ibnlive.com/xml/top.xml";
        String dna = "http://www.dnaindia.com/syndication/rss_topnews.xml";
        String ndtv="http://feeds.feedburner.com/NDTV-LatestNews?format=xml";
        String bbc="http://feeds.bbci.co.uk/news/rss.xml?edition=int";

        String newsPaper = getIntent().getStringExtra("newsPaper");
        String url="";
        switch (newsPaper){
            case "The Hindu":
                url = theHindu;
                setTitle("The Hindu");
                break;
            case "Times Of India":
                url = timesOfIndia;
                setTitle("Times Of India");
                break;
            case "DD News":
                url = ddNews;
                setTitle("DD News");
                break;
            case "Live Mint":
                url = liveMint;
                setTitle("Live Mint");
                break;
            case "Economic Times":
                url = economicTimes;
                setTitle("Economic Times");
                break;
            case "Business Standard":
                url = businessStandard;
                setTitle("Business Standard");
                break;
            case "Indian Express":
                url = indianExpress;
                setTitle("Indian Express");
                break;
            case "CNNIBN":
                url = cnnIBN;
                setTitle("CNNIBN");
                break;
            case "DNA":
                url = dna;
                setTitle("DNA");
                break;
            case "NDTV":
                url = ndtv;
                setTitle("NDTV");
                break;
            case "BBC":
                url = bbc;
                setTitle("BBC");
                break;

        }

        threadConn.execute(url);



    }


    class MyHttpConnection extends AsyncTask<String, String, ArrayList<RssItem>>
    {
        URL url;
        HttpURLConnection conn;
        InputStream is;
        EditText xmlTextBox;
        String xmlFinal;
        ArrayList<RssItem> arrayRSS = new ArrayList<RssItem>();
        RssItem rss = new RssItem();
        @Override
        protected ArrayList<RssItem> doInBackground(String... params) {
            try {
                url = new URL(params[0]);
                Log.i("url krk",url.toString());
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                is = conn.getInputStream();
                 processXML();
                /*
                int read =-1;
                byte[] buffer = new byte[1024];
                ByteArrayOutputStream os = new ByteArrayOutputStream();

                while((read=is.read(buffer))!=-1)
                {
                    os.write(buffer);
                }
                os.close();

                xmlFinal = new String(os.toByteArray());
                Log.i("final XML RK",xmlFinal);
                publishProgress(xmlFinal);
                */

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally{
                if(conn!=null)
                    conn.disconnect();
                if(is!=null)
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

            return arrayRSS;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

           // xmlTextBox = (EditText)findViewById(R.id.editText_XML);
            //xmlTextBox.setText(values[0]);
        }

        public void processXML()
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document xmlDocument = builder.parse(is);
                Element rootElement = xmlDocument.getDocumentElement();
                //Log.d("root Element RKK",rootElement.getTagName());
                NodeList itemsList = rootElement.getElementsByTagName("item");
                NodeList itemChildren;
                Node currentItem;
                Node currentChild;
                for(int i=0;i<itemsList.getLength();i++)
                {
                    RssItem rss = new RssItem("","","","","");
                    currentItem = itemsList.item(i);
                    itemChildren = currentItem.getChildNodes();
                    //Log.w("ITEM",currentItem.getNodeName());
                    for(int j=0;j<itemChildren.getLength();j++)
                    {
                        currentChild = itemChildren.item(j);
                        if(currentChild.getNodeName().equalsIgnoreCase("title"))
                        {
                            if(currentChild.getTextContent()!=null)
                                rss.setTitle(currentChild.getTextContent());
                        }
                        else if(currentChild.getNodeName().equalsIgnoreCase("description"))
                        {
                            if(currentChild.getTextContent()!=null)
                                rss.setDescription(currentChild.getTextContent());
                        }
                        else if(currentChild.getNodeName().equalsIgnoreCase("link"))
                        {
                            if(currentChild.getTextContent()!=null)
                                rss.setLink(currentChild.getTextContent());
                        }
                        else if(currentChild.getNodeName().equalsIgnoreCase("pubDate"))
                        {
                            if(currentChild.getTextContent()!=null)
                            {
                                rss.setDate(currentChild.getTextContent());
                            }

                        }
                        else if(currentChild.getNodeName().equalsIgnoreCase("image"))
                        {
                            if(currentChild.getTextContent()!=null)
                            {
                                rss.setImageUrl(currentChild.getTextContent());
                            }

                        }

                       //arrayRSS.add(rss);
                     /*Log.i("debug","--------ITEM---------");
                        //Log.d(" TITLE ",rss.getTitle());
                      Log.d(" Link ",rss.getLink());
                        Log.d(" description ",rss.getDescription());
                        Log.d(" Date ",rss.getDate());
                        Log.d(" imageURL ",rss.getImageUrl());
                    */
                    }

                    arrayRSS.add(i,rss);
                 //   Log.i("debug","--------ITEM---------");
                  //  Log.d(" TITLE ",i+" : "+rss.getTitle());
                }

            /*    for(int i=0;i<arrayRSS.size();i++)
                {
                    Log.w("final item num : ",""+i);
                    RssItem temp = arrayRSS.get(i);
                    Log.d(" TITLE ",temp.getTitle());
                    Log.d(" description ",temp.getDescription());
                }
                */

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        protected void onPostExecute(ArrayList<RssItem> tempRss) {
            super.onPostExecute(tempRss);
            final ArrayList<RssItem> arrayRSS = tempRss;
            ListView listview_rss =(ListView)findViewById(R.id.listView_rssparse);
            //RssNewsAdapter newsadapter = new RssNewsAdapter(FourthActivity_Threading.this,arrayRSS);
            Log.d("in post execute ", "arrayrss title : ");

            String[] title = new String[arrayRSS.size()];
            String[] description = new String[arrayRSS.size()];
            String[] link = new String[arrayRSS.size()];
            String[] date = new String[arrayRSS.size()];
            String[] imageURL = new String[arrayRSS.size()];
            for(int i=0;i<arrayRSS.size();i++)
            {
           //     Log.w("item num : ",""+i);
                RssItem temp = arrayRSS.get(i);
             //   Log.d(" TITLE ",temp.getTitle());
               // Log.d(" description ",temp.getDescription());

                title[i]=temp.getTitle();
                description[i] = temp.getDescription();
                link[i]= temp.getLink();
                date[i] = temp.getDate();
                imageURL[i] = temp.getImageUrl();
            }
            RssNewsAdapter newsadapter = new RssNewsAdapter(FourthActivity_Threading.this,imageURL,title,date);
           listview_rss.setAdapter(newsadapter);
            //ArrayAdapter<String>  tempadapter = new ArrayAdapter<String>(FourthActivity_Threading.this,R.layout.layout_rss,R.id.textview_title,newwsss);
           //listview_rss.setAdapter(tempadapter);
            listview_rss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(FourthActivity_Threading.this,RssNewsItem.class);
                    i.putExtra("url",arrayRSS.get(position).getLink());
                    startActivity(i);
                }
            });
            Log.i("finished", "in getVIew");
        }
    }


    public class RssItem
    {
        String title;
        String description;
        String link;
        String date;
        String imageUrl;

        public RssItem()
        {

        }
        public RssItem(String title,String description,String link,String date,String imageURL)
        {
            this.title = title;
            this.description = description;
            this.link = link   ;
            this.date = date;
            this.imageUrl = imageURL;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public String getTitle()
        {
            if(title!=null)
                return title;
            else
                return "NO TITLE";
        }

        public void setDescription(String description)
        {

            this.description = description;
        }

        public String getDescription()
        {
            if(description!=null)
                return description;
            else return "NO DESCRIPTION";
        }
        public void setLink(String link)
        {
            this.link = link;
        }

        public String getLink()
        {
            if(link!=null)
                return link;
            else return "NO LINK";

        }

        public void setDate(String date)
        {
            this.date = date;
        }

        public String getDate()
        {
            if(date!=null)
                return date;
            else return "NO DATE";
        }

        public void setImageUrl(String imageUrl)
        {

            this.imageUrl = imageUrl;
        }

        public String getImageUrl()
        {
            if(imageUrl!=null)
                return imageUrl;
            else return "NO IMAGE";
        }
    }

    class RssNewsAdapter extends ArrayAdapter
    {
        Context c;
        String[] imageUrls;
        String[] titles;
        String[] date;
        LayoutInflater inflater;
        RssNewsAdapter(Context con, String[] imgs, String[] titles, String[] date)
        {
            super(con,R.layout.layout_rss,R.id.textview_title,titles);
            this.c = con;
            this.date = date;
            this.titles = titles;
            this.imageUrls=imgs;
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /*LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = inflater.inflate(R.layout.layout_rss,parent,false);
            ImageView img = (ImageView)row.findViewById(R.id.postThumb);
            TextView textView_titles = (TextView)row.findViewById(R.id.textview_title);
            TextView textView_description =(TextView)row.findViewById(R.id.textview_date);

            ImageLoader imgload = ImageLoader.getInstance();
            imgload.displayImage(imageUrls[position],img);

            textView_titles.setText(titles[position]);
            textView_description.setText(date[position]);*/
            ViewHolder viewHolder;
            View row = convertView;
            if (row==null)
            {
                row = inflater.inflate(R.layout.layout_rss,parent,false);
                viewHolder = new ViewHolder(row);
                row.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) row.getTag();
            }
//            RssItem rssitem = arr.get(position);
            viewHolder.date.setText(date[position]);
            viewHolder.title.setText(titles[position]);

            ImageLoader imgload = ImageLoader.getInstance();
            imgload.displayImage(imageUrls[position],viewHolder.image);
            if(viewHolder.image.getDrawable() == null)
            {
                viewHolder.image.setImageResource(R.drawable.noimage1);
            }



            return row;
        }
    }

    public class ViewHolder
    {
        ImageView image;
        TextView title ;
        TextView date ;

        public ViewHolder(View row)
        {
            image = (ImageView)row.findViewById(R.id.postThumb);
            title = (TextView)row.findViewById(R.id.textview_title);
            date = (TextView)row.findViewById(R.id.textview_date);
        }
    }


}

