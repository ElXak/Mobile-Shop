package com.ebo.mobileshop.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ebo.mobileshop.vo.Banner
import com.ebo.mobileshop.vo.Section
import com.ebo.mobileshop.vo.Image
import com.ebo.mobileshop.vo.Item

// Defining database class that extends class RoomDatabase
// with abstract that means you can't instantiate the class directly in your code
// instead you will need instance of a subclass and that will be handled by Room Architecture

// entities is a list of entity classes.
// If there more then 1 entity class put them with comma delimiter
// version of database is Int and starts with 1, but if you change the database structure later
// you upgrade the version of database and you have to add some code to handle database upgrade
// exportSchema = false means don't generate files to document the database
@Database(
    entities = [
        Section::class,
        Banner::class,
        Item::class,
        Image::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MobileShopDb: RoomDatabase() {

    // for each DAO add an abstract fun. Name the fun descriptively
    // and have it return of your DAO instance
    // since sectionDao is instance of interface not superclass don't include "()"
    abstract fun sectionDao(): SectionDao
    abstract fun bannerDao(): BannerDao
    abstract fun itemDao(): ItemDao
    abstract fun imageDao(): ImageDao

    // fun to get the instance of this class
    companion object {
        // Volatile means this obj can be accessed by more then 1 thread at the time
        @Volatile
        private var INSTANCE: MobileShopDb? = null

        fun getDatabase(context: Context): MobileShopDb {
            // if INSTANCE == null create an instance
            if (INSTANCE == null) {
                // synchronized means this block can be called by 1 thread at a time
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        // applicationContext to make sure not an activity context
                        context.applicationContext,
                        MobileShopDb::class.java,
                        // mobileshop.db is name of file in system storage
                        "mobileshop.db"
                    ).build()
                }
            }
            // !! means if it is null crush the application
            return INSTANCE!!
        }

/*
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE sections MODIFY parentId INT NULL")
            }

        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, " +
                        "PRIMARY KEY(`id`))")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Book ADD COLUMN pub_year INTEGER")
            }
        }

        Room.databaseBuilder(applicationContext, MyDb::class.java, "database-name")
        .addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
*/
    }

}