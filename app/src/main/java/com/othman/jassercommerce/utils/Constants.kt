package com.othman.jassercommerce.utils

object Constants {

    private const val NULL = "غير محدد"
    const val AREA_UNIT = "متر مربع"

    //estate types
    private const val HOUSE = "منزل"
    private const val VILLA = "فيلا"
    private const val OFFICE = "مكتب"
    private const val SHOP = "محل"
    private const val FARM = "مزرعة"
    private const val LAND = "أرض"
    val estateTypeOptions = arrayListOf(NULL, HOUSE, VILLA, OFFICE, SHOP, FARM, LAND)

    //contract types
    const val SALE = "بيع"
    const val RENT = "آجار"
    const val BET = "رهنية"
    const val BUY = "شراء"

    //dialog types
    const val AREA_DIALOG_TYPE = "area_dialog"
        const val FURNITURE_DIALOG_TYPE = "furniture_dialog"
    const val LOGGER_DIALOG_TYPE = "logger_dialog"
    const val ROOMS_DIALOG_TYPE = "rooms_dialog"
    const val SALE_PRICE_DIALOG_TYPE = SALE
    const val RENT_PRICE_DIALOG_TYPE = RENT
    const val BET_PRICE_DIALOG_TYPE = BET

    //roomNo list
    private const val ONE_ROOM = "غرفة واحدة"
    private const val ONE_R00M_WITH_SALON = "غرفة وصالون"
    private const val TWO_ROOM_WITH_DISTRIBUTOR  = "غرفتين وموزع"
    private const val TWO_R00M_WITH_SALON = "غرفتين وصالون"
    private const val THREE_ROOM_WITH_DISTRIBUTOR  = "ثلاث غرف وموزع"
    private const val THREE_R00M_WITH_SALON = "ثلاث غرف وصالون"
    private const val FOUR_ROOM_WITH_DISTRIBUTOR  = "أربع غرف وموزع"
    private const val FOUR_R00M_WITH_SALON = "أربع غرف وصالون"
    private const val FIFE_ROOM_WITH_DISTRIBUTOR  = "خمس غرف وموزع"
    private const val FIFE_R00M_WITH_SALON = "خمس غرف وصالون"
    private const val SIX_ROOM_WITH_DISTRIBUTOR  = "ست غرف وموزع"
    private const val SIX_R00M_WITH_SALON = "ست غرف وصالون"
    private const val SEVEN_ROOM_WITH_DISTRIBUTOR  = "سبع غرف وموزع"
    private const val SEVEN_R00M_WITH_SALON = "سبع غرف وصالون"
    private const val EIGHT_ROOM_WITH_DISTRIBUTOR  = "ثمان غرف وموزع"
    private const val EIGHT_ROOM_WITH_SALON  = "ثمان غرف وصالون"
    private const val NINE_ROOM_WITH_DISTRIBUTOR  = "تسع غرف وموزع"
    private const val NINE_ROOM_WITH_SALON  = "تسع غرف وصالون"
    private const val OTHER_ROOM_N0 = "أخرى"
    val roomsOptions = arrayListOf(NULL, ONE_ROOM, ONE_R00M_WITH_SALON,
        TWO_ROOM_WITH_DISTRIBUTOR, TWO_R00M_WITH_SALON,
        THREE_ROOM_WITH_DISTRIBUTOR, THREE_R00M_WITH_SALON,
        FOUR_ROOM_WITH_DISTRIBUTOR, FOUR_R00M_WITH_SALON,
        FIFE_ROOM_WITH_DISTRIBUTOR , FIFE_R00M_WITH_SALON,
        SIX_ROOM_WITH_DISTRIBUTOR, SIX_R00M_WITH_SALON,
        SEVEN_ROOM_WITH_DISTRIBUTOR, SEVEN_R00M_WITH_SALON,
        EIGHT_ROOM_WITH_DISTRIBUTOR, EIGHT_ROOM_WITH_SALON,
        NINE_ROOM_WITH_DISTRIBUTOR, NINE_ROOM_WITH_SALON,
        OTHER_ROOM_N0)

    //floor list
    private const val SECOND_UNDER_GROUND = "ثاني تحت الأرض"
    private const val FIRST_UNDER_GROUND = "أول تحت الأرض"
    private const val SAME_GROUND = "أرضي"
    private const val HANGING = "معلق"
    private const val FIRST = "أول"
    private const val SECOND = "ثاني"
    private const val THIRD = "ثالث"
    private const val FOURTH = "رابع"
    private const val FIFTH = "خامس"
    private const val SIXTH = "سادس"
    private const val SEVENTH = "سابع"
    private const val EIGHTH = "ثامن"
    private const val NINTH = "تاسع"
    private const val SURFACE = "سطوح"
    private const val GARDEN_UNDER_GROUND = "حديقة نزول"
    private const val GARDEN_UPPER_GROUND = "حديقة فوق الأرض"
    private const val GARDEN_SAME_GROUND = "حديقة أرضي"
    val floorOptions = arrayListOf (
    NULL, SECOND_UNDER_GROUND, FIRST_UNDER_GROUND, SAME_GROUND,
    HANGING, FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH, SEVENTH, EIGHTH, NINTH, SURFACE, GARDEN_UNDER_GROUND,
    GARDEN_SAME_GROUND, GARDEN_UPPER_GROUND)

    //directions list
    private const val FULL  = "بلاطة كاملة: الاتجاهات الأربعة"
    private const val HALF  = "نصف بلاطة"
    private const val CORNER  = "زاوية"
    private const val PADDING  = "حشوة"
    private const val HALF_EAST_NORTH_WEST  = "نصف بلاطة: شرقي - شمالي - غربي"
    private const val HALF_EAST_SOUTH_WEST  = "نصف بلاطة: شرقي - قبلي - غربي"
    private const val HALF_NORTH_WEST_SOUTH  = "نصف بلاطة: شمالي - غربي - قبلي"
    private const val HALF_NORTH_EAST_SOUTH  = "نصف بلاطة: شمالي - شرقي - قبلي"
    private const val CORNER_NORTH_EAST  = "زاوية: شمالي - شرقي"
    private const val CORNER_NORTH_WEST  = "زاوية: شمالي - غربي"
    private const val CORNER_SOUTH_EAST  = "زاوية: قبلي - شرقي"
    private const val CORNER_SOUTH_WEST  = "زاوية: قبلي - غربي"
    private const val PADDING_EAST_WEST  = "حشوة: غربي - شرقي"
    private const val PADDING_NORTH_SOUTH  = "حشوة: شمالي - قبلي"
    private const val NORTH  = "شمالي"
    private const val SOUTH  = "قبلي"
    private const val WEST  = "غربي"
    private const val EAST  = "شرقي"
    val directionOptions = arrayListOf(NULL, NORTH, SOUTH, WEST, EAST,
        CORNER_NORTH_EAST, CORNER_NORTH_WEST, CORNER_SOUTH_EAST, CORNER_SOUTH_WEST,
        PADDING_NORTH_SOUTH, PADDING_EAST_WEST,
        HALF_NORTH_EAST_SOUTH, HALF_NORTH_WEST_SOUTH, HALF_EAST_NORTH_WEST, HALF_EAST_SOUTH_WEST,
        FULL)

    //interface list
    private const val FRONT  = "أمامي"
    private const val BACK  = "خلفي"
    val interfaceOptions = arrayListOf(NULL, FRONT, BACK)

    //situation and furnitureSituation list
    private const val SUPER_DELUXE  = "سوبر ديلوكس"
    private const val DELUXE  = "ديلوكس"
    private const val VERY_GOOD  = "جيد جداً"
    private const val GOOD  = "جيد"
    private const val MEDIUM  = "وسط"
    private const val UNDER_MEDIUM  = "دون الوسط"
    private const val FULL_MAINTENANCE  = "صيانة كاملة"
    val situationOptions = arrayListOf(NULL, SUPER_DELUXE, DELUXE, VERY_GOOD, GOOD, MEDIUM,
        UNDER_MEDIUM, FULL_MAINTENANCE)

    //furniture list
    private const val NO_FURNITURE  = "غير مفروش"
    private const val FULL_FURNITURE  = "فرش كامل"
    private const val PARTIAL_FURNITURE  = "فرش جزئي"
    private const val FULL_FURNITURE_ABLE_RENT_WITHOUT  = "فرش كامل مع إمكانية الآجار بدونه"
    private const val PARTIAL_FURNITURE_ABLE_RENT_WITHOUT  = "فرش جزئي مع إمكانية الآجار بدونه"
    val furnitureOptions = arrayListOf(NULL, FULL_FURNITURE, NO_FURNITURE, PARTIAL_FURNITURE)

    //legal list
    private const val CONTRACT  = "عقد"
    private const val  COURT = "حكم محكمة"
    private const val GREEN_STAMP  = "طابو أخضر"
    val legalOptions = arrayListOf(NULL, CONTRACT, COURT, GREEN_STAMP)

    //priority
    private const val IMPORTANT_URGENT  = "مستعجل وهام"
    private const val IMPORTANT_NOT_URGENT  = "غير مستعجل وهام"
    private const val NOT_IMPORTANT_URGENT  = "مستعجل وغير هام"
    private const val NOT_IMPORTANT_NOT_URGENT  = "غير مستعجل وغير هام"
    val priorityOptions = arrayListOf(NULL, IMPORTANT_URGENT, NOT_IMPORTANT_URGENT, IMPORTANT_NOT_URGENT,
        NOT_IMPORTANT_NOT_URGENT)

    //logger list
    private const val OWNER  = "المالك"
    private const val DELEGATE  = "مندوب"
    val loggerOptions = arrayListOf(NULL, OWNER, DELEGATE)

    //floor houses list
    private const val ONE_HOUSE  = "1"
    private const val TWO_HOUSE  = "2"
    private const val THREE_HOUSE  = "3"
    private const val FOUR_HOUSE  = "4"
    private const val FIVE_HOUSE  = "5"
    private const val SIX_HOUSE  = "6"
    val floorHousesOptions = arrayListOf(NULL, ONE_HOUSE, TWO_HOUSE, THREE_HOUSE, FOUR_HOUSE, FIVE_HOUSE, SIX_HOUSE)

    //price unit list
    const val THOUSANDS_POUNDS  = "ألف ليرة"
    const val MILLION_POUNDS  = "مليون ليرة"
    const val BILLION_POUNDS = "مليار ليرة"
    const val DOLLARS  = "دولار"
    const val THOUSANDS_DOLLARS  = "ألف دولار"
    val priceUnitOptions = arrayListOf(THOUSANDS_POUNDS, MILLION_POUNDS, BILLION_POUNDS, DOLLARS, THOUSANDS_DOLLARS)

    //price type list
    private const val FINAL_PRICE  = "سعر نهائي"
    private const val LITTLE_ARGUE_PRICE  = "بازار خفيف"
    private const val ARGUE_PRICE  = "بازار"

    private const val ANNUAL_RENT  = "سنوي"
    private const val HALF_ANNUAL_RENT  = "نصف سنوي"
    private const val QUARTER_ANNUAL_RENT  = "ربع سنوي"
    private const val MONTHLY_RENT  = "شهري"

    private const val ONE_YEAR_BET  = "سنة واحدة"
    private const val TWO_YEAR_BET  = "سنتان"
    private const val THREE_YEAR_BET  = "ثلاث سنوات"
    private const val FOUR_YEAR_BET  = "أربع سنوات"
    private const val FIVE_YEAR_BET  = "خمس سنوات"

    val salePriceTypeOptions = arrayListOf(NULL, FINAL_PRICE, LITTLE_ARGUE_PRICE, ARGUE_PRICE)
    val rentPriceTypeOptions = arrayListOf(ANNUAL_RENT, HALF_ANNUAL_RENT,QUARTER_ANNUAL_RENT, MONTHLY_RENT)
    val betPriceTypeOptions = arrayListOf(ONE_YEAR_BET, TWO_YEAR_BET, THREE_YEAR_BET, FOUR_YEAR_BET, FIVE_YEAR_BET)

    //owner standards list
    private const val GROOMS_ONLY  = "عرسان فقط"
    private const val FEMALE_STUDENTS  = "طالبات فقط"
    private const val SMALL_FAMILY  = "عائلة صغيرة"
    private const val CHILDREN_LESS_FAMILY  = "عائلة بدون أطفال"
    private const val OTHER_STANDARDS  = "معايير أخرى"
    val renterStandardsOptions = arrayListOf(NULL, GROOMS_ONLY, FEMALE_STUDENTS, CHILDREN_LESS_FAMILY,
    SMALL_FAMILY, OTHER_STANDARDS)
}