package pt.unl.fct.iadi.orderprocessingplatform.domain

class Order(
    val id: String,
    val items: List<OrderItem>,
    val userId: String,
    val createdAt: Long = System.currentTimeMillis()
){

}