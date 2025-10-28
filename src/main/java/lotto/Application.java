package lotto;

import lotto.config.LottoSettings;
import lotto.controller.LottoController;
import lotto.service.LottoService;
import lotto.view.InputView;
import lotto.view.OutputView;

public class Application {
    public static void main(String[] args) {
        LottoSettings lottoSettings = LottoSettings.defaults();

        LottoService lottoService = new LottoService(lottoSettings);
        InputView inputView = new InputView(lottoSettings);
        OutputView outputView = new OutputView();

        LottoController lottoController = new LottoController(lottoService, inputView, outputView);

        lottoController.run();
    }
}
