package com.example.asbolsyn.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.asbolsyn.R
import com.example.asbolsyn.extensions.dp

object AlertManager {

    fun showAlert(
        context: Context,
        title: String? = null,
        message: String? = null,
        positive: Int? = null,
        positiveCallback: (() -> Unit)? = null,
        negative: Int? = null,
        negativeCallback: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            positiveCallback?.let {
                setPositiveButton(positive ?: R.string.yes) { _, _ ->
                    it.invoke()
                }
            }
            negativeCallback?.let {
                setNegativeButton(negative ?: R.string.no) { _, _ ->
                    it.invoke()
                }
            }
        }.create().show()
    }

    fun showAlert(
        context: Context,
        title: String? = null,
        message: String? = null,
        ok: (() -> Unit)? = null,
        cancel: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.yes) { _, _ ->
                ok?.invoke()
            }
            .setNegativeButton(R.string.no) { _, _ ->
                cancel?.invoke()
            }
            .create()
            .show()
    }

    fun showOkCancelAlert(
        context: Context,
        title: String? = null,
        message: String? = null,
        ok: (() -> Unit)? = null,
        cancel: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.yes) { _, _ ->
                ok?.invoke()
            }
            .setNegativeButton(R.string.no) { _, _ ->
                cancel?.invoke()
            }
            .create()
            .show()
    }

    fun showOkCancelAlert(
        context: Context,
        message: String? = null,
        ok: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton(R.string.yes) { dialog, _ ->
                ok?.invoke()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    fun showOkCancelAlertWithView(
        context: Context,
        title: String? = null,
        view: View? = null,
        ok: (() -> Unit)? = null,
        cancel: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setView(view)
            .setPositiveButton(R.string.yes) { _, _ ->
                ok?.invoke()
            }
            .setNegativeButton(R.string.no) { _, _ ->
                cancel?.invoke()
            }
            .create()
            .show()
    }

    fun showErrorAlert(
        context: Context,
        title: String?,
        message: String?,
        useCustomTitle: Boolean = false,
        ok: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(context).apply {
            if (useCustomTitle) {
                setCustomTitle(
                    TextView(context).apply {
                        textSize = 16f
                        setPadding(24.dp, 10.dp, 24.dp, 0)
                        setTypeface(null, Typeface.BOLD)
                        setTextColor(Color.BLACK)
                        text = title ?: context.getString(R.string.error)
                    }
                )
            } else {
                setTitle(title ?: context.getString(R.string.error))
            }
            setMessage(message ?: context.getString(R.string.error_general))
            setPositiveButton(R.string.ok) { _, _ ->
                ok?.invoke()
            }
        }.create().show()
    }

    fun showOKAlert(
        context: Context,
        message: String?,
        cancellable: Boolean = true,
        ok: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton(R.string.ok) { _, _ ->
                ok?.invoke()
            }
            .setCancelable(cancellable)
            .create()
            .show()
    }

    fun showOKAlertWithTitle(
        context: Context,
        title: String?,
        message: String?,
        ok: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.ok) { _, _ ->
                ok?.invoke()
            }
            .create()
            .show()
    }

    fun showNonCancellableAlert(
        context: Context,
        title: String?,
        message: String?,
        ok: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.ok) { _, _ ->
                ok?.invoke()
            }
            .setCancelable(false)
            .create()
            .show()
    }

    fun showOkCancelAlertWithButtonTitles(
        context: Context,
        title: String? = null,
        message: String? = null,
        ok: (() -> Unit)? = null,
        positiveButtonTitle: Int,
        negativeButtonTitle: Int
    ) {
        AlertDialog.Builder(context)
            .setMessage(message)
            .setTitle(title)
            .setPositiveButton(positiveButtonTitle) { dialog, _ ->
                ok?.invoke()
                dialog.dismiss()
            }
            .setNegativeButton(negativeButtonTitle) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    fun getCustomAlert(
        context: Context,
        view: View
    ): AlertDialog = AlertDialog.Builder(context).apply {
        setView(view)
    }.create()
}