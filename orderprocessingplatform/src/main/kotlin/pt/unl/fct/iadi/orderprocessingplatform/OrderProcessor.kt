package pt.unl.fct.iadi.orderprocessingplatform

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import pt.unl.fct.iadi.orderprocessingplatform.domain.Order
import pt.unl.fct.iadi.orderprocessingplatform.domain.PaymentRequest
import pt.unl.fct.iadi.orderprocessingplatform.payment.PaymentGateway
import pt.unl.fct.iadi.orderprocessingplatform.pricing.PriceCalculator

@Component
class OrderProcessor(private val priceCalculator : PriceCalculator, private val paymentGateway: PaymentGateway) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val order = Order(
            "ORD-2026-001",
            listOf(
                Order.OrderItem("LAPTOP-001", 2, 999.99),
                Order.OrderItem("MOUSE-042", 3, 29.99),
                Order.OrderItem("KEYBOARD-123", 1, 149.99)
            ),
            "user123"
        )
        val result = processOrder(order)

        for(res in result) {
            println(res)
        }
    }

    fun processOrder(order: Order): List<String> {
        val totalPrice = kotlin.math.round(priceCalculator.calculateTotalPrice(order) * 100)/100
        val paymentRequest = PaymentRequest(order.id, totalPrice)
        val payment = paymentGateway.processPayment(paymentRequest)
        val lines = mutableListOf<String>()

        lines.add("Order ID: ${order.id}")
        lines.add("User ID: ${order.userId}")
        lines.add("Created at: ${order.createdAt}")
        lines.add("")
        lines.add("Items:")

        for (item in order.items) {
            val itemTotal = item.quantity * item.unitPrice
            lines.add("  - ${item.productId}: ${item.quantity} x $${item.unitPrice} = $${"%.2f".format(itemTotal)}")
        }

        lines.add("")
        lines.add("Total Price: $${"%.2f".format(totalPrice)}")
        lines.add("Calculator Used: ${priceCalculator.javaClass.simpleName}")
        lines.add("")
        lines.add("Payment Status: ${payment.status}")
        lines.add("Payment Gateway: ${payment.metadata["gateway"]}")

        if (payment.metadata.containsKey("transactionId")) {
            lines.add("Transaction ID: ${payment.metadata["transactionId"]}")
        }

        if (payment.metadata.containsKey("reason")) {
            lines.add("Reason: ${payment.metadata["reason"]}")
        }

        lines.add("")
        lines.add("=== Processing Complete ===")

        return lines
    }
}
