package edu.naeemaziz.nycschool.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import edu.naeemaziz.nycschool.databinding.SchoolItemBinding
import edu.naeemaziz.nycschool.model.SchoolListResponse
import javax.inject.Inject

class SchoolListAdapter @Inject constructor() :
    RecyclerView.Adapter<SchoolListAdapter.ViewHolder>() {

    private lateinit var binding: SchoolItemBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SchoolListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = SchoolItemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: SchoolListAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: SchoolListResponse) {
            binding.apply {
                schoolName.text = item.schoolName
                totalStudents.text = item.totalStudents
                primaryAddressLine.text = item.primaryAddressLine1
                addressLine2.text = item.city + " , " + item.stateCode + " , " + item.zip
                phonenumber.text = item.phoneNumber
                dbn.text = item.dbn
                phonenumber.setOnClickListener {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:" + item.phoneNumber)
                    context.startActivity(intent)
                }
                satScore.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }


        }

    }

    private var onItemClickListener: ((SchoolListResponse) -> Unit)? = null

    fun setOnItemClickListener(listener: (SchoolListResponse) -> Unit) {
        onItemClickListener = listener
    }

    private val differCallback = object : DiffUtil.ItemCallback<SchoolListResponse>() {
        override fun areItemsTheSame(
            oldItem: SchoolListResponse,
            newItem: SchoolListResponse
        ): Boolean {
            return oldItem.dbn == newItem.dbn
        }

        override fun areContentsTheSame(
            oldItem: SchoolListResponse,
            newItem: SchoolListResponse
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

}