package com.nammakathey.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nammakathey.app.databinding.ItemHeroBinding
import com.nammakathey.app.models.Hero
import com.nammakathey.app.utils.PrefsManager

class HeroAdapter(
    private val heroes: List<Hero>,
    private val prefs: PrefsManager,
    private val onHeroClick: (Hero) -> Unit
) : RecyclerView.Adapter<HeroAdapter.HeroViewHolder>() {

    inner class HeroViewHolder(val binding: ItemHeroBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val binding = ItemHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        val hero = heroes[position]
        val isKn = prefs.isKannada
        val hasBadge = prefs.hasBadge(hero.id)

        holder.binding.apply {
            tvHeroName.text = if (isKn) hero.nameKn else hero.nameEn
            tvHeroCategory.text = if (isKn) hero.categoryKn else hero.categoryEn
            tvHeroYears.text = "${hero.yearBorn} – ${hero.yearDied}"
            tvHeroBio.text = if (isKn) hero.shortBioKn else hero.shortBioEn
            tvHeroEmoji.text = getEmojiForCategory(hero.categoryEn)
            ivBadge.visibility = if (hasBadge) View.VISIBLE else View.GONE
            tvBadgeLabel.visibility = if (hasBadge) View.VISIBLE else View.GONE
            tvStoryCount.text = "${hero.storyPages.size} ${if (isKn) "ಪುಟಗಳು" else "Pages"}"
            root.setOnClickListener { onHeroClick(hero) }
        }
    }

    private fun getEmojiForCategory(category: String): String = when {
        category.contains("Freedom") || category.contains("Queen") || category.contains("Sea Queen") -> "⚔️"
        category.contains("Reformer") || category.contains("Saint") -> "🪔"
        category.contains("Emperor") || category.contains("Ruler") -> "👑"
        category.contains("Founder") -> "🏗️"
        category.contains("Poet") && category.contains("Activist") -> "✍️"
        else -> "⭐"
    }

    override fun getItemCount() = heroes.size
}
