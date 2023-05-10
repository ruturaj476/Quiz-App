package com.example.quizapp

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.databinding.QuestionViewBinding

class QuestionsListAdapter(private val list: MutableLiveData<List<QuestionModel>> = MutableLiveData<List<QuestionModel>>(), activity: FragmentActivity?, viewModel:QuestionViewmodel) : RecyclerView.Adapter<QuestionsListAdapter.ViewHolder>() {

    private lateinit var listener:Listener
    val activity=activity
    val viewModel=viewModel
    class ViewHolder(private val binding: QuestionViewBinding):RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: QuestionModel, listener: Listener, position: Int){
            binding.questionModel=item
            binding.listener=listener
            binding.position=position
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding=QuestionViewBinding.inflate(view)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        listener=activity as Listener
        list.value?.get(position)?.let { holder.bindView(it,listener,position) }
    }

    override fun getItemCount(): Int {
        return list.value?.size!!
    }
}
