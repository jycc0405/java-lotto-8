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
                settings.getMinLottoNumber(),
                settings.getMaxLottoNumber(),
                settings.getLottoNumberPickCount()
        ));
    }

    public WinningLotto createWinningLotto(List<Integer> numbers, int bonus) {
        validateRange(bonus);
        validateBonusNotInWinningNumbers(numbers, bonus);

        return new WinningLotto(create(numbers), bonus);
    }

    private void validateNotNull(List<Integer> numbers) {
        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 로또 번호가 비었습니다.");
        }
    }

    private void validateSize(List<Integer> numbers) {
        if (numbers.size() != settings.getLottoNumberPickCount()) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 " + settings.getLottoNumberPickCount() + "개여야 합니다.");
        }
    }

    private void validateRange(List<Integer> numbers) {
        for (int num : numbers) {
            if (num < settings.getMinLottoNumber() || num > settings.getMaxLottoNumber()) {
                throw new IllegalArgumentException("[ERROR] 로또 번호의 범위는 " + settings.getMinLottoNumber() + " ~ " + settings.getMaxLottoNumber() + " 사이입니다.");
            }
        }
    }

    private void validateRange(int number) {
        if (number < settings.getMinLottoNumber() || number > settings.getMaxLottoNumber()) {
            throw new IllegalArgumentException("[ERROR] 로또 번호의 범위는 " + settings.getMinLottoNumber() + " ~ " + settings.getMaxLottoNumber() + " 사이입니다.");
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
