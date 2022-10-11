package com.example.prodan.user.database


import  android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [GalleryImg::class],version=1)
abstract class ProdanDatabase : RoomDatabase() {

    abstract  fun galleryImgDao(): ProdanDao


    companion object {

        @Volatile
            private var INSTANCE: ProdanDatabase? = null

        fun getDatabase(context: Context) : ProdanDatabase {

            return  INSTANCE ?: synchronized(this) {

                val instance = Room
                    .databaseBuilder(context, ProdanDatabase::class.java, "galleryImg_db")
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }


}