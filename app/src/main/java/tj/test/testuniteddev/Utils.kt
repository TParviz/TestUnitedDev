package tj.test.testuniteddev

import android.content.Context
import android.widget.Toast

fun showMessage(message: String, context: Context){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}