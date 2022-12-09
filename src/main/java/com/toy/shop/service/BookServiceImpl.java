package com.toy.shop.service;

import com.toy.shop.domain.Book;
import com.toy.shop.domain.Category;
import com.toy.shop.dto.BookResponseDto;
import com.toy.shop.dto.BookSaveRequestDto;
import com.toy.shop.dto.BookUpdateRequestDto;
import com.toy.shop.exception.DataNotFoundException;
import com.toy.shop.repository.BookCustomRepository;
import com.toy.shop.repository.BookRepository;
import com.toy.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.toy.shop.common.ResultCode.BOOK_NOT_FOUND;
import static com.toy.shop.common.ResultCode.CATEGORY_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookCustomRepository bookCustomRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public BookResponseDto save(BookSaveRequestDto requestDto) {
        Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(() -> new DataNotFoundException(CATEGORY_NOT_FOUND));

        Book book = Book.createBook(requestDto, category);

        bookRepository.save(book);

        return new BookResponseDto(book);
    }

    @Override
    public List<BookResponseDto> findAll(Long categoryId, String searchWord) {
        List<Book> books = bookCustomRepository.findAll(categoryId, searchWord);

        return books.stream()
                .map(BookResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new DataNotFoundException(BOOK_NOT_FOUND));

        return new BookResponseDto(book);
    }

    @Override
    public BookResponseDto update(Long id, BookUpdateRequestDto requestDto) {
        Category category = null;
        Book book = bookRepository.findById(id).orElseThrow(() -> new DataNotFoundException(BOOK_NOT_FOUND));

        if (requestDto.getCategoryId() != null) {
            category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(() -> new DataNotFoundException(CATEGORY_NOT_FOUND));
        }

        book.updateBook(requestDto, category);

        return new BookResponseDto(book);
    }

    @Override
    public void delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new DataNotFoundException(BOOK_NOT_FOUND));

        bookRepository.delete(book);
    }
}
