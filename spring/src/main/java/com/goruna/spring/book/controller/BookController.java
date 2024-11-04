package com.goruna.spring.book.controller;

import com.goruna.spring.book.service.BookService;
import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "book", description = "예약 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // 예약 신청
    @PostMapping("/category/{categorySeq}/shop/{shopSeq}")
    @Operation(summary = "예약 신청", description = "예약을 신청합니다.")
    public ApiResponse<?> createBook(
            @PathVariable(value = "categorySeq") Long categorySeq,
            @PathVariable(value = "shopSeq") Long shopSeq,
            @RequestParam int bookQty
    ) {
        bookService.createBook(categorySeq, shopSeq, bookQty);
        return ResponseUtil.successResponse("예약이 성공적으로 생성되었습니다.").getBody();
    }
}