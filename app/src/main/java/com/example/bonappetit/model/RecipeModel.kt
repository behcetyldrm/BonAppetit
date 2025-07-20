package com.example.bonappetit.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val ingredient: List<String>,
    val description: String,
    val image: ByteArray?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RecipeModel

        if (name != other.name) return false
        if (ingredient != other.ingredient) return false
        if (description != other.description) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + ingredient.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + image.contentHashCode()
        return result
    }
}
