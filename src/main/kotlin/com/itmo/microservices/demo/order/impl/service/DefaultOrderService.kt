package com.itmo.microservices.demo.order.impl.service

import com.itmo.microservices.demo.common.exception.NotFoundException
import com.itmo.microservices.demo.delivery.api.model.BookingDto
import com.itmo.microservices.demo.order.impl.entity.BookingEntity
import com.itmo.microservices.demo.order.api.model.OrderModel
import com.itmo.microservices.demo.order.api.model.PaymentLogRecordDto
import com.itmo.microservices.demo.order.api.service.OrderService
import com.itmo.microservices.demo.order.common.OrderStatus
import com.itmo.microservices.demo.order.impl.entity.ItemMapEntity
import com.itmo.microservices.demo.order.impl.entity.OrderEntity
import com.itmo.microservices.demo.order.impl.entity.PaymentLogRecordEntity
import com.itmo.microservices.demo.order.impl.repository.OrderRepository
import com.itmo.microservices.demo.order.impl.service.WarehouseService
import org.springframework.stereotype.Service
import org.webjars.NotFoundException
import java.util.*
import java.util.stream.Collectors

@Service
class DefaultOrderService (
    val orderRepository: OrderRepository, val warehouseService: WarehouseService,
    private val eventBus: EventBus
) : OrderService {
    override fun createOrder(): OrderModel {
        val order: OrderEntity = OrderEntity(null, System.currentTimeMillis(), OrderStatus.COLLECTING, emptyList(), null, emptyList())
        orderRepository.save(order)
        return order.toModel()
    }

    override fun getOrder(id: UUID): OrderModel {

        val optionalOrder: Optional<OrderEntity> = orderRepository.findById(id)
        if (optionalOrder.isPresent) {
            val order: OrderEntity = optionalOrder.get()
            return order.toModel()
        } else {
            throw NotFoundException("Order with id $id not found")
        }
    }

    override fun moveItemToCart(orderId: UUID, itemId: UUID, amount: Int) {
        val optionalOrder: Optional<OrderEntity> = orderRepository.findById(orderId);
        if (optionalOrder.isPresent) {
            val order: OrderEntity = optionalOrder.get();
            order.itemsMap.add(ItemMapEntity(
                null,
                itemId,
                amount,
            ))
            orderRepository.save(order);
            EventBus.post();
        } else
            throw NotFoundException("Order or Item not found");
    }

    override fun finalizeOrder(id: UUID): BookingDto {

        val optionalOrder: Optional<OrderEntity> = orderRepository.findById(id)
        val bookingDto = BookingDto(UUID(0,0), emptySet())
        if (optionalOrder.isPresent) {
            val order: OrderEntity = optionalOrder.get()
            order.status = OrderStatus.BOOKED

            orderRepository.save(order)

            bookingDto.failedItems = warehouseService.finalizeItems()
        } else {
            throw NotFoundException("Order with id $id not found")
        }
        return bookingDto

    }

    override fun setDeliverySlot(id: UUID, slotInSec: Int): BookingDto {
        val optionalOrder: Optional<OrderEntity> = orderRepository.findById(id)
        if (optionalOrder.isPresent) {
            val order: OrderEntity = optionalOrder.get()
            order.deliveryDuration = slotInSec
            orderRepository.save(order)
            eventBus.post()
        } else {
            throw NotFoundException("Order with id $id not found")
        }
        return BookingDto(UUID(0, 0), emptySet())
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