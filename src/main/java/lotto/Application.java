package lotto;

import lotto.config.LottoSettings;
import lotto.controller.LottoController;
import lotto.view.InputView;

public class Application {
    public static void main(String[] args) {
        LottoSettings lottoSettings = LottoSettings.defaults();

        InputView inputView = new InputView(lottoSettings);

        LottoController lottoController = new LottoController(inputView);

        lottoController.run();
    }
}
