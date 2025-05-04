package usecase.model;

import lombok.Builder;

@Builder
public record Product(
    Long id,
    String name,
    Double price,
    Long categoryId
) {

}
