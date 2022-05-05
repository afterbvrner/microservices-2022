package com.itmo.microservices.demo.delivery.impl.repository

import com.itmo.microservices.demo.delivery.impl.entity.DeliverySlot
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import java.util.*

@Repository
interface DeliveryRepository: JpaRepository<DeliverySlot, UUID> {
    fun findAllByDatetimeIs(date: Timestamp):List<DeliverySlot>
    fun findAllByIsReservedFalseAndDatetimeGreaterThan(date: Timestamp):List<DeliverySlot>
}