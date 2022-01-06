package com.example.myapplication.Movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import com.squareup.picasso.Picasso

class MoviesListAdapter (var mCtx: Context, var resources:Int, var items:List<MoviesListModel>):
    ArrayAdapter<MoviesListModel>(mCtx, resources, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup):View{
        val layoutInflater:LayoutInflater = LayoutInflater.from(mCtx)
        val view:View = layoutInflater.inflate(resources,null)

        val movieimageView:ImageView = view.findViewById(R.id.movieimage)
        val titleTextView:TextView = view.findViewById(R.id.movietitle)
        val movieyearTextView:TextView = view.findViewById(R.id.movieyear)
        val movierateTextView:TextView = view.findViewById(R.id.movierate)

        var mItem: MoviesListModel = items[position]
        Picasso.get().load(mItem.img).into(movieimageView)
        titleTextView.text = mItem.title
        movieyearTextView.text = mItem.year
        movierateTextView.text = mItem.rate

        return view
    }
}