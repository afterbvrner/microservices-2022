package com.itmo.microservices.demo.order.impl.entity

import com.itmo.microservices.demo.order.common.PaymentStatus
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class PaymentLogRecordEntity {
    @Id
    @GeneratedValue
    var id: UUID? = null
    var timestamp: Long? = null
    var status: PaymentStatus? = null
    var amount: Int? = null
    var transactionId: UUID? = null

    constructor()

    constructor(id: UUID, timestamp: Long, status: PaymentStatus, amount: Int, transactionId: UUID) {
        this.id = id
        this.timestamp = timestamp
        this.status = status
        this.amount = amount
        this.transactionId = transactionId
    }
}
