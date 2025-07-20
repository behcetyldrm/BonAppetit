package com.example.bonappetit.module

import android.content.Context
import androidx.room.Room
import com.example.bonappetit.database.RecipeDatabase
import com.example.bonappetit.model.RecipeModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : RecipeDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = RecipeDatabase::class.java,
            name = "recipe_database"
        ).build()
    }
}