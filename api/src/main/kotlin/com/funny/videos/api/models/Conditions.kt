package com.funny.videos.api.models

import java.util.*

abstract class Conditions(
    // Pagination conditions
    val page: Int = 0,
    val size: Int = 20,
    val sortBy: String? = "id, DESC"
) {
    val offset = page * size

    /**
     * Get order values:
     * - columns must exist in [order_columns]
     * - direction must exist in [COLUMN_DIRECTIONS]
     */
    val orderBy : String get() =
        if (this.sortBy.isNullOrBlank() || this.sortBy.isEmpty()) {
            ""
        } else {
            val orders = this.sortBy.split(",").toMutableList()
            if (orders.size < 2) {
                orders.plus("DESC")
            }
            if (order_columns.containsKey(orders[0])) {
                orders[0] = order_columns.get(orders[0].lowercase(Locale.getDefault())).toString()
            }
            if (!COLUMN_DIRECTIONS.contains(orders[1])) {
                orders[1] = "DESC"
            }

            orders.joinToString(" ")
        }

    /** List of order columns */
    protected abstract val order_columns: Map<String, String>

    companion object {
        private val COLUMN_DIRECTIONS = arrayOf("DESC", "ASC")
    }
}