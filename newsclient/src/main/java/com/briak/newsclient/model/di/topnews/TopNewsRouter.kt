package com.briak.newsclient.model.di.topnews

import ru.terrakok.cicerone.BaseRouter
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.*
import ru.terrakok.cicerone.result.ResultListener
import java.util.HashMap
import javax.inject.Inject

@TopNewsScope
class TopNewsRouter @Inject constructor(): BaseRouter() {
    private val resultListeners = HashMap<Int, ResultListener>()

    /**
     * Subscribe to the screen result.<br></br>
     * **Note:** only one listener can subscribe to a unique resultCode!<br></br>
     * You must call a **removeResultListener()** to avoid a memory leak.
     *
     * @param resultCode key for filter results
     * @param listener   result listener
     */
    fun setResultListener(resultCode: Int, listener: ResultListener) {
        resultListeners[resultCode] = listener
    }

    /**
     * Unsubscribe from the screen result.
     *
     * @param resultCode key for filter results
     */
    fun removeResultListener(resultCode: Int?) {
        resultListeners.remove(resultCode)
    }

    /**
     * Send result data to subscriber.
     *
     * @param resultCode result data key
     * @param result     result data
     * @return TRUE if listener was notified and FALSE otherwise
     */
    protected fun sendResult(resultCode: Int?, result: Any): Boolean {
        val resultListener = resultListeners[resultCode]
        if (resultListener != null) {
            resultListener.onResult(result)
            return true
        }
        return false
    }

    /**
     * Open new screen and add it to the screens chain.
     *
     * @param screenKey screen key
     */
    fun navigateTo(screenKey: String) {
        navigateTo(screenKey, null)
    }

    /**
     * Open new screen and add it to screens chain.
     *
     * @param screenKey screen key
     * @param data      initialisation parameters for the new screen
     */
    fun navigateTo(screenKey: String, data: Any?) {
        executeCommands(Forward(screenKey, data))
    }

    /**
     * Clear the current screens chain and start new one
     * by opening a new screen right after the root.
     *
     * @param screenKey screen key
     */
    fun newScreenChain(screenKey: String) {
        newScreenChain(screenKey, null)
    }

    /**
     * Clear the current screens chain and start new one
     * by opening a new screen right after the root.
     *
     * @param screenKey screen key
     * @param data      initialisation parameters for the new screen
     */
    fun newScreenChain(screenKey: String, data: Any?) {
        executeCommands(
                BackTo(null),
                Forward(screenKey, data)
        )
    }

    /**
     * Clear all screens and open new one as root.
     *
     * @param screenKey screen key
     */
    fun newRootScreen(screenKey: String) {
        newRootScreen(screenKey, null)
    }

    /**
     * Clear all screens and open new one as root.
     *
     * @param screenKey screen key
     * @param data      initialisation parameters for the root
     */
    fun newRootScreen(screenKey: String, data: Any?) {
        executeCommands(
                BackTo(null),
                Replace(screenKey, data)
        )
    }

    /**
     * Replace current screen.
     * By replacing the screen, you alters the backstack,
     * so by going back you will return to the previous screen
     * and not to the replaced one.
     *
     * @param screenKey screen key
     */
    fun replaceScreen(screenKey: String) {
        replaceScreen(screenKey, null)
    }

    /**
     * Replace current screen.
     * By replacing the screen, you alters the backstack,
     * so by going back you will return to the previous screen
     * and not to the replaced one.
     *
     * @param screenKey screen key
     * @param data      initialisation parameters for the new screen
     */
    fun replaceScreen(screenKey: String, data: Any?) {
        executeCommands(Replace(screenKey, data))
    }

    /**
     * Return back to the needed screen from the chain.
     * Behavior in the case when no needed screens found depends on
     * the processing of the [BackTo] command in a [Navigator] implementation.
     *
     * @param screenKey screen key
     */
    fun backTo(screenKey: String) {
        executeCommands(BackTo(screenKey))
    }

    /**
     * Remove all screens from the chain and exit.
     * It's mostly used to finish the application or close a supplementary navigation chain.
     */
    fun finishChain() {
        executeCommands(
                BackTo(null),
                Back()
        )
    }

    /**
     * Return to the previous screen in the chain.
     * Behavior in the case when the current screen is the root depends on
     * the processing of the [Back] command in a [Navigator] implementation.
     */
    fun exit() {
        executeCommands(Back())
    }

    /**
     * Return to the previous screen in the chain and send result data.
     *
     * @param resultCode result data key
     * @param result     result data
     */
    fun exitWithResult(resultCode: Int?, result: Any) {
        exit()
        sendResult(resultCode, result)
    }

    /**
     * Return to the previous screen in the chain and show system message.
     *
     * @param message message to show
     */
    fun exitWithMessage(message: String) {
        executeCommands(
                Back(),
                SystemMessage(message)
        )
    }

    /**
     * Show system message.
     *
     * @param message message to show
     */
    fun showSystemMessage(message: String) {
        executeCommands(SystemMessage(message))
    }
}