package com.itmo.microservices.demo.order.impl.entity

import com.itmo.microservices.demo.order.common.OrderStatus
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "orders")
class OrderEntity {

    @Id
    @GeneratedValue
    var id: UUID? = null
    var timeCreated: Long? = null
    var status: OrderStatus? = null
    @OneToMany(cascade = arrayOf(CascadeType.ALL))
    var itemsMap: MutableList<ItemMapEntity> = mutableListOf<ItemMapEntity>()
    var deliveryDuration: Int? = null
    @OneToMany
    var paymentHistory: List<PaymentLogRecordEntity> = emptyList()

    constructor()

    constructor(id: UUID?,
                timeCreated: Long?,
                status: OrderStatus?,
                itemsMap: MutableList<ItemMapEntity>,
                deliveryDuration: Int?,
                paymentHistory: List<PaymentLogRecordEntity>) {
        this.id = id
        this.timeCreated = timeCreated
        this.status = status
        this.itemsMap = itemsMap
        this.deliveryDuration = deliveryDuration
        this.paymentHistory = paymentHistory
    }
}