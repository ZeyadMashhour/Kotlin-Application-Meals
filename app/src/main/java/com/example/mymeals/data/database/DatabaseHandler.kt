package com.example.mymeals.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mymeals.data.model.Meal
import com.example.mymeals.data.model.MealList

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "MealDatabase"
        private const val TABLE_MEAL = "MealTable"

        private const val KEY_ID_MEAL = "idMeal"
        private const val KEY_STR_AREA = "strArea"
        private const val KEY_STR_CATEGORY = "strCategory"
        private const val KEY_STR_INSTRUCTIONS = "strInstructions"
        private const val KEY_STR_MEAL = "strMeal"
        private const val KEY_STR_MEAL_THUMB = "strMealThumb"
        private const val KEY_STR_YOUTUBE = "strYoutube"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        val createMealTable = ("CREATE TABLE " + TABLE_MEAL + "("
                + KEY_ID_MEAL + " INTEGER PRIMARY KEY,"
                + KEY_STR_AREA + " TEXT,"
                + KEY_STR_CATEGORY + " TEXT,"
                + KEY_STR_INSTRUCTIONS + " TEXT,"
                + KEY_STR_MEAL + " TEXT,"
                + KEY_STR_MEAL_THUMB + " TEXT,"
                + KEY_STR_YOUTUBE + " TEXT)")
        db?.execSQL(createMealTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_MEAL")
        onCreate(db)
    }

    fun deleteMeal(meal: Meal): Int{
        val db = this.writableDatabase
        val success = db.delete(
            TABLE_MEAL,
            KEY_ID_MEAL + "=" + meal.idMeal, null)
        db.close()
        return success
    }

    fun addMeal(meal: Meal): Long {
        val db = this.writableDatabase

        // Check if the ID already exists
        if (mealExists(meal.idMeal.toInt())) {
            // The ID already exists, handle this case accordingly
            db.close()
            return -1
        }

        val contentValues = ContentValues()
        contentValues.put(KEY_ID_MEAL, meal.idMeal.toInt())
        contentValues.put(KEY_STR_AREA, meal.strArea)
        contentValues.put(KEY_STR_CATEGORY, meal.strCategory)
        contentValues.put(KEY_STR_INSTRUCTIONS, meal.strInstructions)
        contentValues.put(KEY_STR_MEAL, meal.strMeal)
        contentValues.put(KEY_STR_MEAL_THUMB, meal.strMealThumb)
        contentValues.put(KEY_STR_YOUTUBE, meal.strYoutube)

        val result = db.insert(TABLE_MEAL, null, contentValues)

        db.close()
        return result
    }

    fun mealExists(id: Int): Boolean {
        val db = this.readableDatabase
        val query = "SELECT $KEY_ID_MEAL FROM $TABLE_MEAL WHERE $KEY_ID_MEAL = $id"
        val cursor = db.rawQuery(query, null)
        val exists = cursor.moveToFirst()
        cursor.close()
        return exists
    }

    fun getMealsFromDatabase(): MealList {
        var mealList = MealList(emptyList())
        val db = this.readableDatabase

        val query = "SELECT * FROM $TABLE_MEAL"

        val cursor = db.rawQuery(query, null)

        cursor.use {
            val meals = mutableListOf<Meal>()
            while (cursor.moveToNext()) {
                val meal = Meal(
                    idMeal = cursor.getInt(cursor.run { getColumnIndex(KEY_ID_MEAL) }).toString(),
                    strArea = cursor.getString(cursor.run { getColumnIndex(KEY_STR_AREA) }),
                    strCategory = cursor.getString(cursor.run { getColumnIndex(KEY_STR_CATEGORY) }),
                    strInstructions = cursor.getString(cursor.run { getColumnIndex(KEY_STR_INSTRUCTIONS) }),
                    strMeal = cursor.getString(cursor.run { getColumnIndex(KEY_STR_MEAL) }),
                    strMealThumb = cursor.getString(cursor.run { getColumnIndex(KEY_STR_MEAL_THUMB) }),
                    strYoutube = cursor.getString(cursor.run { getColumnIndex(KEY_STR_YOUTUBE) })
                )
                meals.add(meal)
            }
            mealList.meals = meals
        }

        db.close()
        return mealList
    }


}