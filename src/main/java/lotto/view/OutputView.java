package lotto.view;

import lotto.domain.Lotto;

import java.util.List;
import java.util.stream.Collectors;

public class OutputView {
    public void printLottos(List<Lotto> lottos) {
        System.out.println(lottos.size() + "개를 구매했습니다.");
        for (Lotto lotto : lottos) {
            String lottoNumbers = lotto.getLottoNumbers()
                    .stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            System.out.println("[" + lottoNumbers + "]");
        }
        System.out.println();
    }
}
