package spring.web.kotlin.domain

import jakarta.persistence.*
import spring.web.kotlin.constant.Category

@Entity
class Item private constructor(
    var name: String,
    var description: String,
    var price: Int,
    var quantity: Int,

    @Enumerated(EnumType.STRING)
    var category: Category
) {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    var id: Long? = null

    companion object {
        fun create(name: String, description: String, price: Int, quantity: Int, category: Category): Item {
            return Item(name, description, price, quantity, category)
        }
    }
    fun update(name: String, description: String, price: Int, quantity: Int, category: Category) {
        this.name = name
        this.description = description
        this.price = price
        this.quantity = quantity
        this.category = category
    }
}