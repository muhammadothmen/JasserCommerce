package com.othman.jassercommerce.models

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class EstateModel(
    val id: Int,
    //
    var deal: String?,           // ex: offer
    var contract: String?,   // ex: sale
    val type: String?,       // ex: house
    //
    val location: String?,
    val rooms: String?,
    var area: Int,
    val floor: String?,
    val direction: String?,
    val interfaced: String?,       // ex: front
    val floorHouses: String?,
    val situation: String?,
    val furniture: String?,
    val furnitureSituation: String?,
    val legal: String?,
    var price: Float,
    val positives: String?,
    val negatives: String?,
    //
    val owner: String?,
    val ownerTel: String?,
    val logger: String?,
    val loggerTel: String?,
    val priority: String?,
    val renterStandards: String?,
    val loggingDate: String?,
    //
    val images: ArrayList<Uri>?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Uri.CREATOR)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(deal)
        parcel.writeString(contract)
        parcel.writeString(type)
        parcel.writeString(location)
        parcel.writeString(rooms)
        parcel.writeInt(area)
        parcel.writeString(floor)
        parcel.writeString(direction)
        parcel.writeString(interfaced)
        parcel.writeString(floorHouses)
        parcel.writeString(situation)
        parcel.writeString(furniture)
        parcel.writeString(furnitureSituation)
        parcel.writeString(legal)
        parcel.writeFloat(price)
        parcel.writeString(positives)
        parcel.writeString(negatives)
        parcel.writeString(owner)
        parcel.writeString(ownerTel)
        parcel.writeString(logger)
        parcel.writeString(loggerTel)
        parcel.writeString(priority)
        parcel.writeString(renterStandards)
        parcel.writeString(loggingDate)
        parcel.writeTypedList(images)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EstateModel> {
        override fun createFromParcel(parcel: Parcel): EstateModel {
            return EstateModel(parcel)
        }

        override fun newArray(size: Int): Array<EstateModel?> {
            return arrayOfNulls(size)
        }
    }
}
