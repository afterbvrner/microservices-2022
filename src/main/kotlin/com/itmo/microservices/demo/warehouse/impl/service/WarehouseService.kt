package com.itmo.microservices.demo.warehouse.impl.service

import com.itmo.microservices.demo.warehouse.api.model.CatalogItemDto
import com.itmo.microservices.demo.warehouse.api.model.ItemAmount
import com.itmo.microservices.demo.warehouse.api.model.ProductBookingModel
import com.itmo.microservices.demo.warehouse.impl.entity.WarehouseItemEntity
import com.itmo.microservices.demo.warehouse.impl.repository.WarehouseRepository
import org.springframework.stereotype.Service
import java.util.UUID
import java.util.stream.Collectors

@Service
class WarehouseService(
        val warehouseRepository: WarehouseRepository
) {

    fun getItems(available: Boolean, size: Int): List<CatalogItemDto> {
        val items: List<WarehouseItemEntity> =
                if (available)
                    warehouseRepository.findAvailableItems(size)
                else
                    warehouseRepository.findAllItems(size)
        return items.stream()
                .map { item -> item.toModel() }
                .collect(Collectors.toList())
    }

    fun finalize(items: List<ItemAmount>): List<UUID> {
        val failedItems = mutableListOf<UUID>()
        for (item in items) {
            val itemEntity = warehouseRepository.findById(item.itemId)
            if (itemEntity.isEmpty || itemEntity.get().amount!! < item.amount)
                failedItems.add(item.itemId)
            else
                warehouseRepository.save(itemEntity.get().withAmount(itemEntity.get().amount!! - item.amount))
        }
        return failedItems
    }

    fun WarehouseItemEntity.toModel(): CatalogItemDto {
        return CatalogItemDto(
                this.id,
                this.title,
                this.description,
                this.price,
                this.amount
        )
    }
}
