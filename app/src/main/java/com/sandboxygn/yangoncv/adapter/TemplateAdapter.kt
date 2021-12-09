package com.sandboxygn.yangoncv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sandboxygn.yangoncv.R
import com.sandboxygn.yangoncv.model.TemplateItem
import com.sandboxygn.yangoncv.ui.ChooseCVTemplateFragment

class TemplateAdapter(private val context: ChooseCVTemplateFragment,private val templateSet : List<TemplateItem>):
RecyclerView.Adapter<TemplateViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateViewHolder {
      val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.cv_template_item,parent,false)
        return TemplateViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: TemplateViewHolder, position: Int) {
        val data = templateSet[position]
        holder.imageView.setImageResource(data.imageResourcesId)
        holder.templateType.text = data.templateName
        holder.cardView.setOnClickListener {
            context.sharedViewModel.setTemplateType(data.templateName)
            context.goToNextScreen()
        }
    }

    override fun getItemCount(): Int {
     return templateSet.size
    }

}