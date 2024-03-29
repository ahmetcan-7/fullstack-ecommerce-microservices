package com.ahmetcan7.productservice.controller;


import com.ahmetcan7.productservice.dto.Pagination;
import com.ahmetcan7.productservice.dto.comment.CommentDto;
import com.ahmetcan7.productservice.dto.product.*;
import com.ahmetcan7.productservice.enumeration.Sort;
import com.ahmetcan7.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductDtoById(@PathVariable UUID id){
        return ResponseEntity.ok(productService.getProductDtoById(id));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByProductId(@PathVariable UUID id){
        return ResponseEntity.ok(productService.getCommentsByProductId(id));
    }

    @GetMapping("/findByIds/{productIds}")
    public ResponseEntity<List<ProductDto>> getProductsByIds(@PathVariable List<UUID> productIds){
        return ResponseEntity.ok(productService.getProductsByIds(productIds));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductDto> saveProduct(@Valid @RequestBody CreateProductRequest createProductRequest){
        return new ResponseEntity<>(productService.createProduct(createProductRequest),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody UpdateProductRequest updateProductRequest,
                                                    @PathVariable UUID id){
        return ResponseEntity.ok(productService.updateProduct(updateProductRequest,id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<UUID> deleteProduct(@PathVariable UUID id){
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductSearchDto>> getProductBySearch(@RequestParam(required = false, defaultValue = "") String searchTerm,
                                                     @RequestParam(required = false, defaultValue = "0") int page,
                                                     @RequestParam(required = false, defaultValue = "10") int size,
                                                     @RequestParam(required = false, defaultValue = "dateAsc") Sort sort,
                                                     @RequestParam(required = false, defaultValue = "") String filter){
        return ResponseEntity.ok(productService.searchProduct(searchTerm,page,size,sort,filter));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Pagination<ProductDto>> getProductByPagination(@RequestParam(required = false,defaultValue = "0")  int pageNo,
                                                             @RequestParam(required = false,defaultValue = "10") int pageSize){
        return ResponseEntity.ok(productService.getAllProducts(pageNo,pageSize));
    }
}
