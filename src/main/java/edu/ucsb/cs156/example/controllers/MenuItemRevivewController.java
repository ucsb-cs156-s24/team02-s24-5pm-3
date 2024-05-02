package edu.ucsb.cs156.example.controllers;

import edu.ucsb.cs156.example.entities.MenuItemReview;
import edu.ucsb.cs156.example.errors.EntityNotFoundException;
import edu.ucsb.cs156.example.repositories.MenuItemReviewRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.time.LocalDateTime;

@Tag(name = "MenuItemReview")
@RequestMapping("/api/menuitemreview")
@RestController
@Slf4j
public class MenuItemRevivewController extends ApiController {

    @Autowired
    MenuItemReviewRepository MenuItemReviewRepository;

    @Operation(summary = "List all menu item reviews")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public Iterable<MenuItemReview> allUMenuItemReview() {
        Iterable<MenuItemReview> reviews = MenuItemReviewRepository.findAll();
        return reviews;
    }

    @Operation(summary = "Create a new review")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/post")
    public MenuItemReview postMenuItemReview(
            @Parameter(name = "itemid") @RequestParam long itemId,
            @Parameter(name = "email") @RequestParam String reviewerEmail,
            @Parameter(name = "stars (from 0 to 5)") @RequestParam int stars,
            @Parameter(name = "date (in iso format, e.g. YYYY-mm-ddTHH:MM:SS)") @RequestParam("localDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime,
            @Parameter(name = "comments") @RequestParam String comments)
            throws JsonProcessingException {

        log.info("localDateTime={}", localDateTime);

        MenuItemReview menuItemReview = new MenuItemReview();
        menuItemReview.setItemId(itemId);
        menuItemReview.setReviewerEmail(reviewerEmail);
        menuItemReview.setStars(stars);
        menuItemReview.setDateReviewed(localDateTime);
        menuItemReview.setComments(comments);

        MenuItemReview savedMenuItemReview = MenuItemReviewRepository.save(menuItemReview);

        return savedMenuItemReview;
    }
}
