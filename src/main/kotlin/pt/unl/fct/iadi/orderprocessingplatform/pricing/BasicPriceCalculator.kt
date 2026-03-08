package pt.unl.fct.iadi.orderprocessingplatform.pricing

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import pt.unl.fct.iadi.orderprocessingplatform.domain.Order

@Component
@ConditionalOnProperty(name = ["pricing.promo.enabled"], havingValue = "false", matchIfMissing = true)
class BasicPriceCalculator : PriceCalculator {
    override fun calculateTotalPrice(order: Order): Double {
        var Sum = 0.0
        for (items in order.items) {
            Sum += items.unitPrice * items.quantity
        }
        return Sum
    }
}