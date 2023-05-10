package com.example.quizapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.databinding.FragmentQuestionsListScreenBinding
import com.example.quizapp.databinding.FragmentSummaryScreenBinding


class SummaryScreen_Fragment : Fragment() {
    private lateinit var Score:TextView
    private lateinit var Restart_btn:Button
    private lateinit var listener: Listener
    private lateinit var exit_btn:Button
    private lateinit var time:TextView
    private lateinit var viewmodel: QuestionViewmodel
    private lateinit var binding: FragmentSummaryScreenBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentSummaryScreenBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        listener=activity as Listener
        viewmodel = ViewModelProvider(requireActivity()).get(QuestionViewmodel::class.java)
        viewmodel.Timer.observe(viewLifecycleOwner, Observer {
            val t=600000-it
            val second=(t/1000)%60
            val minutes=(t/(1000*60))%60
            if(second<10){
                binding.timeUsed.text= "$minutes:0$second"
            }
            else{
                binding.timeUsed.text= "$minutes:$second"
            }
        })
        binding.restartButton.setOnClickListener {
            listener.RulesScreen()
            viewmodel.ResetQuestion()
        }
        binding.exitButton.setOnClickListener {
            requireActivity().finish()
        }
        return fragmentBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewmodel
    }
}