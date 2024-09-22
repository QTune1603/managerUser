package com.example.bt1.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.example.bt1.model.AppUser
import com.example.bt1.R
import com.example.bt1.dao.UserDAO

class SearchUser : AppCompatActivity() {

    private lateinit var userDAO: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_user)

        // Khởi tạo đối tượng UserDAO để làm việc với cơ sở dữ liệu
        userDAO = UserDAO(this)

        val btnSearch = findViewById<Button>(R.id.timKiem)
        val usernameInput = findViewById<EditText>(R.id.Name)
        val userInfoContainer = findViewById<LinearLayout>(R.id.userInfoContainer)

        btnSearch.setOnClickListener {
            val enteredUsername = usernameInput.text.toString()

            if (enteredUsername.isNotEmpty()) {
                // Thực hiện tìm kiếm trong database
                val users = userDAO.search(enteredUsername)
                if (users.isNotEmpty()) {
                    displayUserList(users, userInfoContainer)
                } else {
                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                    userInfoContainer.removeAllViews()
                    userInfoContainer.visibility = View.GONE
                }
            } else {
                Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayUserList(users: List<AppUser>, container: LinearLayout) {
        container.removeAllViews()
        container.visibility = View.VISIBLE

        for (user in users) {
            val userView = layoutInflater.inflate(R.layout.user_info_item, container, false)

            // Định dạng ID thành chuỗi có độ dài 5 ký tự
            val formattedId = String.format("%05d", user.id)

            val idTextView = userView.findViewById<TextView>(R.id.idTextView)
            val usernameTextView = userView.findViewById<TextView>(R.id.userNameTextView)
            val passwordTextView = userView.findViewById<TextView>(R.id.passwordTextView)
            val emailTextView = userView.findViewById<TextView>(R.id.emailTextView)
            val dobTextView = userView.findViewById<TextView>(R.id.dobTextView)
            val genderTextView = userView.findViewById<TextView>(R.id.genderTextView)
            val fullNameTextView = userView.findViewById<TextView>(R.id.fullNameTextView)
            val telTextView = userView.findViewById<TextView>(R.id.telTextView)

            // Hiển thị thông tin của user
            idTextView.text = "ID: $formattedId"
            usernameTextView.text = "UserName: ${user.userName}"
            passwordTextView.text = "PassWord: ${user.password}"
            emailTextView.text = "Email: ${user.email}"
            fullNameTextView.text = "Full Name: ${user.fullName}"
            telTextView.text = "Tel: ${user.tel}"
            dobTextView.text = "Date of Birth: ${user.dateOfBirth}"
            genderTextView.text = "Gender: ${user.gender}"

            // Thêm dấu ba chấm với menu
            val menuOptions = userView.findViewById<ImageView>(R.id.menuOptions)
            menuOptions.setOnClickListener {
                showPopupMenu(menuOptions, user, container, userView)
            }

            container.addView(userView)
        }
    }

    // Hàm để hiển thị PopupMenu
    private fun showPopupMenu(view: View, user: AppUser, container: LinearLayout, userView: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.user_options_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit_user -> {
                    val intent = Intent(this, EditUser::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                    true
                }
                R.id.delete_user -> {
                    showDeleteConfirmationDialog(user, container, userView)
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    // Hàm hiển thị AlertDialog để xác nhận xóa
    private fun showDeleteConfirmationDialog(user: AppUser, container: LinearLayout, userView: View) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Delete")
        builder.setMessage("Are you sure you want to delete this user?")

        builder.setPositiveButton("Yes") { dialog, _ ->
            // Xóa user khỏi database
            deleteUser(user)
            // Xóa view của user sau khi xóa
            container.removeView(userView)
            dialog.dismiss()
            Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss() // Đóng dialog nếu người dùng từ chối xóa
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    // Hàm thực hiện xóa user từ cơ sở dữ liệu
    private fun deleteUser(user: AppUser) {
        userDAO.delete(user.id)
    }
}
