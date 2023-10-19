package com.example.recepti


import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.recepti.databinding.ActivityAddRecipeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRecipeBinding
    var imageURL: String? = null
    var uri: Uri?=null
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()){
                result ->
            if (result.resultCode== RESULT_OK){
                val data =result.data
                uri=data!!.data
                binding.uploadImage.setImageURI(uri)
            }else{
                Toast.makeText(this@AddRecipeActivity,"Morate odabrati sliku", Toast.LENGTH_SHORT).show()
            }
        }
        binding.odaberiSlikuBtn.setOnClickListener{
            val photoPicker=Intent(Intent.ACTION_PICK)
            photoPicker.type="image/*"
            activityResultLauncher.launch(photoPicker)
        }
        binding.spremiBtn.setOnClickListener{
            saveData()
        }
    }
    private fun saveData(){
        val storageReference= FirebaseStorage.getInstance().reference.child("SLIKE").child(uri!!.lastPathSegment!!)

        val builder = AlertDialog.Builder(this@AddRecipeActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.loading)
        val dialog=builder.create()
        dialog.show()

        storageReference.putFile(uri!!).addOnSuccessListener { taskSnapshot ->
            val uriTask=taskSnapshot.storage.downloadUrl
            while(!uriTask.isComplete);
            val urlImage = uriTask.result
            imageURL = urlImage.toString()
            uploadData()
            dialog.dismiss()
        }.addOnFailureListener{
            dialog.dismiss()
        }
    }
    private fun uploadData(){
        val title=binding.nazivEditText.text.toString()
        val ingredients=binding.sastojciEditText.text.toString()
        val preparation=binding.pripremaEditText.text.toString()

        val dataClass=RecipeDataClass(title,ingredients,preparation,imageURL)
        db.collection("recipes").add(dataClass).addOnCompleteListener{
            task->
            if(task.isSuccessful){
                Toast.makeText(this@AddRecipeActivity,"Spremili ste recept :)", Toast.LENGTH_SHORT).show()
                finish()
            }
        }.addOnFailureListener{
            e -> Toast.makeText(this@AddRecipeActivity,e.message.toString(),Toast.LENGTH_SHORT).show()
    }
    }
}