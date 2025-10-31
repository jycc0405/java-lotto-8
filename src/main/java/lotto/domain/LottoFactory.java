package lotto.domain;

import camp.nextstep.edu.missionutils.Randoms;
import lotto.common.ErrorMessage;
import lotto.config.LottoSettings;
import lotto.exception.LottoException;

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
            throw new LottoException(
                    ErrorMessage.INVALID_LOTTO_EMPTY
            );
        }
    }

    private void validateSize(List<Integer> numbers) {
        if (numbers.size() != settings.lottoNumberPickCount()) {
            throw new LottoException(
                    String.format(ErrorMessage.INVALID_LOTTO_NUMBER_COUNT, settings.lottoNumberPickCount())
            );
        }
    }

    private void validateRange(List<Integer> numbers) {
        for (int num : numbers) {
            validateRange(num);
        }
    }

    private void validateRange(int number) {
        if (number < settings.minLottoNumber() || number > settings.maxLottoNumber()) {
            throw new LottoException(
                    String.format(ErrorMessage.INVALID_LOTTO_NUMBER_RANGE, settings.minLottoNumber(), settings.maxLottoNumber())
            );
        }
    }

    private void validateNoDuplicates(List<Integer> numbers) {
        Set<Integer> unique = new HashSet<>(numbers);
        if (unique.size() != numbers.size()) {
            throw new LottoException(
                    ErrorMessage.DUPLICATED_LOTTO_NUMBER
            );
        }
    }

    private void validateBonusNotInWinningNumbers(List<Integer> winningNumbers, int bonus) {
        if (winningNumbers.contains(bonus)) {
            throw new LottoException(
                    ErrorMessage.INVALID_BONUS_DUPLICATE
            );
        }
    }
}
