package lotto.controller;

import lotto.domain.Lotto;
import lotto.service.LottoService;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;
import java.util.Set;

public class LottoController {
    private final LottoService lottoService;
    private final InputView inputView;
    private final OutputView outputView;

    public LottoController(LottoService lottoService, InputView inputView, OutputView outputView) {
        this.lottoService = lottoService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        int money = inputMoneyLoop();
        List<Lotto> userLottos = lottoService.buyLottos(money);
        outputView.printLottos(userLottos);

        Set<Integer> winningNumbers = inputWinningNumbersLoop();
        int bonus = inputBonusLoop(winningNumbers);


    }

    private int inputMoneyLoop() {
        while (true) {
            try {
                return inputView.inputMoney();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Set<Integer> inputWinningNumbersLoop() {
        while (true) {
            try {
                return inputView.inputWinningNumbers();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int inputBonusLoop(Set<Integer> winningNumbers) {
        while (true) {
            try {
                int bonus = inputView.inputBonusNumber();
                validateBonusNotInWinningNumbers(winningNumbers, bonus);
                return bonus;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void validateBonusNotInWinningNumbers(Set<Integer> winningNumbers, int bonus) {
        if (winningNumbers.contains(bonus)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }
}
