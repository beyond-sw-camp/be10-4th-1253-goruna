package com.goruna.spring.bookmark.controller;

import com.goruna.spring.bookmark.dto.BookmarkResponseDTO;
import com.goruna.spring.bookmark.service.BookmarkService;
import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user/{userSeq}/bookmark")
@RequiredArgsConstructor
@Tag(name = "Bookmark API", description = "북마크 추가, 조회, 삭제 API")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    /* 북마크 추가 */
    @PostMapping("/shop/{shopSeq}")
    @Operation(summary = "북마크 추가 API", description = "북마크 추가를 위한 API입니다.")
    public ApiResponse<?> createBookmark(@PathVariable Long userSeq, @PathVariable Long shopSeq) {
        BookmarkResponseDTO responseDTO = bookmarkService.createBookmark(userSeq, shopSeq);
        return ResponseUtil.successResponse("북마크가 성공적으로 추가되었습니다.", responseDTO).getBody();
    }

    /* 북마크 삭제 */
    @DeleteMapping("/{bookmarkSeq}")
    @Operation(summary = "북마크 삭제 API", description = "북마크 삭제를 위한 API입니다.")
    public ApiResponse<?> deleteBookmark(@PathVariable Long userSeq, @PathVariable Long bookmarkSeq) {
        bookmarkService.deleteBookmark(bookmarkSeq);
        return ResponseUtil.successResponse("북마크가 성공적으로 삭제되었습니다.", null).getBody();
    }
}
