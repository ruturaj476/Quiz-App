package com.example.quizapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.databinding.FragmentQuestionDetailScreenBinding
import com.example.quizapp.databinding.FragmentQuestionsListScreenBinding


class QuestionDetailScreen_Fragment : Fragment() {
    private lateinit var viewmodel: QuestionViewmodel
    private lateinit var listener: Listener
    private lateinit var binding: FragmentQuestionDetailScreenBinding
    private var checked: Int=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentQuestionDetailScreenBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        listener=activity as Listener
        viewmodel = ViewModelProvider(requireActivity()).get(QuestionViewmodel::class.java)
        viewmodel.Timer.observe(viewLifecycleOwner, Observer {
            binding.remainingTimeText.text=(it/60000).toString()+":"+(it % 60000).toString().subSequence(0,2)
        })
        var list=viewmodel.getQuestionLiveData()
        var position=arguments?.getSerializable("Question") as? Int
        binding.Options.setOnCheckedChangeListener{group,checkedID->
            if(checkedID==R.id.Option_1){
                checked=1
            }
            else if(checkedID==R.id.Option_2){
                checked=2
            }
            else if(checkedID==R.id.Option_3){
               checked=3
            }
            else if(checkedID==R.id.Option_4){
                checked=4
            }

        }
        binding.bookmark.setOnClickListener {
            if(position?.let { it1 -> list.value?.get(it1)?.bookmark } ==true){
                binding.bookmark.setImageDrawable(getResources().getDrawable(R.drawable.baseline_bookmark_add_24))
                viewmodel.BookmarkStatus(position!!,false)
            }
            else {
                binding.bookmark.setImageDrawable(getResources().getDrawable(R.drawable.baseline_bookmark_added_24))
                viewmodel.BookmarkStatus(position!!,true)
            }

        }
        binding.saveButton.setOnClickListener{
            position?.let { viewmodel.ChoosenOption(it,checked) }
            if(position?.let { it1 -> list.value?.get(it1)?.ChoosenOption }!=0){
                position?.let { it1 -> viewmodel.setanswerStatus(it1,true) }
            }
            if(position?.let { it1 -> list.value?.get(it1)?.ChoosenOption }!=0){
                binding.selectedOptionText.text=position?.let { it1 -> list.value?.get(it1)?.ChoosenOption }.toString()
            }
        }
            binding.clearButton.setOnClickListener {
            position?.let { viewmodel.ChoosenOption(it,0) }
            binding.Options.clearCheck()
            checked=0
            if(position?.let { it1 -> list.value?.get(it1)?.ChoosenOption }==0){
                position?.let { it1 -> viewmodel.setanswerStatus(it1,false) }
            }
                binding.selectedOptionText.text="None"
        }
        binding.nextQuestion.setOnClickListener {
            if (position != null) {
                if(position!! <9){
                    position = position!! + 1
                }
                viewmodel.getQustion(position!!)
            }
            if(position?.let { it1 -> list.value?.get(it1)?.ChoosenOption } ==0){
                binding.Options.clearCheck()
            }
        }
        binding.previousQuestion.setOnClickListener {
            if(position!! >0){
                position = position!! - 1
            }
            viewmodel.getQustion(position!!)
            if(position?.let { it1 -> list.value?.get(it1)?.ChoosenOption } ==0){
                binding.Options.clearCheck()
            }
        }
        return fragmentBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel=viewmodel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}



