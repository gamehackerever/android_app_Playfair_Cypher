package com.hostel.playfair

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.layout.LookaheadLayout
import androidx.compose.ui.text.capitalize

class Matrix : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matrix)
        val key = intent.getStringExtra("KEY").toString()
        val word = intent.getStringExtra("WORD").toString()
        val orgWord = intent.getStringExtra("ORGWORD").toString()
        val mainButton = findViewById<Button>(R.id.mainButton)
        val finalWord = findViewById<TextView>(R.id.finalWord)
        val cypherText = findViewById<TextView>(R.id.cypher)
        var count = 0

        val matrixFull = generateFullMatrix(key)
        val matrixKey = generateKeyMatrix(key)

        finalWord.isSingleLine = true
        cypherText.isSingleLine = true

        mainButton.setOnClickListener() {
            if (count == 0) {
                displayKeyMatrix(matrixKey)
                mainButton.setText(R.string.fill)
                count++
            } else if (count == 1){
                displayFullMatrix(matrixFull)
                mainButton.setText(R.string.next)
                count++
            } else {
                val textViewIds = listOf(
                    R.id.a,
                    R.id.b,
                    R.id.c,
                    R.id.d,
                    R.id.e,
                    R.id.f,
                    R.id.g,
                    R.id.h,
                    R.id.i,
                    R.id.j,
                    R.id.k,
                    R.id.l,
                    R.id.m,
                    R.id.o,
                    R.id.p,
                    R.id.q,
                    R.id.r,
                    R.id.s,
                    R.id.t,
                    R.id.u,
                    R.id.v,
                    R.id.w,
                    R.id.x,
                    R.id.y,
                    R.id.z
                )
                for (i in textViewIds.indices) {
                    val textView = findViewById<TextView>(textViewIds[i])
                    textView.setBackgroundResource(R.drawable.shape)
                }
                val newCount = count - 2


                if (newCount == ((word.length))) {
                    finalWord.text = orgWord.uppercase()
                    Toast.makeText(this, "Encrypted Successfully!", Toast.LENGTH_SHORT).show()
                    mainButton.setText(R.string.done)


                } else {
                    count += 2
                    //Log.e("TAG", newCount.toString())
                    val indices = findCipherPairIndices(word, matrixFull, newCount)
                    val O1Ind = indices[0][0] * 5 + indices[0][1]
                    val O2Ind = indices[1][0] * 5 + indices[1][1]
                    val C1Ind = indices[2][0] * 5 + indices[2][1]
                    val C2Ind = indices[3][0] * 5 + indices[3][1]

                    val O1 = findViewById<TextView>(textViewIds[O1Ind])
                    val O2 = findViewById<TextView>(textViewIds[O2Ind])
                    val C1 = findViewById<TextView>(textViewIds[C1Ind])
                    val C2 = findViewById<TextView>(textViewIds[C2Ind])

                    finalWord.text = finalWord.text.toString() + O1.text.toString()  + O2.text.toString()
                    cypherText.text = cypherText.text.toString() + C1.text.toString()  + C2.text.toString() + ' '

                    if (O1Ind == C2Ind || O1Ind == C1Ind) {
                        O2.setBackgroundColor(Color.RED)
                        C1.setBackgroundColor(Color.BLUE)
                        C2.setBackgroundColor(Color.BLUE)
                        O1.setBackgroundColor(Color.MAGENTA)

                    } else if (O2Ind == C2Ind || O2Ind == C1Ind) {
                        O1.setBackgroundColor(Color.RED)
                        C1.setBackgroundColor(Color.BLUE)
                        C2.setBackgroundColor(Color.BLUE)
                        O2.setBackgroundColor(Color.MAGENTA)
                    } else {
                        O1.setBackgroundColor(Color.RED)
                        O2.setBackgroundColor(Color.RED)
                        C1.setBackgroundColor(Color.BLUE)
                        C2.setBackgroundColor(Color.BLUE)
                    }
                }
            }
        }

        finalWord.setOnLongClickListener {
            // Get the text from the TextView
            val text = finalWord.text

            // Copy the text to the clipboard
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", text)
            clipboard.setPrimaryClip(clip)

            // Show a toast indicating that the text has been copied
            Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_SHORT).show()

            // Return true to indicate that the long click event is handled
            true
        }

        cypherText.setOnLongClickListener {
            // Get the text from the TextView
            val text = cypherText.text

            // Copy the text to the clipboard
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", text)
            clipboard.setPrimaryClip(clip)

            // Show a toast indicating that the text has been copied
            Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_SHORT).show()

            // Return true to indicate that the long click event is handled
            true
        }


    }

    private fun generateFullMatrix(key: String): Array<CharArray> {
        // Initialize a 5x5 matrix
        val matrix = Array(5) { CharArray(5) }

        val keyIndex = 0 // Initialize an index for the key characters

        fillKey(matrix, key, keyIndex)
        fillRest(matrix, key, keyIndex)

        return matrix
    }

    private fun generateKeyMatrix(key: String): Array<CharArray> {
        // Initialize a 5x5 matrix
        val matrix = Array(5) { CharArray(5) }

        val keyIndex = 0 // Initialize an index for the key characters

        fillKey(matrix, key, keyIndex)

        return matrix
    }


    private fun joinMatrix(matrix: Array<CharArray>): CharArray {
        val rows = matrix.size
        val cols = matrix[0].size

        val joinedArray = CharArray(rows * cols)
        var index = 0

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                joinedArray[index++] = matrix[i][j]
            }
        }
        return joinedArray
    }

    private fun displayKeyMatrix(matrix: Array<CharArray>) {
        val joined = joinMatrix(matrix)

        val textViewIds = listOf(R.id.a, R.id.b, R.id.c, R.id.d, R.id.e, R.id.f, R.id.g, R.id.h, R.id.i, R.id.j, R.id.k, R.id.l, R.id.m, R.id.o, R.id.p, R.id.q, R.id.r, R.id.s, R.id.t, R.id.u, R.id.v, R.id.w, R.id.x, R.id.y, R.id.z)

        for (i in textViewIds.indices) {
            val textView = findViewById<TextView>(textViewIds[i])
            textView.text = joined[i].toString()
        }
    }

    private fun displayFullMatrix(matrix: Array<CharArray>) {
        val joined = joinMatrix(matrix)

        val textViewIds = listOf(R.id.a, R.id.b, R.id.c, R.id.d, R.id.e, R.id.f, R.id.g, R.id.h, R.id.i, R.id.j, R.id.k, R.id.l, R.id.m, R.id.o, R.id.p, R.id.q, R.id.r, R.id.s, R.id.t, R.id.u, R.id.v, R.id.w, R.id.x, R.id.y, R.id.z)

        for (i in textViewIds.indices) {
            val textView = findViewById<TextView>(textViewIds[i])
            textView.text = joined[i].toString()
        }
    }

    private fun fillKey(matrix: Array<CharArray>, key: String, keyIndex: Int): Array<CharArray> {
        for (row in 0 until 5) {
            for (col in 0 until 5) {
                matrix[row][col] = 'X'
            }
        }

        var index = keyIndex
        for (c in key) {
            //Log.e("TAG", index.toString())
            // Fill the matrix with the key characters
            matrix[index / 5][index % 5] = c // Place the character in the matrix
            index++ // Move to the next index
        }
        return matrix
    }

    private fun fillRest(matrix: Array<CharArray>, key: String, keyIndex: Int): Array<CharArray> {
        val alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ" // Exclude 'J' from the alphabet

        var index = keyIndex + key.length
        for (c in alphabet) {
            //Log.e("TAG", keyIndex.toString())
            if (!key.contains(c)) { // If the character is not in the key
                matrix[index / 5][index % 5] = c // Place it in the matrix
                index++ // Move to the next index
                Log.w("TAG", index.toString())
            }
        }

        return matrix
    }

    fun findCipherPairIndices(plaintext: String, matrix: Array<CharArray>, i: Int): Array<IntArray> {
        val char1 = plaintext[i] // Get the first character of the pair
        val char2 = plaintext[i + 1]// Get the second character, or 'X' if it doesn't exist

        var row1: Int
        var col1: Int
        var row2: Int
        var col2: Int

        var pairs: IntArray = findPosition(matrix, char1) // Find position of first character
        row1 = pairs[0]
        col1 = pairs[1]

        Log.e("TAG", col1.toString())
        Log.e("TAG", row1.toString())

        pairs = findPosition(matrix, char2) // Find position of second character
        row2 = pairs[0]
        col2 = pairs[1]

        val indices = Array(4) { IntArray(2) }

        indices[0][0] = row1
        indices[0][1] = col1
        indices[1][0] = row2
        indices[1][1] = col2

        // Case 1: If both characters are in the same row
        if (row1 == row2) {
            col1 = (col1 + 1) % 5 // Move to the right by 1 (taking care of boundary)
            col2 = (col2 + 1) % 5 // Move to the right by 1 (taking care of boundary)
        }
        // Case 2: If both characters are in the same column
        else if (col1 == col2) {
            row1 = (row1 + 1) % 5 // Move down by 1 (taking care of boundary)
            row2 = (row2 + 1) % 5 // Move down by 1 (taking care of boundary)
        }
        // Case 3: If characters form a rectangle in the matrix
        else {
            val temp = col1
            col1 = col2
            col2 = temp
            // Swap column of first character with column of second character
            // Swap column of second character with column of first character
        }

        indices[2][0] = row1
        indices[2][1] = col1
        indices[3][0] = row2
        indices[3][1] = col2
        return indices
    }

    fun findPosition(matrix: Array<CharArray>, ch: Char): IntArray {
        var row = -1
        var col = -1 // Initialize row and column to -1 (indicating not found)

        // Search for the character in the matrix
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == ch) { // If character found
                    row = i // Set the row
                    col = j // Set the column
                    return intArrayOf(row, col) // Exit the function
                }
            }
        }
        Log.e("TAG", col.toString())
        Log.e("TAG", row.toString())

        return intArrayOf(row, col)
    }


}