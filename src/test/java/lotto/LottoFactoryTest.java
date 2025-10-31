package lotto;

import lotto.config.LottoSettings;
import lotto.domain.Lotto;
import lotto.domain.LottoFactory;
import lotto.exception.LottoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoFactoryTest {
    private final LottoSettings settings = LottoSettings.defaults();
    private final LottoFactory lottoFactory = new LottoFactory(LottoSettings.defaults());

    @DisplayName("로또 번호가 비었으면 예외를 발생한다.")
    @Test
    void createShouldThrowExceptionWhenNumbersEmpty() {
        assertThatThrownBy(() -> lottoFactory.create(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 번호가 비었습니다.");
    }

    @DisplayName("로또 번호가 부족하면 예외를 발생한다.")
    @Test
    void createShouldThrowExceptionWhenWrongCount() {
        assertThatThrownBy(() -> lottoFactory.create(List.of(1, 2, 3)))
                .isInstanceOf(LottoException.class)
                .hasMessageContaining("[ERROR] 로또 번호는");
    }

    @DisplayName("로또 번호의 개수가 6개가 넘어가면 예외가 발생한다.")
    @Test
    void tooMaanyNumbersThrows() {
        assertThatThrownBy(() -> lottoFactory.create(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void duplicateNumbersThrows() {
        assertThatThrownBy(() -> lottoFactory.create(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호 범위를 벗어나면 예외가 발생한다.")
    @Test
    void outOfRangeThrows() {
        assertThatThrownBy(() -> lottoFactory.create(List.of(0, 2, 3, 4, 5, 6)))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> lottoFactory.create(List.of(1, 2, 3, 4, 5, 46)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("랜덤 생성된 로또는 6개, 정렬, 범위 내, 중복 없음")
    @Test
    void createRandomProducesValidLotto() {
        //랜덤 로또 생성
        Lotto lotto = lottoFactory.createRadom();

        //크기 검사
        assertThat(lotto.getLottoNumbers()).hasSize(settings.lottoNumberPickCount());
        //정렬 검사
        assertThat(lotto.getLottoNumbers()).isSorted();
        //범위 검사
        lotto.getLottoNumbers().forEach(n-> assertThat(n).isBetween(settings.minLottoNumber(),settings.maxLottoNumber()));
        //중복 검사
        assertThat(lotto.getLottoNumbers().stream().distinct().count()).isEqualTo(settings.lottoNumberPickCount());
    }

    @DisplayName("입력 받은 리스트의 숫자대로 로또 번호 생성")
    @Test
    void createShouldReturnLottoWhenValidNumbers() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
        Lotto lotto = lottoFactory.create(numbers);

        assertThat(lotto.getLottoNumbers()).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @DisplayName("당첨 로또 생성 시 당첨 번호와 보너스 번호가 중복되면 예외 처리한다.")
    @Test
    void createWinningLottoShouldThrowExceptionWhenBonusDuplicate() {
        Lotto lotto = lottoFactory.create(List.of(1, 2, 3, 4, 5, 6));

        assertThatThrownBy(() -> lottoFactory.createWinningLotto(lotto, 1))
                .isInstanceOf(LottoException.class)
                .hasMessage("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
    }
}
