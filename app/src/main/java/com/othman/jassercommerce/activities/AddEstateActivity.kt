package com.othman.jassercommerce.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.othman.jassercommerce.R
import com.othman.jassercommerce.adapters.ImagesAdapter
import com.othman.jassercommerce.databases.LocalDatabaseHandler
import com.othman.jassercommerce.dialogs.AddEstateEditTextDialog
import com.othman.jassercommerce.dialogs.AddEstateSliderDialog
import com.othman.jassercommerce.models.EstateModel
import com.othman.jassercommerce.utils.Constants
import kotlinx.android.synthetic.main.activity_add_estate.*
import kotlinx.android.synthetic.main.dialog_image.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
@SuppressLint("NotifyDataSetChanged")
class AddEstateActivity : BaseActivity(), OnClickListener {

    private var calendar = Calendar.getInstance()
    private lateinit var estateDetails : EstateModel
    private var imagesList = ArrayList<Uri>()
    private lateinit var readImagePermission: String
    private lateinit var cameraPermission: String
    private lateinit var openGalleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var openCameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var imagesAdapter: ImagesAdapter
    private lateinit var cameraPhotoPath: String
    private lateinit var cameraPhotoUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_estate)

        mOnBackPressed()
        setupActionBar(toolbar_add_Estate_activity, resources.getString(R.string.Add_estate_activity_title))
        imagesRecycleViewSetup()
        permissionsInit()
        galleryLaunchersInit()
        cameraLauncherInit()
        addEstateContentSetup()
        viewsListenerSetUp()
        updateDateInView()
        contractToggleButtonSetup()
        dealToggleButtonSetup()
        editTextDeleteIconSetup()
        estateDetailsInitiate()

        //tests

        if (intent.hasExtra(Constants.DEAL_INTENT)) {
            estateDetails.deal = intent.getStringExtra(Constants.DEAL_INTENT)
        }


        tb_contract_type.check(R.id.tb_sale_or_buy)
        when(estateDetails.deal){
            Constants.OFFER ->{
                tb_deal.check(R.id.tb_offer)
            }
            Constants.DEMAND ->{
                tb_deal.check(R.id.tb_demand)
            }


        }





    }

    private fun mOnBackPressed() {
        onBackPressedDispatcher.addCallback(this , object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                imagesList.remove(estateImagePlaceHolderUri())
                for (image in imagesList){
                    deleteFile(image)
                }
                finish()
            }
        })
    }




    private fun permissionsInit(){
        readImagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            android.Manifest.permission.READ_MEDIA_IMAGES
        else android.Manifest.permission.READ_EXTERNAL_STORAGE
        cameraPermission = android.Manifest.permission.CAMERA
    }

    private fun dealToggleButtonSetup() {
        tb_contract_type.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked){
                when(checkedId){
                    R.id.tb_offer -> {
                        estateDetails.deal = Constants.OFFER
                    }
                    R.id.tb_demand ->{
                        estateDetails.deal = Constants.DEMAND
                    }
                }
            }
        }
    }

    private fun contractToggleButtonSetup() {
        tb_contract_type.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked){
                when(checkedId){
                    R.id.tb_sale_or_buy -> {
                        estateDetails.contract = Constants.SALE
                        et_price.text?.clear()
                        til_renter_standards.visibility = View.GONE
                    }
                    R.id.tb_rent ->{
                        estateDetails.contract = Constants.RENT
                        et_price.text?.clear()
                        til_renter_standards.visibility = View.VISIBLE
                    }
                    R.id.tb_bet ->{
                        estateDetails.contract = Constants.BET
                        et_price.text?.clear()
                        til_renter_standards.visibility = View.GONE
                    }
                }
            }
        }
    }


    override fun onClick(view: View?) {
        when(view){
            et_date -> {
                DatePickerDialog(
                    this@AddEstateActivity,
                    { view, year, monthOfYear, dayOfMonth ->
                        calendar.set(Calendar.YEAR, year)
                        calendar.set(Calendar.MONTH, monthOfYear)
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        updateDateInView()
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            et_area -> {
                val addEstateSliderDialog = object: AddEstateSliderDialog(this@AddEstateActivity, Constants.AREA_DIALOG_TYPE){
                    override fun onDoneClicked(value: Float, unit: String) {
                        estateDetails.area = value.toInt()
                        this@AddEstateActivity.et_area.setText("${value.toInt()} $unit")
                    }
                }
                addEstateSliderDialog.show()

            }
            et_price -> {
                val addEstateSliderDialog = object: AddEstateSliderDialog(this@AddEstateActivity, estateDetails.contract!!){
                    override fun onDoneClicked(value: Float, unit: String) {
                        estateDetails.price = value
                        this@AddEstateActivity.et_price.setText("${value.toString().removeSuffix(".0")} $unit")
                    }
                }
                addEstateSliderDialog.show()

            }
            btn_add_estate_save -> {
                saveEstate()
                setResult(RESULT_OK)
                finish()
            }

        }
    }


    private fun saveEstate(){

        imagesList.remove(estateImagePlaceHolderUri())

        EstateModel(
            estateDetails.id,

            estateDetails.deal,
            estateDetails.contract,
            et_estate_type.text.toString(),

            et_location.text.toString(),
            et_estate_rooms.text.toString(),
            estateDetails.area,
            et_floor.text.toString(),
            et_direction.text.toString(),
            et_interface.text.toString(),
            et_floor_houses.text.toString() ,
            et_estate_situation.text.toString(),
            et_furniture.text.toString(),
            et_furniture_situation.text.toString(),
            et_legal.text.toString(),
            estateDetails.price,
            et_positives.text.toString(),
            et_negatives.text.toString(),
            et_owner.text.toString(),
            et_owner_tel.text.toString(),
            et_logger.text.toString(),
            et_logger_tel.text.toString(),
            et_priority.text.toString(),
            et_renter_standards.text.toString(),
            et_date.text.toString(),
            imagesList
        ).also {
            val db = LocalDatabaseHandler(this)
            db.addEstate(it)
        }
    }

    private fun estateDetailsInitiate(){

        estateDetails = EstateModel(
            0,

            Constants.OFFER,
            Constants.SALE,
            et_estate_type.text.toString(),

            et_location.text.toString(),
            et_estate_rooms.text.toString(),
            50,
            et_floor.text.toString(),
            et_direction.text.toString(),
            et_interface.text.toString(),
            et_floor_houses.text.toString() ,
            et_estate_situation.text.toString(),
            et_furniture.text.toString(),
            et_furniture_situation.text.toString(),
            et_legal.text.toString(),
            50.0f,
            et_positives.text.toString(),
            et_negatives.text.toString(),
            et_owner.text.toString(),
            et_owner_tel.text.toString(),
            et_logger.text.toString(),
            et_logger_tel.text.toString(),
            et_priority.text.toString(),
            et_renter_standards.text.toString(),
            et_date.text.toString(),
            imagesList
        )



    }

    private fun addEstateContentSetup() {
        editTextOptionsSetup(et_estate_type, Constants.estateTypeOptions)
        editTextOptionsSetup(et_estate_rooms, Constants.roomsOptions)
        editTextOptionsSetup(et_floor, Constants.floorOptions)
        editTextOptionsSetup(et_direction, Constants.directionOptions)
        editTextOptionsSetup(et_interface, Constants.interfaceOptions)
        editTextOptionsSetup(et_estate_situation, Constants.situationOptions)
        editTextOptionsSetup(et_furniture, Constants.furnitureOptions)
        editTextOptionsSetup(et_furniture_situation, Constants.situationOptions)
        editTextOptionsSetup(et_legal, Constants.legalOptions)
        editTextOptionsSetup(et_logger, Constants.loggerOptions)
        editTextOptionsSetup(et_priority, Constants.priorityOptions)
        editTextOptionsSetup(et_floor_houses, Constants.floorHousesOptions)
        editTextOptionsSetup(et_renter_standards, Constants.renterStandardsOptions)
    }

    private fun editTextOptionsSetup(autoCompleteTextView: AutoCompleteTextView ,options: ArrayList<String>){
        val adapter = ArrayAdapter(this@AddEstateActivity, R.layout.list_item, options)
        autoCompleteTextView.setAdapter(adapter)


        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            if (position == 0){
                autoCompleteTextView.text.clear()
                autoCompleteTextView.clearFocus()
            }

            if (position == adapter.count - 1 &&
                (autoCompleteTextView == et_estate_rooms
                        || autoCompleteTextView == et_furniture
                        || autoCompleteTextView == et_logger
                        )){
                showEtDialog(autoCompleteTextView,adapter)
            }
        }
    }


    private fun showEtDialog(autoCompleteTextView: AutoCompleteTextView, adapter: ArrayAdapter<String>){

        var title = ""
        when(autoCompleteTextView){
            et_estate_rooms -> {
                title = resources.getString(R.string.hint_rooms)
            }
            et_furniture -> {
                title = resources.getString(R.string.hint_furniture)
            }
            et_logger -> {
                title = resources.getString(R.string.dialog_hint_logger)
            }
        }

        val etDialog = object: AddEstateEditTextDialog(this@AddEstateActivity,title){
            override fun onDoneClicked(data: String) {
                /*val previousText = autoCompleteTextView.text
                autoCompleteTextView.setText("$previousText: $data", false)*/
                autoCompleteTextView.append(": $data")
                adapter.filter.filter(null)
                autoCompleteTextView.dismissDropDown()
            }

            override fun onCancelClicked() {
                autoCompleteTextView.text.clear()
                autoCompleteTextView.clearFocus()
            }
        }

        etDialog.show()
    }

    private fun viewsListenerSetUp() {
        et_date.setListener()
        et_area.setListener()
        et_price.setListener()
        btn_add_estate_save.setListener()
    }


    private fun View.setListener(){
        this.setOnClickListener(this@AddEstateActivity)
    }

    private fun updateDateInView() {
        val myFormat = "yyyy/MM/dd"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        et_date.setText(sdf.format(calendar.time).toString())
        btn_date_delete.visibility = View.VISIBLE
    }



    private fun imagesRecycleViewSetup(){
        if (imagesList.isEmpty()){
            imagesList.add(estateImagePlaceHolderUri())
        }
        imagesAdapter = ImagesAdapter(this@AddEstateActivity,imagesList)
        rv_image.layoutManager = LinearLayoutManager(this@AddEstateActivity,
            LinearLayoutManager.HORIZONTAL ,false)
        rv_image.setHasFixedSize(true)
        rv_image.adapter = imagesAdapter
        imagesAdapter.setOnClickListener(object: ImagesAdapter.OnClickListener{
            override fun onItemClick(position: Int) {
                if (imagesList[position] == estateImagePlaceHolderUri()){
                    if (permissionGrantedTest(readImagePermission) && permissionGrantedTest(cameraPermission)){
                        imageDialogShow()
                    }
                    else{
                        imagePermissionLauncher()
                    }
                }else{
                    try {
                        val openImageIntent = Intent(Intent.ACTION_VIEW)
                        openImageIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                        openImageIntent.setDataAndType(imagesList[position],"image/*")
                        ContextCompat.startActivity(this@AddEstateActivity, openImageIntent, null)
                    }catch (e:Exception){
                        Log.e("JC",e.toString())
                    }
                }

            }

            override fun onDeleteClick(position: Int) {
                deleteFile(imagesList[position])
                imagesList.removeAt(position)
                if (imagesList.lastOrNull() != estateImagePlaceHolderUri()){
                    imagesList.add(estateImagePlaceHolderUri())
                }
                imagesAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun imageDialogShow(){

        Dialog(this@AddEstateActivity, R.style.Dialog_Theme).apply {
            setContentView(R.layout.dialog_image)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCanceledOnTouchOutside(true)
            setCancelable(true)
            btn_dialog_image_gallery.setOnClickListener {
                chooseImageFromGallery()
                dismiss()
                }
            btn_dialog_image_camera.setOnClickListener {
                takePhotoFromCamera()
                dismiss()
            }
            show()
        }
    }


    private fun estateImagePlaceHolderUri(): Uri {
        return Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE +"://" +
                    resources.getResourcePackageName(R.drawable.estate_image_place_holder) + '/' +
                    resources.getResourceTypeName(R.drawable.estate_image_place_holder) + '/' +
                    resources.getResourceEntryName(R.drawable.estate_image_place_holder)
        )
    }

    private fun editTextDeleteIconSetup(){

        OnClickListener{
            when(it){
                btn_area_delete ->{
                    et_area.text?.clear()
                }
                btn_price_delete ->{
                    et_price.text?.clear()
                }
                btn_date_delete ->{
                    et_date.text?.clear()
                }
            }
        }.apply {
            btn_area_delete.setOnClickListener(this)
            btn_price_delete.setOnClickListener(this)
            btn_date_delete.setOnClickListener(this)
        }

        et_area.addTextChangedListener (object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(text: Editable?) {
                if (text.isNullOrEmpty()){
                    btn_area_delete.visibility = View.INVISIBLE
                }else{
                    btn_area_delete.visibility = View.VISIBLE
                }
            }
        })

        et_price.addTextChangedListener (object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(text: Editable?) {
                if (text.isNullOrEmpty()){
                    btn_price_delete.visibility = View.INVISIBLE
                }else{
                    btn_price_delete.visibility = View.VISIBLE
                }
            }
        })

        et_date.addTextChangedListener (object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(text: Editable?) {
                if (text.isNullOrEmpty()){
                    btn_date_delete.visibility = View.INVISIBLE
                }else{
                    btn_date_delete.visibility = View.VISIBLE
                }
            }
        })

    }

    private fun imagePermissionLauncher(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                readImagePermission) ||
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                cameraPermission)
        ) {
            showRationalDialogForPermissions()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(readImagePermission, cameraPermission),
                Constants.IMAGES_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun permissionGrantedTest(permission: String) :Boolean{
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this)
            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss()
            }.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.IMAGES_PERMISSION_REQUEST_CODE){
            if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) &&
                (grantResults[1] == PackageManager.PERMISSION_GRANTED)){
                imageDialogShow()
            }
        }
    }

    private fun galleryLaunchersInit(){
        openGalleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                try {
                    if (result.data?.data != null) {
                        saveGalleryImage(result.data!!.data!!)
                    }else if (result.data?.clipData != null){
                        val count = result.data?.clipData?.itemCount
                        for (i in 0 until count!!) {
                            if (imagesList.size <= 10 && imagesList.contains(estateImagePlaceHolderUri())){
                                saveGalleryImage(result.data?.clipData?.getItemAt(i)?.uri!!)
                            }else{
                                break
                            }
                            Log.e("JC", "path: ${imagesList[i]}")
                        }
                    }
                    imagesAdapter.notifyDataSetChanged()
                }catch (e: Exception){
                    Log.e("JC", e.toString())
                    Toast.makeText(this@AddEstateActivity,
                        "Oops, Failed",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun cameraLauncherInit(){
        openCameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK ) {
                resizeAndSaveImage(cameraPhotoPath,cameraPhotoUri)
                imagesAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file = createImageFile()
        cameraPhotoPath = file.absolutePath
        cameraPhotoUri = createAccessibleUriForFile(file)
        cameraPhotoUri.let {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,it)
            openCameraLauncher.launch(intent)
        }
    }


    private fun chooseImageFromGallery() {
        val pickIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        pickIntent.addCategory(Intent.CATEGORY_OPENABLE)
        pickIntent.type = "image/*"
        openGalleryLauncher.launch(pickIntent)
    }

    private fun saveGalleryImage(sourceUri: Uri){
        val file = createImageFile()
        val photoPath = file.absolutePath
        val photoUri = createAccessibleUriForFile(file)
        copyGalleryImageToAppData(this@AddEstateActivity,sourceUri,photoUri)
        resizeAndSaveImage(photoPath,photoUri)
    }

    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss_SSS",Locale.ENGLISH).format(Date())
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
        return File(storageDir + File.separator +
                "Jasser_Estate_" + timeStamp + ".jpg")
    }

    private fun createAccessibleUriForFile(file: File): Uri {
        return file.let {
            FileProvider.getUriForFile(
                this@AddEstateActivity,
                "com.othman.jassercommerce.fileProvider",
                it
            )
        }
    }

    private fun copyGalleryImageToAppData(context: Context, pathFrom: Uri, pathTo: Uri?) {
        context.contentResolver.openInputStream(pathFrom).use { inputStream: InputStream? ->
            if (pathTo == null || inputStream == null) return
            context.contentResolver.openOutputStream(pathTo).use { out ->
                if (out == null) return
                // Transfer bytes from in to out
                val buf = ByteArray(1024)
                var len: Int
                while (inputStream.read(buf).also { len = it } > 0) {
                    out.write(buf, 0, len)
                }
            }
        }
    }

    private fun resizeAndSaveImage(photoPath: String, uriToDelete: Uri): Uri? {
        // Get the dimensions of the View
        // val targetW: Int = iv_image.width
        // val targetH: Int = iv_image.height
        var scaledBitmap: Bitmap? = null
        val bmOptions = BitmapFactory.Options()
        bmOptions .apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true
            scaledBitmap = BitmapFactory.decodeFile(photoPath, bmOptions)
            val photoW: Int = outWidth
            val photoH: Int = outHeight
            val scale = Math.max(outHeight/1000,outWidth/1000)
            // Determine how much to scale down the image
            // val scaleFactor: Int = Math.max(1, Math.min(photoW / targetW, photoH / targetH))
            // Decode the image file into a Bitmap sized to fill the View
            inJustDecodeBounds = false
            inSampleSize = scale
        }
        val exif =  ExifInterface(photoPath)
        val orientation: Int = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
        Log.d("JC", "Exif: $orientation");
        val matrix = Matrix()
        when (orientation) {
            6 -> {
                matrix.postRotate(90f)
                Log.d("JC", "Exif: $orientation")
            }
            3->  {
                matrix.postRotate(180f)
                Log.d("JC", "Exif: $orientation")
            }
            8-> {
                matrix.postRotate(270f)
                Log.d("JC", "Exif: $orientation")
            }
        }

        return BitmapFactory.decodeFile(photoPath, bmOptions)?.let { bitmap ->
            try {
                val file = createImageFile()
                val stream: OutputStream = FileOutputStream(file)
                scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.width, bitmap.height, matrix, true)
                scaledBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.flush()
                stream.close()
                createAccessibleUriForFile(file).apply {
                    addImageToImageList(this)
                    deleteFile(uriToDelete)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Uri.parse("")
            }
        }
    }

    private fun addImageToImageList(uri: Uri){
        imagesList.add(imagesList.lastIndex,uri)

        if (imagesList.size > 10){
            imagesList.remove(estateImagePlaceHolderUri())
        }
        //temporaryImageList.add(uri)
    }

    private fun deleteFile(uri: Uri){
        try {
            val deleted = contentResolver.delete(uri, null, null)
            Log.e("JC",deleted.toString())
        }catch (e: Exception){
            Log.e("JC",e.toString())
        }

    }

}
