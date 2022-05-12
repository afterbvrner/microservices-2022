package com.itmo.microservices.demo.order.impl.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class ItemMapEntity {
    @Id
    @GeneratedValue
    var id : UUID? = null
    var catalogItemId: UUID? = null
    var amount : Int? = null

    constructor()

    constructor(id: UUID?, catalogItemId: UUID, amount: Int) {
        this.id = id
        this.catalogItemId = catalogItemId
        this.amount = amount
    }
}
