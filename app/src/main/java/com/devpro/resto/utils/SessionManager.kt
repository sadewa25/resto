package com.devpro.resto.utils

import android.content.Context
import android.content.SharedPreferences
import com.devpro.resto.R

class SessionManager(private val context: Context?) {

    // Shared pref mode
    val PRIVATE_MODE = 0

    // Sharedpref file name
    private val PREF_NAME = context?.resources?.getString(R.string.app_name)

    var pref: SharedPreferences? = context?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    var editor: SharedPreferences.Editor? = pref?.edit()

    private val is_login = "is_login"
    fun setLogin(check: Boolean) {
        editor?.putBoolean(is_login, check)
        editor?.apply()
    }

    fun getLogin(): Boolean? {
        return pref?.getBoolean(is_login, false);
    }

    private val idUser = "_id"
    fun setIDUser(data: String) {
        editor?.putString(idUser, data)
        editor?.apply()
    }

    fun getIDUser(): String? {
        return pref?.getString(idUser, ""); }

    private val roleUser = "roleUser"
    fun setRoleUser(data: String) {
        editor?.putString(roleUser, data)
        editor?.apply()
    }

    fun getRoleUser(): String? {
        return pref?.getString(roleUser, ""); }

    private val tablesUser = "tablesUser"
    fun setTablesUser(data: String) {
        editor?.putString(tablesUser, data)
        editor?.apply()
    }

    fun getTablesUser(): String? {
        return pref?.getString(tablesUser, ""); }

    private val nameTablesUser = "nameTablesUser"
    fun setNameTablesUser(data: String) {
        editor?.putString(nameTablesUser, data)
        editor?.apply()
    }

    fun getNameTablesUser(): String? {
        return pref?.getString(nameTablesUser, ""); }

    private val noOrder = "noOrder"
    fun setNoOrder(data: String) {
        editor?.putString(noOrder, data)
        editor?.apply()
    }

    fun getNoOrder(): String? {
        return pref?.getString(noOrder, ""); }

    private val idCart = "idCart"
    fun setIdCart(data: String) {
        editor?.putString(idCart, data)
        editor?.apply()
    }

    fun getIdCart(): String? {
        return pref?.getString(idCart, ""); }


}