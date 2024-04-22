package org.andrey.database.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.andrey.database.entity.Book;
import org.andrey.dto.filter.BookFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class FilterBookRepositoryImpl implements FilterBookRepository {

    private final EntityManager entityManager;

    @Override
    public List<Book> findAllByFilter(BookFilter filter, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);
        query.select(book);

        List<Predicate> predicates = new ArrayList<>();

        if(filter.name() != null) predicates.add(cb.like(book.get("name"), capitalized(filter.name())));
        if(filter.author() != null) predicates.add(cb.like(book.get("author"), capitalizedAndContains(filter.author())));
        if(filter.genre() != null) predicates.add(cb.like(book.get("genre"), capitalizedAndContains(filter.genre())));
        if(filter.yearOfPublishGe() != null) predicates.add(cb.ge(book.get("yearOfPublish"), filter.yearOfPublishGe()));
        if(filter.pagesLe() != null) predicates.add(cb.le(book.get("pages"), filter.pagesLe()));

        Integer pricePaperFrom = filter.pricePaperFrom();
        Integer pricePaperTo = filter.pricePaperTo();
        if(pricePaperFrom != null && pricePaperTo != null && pricePaperFrom < pricePaperTo) {
            predicates.add(cb.between(book.get("pricePaper"), pricePaperFrom, pricePaperTo));
        } else if (pricePaperFrom == null && pricePaperTo != null) {
            predicates.add(cb.le(book.get("pricePaper"), pricePaperTo));
        } else if (pricePaperFrom != null && pricePaperTo == null) {
            predicates.add(cb.ge(book.get("pricePaper"), pricePaperFrom));
        }

        Integer priceDigitalFrom = filter.priceDigitalFrom();
        Integer priceDigitalTo = filter.priceDigitalTo();
        if(priceDigitalFrom != null && priceDigitalTo != null && priceDigitalFrom < priceDigitalTo) {
            predicates.add(cb.between(book.get("priceDigital"), priceDigitalFrom, priceDigitalTo));
        } else if (priceDigitalFrom == null && priceDigitalTo != null) {
            predicates.add(cb.le(book.get("priceDigital"), priceDigitalTo));
        } else if (priceDigitalFrom != null && priceDigitalTo == null) {
            predicates.add(cb.ge(book.get("priceDigital"), priceDigitalFrom));
        }

        query.where(predicates.toArray(Predicate[]::new));
        query.orderBy(QueryUtils.toOrders(pageable.getSort(), book, cb));

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
//        int offset = (int) pageable.getOffset();
        return entityManager.createQuery(query)
//                .setFirstResult(offset)
                .setFirstResult(pageNumber * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    private static String capitalized(String word) {
        return word != null && word.length() > 1 ? Stream.of(word)
                .map(String::toLowerCase)
                .map(it -> it.substring(0, 1).toUpperCase() + it.substring(1))
                .findFirst()
                .get() : null;
    }

    private static String capitalizedAndContains(String word) {
        return word != null ? Stream.of(word)
                .map(FilterBookRepositoryImpl::capitalized)
                .map(FilterBookRepositoryImpl::contains)
                .findFirst()
                .get() : null;
    }

    private static String contains(String word) {
        return word != null ? "%" + word + "%" : null;
    }
}
