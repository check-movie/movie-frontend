package com.example.myapplication.Comment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
//import androidx.core.content.ContextCompat.*
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.myapplication.R
import kotlinx.android.synthetic.main.commentrow.*

class CommentAdapter (var mCtx: Context, var resources:Int, var items:List<CommentModel>):
    ArrayAdapter<CommentModel>(mCtx, resources, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup):View{
        val layoutInflater:LayoutInflater = LayoutInflater.from(mCtx)
        val view:View = layoutInflater.inflate(resources,null)

        val commentauthoravatar:ImageView = view.findViewById(R.id.commentauthoravatar)
        val commentopinion:TextView = view.findViewById(R.id.commentopinion)
        val commentauthor:TextView = view.findViewById(R.id.commentauthor)
        val commentdate:TextView = view.findViewById(R.id.commentdate)
        val editcomment:Button = view.findViewById(R.id.editcomment)
        val deletecomment:Button = view.findViewById(R.id.deletecomment)

        var mItem: CommentModel = items[position]
        commentauthoravatar.loadSvg(mItem.author_avatar)
        commentauthor.text = mItem.author
        commentopinion.text = mItem.opinion
        commentdate.text = mItem.created_at
        editcomment.setOnClickListener{
            var editcomm: Intent = Intent(mCtx, CommentEdit::class.java)
            editcomm.putExtra("id", mItem.id)
            editcomm.putExtra("opinion", mItem.opinion)
            mCtx.startActivity(editcomm)
        }
        deletecomment.setOnClickListener{
            var delcomm: Intent = Intent(mCtx, CommentDelete::class.java)
            delcomm.putExtra("id", mItem.id)
            mCtx.startActivity(delcomm)
        }

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