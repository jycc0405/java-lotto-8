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
        List<Lotto> userLottos = purchaseLottosLoop();
        outputView.printLottos(userLottos);

        WinningLotto winningLotto = createWinningLottoLoop();
        LottoResultDto result = lottoService.checkResult(userLottos, winningLotto);
        outputView.printResult(result);
    }

    private List<Lotto> purchaseLottosLoop() {
        while (true) {
            try {
                int money = inputView.inputMoney();
                return lottoService.buyLottos(money);
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

    private int inputBonusLoop() {
        while (true) {
            try {
                return inputView.inputBonusNumber();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private WinningLotto createWinningLottoLoop() {
        while (true) {
            try {
                List<Integer> winningNumbers = inputWinningNumbersLoop();
                int bonus = inputBonusLoop();

                return lottoService.createWinningLotto(winningNumbers, bonus);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
