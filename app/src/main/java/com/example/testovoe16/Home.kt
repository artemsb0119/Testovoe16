package com.example.testovoe16

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Home : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerViewMain: RecyclerView
    private lateinit var adapter: CryptaAdapter

    private lateinit var crypta: List<Crypta>

    private val sharedViewModel: SharedViewModel by lazy {
        ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

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
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerViewMain = view.findViewById(R.id.recyclerViewMain)
        val verticalLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewMain.layoutManager = verticalLayoutManager
        loadData()

        return view
    }

    private fun loadData() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val data = URL("http://135.181.248.237/16/crypta.json").readText()
                val gson = Gson()
                crypta = gson.fromJson(data, Array<Crypta>::class.java).toList()


                requireActivity().runOnUiThread {
                    val activityContext = requireActivity()
                    val sharedPreferences = activityContext.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
                    for (i in crypta.indices) {
                        val addValue = sharedPreferences.getBoolean("add_$i", false)
                        crypta[i].add = addValue
                        val existingCrypto = sharedViewModel.selectedCrypts.value?.find { it.id == crypta[i].id }
                        if (addValue && existingCrypto == null) {
                            sharedViewModel.addCrypto(crypta[i])
                        }
                    }
                    adapter = CryptaAdapter(requireContext(), crypta, sharedViewModel)

                    recyclerViewMain.adapter = adapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}