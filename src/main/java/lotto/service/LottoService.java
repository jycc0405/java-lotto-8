package lotto.service;

import camp.nextstep.edu.missionutils.Randoms;
import lotto.config.LottoSettings;
import lotto.domain.Lotto;
import lotto.domain.Rank;
import lotto.domain.WinningLotto;
import lotto.dto.LottoResultDto;

import java.util.*;

public class LottoService {
    LottoSettings settings;

    public LottoService(LottoSettings settings) {
        this.settings = settings;
    }

    public List<Lotto> buyLottos(int money) {
        int count = money / settings.getUnit();

        List<Lotto> lottos = new ArrayList<>();

        for (int i = 0; i < count; ++i) {
            lottos.add(generateLotto());
        }
        return lottos;
    }

    private Lotto generateLotto() {
        return new Lotto(Randoms.pickUniqueNumbersInRange(settings.getMinLottoNumber(), settings.getMaxLottoNumber(), settings.getLottoNumberPickCount()));
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
}
