package com.example.bt1.dao

import com.example.bt1.model.AppUser
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class UserDAO(context: Context) {
    private val dbHelper = DBHelper(context)
    private val database: SQLiteDatabase = dbHelper.writableDatabase

    // Hàm thêm user vào database
    fun add(user: AppUser): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COLUMN_USER_NAME, user.userName)
            put(DBHelper.COLUMN_PASSWORD, user.password)
            put(DBHelper.COLUMN_FULL_NAME, user.fullName)
            put(DBHelper.COLUMN_EMAIL, user.email)
            put(DBHelper.COLUMN_TEL, user.tel)
            put(DBHelper.COLUMN_DOB, user.dateOfBirth)
            put(DBHelper.COLUMN_GENDER, user.gender)
        }

        // Thêm user vào cơ sở dữ liệu và lấy ID mới sinh ra
        val newId = database.insert(DBHelper.TABLE_USERS, null, values)
        return if (newId != -1L) {
            newId.toInt()  // Chuyển Long sang Int nếu insert thành công
        } else {
            -1 // Nếu insert thất bại, trả về -1
        }
    }

    // Hàm sửa thông tin user trong database (cập nhật user)
    fun update(user: AppUser): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COLUMN_USER_NAME, user.userName)
            put(DBHelper.COLUMN_PASSWORD, user.password)
            put(DBHelper.COLUMN_FULL_NAME, user.fullName)
            put(DBHelper.COLUMN_EMAIL, user.email)
            put(DBHelper.COLUMN_TEL, user.tel)
            put(DBHelper.COLUMN_DOB, user.dateOfBirth)
            put(DBHelper.COLUMN_GENDER, user.gender) // Thêm gender khi cập nhật
        }

        val result = database.update(
            DBHelper.TABLE_USERS,
            values,
            "${DBHelper.COLUMN_USER_ID} = ?",
            arrayOf(user.id.toString())
        )
        return result > 0
    }

    // Hàm xóa user khỏi database theo ID
    fun delete(id: Int): Boolean {
        val db = dbHelper.writableDatabase
        val result = database.delete(
            DBHelper.TABLE_USERS,
            "${DBHelper.COLUMN_USER_ID} = ?",
            arrayOf(id.toString())
        )
        return result > 0
    }

    // Hàm tìm kiếm user theo từ khóa (tìm theo username hoặc email)
    fun search(userName: String = "", email: String = "", tel: String = ""): List<AppUser> {
        val db = dbHelper.writableDatabase
        val userList = ArrayList<AppUser>()
        val query = "SELECT * FROM ${DBHelper.TABLE_USERS} WHERE ${DBHelper.COLUMN_USER_NAME} LIKE ? OR ${DBHelper.COLUMN_EMAIL} LIKE ? OR ${DBHelper.COLUMN_TEL} LIKE ?"
        val cursor: Cursor = database.rawQuery(query, arrayOf("%$userName%", "%$email%", "%$tel%"))

        if (cursor.moveToFirst()) {
            do {
                val user = AppUser(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_USER_ID)),
                    userName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_USER_NAME)),
                    password = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PASSWORD)),
                    fullName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_FULL_NAME)),
                    email = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_EMAIL)),
                    tel = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TEL)),
                    dateOfBirth = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DOB)),
                    gender = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_GENDER))
                )
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return userList
    }

    // Thêm hàm getAllUsers trong UserDAO
    fun getAllUsers(): List<AppUser> {
        val db = dbHelper.writableDatabase
        val userList = ArrayList<AppUser>()
        val query = "SELECT * FROM ${DBHelper.TABLE_USERS}"
        val cursor: Cursor = database.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val user = AppUser(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_USER_ID)),
                    userName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_USER_NAME)),
                    password = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PASSWORD)),
                    fullName = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_FULL_NAME)),
                    email = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_EMAIL)),
                    tel = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TEL)),
                    dateOfBirth = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DOB)),
                    gender = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_GENDER))
                )
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return userList
    }

    // Thêm hàm edit trong UserDAO
    fun edit(user: AppUser): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COLUMN_USER_NAME, user.userName)
            put(DBHelper.COLUMN_PASSWORD, user.password)
            put(DBHelper.COLUMN_FULL_NAME, user.fullName)
            put(DBHelper.COLUMN_EMAIL, user.email)
            put(DBHelper.COLUMN_TEL, user.tel)
            put(DBHelper.COLUMN_DOB, user.dateOfBirth)
            put(DBHelper.COLUMN_GENDER, user.gender) // Thêm cột gender nếu có
        }

        // Cập nhật thông tin người dùng trong database, dựa vào ID của user
        val result = database.update(
            DBHelper.TABLE_USERS,
            values,
            "${DBHelper.COLUMN_USER_ID} = ?",
            arrayOf(user.id.toString())
        )
        // Kiểm tra nếu cập nhật thành công
        return result > 0
    }



}
