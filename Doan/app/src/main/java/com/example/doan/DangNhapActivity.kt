package com.example.doan

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.doan.Data.dangnhap
import com.example.doan.databinding.ActivityDangNhapBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class DangNhapActivity : AppCompatActivity() {


    // Khai báo biến để lưu trữ thông tin đăng nhập của người dùng
    private lateinit var dbdangnhap: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var txtUserName: EditText
    lateinit var binding: ActivityDangNhapBinding
    private lateinit var currentUser: FirebaseUser


    private lateinit var txtUserEmail: EditText

    // Hằng số RC_SIGN_IN được sử dụng để xác định mã yêu cầu khi gửi yêu cầu đăng nhập
    private val RC_SIGN_IN = 123
    private val TAG = "MainActivity"
    private val ten = "MainActivity"
    private val emaill = "MainActivity"

    // Phương thức onCreate() được gọi khi activity được tạo ra

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dang_nhap)


        val btnSignIn = findViewById<ImageView>(R.id.google)
        txtUserName = findViewById(R.id.username)
        txtUserEmail = findViewById(R.id.password)

        dbdangnhap = FirebaseDatabase.getInstance().getReference("dangnhap")
        // Khởi tạo GoogleSignInOptions để cấu hình yêu cầu đăng nhập của người dùng
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Khởi tạo GoogleSignInClient để đăng nhập với tài khoản Google

        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.app_name))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, options)


        // Bắt đầu quá trình đăng nhập khi người dùng nhấn vào nút đăng nhập

        btnSignIn.setOnClickListener {
            // window infor login
            googleSignInClient.signOut().addOnCompleteListener {
                val intent = googleSignInClient.signInIntent
                startActivityForResult(intent, 10001)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener {
                    if (task.isSuccessful) {
                        currentUser = FirebaseAuth.getInstance().currentUser!!
                        val user = dangnhap(
                            currentUser.displayName.toString(),
                            currentUser.photoUrl.toString(),
                            currentUser.email.toString(),
                            "",
                            "",
                        )
                        var count: Int = 0
                        // check data exists
                        val db = Firebase.firestore
                        val collectionRef = db.collection("users")

                        val check =
                            collectionRef.whereEqualTo("email", currentUser.email.toString())
                        check.get().addOnSuccessListener { documents ->
                            for (document in documents) {
                                count++
                            }
                            if (count == 0) {
                                collectionRef.add(user)
                                    .addOnSuccessListener { documentReference ->
                                        Toast.makeText(
                                            this,
                                            "Đăng kí thành công!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    .addOnFailureListener { e ->

                                    }
                            }
                            val  fragment4= Fragment_4()
                            val dem = 1
                            val bundle = Bundle().apply {
                                putString("dem", dem.toString())
                            }
                            fragment4.arguments = bundle

                            // Thực hiện thay thế Fragment hiện tại bằng Fragment đích
                            fragment4.arguments = bundle
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, fragment4)
                                .commit()


                        }
                    } else {
                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }


}



