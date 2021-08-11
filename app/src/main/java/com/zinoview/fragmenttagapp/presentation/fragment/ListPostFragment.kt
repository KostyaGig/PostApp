package com.zinoview.fragmenttagapp.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.presentation.*


/**
 * @author Zinoview on 30.07.2021
 * k.gig@list.ru
 */

class ListPostFragment : BaseFragment() {

    private val viewModel by lazy {
        application.viewModel(application.postModule)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.data()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("MainActivity","ListPostFragment onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

         val adapter = PostAdapter(Abstract.FactoryMapper.RecyclerViewTypeMapper(), object : PostItemListener {
            override fun onItemClick(uiPost: UiPost) {
                val clickedUiPost = uiPost.map(ClickedUiPostMapper())
                val cacheFragment = CacheFragment()

                cacheFragment.arguments = Bundle().apply {
                    putSerializable(UI_POST_EXTRA, clickedUiPost)
                }

                requireActivity().supportFragmentManager.beginTransaction().apply {
                    replace(R.id.container,cacheFragment)
                    addToBackStack(null)
                    commit()
                }
            }
        })
        recyclerView.adapter = adapter

        viewModel.observe(viewLifecycleOwner) { uiPosts ->
                adapter.updateList(uiPosts)
            }
        }

    override fun layout(): Int = R.layout.list_post_fragment
}


