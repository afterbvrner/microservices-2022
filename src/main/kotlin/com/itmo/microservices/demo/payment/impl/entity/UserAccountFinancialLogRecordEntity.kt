package com.itmo.microservices.demo.payment.impl.entity

import com.itmo.microservices.demo.payment.api.model.FinancialOperationType
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class UserAccountFinancialLogRecordEntity {

    @Id
    @GeneratedValue
    var id: UUID? = null
    var type: FinancialOperationType? = null
    var amount: Int? = null
    var orderId: UUID? = null
    var paymentTransactionId: UUID? = null
    var timestamp: Long? = null

    constructor()

    constructor(id: UUID?, type: FinancialOperationType?, amount: Int?, orderId: UUID?, paymentTransactionId: UUID?, timestamp: Long?) {
        this.id = id
        this.type = type
        this.amount = amount
        this.orderId = orderId
        this.paymentTransactionId = paymentTransactionId
        this.timestamp = timestamp
    }
}