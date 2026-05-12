package com.nammakathey.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nammakathey.app.databinding.ItemBadgeBinding
import com.nammakathey.app.models.Badge
import com.nammakathey.app.utils.PrefsManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BadgeAdapter(
    private val badges: List<Badge>,
    private val prefs: PrefsManager
) : RecyclerView.Adapter<BadgeAdapter.BadgeViewHolder>() {

    inner class BadgeViewHolder(val binding: ItemBadgeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgeViewHolder {
        val binding = ItemBadgeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BadgeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BadgeViewHolder, position: Int) {
        val badge = badges[position]
        val isKn = prefs.isKannada
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        holder.binding.apply {
            tvBadgeHeroName.text = if (isKn) badge.heroNameKn else badge.heroNameEn
            tvBadgeDistrict.text = if (isKn) badge.districtNameKn else badge.districtNameEn
            tvBadgeDate.text = dateFormat.format(Date(badge.earnedAt))
            tvBadgeScore.text = "${badge.score}/3 ⭐"
            tvBadgeEmoji.text = "🏆"
        }
    }

    override fun getItemCount() = badges.size
}
