package com.itmo.microservices.demo.order.impl.service

import com.itmo.microservices.demo.common.exception.NotFoundException
import com.itmo.microservices.demo.delivery.api.model.BookingDto
import com.itmo.microservices.demo.order.impl.entity.BookingEntity
import com.itmo.microservices.demo.order.api.model.OrderModel
import com.itmo.microservices.demo.order.api.model.PaymentLogRecordDto
import com.itmo.microservices.demo.order.api.service.OrderService
import com.itmo.microservices.demo.order.impl.entity.ItemMapEntity
import com.itmo.microservices.demo.order.impl.entity.OrderEntity
import com.itmo.microservices.demo.order.impl.entity.PaymentLogRecordEntity
import com.itmo.microservices.demo.order.impl.repository.ItemMapRepository
import com.itmo.microservices.demo.order.impl.repository.OrderRepository
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class DefaultOrderService (
    val orderRepository: OrderRepository,
    val itemMapRepository : ItemMapRepository,
        ) : OrderService {
    override fun createOrder(): OrderModel {
        TODO("Not yet implemented")
    }

    override fun getOrder(id: UUID): OrderModel {
        TODO("Not yet implemented")
    }

    override fun moveItemToCart(orderId: UUID, itemId: UUID, amount: Int) {
        val optionalOrder: Optional<OrderEntity> = orderRepository.findById(orderId);
        val optionalItem: Optional<ItemMapEntity> = itemMapRepository.findById(itemId);
        if (optionalOrder.isPresent && optionalItem.isPresent) {
            val order: OrderEntity = optionalOrder.get();
            val item: ItemMapEntity = optionalItem.get();

            if (item.amount!! > 0) {
                //order.itemsMap.add(item)
                orderRepository.save(order);
                eventBus
            } else
                throw NotFoundException("Amount item ($itemId) is 0");

            //order.itemsMap.
        } else
            throw NotFoundException("Order or Item not found");
    }

    override fun finalizeOrder(id: UUID): BookingDto {
        TODO("Not yet implemented")
    }

    override fun setDeliverySlot(id: UUID, slotInSec: Int): BookingDto {
        TODO("Not yet implemented")
    }

    fun PaymentLogRecordEntity.toModel(): PaymentLogRecordDto {
        return PaymentLogRecordDto(
            this.timestamp,
            this.status,
            this.amount,
            this.transactionId
        )
    }

    fun OrderEntity.toModel(): OrderModel {
        return OrderModel(
            this.id,
            this.timeCreated,
            this.status,
            this.itemsMap.associate { it.id to it.amount },
            this.deliveryDuration,
            this.paymentHistory.stream()
                .map { item -> item.toModel() }
                .collect(Collectors.toList())
        )
    }

    fun BookingEntity.toModel(): BookingDto {
        return BookingDto(this.id, this.failedItems)
    }
}