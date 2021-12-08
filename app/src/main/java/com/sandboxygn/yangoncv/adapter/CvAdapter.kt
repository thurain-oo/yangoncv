package com.sandboxygn.yangoncv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sandboxygn.yangoncv.R
import java.io.File

class CvAdapter(
    private val context: Context,
    private val pdfFiles: List<File>,
    private val listener: OnPdfItemSelectListener
) : RecyclerView.Adapter<CvViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CvViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.cv_list_item, parent, false)

        return CvViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CvViewHolder, position: Int) {
        holder.cvFileName.text = pdfFiles[position].name
        holder.container.setOnClickListener {
            listener.onPdfSelected(pdfFiles[position])

        }
    }

    override fun getItemCount(): Int {
        return pdfFiles.size
    }
}