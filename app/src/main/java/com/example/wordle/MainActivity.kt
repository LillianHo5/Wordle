package com.example.wordle

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.wordle.FourLetterWordList.getRandomFourLetterWord

class MainActivity : AppCompatActivity() {
    private var counter = 0
    private var streak = 0
    private var wordToGuess = getRandomFourLetterWord()

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

        val streakCount = findViewById<TextView>(R.id.streak_count)
        streakCount.text = streak.toString()
        val guessButton = findViewById<Button>(R.id.guess_btn)
        val resetButton = findViewById<Button>(R.id.reset_btn)

        val answer = findViewById<TextView>(R.id.answer)
        answer.text = wordToGuess

        /**
         * Resets TextViews to original visibility, resets counter, and chooses a new four letter word
         * for the user to guess in order to allow users to play a new round of Wordle.
         */
        fun reset() {
            guess1.visibility = View.INVISIBLE
            guess1input.visibility = View.INVISIBLE
            guess1check.visibility = View.INVISIBLE
            guess1result.visibility = View.INVISIBLE
            guess2.visibility = View.INVISIBLE
            guess2input.visibility = View.INVISIBLE
            guess2check.visibility = View.INVISIBLE
            guess2result.visibility = View.INVISIBLE
            guess3.visibility = View.INVISIBLE
            guess3input.visibility = View.INVISIBLE
            guess3check.visibility = View.INVISIBLE
            guess3result.visibility = View.INVISIBLE
            answer.visibility = View.INVISIBLE
            counter = 0

            wordToGuess = getRandomFourLetterWord()
            answer.text = wordToGuess
        }

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
            // Guessed word is a valid input
            else {
                counter += 1
                var result = checkGuess(guessedWord)
                Log.i("TAG", counter.toString())
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
                }

                // Checks if user guessed the correct word within their three tries or if all three tries were used
                if (counter == 3 || guessedWord == wordToGuess) {
                    // Add to streak to keep count of how many words were guessed correctly in one sitting
                    if (guessedWord == wordToGuess) {
                        streak += 1
                        streakCount.text = streak.toString()
                    }
                    answer.visibility = View.VISIBLE

                    guessButton.visibility = View.INVISIBLE
                    resetButton.visibility = View.VISIBLE
                    resetButton.setOnClickListener {
                        reset()
                        resetButton.visibility = View.INVISIBLE
                        guessButton.visibility = View.VISIBLE
                    }
                }
                // Clears EditText after guess is made
                editText.text.clear()
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