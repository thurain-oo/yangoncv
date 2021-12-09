package com.sandboxygn.yangoncv.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.sandboxygn.yangoncv.R

class TemplateViewHolder(view: View):RecyclerView.ViewHolder(view) {
    val imageView : ImageView = view.findViewById(R.id.template_image_view)
    val templateType : TextView = view.findViewById(R.id.template_text_view)
    val cardView : CardView = view.findViewById(R.id.card_template)
}