package lotto.controller;

import lotto.domain.Lotto;
import lotto.domain.WinningLotto;
import lotto.dto.LottoResultDto;
import lotto.service.LottoService;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;

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

        List<Integer> winningNumbers = inputWinningNumbersLoop();
        int bonus = inputBonusLoop(winningNumbers);
        WinningLotto winningLotto = new WinningLotto(new Lotto(winningNumbers), bonus);

        LottoResultDto result = lottoService.checkResult(userLottos, winningLotto);
        outputView.printResult(result);
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

    private List<Integer> inputWinningNumbersLoop() {
        while (true) {
            try {
                return inputView.inputWinningNumbers();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int inputBonusLoop(List<Integer> winningNumbers) {
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

    private void validateBonusNotInWinningNumbers(List<Integer> winningNumbers, int bonus) {
        if (winningNumbers.contains(bonus)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }
}
