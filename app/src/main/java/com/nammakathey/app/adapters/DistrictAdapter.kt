package com.nammakathey.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nammakathey.app.databinding.ItemDistrictBinding
import com.nammakathey.app.models.District
import com.nammakathey.app.utils.PrefsManager

class DistrictAdapter(
    private val districts: List<District>,
    private val prefs: PrefsManager,
    private val onDistrictClick: (District) -> Unit
) : RecyclerView.Adapter<DistrictAdapter.DistrictViewHolder>() {

    private val districtColors = intArrayOf(
        0xFFFF6B00.toInt(), 0xFFE91E63.toInt(), 0xFF9C27B0.toInt(), 0xFF3F51B5.toInt(),
        0xFF009688.toInt(), 0xFF4CAF50.toInt(), 0xFFFF9800.toInt(), 0xFFF44336.toInt()
    )

    private val districtEmojis = listOf("⚔️", "👑", "🪔", "🌊", "🏙️", "🏛️", "🌸", "✍️")

    inner class DistrictViewHolder(val binding: ItemDistrictBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistrictViewHolder {
        val binding = ItemDistrictBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DistrictViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DistrictViewHolder, position: Int) {
        val district = districts[position]
        val isKn = prefs.isKannada
        val colorIndex = district.colorIndex % districtColors.size

        holder.binding.apply {
            tvDistrictName.text = if (isKn) district.nameKn else district.nameEn
            tvHeroCount.text = "${district.heroes.size} ${if (isKn) "ವೀರರು" else "Heroes"}"
            tvEmoji.text = districtEmojis.getOrElse(colorIndex) { "⭐" }
            cardDistrict.setCardBackgroundColor(districtColors[colorIndex])
            root.setOnClickListener { onDistrictClick(district) }
        }
    }

    override fun getItemCount() = districts.size
}
