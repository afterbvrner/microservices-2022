package com.itmo.microservices.demo.warehouse.api.model

import java.util.*

data class ItemAmount (
        val itemId: UUID,
        val amount: Int
)
