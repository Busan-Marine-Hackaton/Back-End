package com.example.marinehackatonbe.api;

import com.example.marinehackatonbe.application.ProductService;
import com.example.marinehackatonbe.domain.Product;
import com.example.marinehackatonbe.dto.ProductDto;
import com.example.marinehackatonbe.global.domain.CommonResponse;
import com.example.marinehackatonbe.global.exception.CustomException;
import com.example.marinehackatonbe.global.exception.ExceptionContent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;
	@PostMapping("/buy")
	public ResponseEntity<CommonResponse<Void>> buyProduct(@RequestParam Long memberId, @RequestParam Long productId) {
		if (productService.buyProduct(memberId, productId)) {
			return ResponseEntity.ok().body(CommonResponse.ofSuccess("물건 구매에 성공하였습니다.", null));
		} else {
			throw new CustomException(ExceptionContent.FAIL_PURCHASE);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<CommonResponse<List<Product>>> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		return ResponseEntity.ok().body(CommonResponse.ofSuccess("모든 물건 리스트가 반환되었습니다.", products));
	}

	@PostMapping
	public ResponseEntity<CommonResponse<Void>> createProduct(@RequestBody ProductDto productDto) {
		productService.createProduct(productDto);
		return ResponseEntity.ok().body(CommonResponse.ofSuccess("Product가 성공적으로 생성되었습니다.", null));
	}
}
