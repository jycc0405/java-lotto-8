package lotto;

import lotto.config.LottoSettings;
import lotto.controller.LottoController;
import lotto.domain.LottoFactory;
import lotto.service.LottoService;
import lotto.view.InputView;
import lotto.view.OutputView;

public class Application {
    public static void main(String[] args) {
        LottoSettings lottoSettings = LottoSettings.defaults();
        LottoFactory lottoFactory = new LottoFactory(lottoSettings);

        LottoController lottoController = new LottoController(
                new LottoService(lottoFactory, lottoSettings),
                new InputView(),
                new OutputView());

        lottoController.run();
    }
}
