package com.example.crocodile
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
/*
@Entity
data class Word(
    //@PrimaryKey(autoGenerate = true) val id: Int,
    @PrimaryKey val word: String,
//    @ColumnInfo(name = "id", index = true) val id: Int,
    @ColumnInfo(name = "difficulty") val difficulty: Double? = null,
    @ColumnInfo(name = "theme") val theme: String? = null
    ){
    fun Word.toString(): String{
        return this.word
    }
}*/

@Entity
data class Vocab(
    //@PrimaryKey(autoGenerate = true) val id: Int,
    @PrimaryKey val name: String,
    @ColumnInfo(name = "list") val list: List<String>,
    @ColumnInfo(name = "difficulty") val difficulty: Double? = null,
    @ColumnInfo(name = "ageRate") val ageRate: Int? = 0
    ){
    fun Vocab.toString(): String{
        return this.name
    }
}