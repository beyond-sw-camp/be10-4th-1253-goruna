package com.goruna.spring.shop.service;

import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.product.entity.Product;
import com.goruna.spring.product.repository.ProductRepository;
import com.goruna.spring.shop.dto.ShopDetailReadResDTO;
import com.goruna.spring.shop.dto.ShopListReadResDTO;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    // 카테고리 별 매장 목록 조회
    public List<ShopListReadResDTO> readShopsByCategory(Long categorySeq, Integer page) {
        if (page == null || page < 1) {
            throw new CustomException(ErrorCodeType.INVALID_VALUE);
        }

        int pageSize = 6;
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        List<Product> shops = shopRepository.readShopByCategorySeq(categorySeq, pageable);
        return shops.stream()
                .map(shop -> {
                    return modelMapper.map(shop, ShopListReadResDTO.class);
                })
                .collect(Collectors.toList());
    }

    // 매장 상세 내역 조회
    public ShopDetailReadResDTO readShopDetail(Long shopSeq) {
        Shop shop = shopRepository.findById(shopSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.SHOP_NOT_FOUND));

        Product product = productRepository.findFirstByShop_ShopSeqOrderByRegDateDesc(shopSeq);

        ShopDetailReadResDTO shopDetailReadResDTO = modelMapper.map(shop, ShopDetailReadResDTO.class);
        shopDetailReadResDTO.setProductClosedAt(product.getProductClosedAt());
        shopDetailReadResDTO.setProductName(product.getProductName());
        shopDetailReadResDTO.setProductDescription(product.getProductDescription());
        shopDetailReadResDTO.setProductQty(product.getProductQty());
        shopDetailReadResDTO.setProductOriginalPrice(product.getProductOriginalPrice());
        shopDetailReadResDTO.setProductSalePrice(product.getProductSalePrice());
        shopDetailReadResDTO.setProductImgUrl(product.getProductImgUrl());
        return shopDetailReadResDTO;
    }
}