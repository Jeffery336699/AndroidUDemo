package com.ellison.demo.path

import android.graphics.Path
import android.graphics.PathIterator
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ellison.demo.databinding.ActivityPathBinding

class PathActivity : AppCompatActivity() {
    @RequiresApi(34)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPathBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val path = Path().apply {
            moveTo(1.0f, 1.0f)
            lineTo(2.0f, 2.0f)
            close()
        }

        val pathIterator = path.pathIterator
        val builder = StringBuilder()
        for (segment in pathIterator) {
            Log.i("Path", "segment action: ${segment.verb.toPathAction()}")
            builder.append("segment action: ${segment.verb.toPathAction()}").append("\n")
            for (f in segment.points) {
                Log.i("Path", "point: $f")
                builder.append("\t").append("\t").append("point: $f").append("\n")
            }
        }
        binding.textView.text = builder.toString()
    }

    private fun Int.toPathAction() =
        when (this) {
            PathIterator.VERB_MOVE  -> "VERB_MOVE"
            PathIterator.VERB_LINE  -> "VERB_LINE"
            PathIterator.VERB_QUAD  -> "VERB_QUAD"
            PathIterator.VERB_CONIC -> "VERB_CONIC"
            PathIterator.VERB_CUBIC -> "VERB_CUBIC"
            PathIterator.VERB_CLOSE -> "VERB_CLOSE"
            PathIterator.VERB_DONE  -> "VERB_DONE"
            else                    -> "VERB_DONE"
        }
}