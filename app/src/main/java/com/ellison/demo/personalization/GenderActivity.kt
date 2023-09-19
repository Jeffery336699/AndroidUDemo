package com.ellison.demo.personalization

import android.app.GrammaticalInflectionManager
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ellison.demo.R
import com.ellison.demo.databinding.GenderLayoutBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 作者博客:
 *      https://blog.csdn.net/allisonchen/article/details/131269883?spm=1001.2014.3001.5501
 */
class GenderActivity : AppCompatActivity() {
    private lateinit var gIM: GrammaticalInflectionManager
    private lateinit var binding: GenderLayoutBinding

    @RequiresApi(34)
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Gender", "onCreate()")

        binding = GenderLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gIM = getSystemService(GrammaticalInflectionManager::class.java)
        updateCurrentGender()

        binding.changeGender.setOnClickListener {
            Log.d("Gender", "change Gender button tapped")

            // Set app's grammatical gender to feminine
            gIM.setRequestedApplicationGrammaticalGender(
                gIM.applicationGrammaticalGender.nextGender()
            )

            // updateCurrentGender()
            GlobalScope.launch(Dispatchers.Main) {
                delay(50)
                updateCurrentGender()
            }
        }
    }

    @RequiresApi(34)
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d("Gender", "GenderActivity# onConfigurationChanged()" +
                " new gender:${newConfig.grammaticalGender.genderToString()}" +
                // " current gender:${gIM.applicationGrammaticalGender.genderToString()}" +
                " current text:${binding.textview.text}"
        )

        // Resources will be updated with new configuration
        val newText = resources.getString(R.string.example_string)
        Log.d("Gender", "onConfigurationChanged()" +
                " new text:${resources.getString(R.string.example_string)}"
        )
        // work finally
        binding.textview.text = newText

        // not work
        binding.textview.invalidate()

        // not work either
        binding.textview.dispatchConfigurationChanged(newConfig)
        // 自定义 TextView 复写 onConfigurationChanged()

        // TODO: 尽是bug,依然没有效果??
    }

    @RequiresApi(34)
    private fun updateCurrentGender() {
        gIM.applicationGrammaticalGender.genderToString().let {
            Log.d("Gender", "current gender: $it")
            binding.changeGender.text = "Grammatical gender:$it"
        }
    }

    private fun Int.nextGender() =
        when (this) {
            Configuration.GRAMMATICAL_GENDER_NEUTRAL -> Configuration.GRAMMATICAL_GENDER_MASCULINE
            Configuration.GRAMMATICAL_GENDER_MASCULINE -> Configuration.GRAMMATICAL_GENDER_FEMININE
            Configuration.GRAMMATICAL_GENDER_FEMININE -> Configuration.GRAMMATICAL_GENDER_NOT_SPECIFIED
            Configuration.GRAMMATICAL_GENDER_NOT_SPECIFIED -> Configuration.GRAMMATICAL_GENDER_NEUTRAL
            else -> Configuration.GRAMMATICAL_GENDER_NEUTRAL
        }
}

fun Int.genderToString() =
        when (this) {
            Configuration.GRAMMATICAL_GENDER_NEUTRAL -> "Neutral"
            Configuration.GRAMMATICAL_GENDER_MASCULINE -> "Masculine"
            Configuration.GRAMMATICAL_GENDER_FEMININE -> "Feminine"
            Configuration.GRAMMATICAL_GENDER_NOT_SPECIFIED -> "Not specified"
            else -> "Not specified"
        }