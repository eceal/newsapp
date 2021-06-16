package com.example.apcentece

import android.os.Bundle
import android.view.View.OnFocusChangeListener
import android.widget.Adapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.apcentece.adapter.NewsListAdapter
import com.example.apcentece.model.Articles
import com.example.apcentece.model.Headlines
import com.example.apcentece.util.GenericUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {
    val API_KEY = "c60e9149638343cb91dc13f4ce1fa675"
    var newsLayout: RecyclerView? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null
    var searchEt: EditText? = null
    var adapter: Adapter? = null
    var articleList: MutableList<Articles> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        newsLayout = findViewById(R.id.news_rv)
        swipeRefreshLayout = findViewById(R.id.news_swipeRefresh)
        newsLayout?.setLayoutManager(LinearLayoutManager(this))
        val country = GenericUtils.getCountry()
        searchEt = findViewById(R.id.search_et)
        searchEt?.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                //search
                if (!GenericUtils.isNullOrEmpty(searchEt?.getText().toString())) {
                    swipeRefreshLayout?.setOnRefreshListener(OnRefreshListener {
                        retrieveJson(
                            searchEt?.getText().toString(),
                            country,
                            API_KEY
                        )
                    })
                    retrieveJson(searchEt?.getText().toString(), country, API_KEY)
                } else {
                    swipeRefreshLayout?.setOnRefreshListener(OnRefreshListener {
                        retrieveJson(
                            "",
                            country,
                            API_KEY
                        )
                    })
                    retrieveJson("", country, API_KEY)
                }
            }
        })
        swipeRefreshLayout?.setOnRefreshListener(OnRefreshListener {
            retrieveJson(
                "",
                country,
                API_KEY
            )
        })
        retrieveJson("", country, API_KEY)
    }

    fun retrieveJson(query: String?, country: String?, apiKey: String?) {
        swipeRefreshLayout!!.isRefreshing = true
        val call: Call<Headlines>
        call = if (!GenericUtils.isNullOrEmpty(searchEt!!.text.toString())) {
            ApiClient.getInstance().api.getSpecificData(query, apiKey)
        } else {
            ApiClient.getInstance().api.getHeadlines(country, apiKey)
        }
        call.enqueue(object : Callback<Headlines> {
            override fun onResponse(call: Call<Headlines>, response: Response<Headlines>) {
                if (response != null && response.isSuccessful && response.body()!!.articles != null) {
                    swipeRefreshLayout!!.isRefreshing = false
                    articleList.clear()
                    articleList = response.body()!!.articles
                    val newsArrayList = ArrayList(articleList)
                    val productAdapter = NewsListAdapter(this@MainActivity, newsArrayList)
                    newsLayout!!.adapter = productAdapter
                }
            }

            override fun onFailure(call: Call<Headlines>, t: Throwable) {
                swipeRefreshLayout!!.isRefreshing = false
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}