package com.onwordiesquire.android.getyourreviews.ui.reviewList

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.onwordiesquire.android.getyourreviews.R
import com.onwordiesquire.android.getyourreviews.data.response.ReviewDto
import com.onwordiesquire.android.getyourreviews.utils.inflate
import com.onwordiesquire.android.getyourreviews.utils.showContent
import kotlinx.android.synthetic.main.item_review.view.*

class ReviewListAdapter : ListAdapter<ReviewDto, ReviewListAdapter.ReviewItemViewHolder>(ReviewItemDiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewItemViewHolder =
            parent.inflate(R.layout.item_review).run { ReviewItemViewHolder(this) }

    override fun onBindViewHolder(holder: ReviewItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ReviewItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ReviewDto) {
            with(itemView) {
                txtTitle.showContent(item.title)
                txtDetails.showContent(item.message)
                txtDate.showContent(item.date)
                txtAuthor.showContent(item.author)
                txtRating.showContent(item.rating.toString())
            }
        }
    }
}

class ReviewItemDiffUtilCallBack : DiffUtil.ItemCallback<ReviewDto>() {
    override fun areItemsTheSame(oldItem: ReviewDto?, newItem: ReviewDto?) =
            oldItem?.review_id == newItem?.review_id ?: false

    override fun areContentsTheSame(oldItem: ReviewDto?, newItem: ReviewDto?) =
            oldItem?.equals(newItem) ?: false
}

