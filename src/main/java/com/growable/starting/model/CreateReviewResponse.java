package com.growable.starting.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReviewResponse {
    private Review review;
    private Mentor mentor;

    public CreateReviewResponse(Review review, Mentor mentor) {
        this.review = review;
        this.mentor = mentor;
    }
}
