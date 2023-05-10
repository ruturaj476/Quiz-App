package com.example.quizapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.databinding.FragmentQuestionsListScreenBinding


class QuestionsListScreen_Fragment : Fragment() {

    private lateinit var viewmodel: QuestionViewmodel
    private lateinit var listener: Listener
    private lateinit var binding: FragmentQuestionsListScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentQuestionsListScreenBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        listener = activity as Listener
        binding.recyclerViewofQuestion.layoutManager = LinearLayoutManager(context)
        viewmodel = ViewModelProvider(requireActivity()).get(QuestionViewmodel::class.java)

        val list = viewmodel.getQuestionLiveData()
        binding.recyclerViewofQuestion.adapter = QuestionsListAdapter(list, activity, viewmodel)
        viewmodel.Timer.observe(viewLifecycleOwner, Observer {
            binding.remainingTime.text= (it/60000).toString()+":"+(it % 60000).toString().subSequence(0,2)
       })
        return fragmentBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewmodel
    }
}


