package com.zinoview.fragmenttagapp.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.core.FragmentManager
import com.zinoview.fragmenttagapp.presentation.post.ClickedUiPostMapper
import com.zinoview.fragmenttagapp.presentation.post.PostAdapter
import com.zinoview.fragmenttagapp.presentation.post.PostItemListener
import com.zinoview.fragmenttagapp.presentation.post.UiPost


/**
 * @author Zinoview on 30.07.2021
 * k.gig@list.ru
 */

class ListPostFragment : BaseFragment() {

    private val postViewModel by lazy {
        application.viewModel(application.postModule)
    }

    private val navigationViewModel by lazy {
        application.viewModel(application.navigationModule)
    }

    private val bundleGenerator by lazy {
        application.generator.bundleGenerator
    }

    private val fragmentManager by lazy {
        FragmentManager.Base(requireActivity() as AppCompatActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postViewModel.data()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

         val adapter = PostAdapter(application.mapper.recyclerViewTypeMapper, object : PostItemListener {
            override fun onItemClick(uiPost: UiPost) {
                val clickedUiPost = uiPost.map(ClickedUiPostMapper())

                navigationViewModel.navigate(
                    fragmentManager,
                    bundleGenerator.generate(
                        Pair(
                            UI_POST_EXTRA,
                            clickedUiPost)
                    ))
            }
        })

        recyclerView.adapter = adapter

        postViewModel.observe(viewLifecycleOwner) { uiPosts ->
                adapter.updateList(uiPosts)
            }
        }

    override fun layout(): Int = R.layout.list_post_fragment
}


