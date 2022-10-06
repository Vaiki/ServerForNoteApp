package com.example.auth

import io.ktor.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

private val hashKey = System.getenv("HASH_KEY").toByteArray()
private val hMacKey = SecretKeySpec(hashKey,"HmacSHA1")

    //pass into hash
fun hash(pass:String):String{
    val hMac = Mac.getInstance("HmacSHA1")
    hMac.init(hMacKey)
    return hex(hMac.doFinal(pass.toByteArray(Charsets.UTF_8)))
}