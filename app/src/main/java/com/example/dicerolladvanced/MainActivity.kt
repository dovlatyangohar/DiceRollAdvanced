package com.example.dicerolladvanced

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cutsom_layout.view.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var winnerScore: Int = 50  //default values
    private var roundCounter: Int = 10
    private lateinit var diceOneImage: ImageView
    private lateinit var diceTwoImage: ImageView
    private lateinit var resultText: String
    private lateinit var playerOne: TextView
    private lateinit var playerTwo: TextView
    private var dialogTitle = "Starting the game..."
    private var playerOneScore: Int = 0
    private var playerTwoScore: Int = 0
    private var sumOfDices: Int = 0
    private var playerOneTurn: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        diceOneImage = findViewById(R.id.dice_image1)
        diceTwoImage = findViewById(R.id.dice_image2)
        playerOne = findViewById(R.id.player_one)
        playerTwo = findViewById(R.id.player_two)

        start_game_btn.setOnClickListener {
            showDialog()
        }

        roll_btn.setOnClickListener {
            if (playerOneTurn) {
                playerOneScore = sumOfDices
                playerOne.text = "Player 1 score: $playerOneScore"
                rollDice()

            } else {
                playerTwoScore = sumOfDices
                playerTwo.text = "Player 2 score: $playerTwoScore"
                rollDice()

            }

        }
        reset_btn.setOnClickListener {
            reset()
        }
    }

    private fun showDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.cutsom_layout, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle(dialogTitle)
        val mAlertDialog = mBuilder.show()
        mDialogView.start_btn.setOnClickListener {
            mAlertDialog.dismiss()
            if (mDialogView.dialog_winner_score.text.toString() != "" && mDialogView.dialog_edge_point.text.toString() != "") {
                winnerScore = mDialogView.dialog_winner_score.text.toString().toInt()
                roundCounter = mDialogView.dialog_edge_point.text.toString().toInt()
            } else {
                Toast.makeText(this, "Starting with default values...", Toast.LENGTH_SHORT).show()

            }


        }
        mDialogView.cancel_btn.setOnClickListener { mAlertDialog.dismiss() }
    }

    private fun rollDice() {
        diceOneImage.setImageResource(getRandomDiceImage())
        val diceOnePoints = resultText.toInt()

        diceTwoImage.setImageResource(getRandomDiceImage())
        val diceTwoPoints = resultText.toInt()
        sumOfDices += diceOnePoints + diceTwoPoints

        playerOneTurn = !playerOneTurn
        checkForWin()
        (roundCounter)--
        Toast.makeText(this,"$roundCounter",Toast.LENGTH_SHORT).show()

    }

    private fun getRandomDiceImage(): Int {
        val result = Random().nextInt(6) + 1
        resultText = result.toString()
        return when (result) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }

    private fun checkForWin() {

        if (roundCounter > 0) {
            if (playerOneScore >= winnerScore) {
                playerOneWins()
            }
            if (playerTwoScore >= winnerScore) {
                playerTwoWins()
            }
        } else {
            dialogTitle = "Limit has expired"
            showDialog()
            reset()
        }


    }

    private fun playerOneWins() {
        dialogTitle = "Player One Wins!!"
        showDialog()
        reset()
    }

    private fun playerTwoWins() {
        dialogTitle = "Player Two Wins!!"
        showDialog()
        reset()
    }

    private fun reset() {
        winnerScore = 50
        roundCounter = 10
        sumOfDices = 0
        playerOneScore = 0
        playerTwoScore = 0
        playerOne.text = "Player 1 score: $playerOneScore"
        playerTwo.text = "Player 2 score: $playerTwoScore"
        playerOneTurn = true
        dialogTitle = "Starting the game..."

    }

}

