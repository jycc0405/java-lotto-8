package lotto.domain;

import camp.nextstep.edu.missionutils.Randoms;
import lotto.config.LottoSettings;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LottoFactory {
    private final LottoSettings settings;

    public LottoFactory(LottoSettings settings) {
        this.settings = settings;
    }

    public Lotto create(List<Integer> numbers) {
        validateNotNull(numbers);
        validateSize(numbers);
        validateRange(numbers);
        validateNoDuplicates(numbers);

        return new Lotto(numbers);
    }

    public Lotto createRadom() {
        return create(Randoms.pickUniqueNumbersInRange(
                settings.minLottoNumber(),
                settings.maxLottoNumber(),
                settings.lottoNumberPickCount()
        ));
    }

    public WinningLotto createWinningLotto(Lotto winningLotto, int bonus) {
        validateRange(bonus);
        validateBonusNotInWinningNumbers(winningLotto.getLottoNumbers(), bonus);

        return new WinningLotto(winningLotto, bonus);
    }

    private void validateNotNull(List<Integer> numbers) {
        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 로또 번호가 비었습니다.");
        }
    }

    private void validateSize(List<Integer> numbers) {
        if (numbers.size() != settings.lottoNumberPickCount()) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 " + settings.lottoNumberPickCount() + "개여야 합니다.");
        }
    }

    private void validateRange(List<Integer> numbers) {
        for (int num : numbers) {
            if (num < settings.minLottoNumber() || num > settings.maxLottoNumber()) {
                throw new IllegalArgumentException("[ERROR] 로또 번호의 범위는 " + settings.minLottoNumber() + " ~ " + settings.maxLottoNumber() + " 사이입니다.");
            }
        }
    }

    private void validateRange(int number) {
        if (number < settings.minLottoNumber() || number > settings.maxLottoNumber()) {
            throw new IllegalArgumentException("[ERROR] 로또 번호의 범위는 " + settings.minLottoNumber() + " ~ " + settings.maxLottoNumber() + " 사이입니다.");
        }
    }

    private void validateNoDuplicates(List<Integer> numbers) {
        Set<Integer> unique = new HashSet<>(numbers);
        if (unique.size() != numbers.size()) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복될 수 없습니다.");
        }
    }

    private void validateBonusNotInWinningNumbers(List<Integer> winningNumbers, int bonus) {
        if (winningNumbers.contains(bonus)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }
}
