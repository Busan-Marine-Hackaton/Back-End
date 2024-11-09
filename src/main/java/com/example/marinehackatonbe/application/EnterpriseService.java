package com.example.marinehackatonbe.application;

import com.example.marinehackatonbe.domain.Enterprise;
import com.example.marinehackatonbe.dto.EnterpriseRankingResponse;
import com.example.marinehackatonbe.global.exception.CustomException;
import com.example.marinehackatonbe.global.exception.ExceptionContent;
import com.example.marinehackatonbe.infrastructure.EnterpriseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EnterpriseService {
	private final EnterpriseRepository enterpriseRepository;

	@Transactional
	public void createEnterprise(String name, String realId, String password) {
		Enterprise newEnterprise = Enterprise.builder()
			.name(name)
			.realId(realId)
			.password(password)
			.point(0)
			.build();
		enterpriseRepository.save(newEnterprise);
	}

	public Long login(String name, String realId, String password) {
		Enterprise enterprise = enterpriseRepository.findByNameAndRealId(name, realId)
			.orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_ENTERPRISE));
		if (enterprise.getPassword().equals(password)) {
			return enterprise.getId();
		} else {
			throw new CustomException(ExceptionContent.WRONG_PASSWORD);
		}
	}

	public List<Enterprise> getRanking() {
		List<Enterprise> ranking = enterpriseRepository.findAll();
		ranking.sort((e1, e2) -> Integer.compare(e2.getPoint(), e1.getPoint()));
		return ranking;
	}

	public EnterpriseRankingResponse getEnterprisePointAndRanking(Long enterpriseId) {
		List<Enterprise> rankingList = enterpriseRepository.findAll();
		rankingList.sort((e1, e2) -> Integer.compare(e2.getPoint(), e1.getPoint()));

		Enterprise enterprise = enterpriseRepository.findById(enterpriseId)
			.orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_ENTERPRISE));

		int ranking = rankingList.indexOf(enterprise) + 1; // 랭킹은 1부터 시작

		if (ranking == 0) {
			throw new CustomException(ExceptionContent.NOT_FOUND_ENTERPRISE);
		}

		return EnterpriseRankingResponse.builder()
			.enterpriseId(enterprise.getId())
			.name(enterprise.getName())
			.realId(enterprise.getRealId())
			.photoUrl(enterprise.getPhotoUrl())
			.point(enterprise.getPoint())
			.ranking(ranking)
			.build();
	}

	@Transactional
	public void addEnterprisePoints(Long enterpriseId, int points) {
		Enterprise enterprise = enterpriseRepository.findById(enterpriseId)
			.orElseThrow(() -> new CustomException(ExceptionContent.NOT_FOUND_ENTERPRISE));

		enterprise.addPoints(points);
	}
}
