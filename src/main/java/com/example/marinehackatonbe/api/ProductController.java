package com.example.marinehackatonbe.api;

import com.example.marinehackatonbe.application.ProductService;
import com.example.marinehackatonbe.domain.Product;
import com.example.marinehackatonbe.dto.ProductDto;
import com.example.marinehackatonbe.global.domain.CommonResponse;
import com.example.marinehackatonbe.global.exception.CustomException;
import com.example.marinehackatonbe.global.exception.ExceptionContent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;
	@Operation(summary = "물건 구매", description = "멤버 ID와 상품 ID를 통해 물건을 구매합니다.")
	@ApiResponse(responseCode = "200", description = "물건 구매 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
	@PostMapping("/buy")
	public ResponseEntity<CommonResponse<Void>> buyProduct(
		@Parameter(description = "멤버 ID", required = true) @RequestParam Long memberId,
		@Parameter(description = "상품 ID", required = true) @RequestParam Long productId) {
		if (productService.buyProduct(memberId, productId)) {
			return ResponseEntity.ok().body(CommonResponse.ofSuccess("물건 구매에 성공하였습니다.", null));
		} else {
			throw new CustomException(ExceptionContent.FAIL_PURCHASE);
		}
	}

	@Operation(summary = "모든 상품 조회", description = "모든 상품 리스트를 반환합니다.")
	@ApiResponse(responseCode = "200", description = "상품 리스트 반환 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
	@GetMapping("/all")
	public ResponseEntity<CommonResponse<List<Product>>> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		return ResponseEntity.ok().body(CommonResponse.ofSuccess("모든 상품 리스트가 반환되었습니다.", products));
	}

	@Operation(summary = "상품 생성", description = "새로운 상품을 생성합니다.")
	@ApiResponse(responseCode = "200", description = "상품 생성 성공", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
	@PostMapping
	public ResponseEntity<CommonResponse<Void>> createProduct(@RequestBody ProductDto productDto) {
		productService.createProduct(productDto);
		return ResponseEntity.ok().body(CommonResponse.ofSuccess("Product가 성공적으로 생성되었습니다.", null));
	}
}
