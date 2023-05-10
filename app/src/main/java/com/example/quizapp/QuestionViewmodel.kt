package com.example.quizapp

import android.app.Application

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import kotlin.math.log

private const val API_URL = "https://raw.githubusercontent.com/tVishal96/sample-english-mcqs/master/db.json"
private const val RESPONSE_ENTRY_KEY = "questions"

class QuestionViewmodel(application: Application):AndroidViewModel(application),Response.Listener<String>,Response.ErrorListener {
    init {
        val queue=Volley.newRequestQueue(application)
        val request= StringRequest(Request.Method.GET,API_URL,this,this)
        queue.add(request)
    }
    val question = MutableLiveData<QuestionModel>()
    private val questionLiveData: MutableLiveData<List<QuestionModel>> =
        MutableLiveData<List<QuestionModel>>()
    var quizScore:Int=0
    var Timer=MutableLiveData<Long>()
    var autoSubmit=MutableLiveData<Boolean>()

    fun getQuestionLiveData(): MutableLiveData<List<QuestionModel>> {
        return questionLiveData
    }
    fun recieveScore(): Int {
        for (i in 0..((questionLiveData.value?.size)?.minus(1)!!)){
            if (questionLiveData.value?.get(i)?.ChoosenOption== questionLiveData.value!!.get(i).answer+1){
                quizScore++
            }
        }
        return quizScore
    }
    fun setTimer(timer:Long){
        Timer.value=timer
    }
    fun getQustion(position:Int)
    {
        question.postValue(questionLiveData.value?.get(position))
    }
    override fun onResponse(response: String) {
        try {
            val QuestionModels: List<QuestionModel> = parseResponse(response)
            if (QuestionModels != null) {
            }
            questionLiveData.postValue(QuestionModels)
        } catch (e: JSONException) {
            e.printStackTrace()

        }
    }
    fun setanswerStatus(position:Int,Status:Boolean){
        questionLiveData.value?.get(position)?.answerStatus  =Status
    }
    fun BookmarkStatus(position:Int,Status:Boolean){
        questionLiveData.value?.get(position)?.bookmark =Status
    }
    fun ChoosenOption(position: Int,option:Int){
        questionLiveData.value?.get(position)?.ChoosenOption=option
    }
    override fun onErrorResponse(error: VolleyError?) {

    }
    @Throws(JSONException::class)
    private fun parseResponse(response: String): List<QuestionModel> {
        var models: ArrayList<QuestionModel> = ArrayList()
        val res = JSONObject(response)
        val entries = res.optJSONArray(RESPONSE_ENTRY_KEY)

        for (i in 0 until entries.length()) {
            val obj = entries[i] as JSONObject
            val question = obj.getString("question")
            val options = obj.getJSONArray("options")
            val answer = obj.getInt("correct_option")

            var list: ArrayList<String> = ArrayList()
            list.add(options[0] as String)
            list.add(options[1] as String)
            list.add(options[2] as String)
            list.add(options[3] as String)

            val model = QuestionModel(question, list, answer, false, false)
            models.add(model)
        }
        models.shuffle()
        return models
    }

    fun ResetQuestion() {
        for (position in 0 ..(questionLiveData.value?.size?.minus(1)!!)) {
            questionLiveData.value?.get(position)?.bookmark = false
            questionLiveData.value?.get(position)?.ChoosenOption = 0
            questionLiveData.value?.get(position)?.answerStatus = false
        }
    }


}