package com.itmo.microservices.demo.warehouse.api.model
import java.util.*
import java.util.UUID.randomUUID

data class CatalogItemDto(
    var id: UUID,
    var title: String,
    var description: String,
    var price: Int,
    var amount: Int
)
