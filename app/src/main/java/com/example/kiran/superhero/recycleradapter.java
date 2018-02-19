package com.example.kiran.superhero;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

import retrofit2.Call;

/**
 * Created by kiran on 2/9/18.
 */

public class recycleradapter extends RecyclerView.Adapter<recycleradapter.recyclerviewholder> {

    private Context mctx; // Context object is needed as we will use layout inflater to inflate to list_layout...
    //to create layout inflater object we need context object

    private List<Item> productList;// product is our class
    //it represent all the elements of our product class in a list form

    //to display data we need list so we used List




    public  recycleradapter(Context mctx, List<Item> productList){


        this.mctx = mctx;
        this.productList = productList;
    }




    @Override
    public recyclerviewholder onCreateViewHolder(ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.list_layout,null);
        return new recyclerviewholder(view);//returning to out recyclerrviewholder class at below
    }

    @Override
    public void onBindViewHolder(recyclerviewholder holder, int position) {

        final Item check = productList.get(position);
        holder.textView.setText(check.getTitle());



        //using jsoup library

        Document document = Jsoup.parse(check.getContent());
        //html content is parsed and now is in document object

        holder.texr.setText(document.text());//to get all the text of content in json
        //getting to text of blog in app in document form not in html form like in json



        /*

           //now to get div class  style in texr we can do like

                 Elements elem = document.select("div");
        holder.texr.setText(elem.get(0).attr("style"));

         */





        Elements elements = document.select("img");
        //selecting all the img tag from our content
        //and element is used as image tag are also an element

        //image elements will be saved in elements obj

        //for image now

        Glide.with(mctx).load(elements.get(0).attr("src")).into(holder.image);//goint to content and"
        //1) selecting all element of img tag
        //2 pointing to src of first img tag



        //to load image in webview
        //.load is of byte used here




        //to go to detail activity when we click on recycler item

        //itemview point to any view on recycler view

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mctx,detail_activity.class);//shifting to detail activity when we click on recycler item
                intent.putExtra("url",check.getUrl());//passing url from our class item object check ..so that we can display it in detail actiity through url
                mctx.startActivity(intent);


            }
        });





    }

    @Override
    public int getItemCount() {
        return productList.size();
    }






    class recyclerviewholder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView image;
        TextView texr;



        public recyclerviewholder(View itemView) {

            super(itemView);//superitem (view) should always be in top in constructor of recyclerview holder
            textView = (TextView)itemView.findViewById(R.id.posttitel);
            image = (ImageView)itemView.findViewById(R.id.imageview);
            texr = (TextView)itemView.findViewById(R.id.postdescription);




        }
    }
}

