package spring.web.kotlin.domain.order

import jakarta.persistence.*
import spring.web.kotlin.domain.Base
import spring.web.kotlin.domain.member.Member
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order protected constructor(
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    var orderItems: MutableList<OrderItem> = mutableListOf(),

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "delivery_id")
    var delivery: Delivery,

    var orderDate: LocalDateTime,

    @Enumerated(EnumType.STRING)
    var status: Status
) : Base() {
    companion object {
        fun create(member: Member, delivery: Delivery, orderItems: List<OrderItem>): Order {
            val order = Order(
                member = member,
                delivery = delivery,
                status = Status.ORDER,
                orderDate = LocalDateTime.now()
            )

            orderItems.forEach { order.setOrderItem(it) }

            return order
        }
    }

    fun setOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
        orderItem.order = this
    }

    enum class Status(
        val description: String
    ) {
        ORDER("주문"),
        CANCEL("취소")
    }
}
