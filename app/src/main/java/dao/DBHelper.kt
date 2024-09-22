package com.example.bt1.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "users.db"
        const val DATABASE_VERSION = 1

        // Tên các cột
        const val TABLE_USERS = "users"
        const val COLUMN_USER_ID = "id" // ID tự động tăng
        const val COLUMN_USER_NAME = "username"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_FULL_NAME = "name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_TEL = "tel"
        const val COLUMN_DOB = "dob" // Định dạng ngày sinh
        const val COLUMN_GENDER = "gender"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("DBHelper", "onCreate: Creating users table")

        // Tạo bảng users với các cột đã định nghĩa
        val createUserTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USER_NAME VARCHAR(50),
                $COLUMN_PASSWORD VARCHAR(50),
                $COLUMN_FULL_NAME VARCHAR(250),
                $COLUMN_EMAIL VARCHAR(50),
                $COLUMN_TEL VARCHAR(15),
                $COLUMN_DOB DATETIME,
                $COLUMN_GENDER VARCHAR(50)
            )
        """
        db?.execSQL(createUserTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Khi nâng cấp, xóa bảng cũ và tạo lại
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }
}
