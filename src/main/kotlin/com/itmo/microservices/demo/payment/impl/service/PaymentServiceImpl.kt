package com.itmo.microservices.demo.payment.impl.service

import com.itmo.microservices.demo.payment.api.model.PaymentSubmissionDto
import com.itmo.microservices.demo.payment.api.model.SubmissionResult
import com.itmo.microservices.demo.payment.api.model.UserAccountFinancialLogRecordDto
import com.itmo.microservices.demo.payment.api.service.PaymentService
import com.itmo.microservices.demo.payment.impl.entity.PaymentSubmissionEntity
import com.itmo.microservices.demo.payment.impl.repository.PaymentSubmissionRepository
import com.itmo.microservices.demo.payment.impl.repository.UserAccountFinancialLogRecordRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PaymentServiceImpl(private val paymentSubmissionRepository: PaymentSubmissionRepository,
                         private val userAccountFinancialLogRecordRepository: UserAccountFinancialLogRecordRepository): PaymentService {

    override fun getPaymentFinLog(orderId: UUID): List<UserAccountFinancialLogRecordDto> {
        return userAccountFinancialLogRecordRepository.findAllByOrderId(orderId)
            .map { x -> UserAccountFinancialLogRecordDto(x.type, x.amount, x.orderId, x.paymentTransactionId, x.timestamp) }
    }

    override fun orderPayment(orderId: UUID): PaymentSubmissionDto {
        val transactionId = UUID.randomUUID();
        val submission = PaymentSubmissionEntity(
                null,
                System.currentTimeMillis(),
                transactionId,
                SubmissionResult.SUCCESS
        )
        paymentSubmissionRepository.save(submission)
        return submission.toModel()
    }


    fun PaymentSubmissionEntity.toModel(): PaymentSubmissionDto {
        return PaymentSubmissionDto(
                this.timestamp,
                this.transactionId
        )
    }
}