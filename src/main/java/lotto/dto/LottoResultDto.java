package lotto.dto;

import lotto.domain.Rank;

import java.util.Map;

public record LottoResultDto(Map<Rank, Integer> result, double profitRate) {
}
