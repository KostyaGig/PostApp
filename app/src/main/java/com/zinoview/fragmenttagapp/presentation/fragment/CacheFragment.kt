package com.zinoview.fragmenttagapp.presentation.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.core.PostApp
import com.zinoview.fragmenttagapp.presentation.ClickedUiPost
import com.zinoview.fragmenttagapp.presentation.PostTextMapper
import com.zinoview.fragmenttagapp.presentation.customview.PostTextView


/**
 * @author Zinoview on 30.07.2021
 * k.gig@list.ru
 */
class CacheFragment : BaseFragment() {

    private val viewModel by lazy {
        (requireActivity().application as PostApp).cachePostViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //todo make different sealed classes for write data and read data (Sealed class RecordCacheData (Success,Fail), ReadCacheData (Success,Fail))

        //todo after update livedata в independent (зависимости) от силд класса
        //todo после того,как сделаю сохранение проверять если этот пост уже закеширован,то сказать об этом пользователю
        arguments?.let {
            val clickedUiPost = it.getSerializable(UI_POST_EXTRA) as ClickedUiPost

            val postInfoTextView = view.findViewById<PostTextView>(R.id.post_info_tv)
            clickedUiPost.map(postInfoTextView)

            val postTitleTextView = view.findViewById<Button>(R.id.post_title_tv).setOnClickListener {
                //read
                viewModel.cachedData()
            }
            val postSizeTextView = view.findViewById<Button>(R.id.post_size_tv).setOnClickListener {
                //write
                viewModel.writeData(clickedUiPost.map(PostTextMapper()))
            }
            viewModel.observe(this) {
                Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun layout(): Int = R.layout.cache_fragment
}