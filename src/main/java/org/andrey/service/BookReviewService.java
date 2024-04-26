package org.andrey.service;

import lombok.RequiredArgsConstructor;
import org.andrey.database.entity.BookReview;
import org.andrey.database.repository.BookRepository;
import org.andrey.database.repository.BookReviewRepository;
import org.andrey.database.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public void addReview(Long bookId, String email, String review) {
        bookReviewRepository.save(BookReview.builder()
                .book(bookRepository.findById(bookId).orElseThrow())
                .user(userRepository.findByEmail(email).orElseThrow())
                .review(review)
                .build());
    }

}
