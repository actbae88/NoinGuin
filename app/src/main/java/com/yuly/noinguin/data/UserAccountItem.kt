package com.yuly.noinguin.data

data class UserAccountItem(
    var no:Int,
    var id:String,
    var password:String,
    var age:String,
    var imgFile:String)

data class LoginResponse(
    var rowNum:Int,
    var account:UserAccountItem
)

data class MyInfomationDataChange(
    var rowNum: Int,
    var newData: String
)
