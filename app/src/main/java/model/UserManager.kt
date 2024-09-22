package com.example.bt1.model

import android.content.Context  // Import Context
import com.example.bt1.dao.UserDAO  // Import UserDAO

class UserManager(private val context: Context) {
    private val userDAO = UserDAO(context)  // Truyền Context vào UserDAO

    fun addUser(user: AppUser): Boolean {
        return if (!isUserExists(user.userName) && !isEmailExists(user.email) && !isTelExists(user.tel)) {
            userDAO.add(user) > 0  // Thêm vào cơ sở dữ liệu và kiểm tra kết quả
        } else {
            false  // Người dùng đã tồn tại hoặc email hoặc tel đã tồn tại
        }
    }

    fun isUserExists(userName: String): Boolean {
        return userDAO.search(userName).isNotEmpty()  // Tìm kiếm theo username
    }

    fun isEmailExists(email: String): Boolean {
        return userDAO.search(email = email).isNotEmpty()  // Tìm kiếm theo email
    }

    fun isTelExists(tel: String): Boolean {
        return userDAO.search(tel = tel).isNotEmpty()  // Tìm kiếm theo số điện thoại
    }

    fun getUserByUsername(username: String): AppUser? {
        return userDAO.search(userName = username).firstOrNull()
    }

    fun getAllUsers(): List<AppUser> {
        return userDAO.getAllUsers()  // Trả về danh sách người dùng từ cơ sở dữ liệu
    }

    fun updateUser(updatedUser: AppUser) {
        userDAO.edit(updatedUser)  // Cập nhật người dùng trong cơ sở dữ liệu
    }

    fun deleteUser(user: AppUser): Boolean {
        return userDAO.delete(user.id)
    }
}
