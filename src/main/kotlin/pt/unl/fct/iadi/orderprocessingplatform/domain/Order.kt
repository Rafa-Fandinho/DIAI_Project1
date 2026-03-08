package pt.unl.fct.iadi.orderprocessingplatform.domain

data class Order(
    val id: String,
    val items: List<OrderItem>,
    val userId: String,
    val createdAt: Long = System.currentTimeMillis()
){
    data class OrderItem(
        val productId: String,
        val quantity: Int,
        val unitPrice: Double
    )
}