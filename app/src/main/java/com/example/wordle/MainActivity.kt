package com.example.wordle

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.wordle.FourLetterWordList.getRandomFourLetterWord

class MainActivity : AppCompatActivity() {
    private var counter = 0;
    private val wordToGuess = getRandomFourLetterWord()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val guess1 = findViewById<TextView>(R.id.guess1)
        val guess1input = findViewById<TextView>(R.id.guess1input)
        val guess1check = findViewById<TextView>(R.id.guess1check)
        val guess1result = findViewById<TextView>(R.id.guess1result)
        val guess2 = findViewById<TextView>(R.id.guess2)
        val guess2input = findViewById<TextView>(R.id.guess2input)
        val guess2check = findViewById<TextView>(R.id.guess2check)
        val guess2result = findViewById<TextView>(R.id.guess2result)
        val guess3 = findViewById<TextView>(R.id.guess3)
        val guess3input = findViewById<TextView>(R.id.guess3input)
        val guess3check = findViewById<TextView>(R.id.guess3check)
        val guess3result = findViewById<TextView>(R.id.guess3result)

        val answer = findViewById<TextView>(R.id.answer)
        answer.text = wordToGuess
        val guessButton = findViewById<Button>(R.id.button)

        guessButton.setOnClickListener {
            val editText = findViewById<EditText>(R.id.guessedWordInput)
            val guessedWord = editText.text.toString().uppercase()

            // Check if the guessed word is a valid input (guessed word is 4 letters long and contains only letters)
            if (guessedWord.length != 4 || !lettersCheck(guessedWord)) {
                Toast.makeText(
                    it.context,
                    "Invalid guess. Guess with a word that has 4 letters!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            // guessed word is a valid input
            else {
                counter += 1;
                var result = checkGuess(guessedWord)

                if (counter > 3) {
                    Toast.makeText(
                        it.context,
                        "You have exceeded the maximum number of guesses!",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Prevents users from making another guess
                    guessButton.isEnabled = false
                    guessButton.isClickable = false
                } else if (counter == 1) {
                    guess1input.text = guessedWord
                    guess1result.text = result
                    guess1.visibility = View.VISIBLE
                    guess1input.visibility = View.VISIBLE
                    guess1check.visibility = View.VISIBLE
                    guess1result.visibility = View.VISIBLE
                } else if (counter == 2) {
                    guess2input.text = guessedWord
                    guess2result.text = result
                    guess2.visibility = View.VISIBLE
                    guess2input.visibility = View.VISIBLE
                    guess2check.visibility = View.VISIBLE
                    guess2result.visibility = View.VISIBLE
                } else {
                    guess3input.text = guessedWord
                    guess3result.text = result
                    guess3.visibility = View.VISIBLE
                    guess3input.visibility = View.VISIBLE
                    guess3check.visibility = View.VISIBLE
                    guess3result.visibility = View.VISIBLE
                    answer.visibility = View.VISIBLE
                }

                if (guessedWord == wordToGuess) {
                    answer.visibility = View.VISIBLE
                }
            }
        }
    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of the guessed word where:
     *   green represents the right letter in the right place
     *   yellow represents the right letter in the wrong place
     *   red represents a letter not in the target word
     */
    private fun checkGuess(guess: String): SpannableString {
        val result = SpannableString(guess)
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result.setSpan(
                    ForegroundColorSpan(Color.GREEN),
                    i,
                    i + 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            } else if (guess[i] in wordToGuess) {
                result.setSpan(
                    ForegroundColorSpan(resources.getColor(R.color.road_sign_yellow)),
                    i,
                    i + 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            } else {
                result.setSpan(
                    ForegroundColorSpan(Color.RED),
                    i,
                    i + 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return result
    }

    /**
     * Parameter / Field:
     *   guess : String - what the user entered as their guess
     *
     * Returns a Boolean where:
     *   true if all characters in the guessed word are letters
     *   false if the guessed word contains character(s) that are not letters
     */
    private fun lettersCheck(guess: String): Boolean {
        return guess.all {
            it.isLetter()
        }
    }
}