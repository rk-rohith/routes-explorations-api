package com.routes.explorations.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinationCategoryId implements Serializable {
    private Long destinationId;
    private Long categoryId;
}

