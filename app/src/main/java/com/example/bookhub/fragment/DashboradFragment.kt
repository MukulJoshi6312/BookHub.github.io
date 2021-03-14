package com.example.bookhub.fragment

import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bookhub.R
import com.example.bookhub.adapter.DashboardRecyclerAdapter
import com.example.bookhub.model.Book
import com.example.bookhub.util.connectionManager
import com.android.volley.Request.Method as Method1

//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

class DashboradFragment : Fragment() {

//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
    lateinit var recyclerDashboard : RecyclerView

    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var btnCheckInternet:Button



    lateinit var recyclerAdapter: DashboardRecyclerAdapter

//    val bookInfoList = arrayListOf<Book>(
//        Book("P.S. I love You", "Cecelia Ahern", "Rs. 299", "4.5", R.drawable.ps_ily),
//        Book("The Great Gatsby", "F. Scott Fitzgerald", "Rs. 399", "4.1", R.drawable.great_gatsby),
//        Book("Anna Karenina", "Leo Tolstoy", "Rs. 199", "4.3", R.drawable.anna_kare),
//        Book("Madame Bovary", "Gustave Flaubert", "Rs. 500", "4.0", R.drawable.madame),
//        Book("War and Peace", "Leo Tolstoy", "Rs. 249", "4.8", R.drawable.war_and_peace),
//        Book("Lolita", "Vladimir Nabokov", "Rs. 349", "3.9", R.drawable.lolita),
//        Book("Middlemarch", "George Eliot", "Rs. 599", "4.2", R.drawable.middlemarch),
//        Book("The Adventures of Huckleberry Finn", "Mark Twain", "Rs. 699", "4.5", R.drawable.adventures_finn),
//        Book("Moby-Dick", "Herman Melville", "Rs. 499", "4.5", R.drawable.moby_dick),
//        Book("The Lord of the Rings", "J.R.R Tolkien", "Rs. 749", "5.0", R.drawable.lord_of_rings)
//    )
//


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashborad, container, false)

        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)

        btnCheckInternet = view.findViewById(R.id.btnCheckInternet)

        btnCheckInternet.setOnClickListener {
            if(connectionManager().checkConnectivity(activity as Context)){
                 val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Success")
                dialog.setMessage("Internet connection found")
                dialog.setPositiveButton("ok"){text,listener ->

                }
                dialog.setNegativeButton("Cancel"){text,listener ->

                }
                dialog.create()
                dialog.show()

            }else{
                val dialog = AlertDialog.Builder(activity as  Context)
                dialog.setTitle("Error")
                dialog.setMessage("Internet Conncetion is not found")
                dialog.setPositiveButton("ok"){text,listener ->

                }
                dialog.setNegativeButton("Cancel"){text,listener ->

                }

                dialog.create()
                dialog.show()


            }

        }

        layoutManager = LinearLayoutManager(activity)

        recyclerAdapter = DashboardRecyclerAdapter(activity as Context, bookInfoList)

        recyclerDashboard.adapter = recyclerAdapter

        recyclerDashboard.layoutManager = layoutManager

        recyclerDashboard.addItemDecoration(
            DividerItemDecoration(
                recyclerDashboard.context,
                (layoutManager as LinearLayoutManager).orientation
            )
        )

        val queue = Volley.newRequestQueue(activity as Context)

        val url = "http://13.235.250.119/vl/book/fetch_books/"

        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET,url,null, Response.Listener {

            // here we will handle the tresponse
            val success = it.getBoolean("seccess")

            if(success){
                val data = it.getJSONArray("data")
                for (i in 0 until  data.length()){
                    val bookJsonObjects = data.getJSONObject(i)

                    val bookObject = Book(
                        bookJsonObjects.getString("book_id"),
                        bookJsonObjects.getString("name"),
                        bookJsonObjects.getString("author"),
                        bookJsonObjects.getString("rating"),
                        bookJsonObjects.getString("price"),
                        bookJsonObjects.getString("image")

                    )
                    

                }
            }

        }, Response.ErrorListener {

            //here we will handle the error
            println("Error is $it")

        }){

            override fun getHeaders(): MutableMap<String, String> {

                val headers = HashMap<String , String>()
                headers["Content-type"] = "application/json"
                headers["token"] = "b97f2ba6c296d0"
                return headers
            }
        }
        queue.add(jsonObjectRequest)

        return view
    }

/*
companion object {

@JvmStatic
fun newInstance(param1: String, param2: String) =
DashboradFragment().apply {
arguments = Bundle().apply {
putString(ARG_PARAM1, param1)
putString(ARG_PARAM2, param2)
}
}
}
*/
}