package com.example.crocodile

import android.content.Context
import androidx.room.*
/*
@Dao
interface WordDao {
    @Query("SELECT * FROM word")
    fun getAll(): List<Word>

    @Query("SELECT * FROM word WHERE difficulty > (:diff-1) AND difficulty <= :diff")
    fun loadAllByDiff(diff: Int): List<Word>

    @Query("SELECT * FROM word WHERE theme = :theme")
    fun loadAllByTheme(theme: String): List<Word>

    @Query("UPDATE word SET difficulty = difficulty + :amount WHERE word = :word")
    fun updateDiff(word: String ,amount: Double): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg words: Word)

    @Delete
    fun delete(word: Word)
}*/

@Dao
interface VocabDao {
//    @Query("SELECT name FROM vocab")
//    fun getVocab(name: String): Vocab

    @Query("SELECT * FROM vocab")
    fun getAll(): List<Vocab>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg vocabs: Vocab)

    @Delete
    fun delete(vocab: Vocab)
}



@Database(entities = arrayOf(Vocab::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
//    abstract fun wordDao(): WordDao
    abstract fun vocabDao(): VocabDao


    companion object{
        var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "WordDataBase"
                ).build()
            }

            return INSTANCE as AppDatabase
        }
    }

}

/*@Database(entities = arrayOf(Student::class), version = 1, exportSchema = false)
abstract class RoomSingleton : RoomDatabase(){
    abstract fun studentDao():StudentDao

    companion object{
        private var INSTANCE: RoomSingleton? = null
        fun getInstance(context: Context): RoomSingleton{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    RoomSingleton::class.java,
                    "roomdb")
                    .build()
            }

            return INSTANCE as RoomSingleton
        }
    }
}*/
