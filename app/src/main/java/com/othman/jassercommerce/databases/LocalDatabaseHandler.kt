package com.othman.jassercommerce.databases

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.util.Log
import com.othman.jassercommerce.models.EstateModel

class LocalDatabaseHandler(context: Context?): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1 // Database version
        private const val DATABASE_NAME = "EstatesDatabase" // Database name
        private const val TABLE_ESTATES = "EstatesTable" // Table Name
        private const val ENCODING_SETTING = "PRAGMA encoding ='windows-1256'" // for enable the arabic

        //All the Columns names
        private const val KEY_ID = "_id"

        private const val KEY_DEAL = "deal"
        private const val KEY_CONTRACT = "contract"
        private const val KEY_TYPE = "type"

        private const val KEY_LOCATION = "location"
        private const val KEY_ROOMS = "rooms"
        private const val KEY_AREA = "area"
        private const val KEY_FLOOR = "floor"
        private const val KEY_DIRECTION = "direction"
        private const val KEY_INTERFACE = "interface"
        private const val KEY_FLOOR_HOUSES = "floorHouses"
        private const val KEY_SITUATION = "situation"
        private const val KEY_FURNITURE = "furniture"
        private const val KEY_FURNITURE_SITUATIONS = "furnitureSituation"
        private const val KEY_LEGAL = "legal"
        private const val KEY_PRICE = "price"
        private const val KEY_POSITIVES = "positives"
        private const val KEY_NEGATIVES = "negatives"

        private const val KEY_OWNER = "owner"
        private const val KEY_OWNER_TEL = "OwnerTel"
        private const val KEY_LOGGER = "Logger"
        private const val KEY_LOGGER_TEL = "LoggerTel"
        private const val KEY_PRIORITY = "priority"
        private const val KEY_RENTER_STANDARDS = "renterStandards"
        private const val KEY_LOGGING_DATE = "loggingDate"

        private const val KEY_IMAGES_NO = "imagesNo"
        private const val KEY_IMAGE_1 = "image_1"
        private const val KEY_IMAGE_2 = "image_2"
        private const val KEY_IMAGE_3 = "image_3"
        private const val KEY_IMAGE_4 = "image_4"
        private const val KEY_IMAGE_5 = "image_5"
        private const val KEY_IMAGE_6 = "image_6"
        private const val KEY_IMAGE_7 = "image_7"
        private const val KEY_IMAGE_8 = "image_8"
        private const val KEY_IMAGE_9 = "image_9"
        private const val KEY_IMAGE_10 = "image_10"

        private val imageKeyList = arrayListOf(KEY_IMAGE_1, KEY_IMAGE_2, KEY_IMAGE_3, KEY_IMAGE_4,
            KEY_IMAGE_5, KEY_IMAGE_6, KEY_IMAGE_7, KEY_IMAGE_8, KEY_IMAGE_9, KEY_IMAGE_10)
    }


    override fun onCreate(db: SQLiteDatabase?) {

        val createEstateTable = "CREATE TABLE IF NOT EXISTS  $TABLE_ESTATES (" +
                "$KEY_ID  INTEGER PRIMARY KEY," +

                "$KEY_DEAL  TEXT," +
                "$KEY_CONTRACT  TEXT," +
                "$KEY_TYPE  TEXT," +

                "$KEY_LOCATION  TEXT," +
                "$KEY_ROOMS  TEXT," +
                "$KEY_AREA  TEXT," +
                "$KEY_FLOOR  TEXT," +
                "$KEY_DIRECTION  TEXT," +
                "$KEY_INTERFACE  TEXT," +
                "$KEY_FLOOR_HOUSES  TEXT," +
                "$KEY_SITUATION  NUMBER," +
                "$KEY_FURNITURE  TEXT," +
                "$KEY_FURNITURE_SITUATIONS  TEXT," +
                "$KEY_LEGAL  TEXT," +
                "$KEY_PRICE  NUMBER," +
                "$KEY_POSITIVES  TEXT," +
                "$KEY_NEGATIVES  TEXT," +

                "$KEY_OWNER  TEXT," +
                "$KEY_OWNER_TEL  TEXT," +
                "$KEY_LOGGER  TEXT," +
                "$KEY_LOGGER_TEL  TEXT," +
                "$KEY_PRIORITY  TEXT," +
                "$KEY_RENTER_STANDARDS  TEXT," +
                "$KEY_LOGGING_DATE  TEXT," +

                "$KEY_IMAGES_NO  TEXT," +
                "$KEY_IMAGE_1  TEXT," +
                "$KEY_IMAGE_2  TEXT," +
                "$KEY_IMAGE_3  TEXT," +
                "$KEY_IMAGE_4  TEXT," +
                "$KEY_IMAGE_5  TEXT," +
                "$KEY_IMAGE_6  TEXT," +
                "$KEY_IMAGE_7  TEXT," +
                "$KEY_IMAGE_8  TEXT," +
                "$KEY_IMAGE_9  TEXT," +
                "$KEY_IMAGE_10  TEXT)"

        db?.execSQL(createEstateTable)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_ESTATES")
        onCreate(db)
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        db?.execSQL(ENCODING_SETTING)
    }

    /**
     * Function to insert a Happy Place details to SQLite Database.
     */
    fun addEstate(estate: EstateModel): Long {
        val db = this.writableDatabase

        val contentValues = createContentValues(estate)

        // Inserting Row
        val result = db.insert(TABLE_ESTATES, null, contentValues)
        //db.close() // Closing database connection
        return result
    }

    /**
     * Function to update record
     */
    fun updateEstate(estate: EstateModel): Int {
        val db = this.writableDatabase

        val contentValues = createContentValues(estate)

        // Updating Row
        val success = db.update(TABLE_ESTATES, contentValues, KEY_ID + "=" + estate.id, null)
        db.close() // Closing database connection
        return success
    }

    /**
     * Function to delete happy place details.
     */
    fun deleteEstate(happyPlace: EstateModel): Int {
        val db = this.writableDatabase
        // Deleting Row
        val success = db.delete(TABLE_ESTATES, KEY_ID + "=" + happyPlace.id, null)
        db.close() // Closing database connection
        return success
    }

    @SuppressLint("Range")
    fun getEstatesList(): ArrayList<EstateModel> {

        // A list is initialize using the data model class in which we will add the values from cursor.
        val estatesList: ArrayList<EstateModel> = ArrayList()

        val selectQuery = "SELECT  * FROM $TABLE_ESTATES" // Database select query

        val db = this.readableDatabase

        try {
            val cursor: Cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {

                    val imagesNo = cursor.getInt(cursor.getColumnIndex(KEY_IMAGES_NO))
                    val imagesList = ArrayList<Uri>()
                    for (image in 0 until imagesNo){
                        imagesList.add( Uri.parse(cursor.getString(cursor.getColumnIndex(imageKeyList[image]))))
                    }

                    val estate = EstateModel(
                        cursor.getInt(cursor.getColumnIndex(KEY_ID)),

                        cursor.getString(cursor.getColumnIndex(KEY_DEAL)),
                        cursor.getString(cursor.getColumnIndex(KEY_CONTRACT)),
                        cursor.getString(cursor.getColumnIndex(KEY_TYPE)),

                        cursor.getString(cursor.getColumnIndex(KEY_LOCATION)),
                        cursor.getString(cursor.getColumnIndex(KEY_ROOMS)),
                        cursor.getInt(cursor.getColumnIndex(KEY_AREA)),
                        cursor.getString(cursor.getColumnIndex(KEY_FLOOR)),
                        cursor.getString(cursor.getColumnIndex(KEY_DIRECTION)),
                        cursor.getString(cursor.getColumnIndex(KEY_INTERFACE)),
                        cursor.getString(cursor.getColumnIndex(KEY_FLOOR_HOUSES)),
                        cursor.getString(cursor.getColumnIndex(KEY_SITUATION)),
                        cursor.getString(cursor.getColumnIndex(KEY_FURNITURE)),
                        cursor.getString(cursor.getColumnIndex(KEY_FURNITURE_SITUATIONS)),
                        cursor.getString(cursor.getColumnIndex(KEY_LEGAL)),
                        cursor.getFloat(cursor.getColumnIndex(KEY_PRICE)),
                        cursor.getString(cursor.getColumnIndex(KEY_POSITIVES)),
                        cursor.getString(cursor.getColumnIndex(KEY_NEGATIVES)),

                        cursor.getString(cursor.getColumnIndex(KEY_OWNER)),
                        cursor.getString(cursor.getColumnIndex(KEY_OWNER_TEL)),
                        cursor.getString(cursor.getColumnIndex(KEY_LOGGER)),
                        cursor.getString(cursor.getColumnIndex(KEY_LOGGER_TEL)),
                        cursor.getString(cursor.getColumnIndex(KEY_PRIORITY)),
                        cursor.getString(cursor.getColumnIndex(KEY_RENTER_STANDARDS)),
                        cursor.getString(cursor.getColumnIndex(KEY_LOGGING_DATE)),
                        imagesList
                        )
                    estatesList.add(estate)

                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        return estatesList
    }


    private fun createContentValues(estate: EstateModel): ContentValues{
        val contentValues = ContentValues()

        contentValues.put(KEY_DEAL, estate.deal)
        contentValues.put(KEY_CONTRACT, estate.contract)
        contentValues.put(KEY_TYPE, estate.type)

        contentValues.put(KEY_LOCATION, estate.location)
        contentValues.put(KEY_ROOMS, estate.rooms)
        contentValues.put(KEY_AREA, estate.area)
        contentValues.put(KEY_FLOOR, estate.floor)
        contentValues.put(KEY_DIRECTION, estate.direction)
        contentValues.put(KEY_INTERFACE, estate.interfaced)
        contentValues.put(KEY_FLOOR_HOUSES, estate.floorHouses)
        contentValues.put(KEY_SITUATION, estate.situation)
        contentValues.put(KEY_FURNITURE, estate.furniture)
        contentValues.put(KEY_FURNITURE_SITUATIONS, estate.furnitureSituation)
        contentValues.put(KEY_LEGAL, estate.legal)
        contentValues.put(KEY_PRICE, estate.price)
        contentValues.put(KEY_POSITIVES, estate.positives)
        contentValues.put(KEY_NEGATIVES, estate.negatives)

        contentValues.put(KEY_OWNER, estate.owner)
        contentValues.put(KEY_OWNER_TEL, estate.ownerTel)
        contentValues.put(KEY_LOGGER, estate.logger)
        contentValues.put(KEY_LOGGER_TEL, estate.loggerTel)
        contentValues.put(KEY_PRIORITY, estate.priority)
        contentValues.put(KEY_RENTER_STANDARDS, estate.renterStandards)
        contentValues.put(KEY_LOGGING_DATE, estate.loggingDate)

        contentValues.put(KEY_IMAGES_NO, estate.images!!.size)
        for (image  in 0 until estate.images.size){
            contentValues.put(imageKeyList[image], estate.images[image].toString())
        }

        return contentValues
    }
}