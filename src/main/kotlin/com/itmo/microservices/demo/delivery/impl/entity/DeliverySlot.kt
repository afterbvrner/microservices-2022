package com.itmo.microservices.demo.delivery.impl.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class DeliverySlot {
    @Id
    var id: UUID? = null
    var datetime: Date? = null
    var isReserved: Boolean? = null
}