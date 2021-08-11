package com.zinoview.fragmenttagapp.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.presentation.ClickedUiPost
import com.zinoview.fragmenttagapp.presentation.PostTextMapper
import com.zinoview.fragmenttagapp.presentation.customview.PostButton
import com.zinoview.fragmenttagapp.presentation.customview.PostTextView


/**
 * @author Zinoview on 30.07.2021
 * k.gig@list.ru
 */
class CacheFragment : BaseFragment() {

    private val viewModel by lazy {
        application.viewModel(application.cacheModule)
    }

    //todo fix lazy init
    private val recordCacheStateCheck by lazy {
        application.checkerDependencyContainer.recordCacheStateCheck
    }

    private val existingCacheCheck by lazy {
        application.checkerDependencyContainer.existingCacheCheck
    }

    private val createdCacheFileChecker by lazy {
        application.checkerDependencyContainer.createdCacheFileCheck
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            arguments?.let {
                val clickedUiPost = it.getSerializable(UI_POST_EXTRA) as ClickedUiPost

                val clickedPostInformation = clickedUiPost.map(PostTextMapper())

                val tempCachePostClicked = application.cacheUiPostClicked(clickedPostInformation)

                val postInfoTextView = view.findViewById<PostTextView>(R.id.post_info_tv)
                clickedUiPost.map(postInfoTextView)

                //region initViews
                view.findViewById<Button>(R.id.post_title_tv).setOnClickListener {
                    viewModel.data()
                }
                view.findViewById<Button>(R.id.post_size_tv).setOnClickListener {
                    viewModel.writeData(tempCachePostClicked)
                }

                val deleteCacheBtn = view.findViewById<PostButton>(R.id.delete_cached_btn)

                deleteCacheBtn.setOnClickListener {
                    viewModel.update(tempCachePostClicked)
                }

                viewModel.observe(this, { recordCacheState ->
                    recordCacheStateCheck.check(Pair(recordCacheState, deleteCacheBtn))
                }, { readData ->
                    existingCacheCheck.check(Triple(clickedPostInformation, readData, deleteCacheBtn))
                    createdCacheFileChecker.check(Pair(readData,deleteCacheBtn))
                    Log.d("MyCache", readData)
                })
            }
        }

    override fun layout(): Int = R.layout.cache_fragment
}