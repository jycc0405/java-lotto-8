package lotto;

import lotto.config.LottoSettings;
import lotto.domain.LottoFactory;
import lotto.service.LottoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LottoServiceTest {
    private final LottoSettings settings = LottoSettings.defaults();
    private final LottoFactory factory = new LottoFactory(settings);
    private final LottoService service = new LottoService(factory, settings);

    @DisplayName("구입 금액이 최소 단위보다 작으면 예외")
    @Test
    void moneyTooSmallThrows() {
        assertThatThrownBy(() -> service.buyLottos(settings.getUnit() - 100))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("구입 금액이 단위로 나누어지지 않으면 예외")
    @Test
    void moneyNotMultipleThrows() {
        assertThatThrownBy(() -> service.buyLottos(settings.getUnit() + 500))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정상 금액이면 해당 장수만큼 로또 발행")
    @Test
    void buyLottosGeneratesCorrectCount() {
        int money = settings.getUnit() * 3;
        assertThat(service.buyLottos(money)).hasSize(3);
    }
}
