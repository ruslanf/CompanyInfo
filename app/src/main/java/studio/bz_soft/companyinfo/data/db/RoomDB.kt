package studio.bz_soft.companyinfo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import studio.bz_soft.companyinfo.data.db.converters.Converters
import studio.bz_soft.companyinfo.data.models.db.Companies
import studio.bz_soft.companyinfo.data.models.db.Company
import studio.bz_soft.companyinfo.root.Constants.DB_NAME

@Database(entities = [Companies::class, Company::class],
    version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RoomDB : RoomDatabase() {

    abstract fun companiesDao() : CompaniesDao
    abstract fun companyDao() : CompanyDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getDataBase(context: Context): RoomDB =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: createDB(context).also { INSTANCE = it }
            }

        private fun createDB(context: Context) = Room
            .databaseBuilder(context.applicationContext, RoomDB::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}