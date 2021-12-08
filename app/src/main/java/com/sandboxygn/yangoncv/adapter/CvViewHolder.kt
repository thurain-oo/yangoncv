package com.sandboxygn.yangoncv.adapter

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.sandboxygn.yangoncv.R

class CvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var cvFileName: TextView = itemView.findViewById(R.id.text_pdf_file_name)
    var container: CardView = itemView.findViewById(R.id.card_pdf)
}