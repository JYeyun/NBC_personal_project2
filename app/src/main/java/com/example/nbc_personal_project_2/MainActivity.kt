package com.example.nbc_personal_project2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animate
import androidx.compose.animation.fadeOut
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nbc_personal_project_2.DetailActivity
import com.example.nbc_personal_project_2.Product
import com.example.nbc_personal_project_2.R
import com.example.nbc_personal_project_2.RecyclerViewAdapter
import com.example.nbc_personal_project_2.databinding.ActivityMainBinding
import com.example.nbc_personal_project_2.showProductList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var notificationManager: NotificationManager
    private val CHANNEL_ID = "textChannel"
    private val list = showProductList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        createNotificationChannel()

        binding.btnBack.setOnClickListener{
            dialog(binding.btnBack)
        }
        binding.btnNotification.setOnClickListener{
            displayNotification()
        }

        scrollEvent()
        initRecyclerView()
    }
    private fun scrollEvent(){
        var isTop = true
        binding.mainRecyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!binding.mainRecyclerView.canScrollVertically(-1)&&newState==RecyclerView.SCROLL_STATE_IDLE){
                    binding.upBtn.visibility = View.GONE
                    binding.upBtn.animate().alpha(0.0f).setDuration(1000)
                    isTop = true
                }else{
                    if(isTop){
                        binding.upBtn.visibility = View.VISIBLE
                        binding.upBtn.animate().alpha(1.0f).setDuration(1000)
                        isTop=false
                    }
                }
            }
        })
        binding.upBtn.setOnClickListener {
            binding.mainRecyclerView.smoothScrollToPosition(0)
        }
    }
    private fun initRecyclerView(){
        val adapter = RecyclerViewAdapter()
        adapter.datalist = showProductList()

        adapter.setOnItemClickListener(object:RecyclerViewAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position:Int){
                val intent:Intent = Intent(this@MainActivity, DetailActivity::class.java)
                val data = Product(
                    list[position].img,
                    list[position].price,
                    list[position].user,
                    list[position].address,
                    list[position].temperature,
                    list[position].title,
                    list[position].description,
                    list[position].like,
                    list[position].chat
                )
                intent.putExtra("product", data)
                Log.d("test", "$position")
                startActivity(intent)
            }
        })
        binding.mainRecyclerView.adapter = adapter
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
    }
    private fun dialog(view:View){
        val builder = AlertDialog.Builder(this)
        val listener = DialogInterface.OnClickListener{ _, p0 ->
            if(p0==DialogInterface.BUTTON_POSITIVE) finish()
        }
        builder.setTitle("종료")
            .setMessage("정말 종료하시겠습니까?")
            .setPositiveButton("확인", listener)
            .setNegativeButton("취소", null)
            .show()
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification_CH"
            val descriptionText = "Test"
            val channel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }
    }

    private fun displayNotification(){
        val notificationId = 66
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification)
            .setContentTitle("Notification")
            .setContentText("Text")
            .build()
        notificationManager?.notify(notificationId, notification)
    }

}