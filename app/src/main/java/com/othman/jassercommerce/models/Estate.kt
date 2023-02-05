package com.othman.jassercommerce.models

import android.net.Uri
import java.util.ArrayList

data class Estate(
    var id: Int,
    val type: String?,       // ex: house
    val deal: Int,           // ex: offer
    val contract: String?,   // ex: sale
    val location: String?,
    val area: Int,
    val rooms: String?,
    val direction: String?,
    val side: String?,       // ex: front
    val height: String?,
    val situation: String?,
    val furniture: String?,
    val furnitureSituation: String?,
    val price: Double,
    val positives: String?,
    val negatives: String?,
    val priority: String?,
    val legal: String?,
    val owner: String?,
    val ownerTel: String?,
    var completed: Boolean,
    val images: ArrayList<Uri>?
)
