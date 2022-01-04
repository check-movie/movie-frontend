package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest

class CommentAdapter (var mCtx: Context, var resources:Int, var items:List<CommentModel>):
    ArrayAdapter<CommentModel>(mCtx, resources, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup):View{
        val layoutInflater:LayoutInflater = LayoutInflater.from(mCtx)
        val view:View = layoutInflater.inflate(resources,null)

        val commentauthoravatar:ImageView = view.findViewById(R.id.commentauthoravatar)
        val commentopinion:TextView = view.findViewById(R.id.commentopinion)
        val commentauthor:TextView = view.findViewById(R.id.commentauthor)
        val commentdate:TextView = view.findViewById(R.id.commentdate)

        var mItem:CommentModel = items[position]
        commentauthoravatar.loadSvg(mItem.author_avatar)
        commentauthor.text = mItem.author
        commentopinion.text = mItem.opinion
        commentdate.text = mItem.created_at

        return view
    }
fun ImageView.loadSvg(url: String) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(500)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}
}