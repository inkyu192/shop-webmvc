package spring.web.kotlin.domain.order

import jakarta.persistence.*
import spring.web.kotlin.domain.Base
import spring.web.kotlin.domain.item.Item

@Entity
@Table(name = "order_item")
class OrderItem protected constructor(
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item: Item,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: Order? = null,

    var orderPrice: Int,
    var count: Int
) : Base() {
    companion object {
        fun create(item: Item, orderPrice: Int, count: Int) = OrderItem(
            item = item,
            orderPrice = orderPrice,
            count = count
        )
            .also { it.item.removeQuantity(count) }
    }
}