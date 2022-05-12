package com.itmo.microservices.demo.payment.impl.entity

import com.itmo.microservices.demo.payment.api.model.SubmissionResult
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class PaymentSubmissionEntity {

    @Id
    @GeneratedValue
    var id: UUID? = null
    var timestamp: Long? = null
    var transactionId: UUID? = null
    var result: SubmissionResult? = null

    constructor()

    constructor(id: UUID?, timestamp: Long?, transactionId: UUID?, result: SubmissionResult) {
        this.id = id
        this.timestamp = timestamp
        this.transactionId = transactionId
    }
}
