package com.example.quizapp

import android.app.ActivityManager
import android.app.AlertDialog
import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
private const val SAVE_INSTANCE = "save_instance"
private const val POS="position"

class MainActivity : AppCompatActivity(),Listener {
    private var position:Int=0
    private lateinit var questionviewmodel: QuestionViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        questionviewmodel= ViewModelProvider(this).get(QuestionViewmodel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            when(savedInstanceState.getInt(SAVE_INSTANCE,0)){
                1 ->{
                    splashscreen()
                }
                2 ->{
                    RulesScreen()
                }
                3 ->{
                    startQuiz()
                }
                4 ->{
                    SetQuestion(position)
                }
                5 -> {
                    Summary_Screen()
                }
            }
        }
        else
        {
            supportFragmentManager.beginTransaction().add(R.id.container,SplashScreen_Fragment()).commit()
            splashscreen()
        }
        val counterReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                questionviewmodel.setTimer(p1?.getLongExtra(TimeService.KEY,-1)?: -1)
                questionviewmodel.autoSubmit.value = p1?.getBooleanExtra(TimeService.FINISH_KEY,false)
            }
        }
        val filter = IntentFilter(TimeService.PACKAGE)
        registerReceiver(counterReceiver, filter)
        questionviewmodel.autoSubmit.observe(this, Observer {
            if(it){
                Summary_Screen()
            }
        })
    }
    fun Context.isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.getRunningServices(Integer.MAX_VALUE)
            .any { it.service.className == serviceClass.name }
    }
    override fun splashscreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            val fragment = SetupScreen_Fragment()
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
        }, 1500)
    }

    override fun startQuiz() {
        if(!applicationContext.isMyServiceRunning(TimeService::class.java))
        {
            startService()
        }
        val fragment = QuestionsListScreen_Fragment()
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
    }
    private fun startService() {

        val serviceIntent = Intent(this, TimeService::class.java)
        startService(serviceIntent)
    }
    private fun stopService() {

        val serviceIntent = Intent(this, TimeService::class.java)
        stopService(serviceIntent)
    }
    override fun SetQuestion(position:Int) {
        this.position =position
        val fragment=QuestionDetailScreen_Fragment()
        questionviewmodel.getQustion(position)
        val args=Bundle()
        args.putSerializable("Question",position)
        fragment.arguments=args
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()

        transaction.replace(R.id.container,fragment).addToBackStack(null)
        transaction.commit()
    }

    override fun Summary_Screen() {
        stopService()
        val fragment=SummaryScreen_Fragment()
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()

        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        val currentFragment=supportFragmentManager.findFragmentById(R.id.container)
        if (currentFragment is SummaryScreen_Fragment){
            finish()
        }else if (currentFragment is SetupScreen_Fragment){
            finish()
        }
        else{
            super.onBackPressed()
        }
    }

    override fun RulesScreen() {
        val fragment = SetupScreen_Fragment()
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        val manager = supportFragmentManager
        when(manager.findFragmentById(R.id.container)){
            is SplashScreen_Fragment ->{
                outState.putInt(SAVE_INSTANCE,1)
            }
            is SetupScreen_Fragment ->{
                outState.putInt(SAVE_INSTANCE,2)
            }
            is QuestionsListScreen_Fragment ->{
                    outState.putInt(SAVE_INSTANCE,3)
                }
            is QuestionDetailScreen_Fragment ->{
                    outState.putInt(POS, position)
                    outState.putInt(SAVE_INSTANCE,4)
                }
            is SummaryScreen_Fragment ->{
                outState.putInt(SAVE_INSTANCE,5)
                }
            }
            super.onSaveInstanceState(outState, outPersistentState)
        }
    fun submitPressed(view: View) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this).setTitle("Submit Quiz")
        builder.setMessage("Are you sure you want to Submit the test ?")
        builder.setPositiveButton("Yes",
            DialogInterface.OnClickListener { _, _ ->
                Summary_Screen()
            })

        builder.setNegativeButton("No",
            DialogInterface.OnClickListener { _, _ -> })

        builder.show()
    }
}