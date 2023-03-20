package com.nonetxmxy.mmzqfxy.model

data class Regions(
    val Rpp: List<ProvinceOrCity>,
    val ewgSAY: List<ProvinceOrCity>
)

data class ProvinceOrCity(
    val DmFXvr: String, // province名 城市名
    val beEymAI: Long, // province id 城市 id
    val kgmAjY: Long, // 上一级的id
    val letter: String = "",
    val isShowLetter: Boolean = false
)
