package com.zinoview.fragmenttagapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.presentation.customview.BodyTextView
import com.zinoview.fragmenttagapp.presentation.customview.TitleTextView


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
class PostAdapter(
    private val viewTypeMapper: Abstract.FactoryMapper<UiPost, Int>,
    private val postItemListener: PostItemListener
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private val uiPosts = ArrayList<UiPost>()

    fun updateList(newList: List<UiPost>) {
        this.uiPosts.clear()
        this.uiPosts.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = viewTypeMapper.map(uiPosts[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder = when(viewType) {
        0 -> PostViewHolder.Progress(R.layout.progress_item_view.makeView(parent))
        1 -> PostViewHolder.BasePostViewHolder.Base(R.layout.post_item_view.makeView(parent),postItemListener)
        2 -> PostViewHolder.BasePostViewHolder.Cached(R.layout.cache_post_item_view.makeView(parent),postItemListener)
        else -> PostViewHolder.Failure(R.layout.failure_item_view.makeView(parent))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) = holder.bind(uiPosts[position])


    override fun getItemCount(): Int = uiPosts.size


    abstract class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(uiPost: UiPost) {}

        class Progress(view: View) : PostViewHolder(view)

        abstract class BasePostViewHolder(view: View,private val postItemListener: PostItemListener) : PostViewHolder(view) {
            private val bodyTextView = itemView.findViewById<BodyTextView>(R.id.body_text_view)
            private val titleTextView = itemView.findViewById<TitleTextView>(R.id.title_text_view)

            override fun bind(uiPost: UiPost) {
                uiPost.apply {
                    map(bodyTextView)
                    map(titleTextView)
                }

                itemView.setOnClickListener {
                    postItemListener.onItemClick(uiPost)
                }
            }

            class Base(view: View, postItemListener: PostItemListener)
                : BasePostViewHolder(view, postItemListener)

            class Cached(view: View, postItemListener: PostItemListener)
                : BasePostViewHolder(view, postItemListener)
        }

        class Failure(view: View) : PostViewHolder(view) {
            private val failureTextView = itemView.findViewById<BodyTextView>(R.id.failure_text_view)

            override fun bind(uiPost: UiPost) {
                uiPost.map(failureTextView)
            }
        }
    }
}

interface PostItemListener {
    fun onItemClick(uiPost: UiPost)
}

fun Int.makeView(parent: ViewGroup): View = LayoutInflater.from(parent.context).inflate(this,parent,false)