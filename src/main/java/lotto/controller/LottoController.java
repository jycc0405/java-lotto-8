package lotto.controller;

import lotto.view.InputView;

import java.util.Set;

public class LottoController {
    private final InputView inputView;

    public LottoController(InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        int money = inputMoneyLoop();
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
