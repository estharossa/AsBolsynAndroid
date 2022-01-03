package com.example.asbolsyn.utils.delegateadapter

interface DelegateAdapterItem {

    fun id(): Any

    fun content(): Any

    fun payload(other: Any): Payloadable

    /**
     * Simple marker interface for payloads
     */
    interface Payloadable

    /**
     * Simple sealed class to use with non-changeable items
     */
    sealed class ChangePayload: Payloadable {
        object None: ChangePayload()
    }
}