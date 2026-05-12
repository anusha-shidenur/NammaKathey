package com.nammakathey.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nammakathey.app.databinding.ItemStoryPageBinding
import com.nammakathey.app.models.StoryPage
import com.nammakathey.app.utils.PrefsManager

class StoryPageAdapter(
    private val pages: List<StoryPage>,
    private val prefs: PrefsManager
) : RecyclerView.Adapter<StoryPageAdapter.StoryPageViewHolder>() {

    inner class StoryPageViewHolder(val binding: ItemStoryPageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryPageViewHolder {
        val binding = ItemStoryPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryPageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryPageViewHolder, position: Int) {
        val page = pages[position]
        val isKn = prefs.isKannada
        val context = holder.itemView.context

        holder.binding.apply {
            tvPageTitle.text = if (isKn) page.titleKn else page.titleEn
            tvPageContent.text = if (isKn) page.contentKn else page.contentEn
            tvPageNumber.text = "Page ${page.pageNumber}"
            
            // Load Wikipedia image if available, otherwise show emoji
            if (page.imageUrl.isNotEmpty()) {
                tvIllustration.text = ""  // Clear emoji
                ivStoryImage.visibility = android.view.View.VISIBLE
                
                Glide.with(context)
                    .load(page.imageUrl)
                    .centerCrop()
                    .into(ivStoryImage)
            } else {
                tvIllustration.text = page.illustrationEmoji
                ivStoryImage.visibility = android.view.View.GONE
            }
        }
    }

    override fun getItemCount() = pages.size
}
