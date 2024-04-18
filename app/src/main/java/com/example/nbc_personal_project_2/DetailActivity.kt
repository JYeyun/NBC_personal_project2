package com.example.nbc_personal_project_2

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.nbc_personal_project_2.databinding.ActivityDetailBinding

class DetailActivity :AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()
        binding.detailBackBtn.setOnClickListener{
            finish()
        }
        binding.detailHeart.setOnClickListener{

        }
    }
    fun init(){
        val intent:Intent = getIntent()
        var productData = intent?.getParcelableExtra<Product>("product")
        Log.d("intent", "$intent")
        Log.d("product", "$productData")
        binding.detailImage.setImageResource(productData!!.img)
        binding.detailTitle.text = productData?.title
        binding.detailPrice.text = productData?.price
        binding.detailUserAddress.text = productData?.address
        binding.detailUserTemp.text = productData?.temperature
        binding.detailUsername.text = productData?.user
        binding.detailDetail.text = productData?.description
    }
}