package com.itmo.microservices.demo.delivery.impl.entity

import java.sql.Timestamp
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class DeliverySlot {
    @Id
    @GeneratedValue
    var id: UUID? = null
    var datetime: Timestamp? = null
    var isReserved: Boolean? = null
    var seconds: Int? = null
    var orderId: Int? = null
}