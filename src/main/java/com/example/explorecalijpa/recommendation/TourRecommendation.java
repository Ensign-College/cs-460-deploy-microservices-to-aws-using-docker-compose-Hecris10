package com.example.explorecalijpa.recommendation;

/**
 * DTO for Tour Recommendation API responses.
 * Contains tour summary with rating statistics.
 */
public record TourRecommendation(
    Integer tourId,
    String title,
    Double averageScore,
    Long reviewCount) {
}
