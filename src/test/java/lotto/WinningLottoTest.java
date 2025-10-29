package lotto;

import lotto.config.LottoSettings;
import lotto.domain.Lotto;
import lotto.domain.LottoFactory;
import lotto.domain.Rank;
import lotto.domain.WinningLotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WinningLottoTest {
    private final LottoSettings settings = LottoSettings.defaults();
    private final LottoFactory factory = new LottoFactory(settings);

    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외")
    @Test
    void bonusDuplicateWithWinningThrows() {
        Lotto winningLotto = factory.create(List.of(1, 2, 3, 4, 5, 6));
        assertThatThrownBy(() -> factory.createWinningLotto(winningLotto, 6))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("매칭 결과가 올바르게 반환")
    @Test
    void matchReturnsCorrectRank() {
        //당첨 번호 생성
        Lotto winning = factory.create(List.of(1, 2, 3, 4, 5, 6));
        WinningLotto winningLotto = new WinningLotto(winning, 7);

        //등수 별 번호 생성
        Lotto userFive = factory.create(List.of(1, 2, 3, 4, 5, 10));
        Lotto userFiveWithBonus = factory.create(List.of(1, 2, 3, 4, 5, 7));
        Lotto userSix = factory.create(List.of(1, 2, 3, 4, 5, 6));
        Lotto userFour = factory.create(List.of(1, 2, 3, 4, 10, 11));
        Lotto userThree = factory.create(List.of(1, 2, 3, 10, 11, 12));

        //출력 검사
        assertThat(winningLotto.match(userSix)).isEqualTo(Rank.FIRST);
        assertThat(winningLotto.match(userFiveWithBonus)).isEqualTo(Rank.SECOND);
        assertThat(winningLotto.match(userFive)).isEqualTo(Rank.THIRD);
        assertThat(winningLotto.match(userFour)).isEqualTo(Rank.FOURTH);
        assertThat(winningLotto.match(userThree)).isEqualTo(Rank.FIFTH);
    }
}
