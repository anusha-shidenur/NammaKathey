package com.nammakathey.app.activities

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.nammakathey.app.R
import com.nammakathey.app.databinding.ActivityQuizBinding
import com.nammakathey.app.models.Badge
import com.nammakathey.app.models.Hero
import com.nammakathey.app.models.QuizQuestion
import com.nammakathey.app.utils.DataManager
import com.nammakathey.app.utils.PrefsManager

class QuizActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_HERO_ID = "hero_id"
    }

    private lateinit var binding: ActivityQuizBinding
    private lateinit var prefs: PrefsManager
    private var hero: Hero? = null
    private var currentQuestionIndex = 0
    private var score = 0
    private var selectedOption = -1
    private var answered = false
    private val optionButtons get() = listOf(binding.btnOption0, binding.btnOption1, binding.btnOption2, binding.btnOption3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = PrefsManager(this)

        val heroId = intent.getStringExtra(EXTRA_HERO_ID) ?: run { finish(); return }
        hero = DataManager.getHeroById(this, heroId)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val isKn = prefs.isKannada
        supportActionBar?.title = if (isKn) getString(R.string.quiz_title_kn) else getString(R.string.quiz_title)

        if (prefs.hasBadge(heroId)) {
            showAlreadyEarned()
        } else {
            showQuestion(0)
        }

        binding.btnCheck.setOnClickListener { handleCheckAnswer() }
        binding.btnNext.setOnClickListener { handleNext() }
        binding.btnFinish.setOnClickListener { finish() }
    }

    private fun showQuestion(index: Int) {
        val h = hero ?: return
        val questions = h.quizQuestions
        if (index >= questions.size) { showResults(); return }

        answered = false
        selectedOption = -1
        val q = questions[index]
        val isKn = prefs.isKannada

        binding.tvQuestionNumber.text = "Question ${index + 1} of ${questions.size}"
        binding.tvQuestion.text = if (isKn) q.questionKn else q.questionEn
        binding.progressBar.progress = ((index.toFloat() / questions.size) * 100).toInt()

        val options = if (isKn) q.optionsKn else q.optionsEn
        optionButtons.forEachIndexed { i, btn ->
            btn.text = options.getOrElse(i) { "" }
            btn.setBackgroundColor(Color.WHITE)
            btn.setTextColor(getColor(R.color.text_primary))
            btn.strokeColor = android.content.res.ColorStateList.valueOf(getColor(R.color.primary_light))
            btn.strokeWidth = 2
            btn.isEnabled = true
            btn.setOnClickListener { selectOption(i, q) }
        }

        binding.tvFeedback.visibility = View.GONE
        binding.btnCheck.visibility = View.VISIBLE
        binding.btnCheck.isEnabled = false
        binding.btnCheck.alpha = 0.5f
        binding.btnNext.visibility = View.GONE
        binding.quizContent.visibility = View.VISIBLE
        binding.resultsLayout.visibility = View.GONE

        val anim = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        binding.quizContent.startAnimation(anim)
    }

    private fun selectOption(index: Int, q: QuizQuestion) {
        if (answered) return
        selectedOption = index
        optionButtons.forEachIndexed { i, btn ->
            if (i == index) {
                btn.setBackgroundColor(getColor(R.color.option_selected))
                btn.strokeColor = android.content.res.ColorStateList.valueOf(getColor(R.color.primary))
                btn.strokeWidth = 4
            } else {
                btn.setBackgroundColor(Color.WHITE)
                btn.strokeColor = android.content.res.ColorStateList.valueOf(getColor(R.color.primary_light))
                btn.strokeWidth = 2
            }
        }
        binding.btnCheck.isEnabled = true
        binding.btnCheck.alpha = 1f
    }

    private fun handleCheckAnswer() {
        if (selectedOption == -1 || answered) return
        val h = hero ?: return
        val q = h.quizQuestions[currentQuestionIndex]
        answered = true
        val isKn = prefs.isKannada
        val isCorrect = selectedOption == q.correctIndex

        optionButtons.forEachIndexed { i, btn ->
            btn.isEnabled = false
            when {
                i == q.correctIndex -> {
                    btn.setBackgroundColor(getColor(R.color.correct_green))
                    btn.setTextColor(Color.WHITE)
                }
                i == selectedOption && !isCorrect -> {
                    btn.setBackgroundColor(getColor(R.color.wrong_red))
                    btn.setTextColor(Color.WHITE)
                }
            }
        }

        if (isCorrect) score++

        binding.tvFeedback.visibility = View.VISIBLE
        binding.tvFeedback.text = if (isCorrect) {
            "✅ ${if (isKn) "ಸರಿ!" else "Correct!"} ${if (isKn) q.explanationKn else q.explanationEn}"
        } else {
            "❌ ${if (isKn) "ತಪ್ಪು!" else "Wrong!"} ${if (isKn) q.explanationKn else q.explanationEn}"
        }
        binding.tvFeedback.setTextColor(if (isCorrect) getColor(R.color.correct_green) else getColor(R.color.wrong_red))

        binding.btnCheck.visibility = View.GONE
        binding.btnNext.visibility = View.VISIBLE
        val isLastQuestion = currentQuestionIndex >= h.quizQuestions.size - 1
        binding.btnNext.text = if (isLastQuestion) {
            if (isKn) "ಫಲಿತಾಂಶ ನೋಡಿ" else "See Results"
        } else {
            if (isKn) "ಮುಂದಿನ ಪ್ರಶ್ನೆ" else "Next Question"
        }
    }

    private fun handleNext() {
        currentQuestionIndex++
        val h = hero ?: return
        if (currentQuestionIndex >= h.quizQuestions.size) {
            showResults()
        } else {
            showQuestion(currentQuestionIndex)
        }
    }

    private fun showResults() {
        val h = hero ?: return
        val isKn = prefs.isKannada
        val total = h.quizQuestions.size

        binding.quizContent.visibility = View.GONE
        binding.resultsLayout.visibility = View.VISIBLE

        binding.tvScore.text = "$score / $total"
        binding.tvResultMessage.text = when {
            score == total -> if (isKn) "🎉 ಅದ್ಭುತ! ನೀವು ಎಲ್ಲ ಪ್ರಶ್ನೆಗಳಿಗೆ ಸರಿ ಉತ್ತರ ನೀಡಿದ್ದೀರಿ!" else "🎉 Amazing! Perfect score!"
            score >= 2 -> if (isKn) "👍 ಬಹಳ ಚೆನ್ನಾಗಿ ಮಾಡಿದ್ದೀರಿ!" else "👍 Great job! Well done!"
            else -> if (isKn) "📖 ಮತ್ತೊಮ್ಮೆ ಕಥೆ ಓದಿ ಮತ್ತೆ ಪ್ರಯತ್ನಿಸಿ!" else "📖 Read the story again and try!"
        }

        if (score >= 2) {
            val district = DataManager.getDistrictById(this, h.districtId)
            val badge = Badge(
                heroId = h.id,
                heroNameEn = h.nameEn,
                heroNameKn = h.nameKn,
                districtNameEn = district?.nameEn ?: "",
                districtNameKn = district?.nameKn ?: "",
                earnedAt = System.currentTimeMillis(),
                score = score
            )
            prefs.saveBadge(badge)
            binding.badgeContainer.visibility = View.VISIBLE
            binding.tvBadgeHero.text = if (isKn) h.nameKn else h.nameEn
            val anim = AnimationUtils.loadAnimation(this, R.anim.scale_in)
            binding.badgeContainer.startAnimation(anim)
        } else {
            binding.badgeContainer.visibility = View.GONE
        }

        val resultsAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.resultsLayout.startAnimation(resultsAnim)
    }

    private fun showAlreadyEarned() {
        val h = hero ?: return
        val isKn = prefs.isKannada
        binding.quizContent.visibility = View.GONE
        binding.resultsLayout.visibility = View.VISIBLE
        binding.tvScore.text = "⭐"
        binding.tvResultMessage.text = if (isKn) "ಈ ಬ್ಯಾಡ್ಜ್ ಈಗಾಗಲೇ ನಿಮ್ಮಲ್ಲಿದೆ!" else "You already earned this badge!"
        binding.badgeContainer.visibility = View.VISIBLE
        binding.tvBadgeHero.text = if (isKn) h.nameKn else h.nameEn
        binding.btnNext.visibility = View.GONE
        binding.btnCheck.visibility = View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) { onBackPressedDispatcher.onBackPressed(); return true }
        return super.onOptionsItemSelected(item)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
