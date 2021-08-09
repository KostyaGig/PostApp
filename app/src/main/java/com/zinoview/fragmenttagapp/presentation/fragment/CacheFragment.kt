package com.zinoview.fragmenttagapp.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.core.Check
import com.zinoview.fragmenttagapp.presentation.ClickedUiPost
import com.zinoview.fragmenttagapp.presentation.PostTextMapper
import com.zinoview.fragmenttagapp.presentation.cache.CacheGenerator
import com.zinoview.fragmenttagapp.presentation.cache.CacheUiPostClicked
import com.zinoview.fragmenttagapp.presentation.customview.PostTextView


/**
 * @author Zinoview on 30.07.2021
 * k.gig@list.ru
 */
class CacheFragment : BaseFragment() {

    private val viewModel by lazy {
        application.cachePostViewModel
    }

    private val existingCacheChecker = Check.ExistingCacheCheck()
    private val recordCacheStateChecker = Check.RecordStateCheck()
    private val createdCacheFileChecker = Check.CreatedCacheFileCheck()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO REFACTOR TODOOOOS
        //todo fixed todo

        arguments?.let {
            val clickedUiPost = it.getSerializable(UI_POST_EXTRA) as ClickedUiPost

            val clickedPostInformation = clickedUiPost.map(PostTextMapper())

            val cacheUiPostClicked = CacheUiPostClicked.Base(
                clickedPostInformation, Check.SameContentCheck(),
                CacheGenerator.Base(clickedPostInformation)
            ) // FIXME: 08.08.2021 with application bad resolve

            //region initViews
            view.findViewById<Button>(R.id.post_title_tv).setOnClickListener {
                viewModel.cachedData()
            }
            view.findViewById<Button>(R.id.post_size_tv).setOnClickListener {
                viewModel.writeData(cacheUiPostClicked)
            }

            val deleteCacheBtn = view.findViewById<Button>(R.id.delete_cached_btn)

            deleteCacheBtn.setOnClickListener {
                viewModel.updateCache(cacheUiPostClicked)
            }

            val postInfoTextView = view.findViewById<PostTextView>(R.id.post_info_tv)
            clickedUiPost.map(postInfoTextView)
            //endregion

            viewModel.cachedData()

            viewModel.observe(this, { recordCacheState ->
                recordCacheStateChecker.check(Pair(recordCacheState, deleteCacheBtn))
            }, { readData ->
                existingCacheChecker.check(Triple(clickedPostInformation, readData, deleteCacheBtn))
                createdCacheFileChecker.check(Pair(readData,deleteCacheBtn))
                Log.d("MyCache", readData)
            })
        }
    }

    override fun layout(): Int = R.layout.cache_fragment
}