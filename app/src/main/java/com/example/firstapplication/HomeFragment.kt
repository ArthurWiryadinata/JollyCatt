package com.example.firstapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.firstapplication.databinding.FragmentHomeBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var requestQueue: RequestQueue
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        requestQueue = Volley.newRequestQueue(requireContext())

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val url = "https://api.npoint.io/3fa9a95557f89f097063"
        fetchCatData(url)

        return binding.root
    }

    private fun fetchCatData(url: String) {
        Log.d("HomeFragment", "Fetching data from $url")
        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    Log.d("HomeFragment", "Response received: $response")
                    val jsonList = parseJSON(response)
                    val adapter = NameAdapter(jsonList, object: NameAdapter.OnItemClickListener{
                        override fun onItemClick(item: json) {
                            val intent = Intent(this@HomeFragment.requireContext(), DetailPage::class.java)
                            intent.putExtra("CatName", item.CatName)
                            intent.putExtra("CatImage", item.CatImage)
                            intent.putExtra("CatPrice", item.CatPrice)
                            intent.putExtra("CatDescription", item.CatDescription)
                            startActivity(intent)
                        }
                    })
                    binding.recyclerView.adapter = adapter
                } catch (e: JSONException) {
                    Log.e("HomeFragment", "JSON parsing error", e)
                }
            },
            Response.ErrorListener { error ->
                Log.e("HomeFragment", "Volley Error", error)
            }
        )
        requestQueue.add(request)
    }

    private fun parseJSON(jsonArray: JSONArray): ArrayList<json> {
        val jsonList = ArrayList<json>()
        try {
            for (i in 0 until jsonArray.length()) {
                val catJson = jsonArray.getJSONObject(i)
                val catID = catJson.getString("CatID")
                val catName = catJson.getString("CatName")
                val catImage = catJson.getString("CatImage")
                val catPrice = catJson.getString("CatPrice")
                val catDescription = catJson.getString("CatDescription")
                jsonList.add(json(catID, catName, catImage, catPrice, catDescription))
            }
        } catch (e: JSONException) {
            Log.e("HomeFragment", "JSON array parsing error", e)
        }
        return jsonList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}



// DIGANTI DENGAN JSON
//        var itemList = getSampleItems()
//        var binding: FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
//        val recyclerView = binding.recyclerView
//        val nameAdapter = NameAdapter(itemList, object: NameAdapter.OnItemClickListener{
//            override fun onItemClick(item: Item) {
//                val intent = Intent(this@HomeFragment.requireContext(), DetailPage::class.java)
//                intent.putExtra("ItemName",item.name)
//                intent.putExtra("ItemPrice",item.price)
//                intent.putExtra("ItemPicture",item.picture)
//                intent.putExtra("ItemDesc",item.description)
//                startActivity(intent)
//            }
//        })
//        recyclerView.adapter = nameAdapter
//
//        // Inflate the layout for this fragment
//        return binding.root