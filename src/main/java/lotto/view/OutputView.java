package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.Rank;
import lotto.dto.LottoResultDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputView {
    public void printLottos(List<Lotto> lottos) {
        System.out.println(lottos.size() + "개를 구매했습니다.");

        for (Lotto lotto : lottos) {
            String lottoNumbers = lotto.getLottoNumbers().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            System.out.println("[" + lottoNumbers + "]");
        }

        System.out.println();
    }

    public void printResult(LottoResultDto result) {
        System.out.println("당첨 통계\n---");

        Map<Rank, Integer> map = result.result();
        List<Rank> order = List.of(Rank.FIFTH, Rank.FOURTH, Rank.THIRD, Rank.SECOND, Rank.FIRST);

        for (Rank r : order) {
            int count = map.getOrDefault(r, 0);
            String bonusText = r.isBonus() ? ", 보너스 볼 일치" : "";
            System.out.printf("%d개 일치%s (%,d원) - %d개%n",
                    r.getCount(),
                    bonusText,
                    r.getPrize(),
                    count);
        }

        System.out.printf("총 수익률은 %.1f%%입니다.%n", result.profitRate());
    }
}
