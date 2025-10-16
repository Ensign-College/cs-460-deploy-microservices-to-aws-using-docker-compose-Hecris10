package com.example.explorecalijpa.web;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.explorecalijpa.business.TourRatingService;

/**
 * Tests that verify the tour rating API can be disabled via feature flags.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "features.tour-ratings=false")
public class TourRatingControllerFeatureFlagTest {

  private static final int TOUR_ID = 999;
  private static final String TOUR_RATINGS_URL = "/tours/" + TOUR_ID + "/ratings";
  private static final String TOUR_RATINGS_AVERAGE_URL = "/tours/" + TOUR_ID + "/ratings/average";
  private static final String TOUR_RATINGS_BATCH_URL = "/tours/" + TOUR_ID + "/ratings/batch";
  private static final String TOUR_RATINGS_DELETE_URL = "/tours/" + TOUR_ID + "/ratings/123";

  @Autowired
  private TestRestTemplate restTemplate;

  @MockBean
  private TourRatingService serviceMock;

  @Test
  void getAllRatingsDisabledReturnsNotFound() {
    ResponseEntity<String> res = restTemplate.getForEntity(TOUR_RATINGS_URL, String.class);
    assertThat(res.getStatusCode(), is(HttpStatus.NOT_FOUND));
  }

  @Test
  void createRatingDisabledReturnsNotFound() {
    RatingDto ratingDto = new RatingDto(5, "Great tour!", 123);
    ResponseEntity<String> res = restTemplate.postForEntity(TOUR_RATINGS_URL, ratingDto, String.class);
    assertThat(res.getStatusCode(), is(HttpStatus.NOT_FOUND));
  }

  @Test
  void getAverageDisabledReturnsNotFound() {
    ResponseEntity<String> res = restTemplate.getForEntity(TOUR_RATINGS_AVERAGE_URL, String.class);
    assertThat(res.getStatusCode(), is(HttpStatus.NOT_FOUND));
  }

  @Test
  void updateWithPutDisabledReturnsNotFound() {
    RatingDto ratingDto = new RatingDto(4, "Updated comment", 123);
    HttpEntity<RatingDto> entity = new HttpEntity<>(ratingDto);
    ResponseEntity<String> res = restTemplate.exchange(TOUR_RATINGS_URL, HttpMethod.PUT, entity, String.class);
    assertThat(res.getStatusCode(), is(HttpStatus.NOT_FOUND));
  }

  @Test
  void updateWithPatchDisabledReturnsNotFound() {
    RatingDto ratingDto = new RatingDto(3, "Patched comment", 123);
    HttpEntity<RatingDto> entity = new HttpEntity<>(ratingDto);
    ResponseEntity<String> res = restTemplate.exchange(TOUR_RATINGS_URL, HttpMethod.PATCH, entity, String.class);
    assertThat(res.getStatusCode(), is(HttpStatus.NOT_FOUND));
  }

  @Test
  void deleteRatingDisabledReturnsNotFound() {
    ResponseEntity<String> res = restTemplate.exchange(TOUR_RATINGS_DELETE_URL, HttpMethod.DELETE, null, String.class);
    assertThat(res.getStatusCode(), is(HttpStatus.NOT_FOUND));
  }

  @Test
  void createManyRatingsDisabledReturnsNotFound() {
    List<Integer> customers = List.of(1, 2, 3);
    ResponseEntity<String> res = restTemplate.postForEntity(TOUR_RATINGS_BATCH_URL + "?score=5", customers, String.class);
    assertThat(res.getStatusCode(), is(HttpStatus.NOT_FOUND));
  }
}