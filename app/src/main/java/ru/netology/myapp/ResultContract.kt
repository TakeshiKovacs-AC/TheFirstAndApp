package ru.netology.myapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

object ResultContractSave : ActivityResultContract<Unit, String?>() {

    override fun createIntent(context: Context, input: Unit) =
        Intent(context, PostContentActivity::class.java)

    override fun parseResult(resultCode: Int, intent: Intent?) =
        if (resultCode == Activity.RESULT_OK) {
            intent?.getStringExtra(RESULT_KEY)
        } else null
}

object ResultContractUpdate : ActivityResultContract<String?, String?>() {

    override fun createIntent(context: Context, input: String?) =
        Intent(context, PostContentActivity::class.java).putExtra(Intent.EXTRA_TEXT, input)

    override fun parseResult(resultCode: Int, intent: Intent?) =
        if (resultCode == Activity.RESULT_OK) {
            intent?.getStringExtra(RESULT_KEY)
        } else null
}

const val RESULT_KEY = "postNewContent"

