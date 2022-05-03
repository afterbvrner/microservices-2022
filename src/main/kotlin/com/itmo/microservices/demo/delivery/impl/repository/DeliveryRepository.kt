package com.itmo.microservices.demo.delivery.impl.repository

import com.itmo.microservices.demo.delivery.impl.entity.DeliverySlot
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DeliveryRepository: JpaRepository<DeliverySlot, UUID> {
    fun getDeliverySlotByDatetimeIs(date: Date):List<DeliverySlot>
    fun getDeliverySlotByIsReservedFalseAndAndDatetimeGreaterThan(date: Date):List<DeliverySlot>
}