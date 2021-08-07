package com.zinoview.fragmenttagapp.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.core.PostApp
import com.zinoview.fragmenttagapp.presentation.*


/**
 * @author Zinoview on 30.07.2021
 * k.gig@list.ru
 */
class ListPostFragment : BaseFragment() {

    override fun layout(): Int = R.layout.list_post_fragment

    private val postViewModel by lazy {
        (requireActivity().application as PostApp).postViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postViewModel.posts()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

        postViewModel.observe(this) { uiPosts ->
            adapter.updateList(uiPosts)
        }
    }

}

