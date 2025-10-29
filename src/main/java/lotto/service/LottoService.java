package lotto.service;

import lotto.config.LottoSettings;
import lotto.domain.Lotto;
import lotto.domain.LottoFactory;
import lotto.domain.Rank;
import lotto.domain.WinningLotto;
import lotto.dto.LottoResultDto;

import java.util.*;

public class LottoService {
    private final LottoFactory factory;
    private final LottoSettings settings;

    public LottoService(LottoFactory factory, LottoSettings settings) {
        this.factory = factory;
        this.settings = settings;
    }

    public List<Lotto> buyLottos(int money) {
        validateMoney(money);
        int count = money / settings.getUnit();

        List<Lotto> lottos = new ArrayList<>();

        for (int i = 0; i < count; ++i) {
            lottos.add(factory.createRadom());
        }

        return lottos;
    }

    public Lotto createLotto(List<Integer> Numbers){
        return factory.create(Numbers);
    }

    public WinningLotto createWinningLotto(Lotto winningLotto, int bonus) {
        return factory.createWinningLotto(winningLotto, bonus);
    }

    public LottoResultDto checkResult(List<Lotto> userLottos, WinningLotto winningLotto) {
        Map<Rank, Integer> result = new EnumMap<>(Rank.class);
        for (Lotto lotto : userLottos) {
            Rank rank = winningLotto.match(lotto);
            result.merge(rank, 1, Integer::sum);
        }

        int totalPrize = result.entrySet().stream()
                .mapToInt(e -> e.getKey().getPrize() * e.getValue())
                .sum();

        int totalSpent = userLottos.size() * settings.getUnit();
        double profitRate = (double) totalPrize / totalSpent * 100;

        return new LottoResultDto(result, profitRate);
    }

    private void validateMoney(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("[ERROR] 음수는 입력할 수 없습니다.");
        }
        if (value < settings.getUnit()) {
            throw new IllegalArgumentException("[ERROR] 최소 " + settings.getUnit() + "원 이상이어야 합니다.");
        }
        if (value % settings.getUnit() != 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액이 " + settings.getUnit() + "원 단위이어야 합니다.");
        }
    }
}
