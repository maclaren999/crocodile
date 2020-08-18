package com.example.crocodile

import android.content.res.ColorStateList
import android.graphics.Color
import kotlinx.android.synthetic.main.activity_main.*

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import androidx.core.graphics.toColor
import com.example.crocodile.com.example.crocodile.fromJson
import com.example.crocodile.com.example.crocodile.getVocabsfromAsset
import com.ginsberg.cirkle.circular
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception
import com.google.common.collect.*

class MainActivity : AppCompatActivity() {

    private var default_vocab: List<String>? = null
//    private var hard_vocab: List<String>? = null

    //    lateinit var current_vocab : ListIterator<String>?
    lateinit var current_vocab: List<String>
    var current_vocab_iterator: Iterator<String?>? = null


    lateinit var db: AppDatabase
    var dbReady: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var vocabLib: Map<String, List<String>> = mapOf<String, List<String>>()
        var vocabLibJson: String = ""
        vocabLibJson = getVocabsfromAsset(applicationContext) ?: ""
        Log.i("DATA1", vocabLibJson)
        vocabLib = fromJson(vocabLibJson)
        Log.i("DATA1", vocabLib.values.toString())

        runOnUiThread {
            buttonNextWord.setTextColor(Color.GRAY)
            buttonNextWord.isClickable = false
        }
        val fillBDjob = CoroutineScope(IO).launch {
            fillDBonCreate(vocabLib)
            dbReady = true
        }

        while (dbReady == false) {
            Thread.sleep(10)
        }
        runOnUiThread {
            buttonNextWord.setTextColor(R.color.colorAccent.toInt())
            buttonNextWord.isClickable = true
        }
        setVocabIterator(vocabLib["hard"]
            ?: listOf("Error: empty vocab").also { throw Exception("Default vocab is NULL!") })
    }

    fun setVocabIterator(vocab: List<String>) {
        current_vocab = vocab.shuffled()
        current_vocab_iterator = Iterables.cycle(current_vocab)?.iterator()
    }

    suspend fun fillDBonCreate(vocabs: Map<String, List<String>>) {
        db = AppDatabase.getInstance(applicationContext)
        Log.i("TAG", "DB created")

        for (i in vocabs) {
            db.vocabDao().insertAll(Vocab(i.key, i.value))
        }
    }
    //For single word concept
/*    suspend fun fillDBonCreate(vocabs: Map<String, List<String?>>) {
            db = AppDatabase.getInstance(applicationContext)
            Log.i("TAG", "DB created")

            vocabs.values.forEach {
                db.wordDao().insertAll(
                    *it.map { str ->
                        Word(str!!)
                    }.toTypedArray()
                )
            }*/
    /*.forEach{
    val wordList = mutableListOf<Word>()
    for (str in it){
        if (str == null) throw Exception("Null String in JSON vocabulary!")
        else{
            wordList.add(Word(str))
        }
        Log.i("TAG", str)

    }
    //db.wordDao().insertAll(*wordList.toTypedArray())
}*/

    fun nextWord(view: View) {
        try {
            textWord.text = current_vocab_iterator?.next() ?: "NULL"
            buttonNextWord.isClickable = false
            val handler = android.os.Handler()
            handler.postDelayed({ buttonNextWord.isClickable = true }, 100)
        } catch (e: Exception) {
            Log.d("nextWord", e.message?.toString() ?: "Error Message is NULL!!!!!!!!!!!")
        }
    }
    /*    fun setNormalVocab(view: View){
    //        current_vocab = vocabLib["normal"]?.listIterator() ?: null  //listOf("Error: no such vocabulary")
            default_vocab = vocabLib["normal"]?.shuffled() ?: listOf("Error: no such vocabulary")
            current_vocab =
        }
        fun setHardVocab(view: View){
    //        current_vocab = vocabLib["hard"]?.listIterator() ?: null
        }*/

/*

    fun setVocabulary(view: RadioGroup){
        view.checkedRadioButtonId
    }
*/
}

//DONE: выбор сложности
//DONE: json словари из assets
//DONE: итерации по кругу
//TODO: github
//TODO: база данных SQLite*-ROOM (Coroutines*)
//TODO: выбор словаря из выпадающего списка
//TODO: свой итератор со встряхиваением на новом кругу
//TODO: база данных FIREBASE
//TODO: добавление своих слов
//TODO: рейтинг слов
