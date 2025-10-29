package lotto;

import lotto.config.LottoSettings;
import lotto.domain.Lotto;
import lotto.domain.LottoFactory;
import lotto.domain.Rank;
import lotto.domain.WinningLotto;
import lotto.dto.LottoResultDto;
import lotto.service.LottoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class LottoResultCalculationTest {
    private final LottoSettings settings = LottoSettings.defaults();
    private final LottoFactory factory = new LottoFactory(settings);
    private final LottoService service = new LottoService(factory, settings);

    @DisplayName("당첨 통계와 수익률 계산 검증")
    @Test
    void calculateResultAndProfitRate() {
        // 당첨 번호
        Lotto winning = factory.create(List.of(1,2,3,4,5,6));
        WinningLotto winningLotto = new WinningLotto(winning, 7);

        // 사용자 티켓 5장: 각각 3,4,5,5(+보너스),6 매칭
        List<Lotto> user = new ArrayList<>();
        user.add(factory.create(List.of(1,2,3,10,11,12))); // 3개 -> FIFTH
        user.add(factory.create(List.of(1,2,3,4,10,11)));  // 4개 -> FOURTH
        user.add(factory.create(List.of(1,2,3,4,5,10)));   // 5개 -> THIRD
        user.add(factory.create(List.of(1,2,3,4,5,7)));    // 5 + 보너스 -> SECOND
        user.add(factory.create(List.of(1,2,3,4,5,6)));    // 6개 -> FIRST

        LottoResultDto result = service.checkResult(user, winningLotto);

        // 카운트 확인
        assertThat(result.result().get(Rank.FIRST)).isEqualTo(1);
        assertThat(result.result().get(Rank.SECOND)).isEqualTo(1);
        assertThat(result.result().get(Rank.THIRD)).isEqualTo(1);
        assertThat(result.result().get(Rank.FOURTH)).isEqualTo(1);
        assertThat(result.result().get(Rank.FIFTH)).isEqualTo(1);

        // 수익률 계산 (총 상금 / (티켓수 * 1000) * 100)
        long totalPrize =
                Rank.FIRST.getPrize() +
                        Rank.SECOND.getPrize() +
                        Rank.THIRD.getPrize() +
                        Rank.FOURTH.getPrize() +
                        Rank.FIFTH.getPrize();
        double expectedRate = (double) totalPrize / (user.size() * settings.getUnit()) * 100;
        assertThat(result.profitRate()).isCloseTo(expectedRate, withinPercentage(0.0001));
    }

    private org.assertj.core.data.Percentage withinPercentage(double p) {
        return org.assertj.core.data.Percentage.withPercentage(p);
    }
}
