package com.hostel.playfair

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.Locale

class EncryptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encryption)

        val keyEdit = findViewById<EditText>(R.id.key)
        val keyText = findViewById<TextView>(R.id.keyText)
        val wordEdit = findViewById<EditText>(R.id.word)
        val wordText = findViewById<TextView>(R.id.wordText)

        keyEdit.isSingleLine = true
        wordEdit.isSingleLine = true

        keyText.isSingleLine = true
        wordText.isSingleLine = true

        keyText.setOnClickListener{
            Toast.makeText(this, "Duplicate Characters are Removed", Toast.LENGTH_LONG).show()
        }

        wordText.setOnClickListener{
            Toast.makeText(this, "Js are replaced by I and X by Z", Toast.LENGTH_LONG).show()
        }

        keyEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called to notify you that, within `s`, the `count` characters beginning at
                // index `start` are about to be replaced by new text with a length of `after` characters.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called to notify you that, somewhere within `s`, the text has been changed.
                // Use this callback to update your UI.
                keyText.text = removeDuplicatesAndUpperCase(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // This method is called to notify you that, somewhere within `s`, the text has been changed.
                // Use this callback to perform actions after the text has been changed.
            }
        })
        wordEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called to notify you that, within `s`, the `count` characters beginning at
                // index `start` are about to be replaced by new text with a length of `after` characters.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called to notify you that, somewhere within `s`, the text has been changed.
                // Use this callback to update your UI.
                wordText.text = insertX(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // This method is called to notify you that, somewhere within `s`, the text has been changed.
                // Use this callback to perform actions after the text has been changed.
            }
        })
        val nextButton = findViewById<Button>(R.id.nextButton)
        nextButton.setOnClickListener() {
            if (keyText.text.contains('J') || wordText.text.contains('J')) {
                Toast.makeText(this, "Please Remove J from Key", Toast.LENGTH_LONG).show()
            } else if (!containsOnlyEnglishLetters(keyText.text.toString()) || !containsOnlyEnglishLetters(wordText.text.toString())) {
                Toast.makeText(
                    this,
                    "Please Enter only English Alphabets as Key/Word!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val intent = Intent(this, Matrix::class.java)
                intent.putExtra("KEY", keyText.text.toString())
                intent.putExtra("WORD", wordText.text.toString())
                intent.putExtra("ORGWORD", wordEdit.text.toString())
                startActivity(intent)
            }
        }

        keyEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                // Move focus to the next EditText
                wordEdit.requestFocus()
                return@setOnEditorActionListener true
            }
            false
        }

        wordEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Hide the keyboard
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(wordEdit.windowToken, 0)
                return@setOnEditorActionListener true
            }
            false
        }
    }

    fun removeDuplicatesAndUpperCase(key: String): String {
        val key = key.uppercase(Locale.ROOT) // Convert the key to uppercase
        var result = "" // Initialize an empty string to store the result

        for (c in key) {
            if (c != ' ' && c !in result) { // Check if the character is not a space and not already in the result string
                result += c // If not, add it to the result string
            }
        }

        return result
    }

    fun insertX(input: String): String {
        var result = ""
        val formattedInput = input.replace(" ", "").uppercase(Locale.ROOT)

        for (i in formattedInput.indices) {
            // Replace 'j' with 'i' and 'x' with 'z'
            val charToInsert = when (formattedInput[i]) {
                'J' -> 'I'
                'X' -> 'Z'
                else -> formattedInput[i]
            }
            result += charToInsert

            // Check if the current character is the same as the next character
            // or if the current character is 'J' and the next character is 'I'
            if ((i < formattedInput.length - 1 && formattedInput[i] == formattedInput[i + 1]) ||
                (formattedInput[i] == 'J' && formattedInput.getOrNull(i + 1) == 'I') ||
                (formattedInput[i] == 'I' && formattedInput.getOrNull(i + 1) == 'J') ||
                    (formattedInput[i] == 'X' && formattedInput.getOrNull(i + 1) == 'Z') ||
                    (formattedInput[i] == 'Z' && formattedInput.getOrNull(i + 1) == 'X')) {
                result += 'X' // Insert 'X' between adjacent repeating characters or 'J' and 'I'
            }
        }

        // If the length of the result is odd, add an additional 'X' at the end
        if (result.length % 2 != 0) {
            result += 'X'
        }

        return result
    }

    private fun containsOnlyEnglishLetters(s: String): Boolean {
        val pattern = Regex("^[A-Za-z]+$")
        return pattern.matches(s)
    }
}