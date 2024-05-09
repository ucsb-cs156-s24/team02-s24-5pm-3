const ucsbDatesFixtures = {
  oneReview: {
    id: 1,
    itemID: 5,
    reviewerEmail: "reviewer@gmail.com",
    stars: 5,
    dateReviewed: "2022-01-02T12:00:00",
    comments: "delicious",
  },
  threeReviews: [
    {
      id: 1,
      itemID: 5,
      reviewerEmail: "reviewer@gmail.com",
      stars: 5,
      dateReviewed: "2022-01-02T12:00:00",
      comments: "delicious",
    },
    {
      id: 2,
      itemID: 10,
      reviewerEmail: "eater@gmail.com",
      stars: 3,
      dateReviewed: "2022-04-03T12:00:00",
      comments: "meh",
    },
    {
      id: 3,
      itemID: 15,
      reviewerEmail: "bigeats@gmail.com",
      stars: 0,
      dateReviewed: "2022-07-04T12:00:00",
      comments: "yuck",
    },
  ],
};

export { menuItemReviewFixtures };
