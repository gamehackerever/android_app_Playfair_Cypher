package com.hostel.playfair

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.TextView

class about : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val about = findViewById<TextView>(R.id.about)
        about.text = Html.fromHtml("    <b>The Playfair cipher</b> is a cryptographic technique used for encryption and decryption of messages. It was invented by Sir Charles Wheatstone in 1854, but later popularized by Baron Playfair in the 19th century. It's a form of a <i>substitution cipher</i>, where each letter in the plaintext is replaced by another letter according to a defined set of rules.\n" +
                "    <br><br>\n" +
                "    Here's how the Playfair cipher works:\n" +
                "    <br><br>\n" +
                "    <b>Key Matrix Formation:</b> First, a key matrix or key table is created. This is typically a 5x5 grid (known as the Playfair square) containing all the letters of the alphabet except for \"J\" (which is usually merged with \"I\" to reduce the grid to 25 letters).\n" +
                "    <br><br>\n" +
                "    <b>Key Generation:</b> The key is used to generate this square, typically by arranging a keyword without repeating letters followed by the remaining letters of the alphabet. For example, if the keyword is \"MONARCHY\", the Playfair square would be:\n" +
                "    <br><br>\n" +
                "        M O N A R<br>\n" +
                "        C H Y B D<br>\n" +
                "        E F G I K<br>\n" +
                "        L P Q S T<br>\n" +
                "        U V W X Z<br><br>\n" +
                "    <b>Handling Repeated Letters:</b> If the plaintext contains any repeated letters or consecutive identical letters, they are separated by a filler letter, usually \"X\". For example, \"HELLO\" becomes \"HELXLO\".\n" +
                "    <br><br>\n" +
                "    <b>Encryption:</b> The plaintext is then processed in pairs of letters. For each pair:\n" +
                "    <br><br>\n" +
                "    - If the letters are in the same row of the grid, they are replaced by the letters to their right, wrapping around to the beginning of the row if necessary.\n" +
                "    <br>\n" +
                "    - If the letters are in the same column, they are replaced by the letters below them, wrapping around to the top if necessary.\n" +
                "    <br>\n" +
                "    - If the letters form a rectangle, they are replaced by the letters on the same row, but at the opposite corners of the rectangle.\n" +
                "    <br>\n" +
                "    - If the letters are different and not in the same row or column, they are replaced by the letters at the corners of the rectangle they form.\n" +
                "    <br><br>\n" +
                "    <b>Decryption:</b> Decryption is essentially the reverse of encryption. Each pair of letters in the ciphertext is processed according to the same rules, but in reverse.\n" +
                "    <br><br>\n" +
                "    <b>Handling Odd Number of Letters:</b> If the plaintext has an odd number of letters, a filler letter (often \"X\") is added at the end before encryption.\n" +
                "    <br><br>\n" +
                "    The Playfair cipher offers a more secure method of encryption compared to simpler substitution ciphers, as it operates on pairs of letters rather than individual letters, making frequency analysis more difficult. However, it can still be susceptible to certain types of attacks, especially if the key matrix is compromised or if the language of the plaintext is known.")
    }
}