package com.nonetxmxy.mmzqfxy.model

data class Regions(
    val Rpp: List<Province>,
    val ewgSAY: List<City>
)

data class Province (
    val DmFXvr: String, // province名
    val beEymAI: Long, // province id
    val kgmAjY: Long, // 上一级的id
)

data class City (
    val DmFXvr: String, // 城市名
    val beEymAI: Long, // 城市 id
    val kgmAjY: Long, // 上一级的id
)