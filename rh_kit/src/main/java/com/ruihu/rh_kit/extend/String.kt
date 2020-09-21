package com.ruihu.rh_kit.extend

import com.google.gson.Gson


/**
 *  Created By RuiHu At 2020/9/15 09:24
 */

inline fun <reified T> Gson.fromJson(json: String) = Gson().fromJson(json, T::class.java)!!

fun <T : Any> T.toJson() = Gson().toJson(this)!!