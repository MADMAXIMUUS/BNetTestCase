package ru.madmax.bnettestcase.domain.model

data class Field(
    val flags: Flags = Flags(),
    val group: Int = 0,
    val image: String = "",
    val name: String = "",
    val show: Int = 0,
    val type: String = "",
    val types_id: Int = 0,
    val value: String = ""
)