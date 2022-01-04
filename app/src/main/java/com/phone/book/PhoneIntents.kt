package com.phone.book

interface PhoneIntents {
    companion object {
        private const val packageName = "com.phone.book"
        const val ACTION_MODIFY_DEPT_SUCCESS = "$packageName.action.MODIFY_DEPT_SUCCESS"
        const val ACTION_MODIFY_CALL_CARD_SUCCESS = "$packageName.action.MODIFY_CALL_CARD_SUCCESS"

    }
}