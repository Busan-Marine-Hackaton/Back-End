package com.example.marinehackatonbe.application;

import com.example.marinehackatonbe.domain.Member;
import com.example.marinehackatonbe.domain.Product;
import com.example.marinehackatonbe.dto.ProductDto;
import com.example.marinehackatonbe.global.exception.CustomException;
import com.example.marinehackatonbe.global.exception.ExceptionContent;
import com.example.marinehackatonbe.infrastructure.MemberRepository;
import com.example.marinehackatonbe.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public boolean buyProduct(Long memberId, Long productId) {
		Member savedMember = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_MEMBER));
		Product savedProduct = productRepository.findById(productId)
			.orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_PRODUCT));
		if (savedMember.getPoint() >= savedProduct.getPrice()) {
			savedMember.deductPoint(savedProduct.getPrice());
			memberRepository.save(savedMember);
			return true;
		}
		return false;
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Transactional
	public void createProduct(ProductDto productDto) {
		Product product = Product.builder()
			.name(productDto.name())
			.photo(productDto.photo())
			.price(productDto.price())
			.build();
		productRepository.save(product);
	}
}
