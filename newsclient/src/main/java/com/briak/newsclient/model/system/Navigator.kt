package com.briak.newsclient.model.system

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

class Navigator(
        private val activity: FragmentActivity,
        private val containerId: Int
) {
    fun replace(fragment: Fragment, data: Any?) {
        val manager: FragmentManager = activity.supportFragmentManager
        try {
            manager.popBackStack("BackStackTag", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } catch (ignored: IllegalStateException) {
            // There's no way to avoid getting this if saveInstanceState has already been called.
        }
        val transaction: FragmentTransaction = manager.beginTransaction()

        transaction.replace(containerId, fragment, fragment::class.java.name)
        transaction.commitAllowingStateLoss()
    }

    fun add(fragment: Fragment) {
        if (activity.supportFragmentManager.findFragmentByTag(fragment::class.java.name) != null) {
            show(fragment)
        } else {
            val transaction: FragmentTransaction = activity.supportFragmentManager.beginTransaction()

            transaction.add(containerId, fragment, fragment::class.java.name)
            transaction.addToBackStack("BackStackTag")
            transaction.commitAllowingStateLoss()
        }
    }

    fun show(fragment: Fragment) {
        val transaction: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
        transaction.show(fragment)
        transaction.commitAllowingStateLoss()
    }

    fun hide(fragment: Fragment) {
        val transaction: FragmentTransaction = activity.supportFragmentManager.beginTransaction()
        transaction.hide(fragment)
        transaction.commitAllowingStateLoss()
    }

    fun remove(fragment: Fragment) {
        val manager: FragmentManager = activity.supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        manager.popBackStack()

        transaction.remove(fragment)
        transaction.commitAllowingStateLoss()
    }
}