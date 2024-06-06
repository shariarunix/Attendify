package com.shariarunix.attendify.data.entity.classEntity

/*
   ClassDateTime
   0 : Repeat Once
   1 : Repeat Daily
   3 : Repeat Custom
 */
sealed interface ClassDateTime

data class RepeatOnce(
    val date: String,
    val startTime: String,
    val endTime: String
) : ClassDateTime

data class RepeatDaily(
    val startTime: String,
    val endTime: String,
    val stopDate: String // Repeat Until :: Stop Repeating on this date
) : ClassDateTime

data class RepeatCustom(
    val classDateTimeList: List<CustomRepeatClassDateTime>,
    val stopDate: String // Repeat Until :: Stop Repeating on this date
) : ClassDateTime

data class CustomRepeatClassDateTime(
    val dayOfWeek: String, // Saturday, Sunday, Monday, Tuesday, Wednesday, Thursday, Friday
    val startTime: String,
    val endTime: String
)
