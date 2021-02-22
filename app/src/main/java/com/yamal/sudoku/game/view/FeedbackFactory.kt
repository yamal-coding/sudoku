package com.yamal.sudoku.game.view

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatCheckBox
import com.yamal.sudoku.R
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FeedbackFactory(
    private val context: Context
){
    suspend fun showSetUpNewGameHintDialog(): Boolean = suspendCancellableCoroutine { continuation ->
        val view = View.inflate(context, R.layout.set_up_new_game_dialog_hint_layout, null)

        var doNotAskAgain = false
        view.findViewById<AppCompatCheckBox>(R.id.do_not_ask_again_checkbox).setOnCheckedChangeListener { _, isChecked ->
            doNotAskAgain = isChecked
        }

        AlertDialog.Builder(context)
            .setTitle(R.string.set_up_new_game_mode_title)
            .setMessage(R.string.set_up_new_game_mode_hint_message)
            .setView(view)
            .setPositiveButton(R.string.set_up_new_game_mode_ok_button) { dialog, _ -> dialog.dismiss() }
            .setOnDismissListener { continuation.resume(doNotAskAgain) }
            .show()
    }
}