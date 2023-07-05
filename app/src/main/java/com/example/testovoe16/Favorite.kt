package com.example.testovoe16

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Favorite : Fragment(), FavoriteAdapter.OnItemClickListener {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerViewFavorite: RecyclerView
    private lateinit var adapter: FavoriteAdapter

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
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        recyclerViewFavorite = view.findViewById(R.id.recyclerViewFavorite)
        val verticalLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerViewFavorite.layoutManager = verticalLayoutManager

        val selectedMatches = sharedViewModel.getMatches().value


        adapter = if (selectedMatches != null && selectedMatches.isNotEmpty()) {
            FavoriteAdapter(requireContext(), selectedMatches)
        } else {
            FavoriteAdapter(requireContext(), emptyList())
        }
        recyclerViewFavorite.adapter = adapter
        adapter.setOnItemClickListener(this)
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Favorite().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(crypta: Crypta) {
        sharedViewModel.removeCrypto(crypta)
        val position = adapter.crypts.indexOf(crypta)
        if (position != -1) {
            adapter.removeItem(position)
        }
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("updateAdapter", true)
        editor.apply()
    }
}