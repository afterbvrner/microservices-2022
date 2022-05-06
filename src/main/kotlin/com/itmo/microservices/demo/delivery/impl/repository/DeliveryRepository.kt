package com.itmo.microservices.demo.delivery.impl.repository

import com.itmo.microservices.demo.delivery.impl.entity.DeliverySlot
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import java.util.*

@Repository
interface DeliveryRepository: JpaRepository<DeliverySlot, UUID> {
    fun findTop1000ByIsReservedFalse():List<DeliverySlot>
    fun findAllBySeconds(sec: Int):List<DeliverySlot>
    fun findByOrderId(orederId: Int):DeliverySlot
    fun findAllByIsReservedTrueAndDatetimeGreaterThan(timestamp: Timestamp): List<DeliverySlot>
}