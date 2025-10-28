package lotto.service;

import camp.nextstep.edu.missionutils.Randoms;
import lotto.config.LottoSettings;
import lotto.domain.Lotto;

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
}
