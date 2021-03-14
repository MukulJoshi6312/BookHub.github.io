package com.example.bookhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bookhub.R
import com.example.bookhub.model.Book
import java.util.ArrayList

class DashboardRecyclerAdapter (val context: Context , val itemList :ArrayList<Book>): RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardRecyclerViewHolder>() {


    class DashboardRecyclerViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtBookName: TextView = view.findViewById(R.id.txtBookName)
        val txtBookAuthor: TextView = view.findViewById(R.id.txtBookAuthor)
        val txtBookPrice: TextView = view.findViewById(R.id.txtBookPrice)
        val txtBookRating: TextView = view.findViewById(R.id.txtBookRating)
        val imgBookImage: ImageView = view.findViewById(R.id.imgBookImage)

        val llContent: LinearLayout = view.findViewById(R.id.llContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardRecyclerViewHolder {
      val view  = LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row,parent,false)
        return DashboardRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
       return itemList.size
    }

    override fun onBindViewHolder(holder: DashboardRecyclerViewHolder, position: Int) {

        val book = itemList[position]
        holder.txtBookName.text = book.bookName
        holder.txtBookAuthor.text = book.bookAuthor
        holder.txtBookPrice.text =book.bookPrice
        holder.txtBookRating.text = book.bookRating
        holder.imgBookImage.setImageResource(book.bookImage as Int)

        holder.llContent.setOnClickListener{
            Toast.makeText(context, "Cliked on ${holder.txtBookName.text}", Toast.LENGTH_SHORT).show()
        }


    }
}
