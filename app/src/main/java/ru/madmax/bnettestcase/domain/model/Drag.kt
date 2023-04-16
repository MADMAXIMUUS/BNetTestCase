package ru.madmax.bnettestcase.domain.model

data class Drag(
    val categories: Categories = Categories(),
    val description: String = "",
    val documentation: String = "",
    val fields: List<Field> = emptyList(),
    val id: Int = 0,
    val image: String = "",
    val name: String = ""
)