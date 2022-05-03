package com.itmo.microservices.demo.delivery.impl.service

import com.itmo.microservices.demo.delivery.api.service.DeliveryService
import com.itmo.microservices.demo.delivery.impl.repository.DeliveryRepository
import com.itmo.microservices.demo.users.impl.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class DeliveryServiceImpl(
    private val deliveryRepository: DeliveryRepository): DeliveryService {

    override fun getAvailableSlots(numberOfSlots: Int): List<Int> =
        deliveryRepository.getDeliverySlotByIsReservedFalseAndAndDatetimeGreaterThan(Date())
            .groupBy { x -> x.datetime }
            .keys
            .mapNotNull { x -> x?.time?.toInt() }
            .slice(0..numberOfSlots)
            .toList()

    override fun reserveAvailableSlot(slot: Int) {
        var slot = deliveryRepository.getDeliverySlotByDatetimeIs(Date(slot.toLong()))
            .first { x -> !x.isReserved!! }
        slot.isReserved = true
        deliveryRepository.save(slot)
    }

    override fun cancelSlot(slot: Int) {
        var slot = deliveryRepository.getDeliverySlotByDatetimeIs(Date(slot.toLong()))
            .first { x -> x.isReserved!! }
        slot.isReserved = false
        deliveryRepository.save(slot)
    }


}