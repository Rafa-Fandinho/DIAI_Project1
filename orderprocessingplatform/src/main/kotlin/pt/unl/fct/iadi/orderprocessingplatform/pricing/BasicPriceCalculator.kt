package pt.unl.fct.iadi.orderprocessingplatform.pricing

import pt.unl.fct.iadi.orderprocessingplatform.domain.Order

class BasicPriceCalculator : PriceCalculator {
    override fun calculateTotalPrice(order: Order): Double {
        var Sum = 0.0
        for (items in order.items) {
            Sum += items.unitPrice
        }
        return Sum
    }
}