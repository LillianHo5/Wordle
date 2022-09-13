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
import android.view.ViewGroup
import android.view.ViewParent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.wordle.FourLetterWordList.getRandomFourLetterWord
import com.github.jinatonic.confetti.CommonConfetti
import com.github.jinatonic.confetti.ConfettiManager

class MainActivity : AppCompatActivity() {
    var confetti: ConfettiManager? = null
    var useStandard = true // determines whether or not to toggle to standard or animal word list
    private var counter = 0
    private var streak = 0
    private var wordToGuess = getRandomFourLetterWord(useStandard)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TextViews used to display the user's guesses and checks
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

        val parent = findViewById<TextView>(R.id.guess1).parent
        // Streak display
        val streakCount = findViewById<TextView>(R.id.streak_count)
        streakCount.text = streak.toString()
        // Buttons used in application, including guess, reset, and toggle to switch lists
        val guessButton = findViewById<Button>(R.id.guess_btn)
        val resetButton = findViewById<Button>(R.id.reset_btn)
        val toggleButton = findViewById<Button>(R.id.toggleNewList_btn)

        val answer = findViewById<TextView>(R.id.answer)
        answer.text = wordToGuess

        /**
         * Resets TextViews to original visibility, resets counter, and chooses a new four letter word
         * for the user to guess in order to allow users to play a new round of Wordle.
         */
        fun reset() {
            confetti?.terminate()
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

            // Grabs a new word for the user to guess
            wordToGuess = getRandomFourLetterWord(useStandard)
            answer.text = wordToGuess
        }

        guessButton.setOnClickListener {
            // Hides Android soft keyboard after clicking on "Guess!" button
            it.hideKeyboard()

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
                val result = checkGuess(guessedWord)
                Log.i("TAG", counter.toString())
                // Make TextViews visible depending on which guess the user is in
                if (counter == 1) {
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
                } else if (counter == 3){
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
                        Toast.makeText(
                            it.context,
                            "Great job!",
                            Toast.LENGTH_SHORT
                        ).show()
                        confetti = CommonConfetti.rainingConfetti(parent as ViewGroup?,
                            intArrayOf(resources.getColor(R.color.sea_foam_green), resources.getColor(R.color.dark_sky_blue),
                                resources.getColor(R.color.ceil), resources.getColor(R.color.light_orange),
                                resources.getColor(R.color.metallic_pink))).stream(10000)
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

        // Change word list used when toggle button is clicked
        toggleButton.setOnClickListener {
            if(useStandard) {
                useStandard = false;
                toggleButton.text = "Basic words"
                reset()
            } else {
                useStandard = true;
                toggleButton.text = "Animal words"
                reset()
            }
            resetButton.visibility = View.INVISIBLE
            guessButton.visibility = View.VISIBLE
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

    /**
     * Hides Android soft keyboard
     */
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}