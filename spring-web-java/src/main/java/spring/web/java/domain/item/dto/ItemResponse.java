package spring.web.java.domain.item.dto;

import spring.web.java.domain.item.Item;

public record ItemResponse(
	Long id,
	String name,
	String description,
	int price,
	int quantity
) {
	public ItemResponse(Item item) {
		this(
			item.getId(),
			item.getName(),
			item.getDescription(),
			item.getPrice(),
			item.getQuantity()
		);
	}
}
