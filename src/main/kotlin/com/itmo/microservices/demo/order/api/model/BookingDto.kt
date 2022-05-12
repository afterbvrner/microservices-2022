package com.itmo.microservices.demo.order.api.model

import java.util.*

data class BookingDto (
    var id: UUID?,
    var failedItems: Set<UUID>
)
