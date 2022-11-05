package canceled

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

internal fun View.hideKeyboard() {
    val keyBoard = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    keyBoard.hideSoftInputFromWindow(windowToken, /*flags: */0)
}