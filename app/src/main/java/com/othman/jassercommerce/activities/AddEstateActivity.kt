package com.othman.jassercommerce.activities

import android.app.DatePickerDialog
import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.othman.jassercommerce.R
import com.othman.jassercommerce.adapters.ImagesAdapter
import com.othman.jassercommerce.dialogs.AddEstateEditTextDialog
import com.othman.jassercommerce.dialogs.AddEstateSliderDialog
import com.othman.jassercommerce.utils.Constants
import kotlinx.android.synthetic.main.activity_add_estate.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddEstateActivity : BaseActivity(), View.OnClickListener {


    private var calendar = Calendar.getInstance()
    private var contractType = Constants.SALE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_estate)

        setupActionBar(toolbar_add_Estate_activity, resources.getString(R.string.Add_estate_activity_title))
        addEstateContentSetup()
        viewsListenerSetUp()
        updateDateInView()
        contractTypeToggleButtonSetup()
        editTextDeleteIconSetup()

        //tests
        recycleViewSetup()

        tb_contract_type.check(R.id.tb_sale_or_buy)
        tb_deal.check(R.id.tb_offer)



    }

    private fun contractTypeToggleButtonSetup() {
        tb_contract_type.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked){
                when(checkedId){
                    R.id.tb_sale_or_buy ->{
                        contractType = Constants.SALE
                        et_price.text?.clear()
                        til_renter_standards.visibility = View.GONE
                    }
                    R.id.tb_rent ->{
                        contractType = Constants.RENT
                        et_price.text?.clear()
                        til_renter_standards.visibility = View.VISIBLE
                    }
                    R.id.tb_bet ->{
                        contractType = Constants.BET
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
                        this@AddEstateActivity.et_area.setText("${value.toString().removeSuffix(".0")} $unit")
                    }
                }
                addEstateSliderDialog.show()

            }
            et_price -> {
                val addEstateSliderDialog = object: AddEstateSliderDialog(this@AddEstateActivity, contractType){
                    override fun onDoneClicked(value: Float, unit: String) {
                        this@AddEstateActivity.et_price.setText("${value.toString().removeSuffix(".0")} $unit")
                    }
                }
                addEstateSliderDialog.show()

            }
        }
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



    private fun recycleViewSetup(){
        var list = ArrayList<Uri>()
        list.add(estateImagePlaceHolderUri())
        list.add(estateImagePlaceHolderUri())
        list.add(estateImagePlaceHolderUri())

        val adapter = ImagesAdapter(this@AddEstateActivity,list)
        rv_image.layoutManager = LinearLayoutManager(this@AddEstateActivity, LinearLayoutManager.HORIZONTAL ,false)
        rv_image.setHasFixedSize(true)
        rv_image.adapter = adapter
        /*val dividerItemDecoration = DividerItemDecoration(this@AddEstateActivity, DividerItemDecoration.HORIZONTAL)
        //dividerItemDecoration.setDrawable()
        rv_image.addItemDecoration(dividerItemDecoration)*/
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


}