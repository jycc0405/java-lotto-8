package lotto.view;

import camp.nextstep.edu.missionutils.Console;
import lotto.config.LottoSettings;

import java.util.*;
import java.util.stream.Collectors;

public class InputView {
    LottoSettings settings;

    public InputView(LottoSettings settings) {
        this.settings = settings;
    }

    public int inputMoney() {
        System.out.println("구입금액을 입력해 주세요.");
        String line = readLine();

        validateNotEmpty(line);

        int money = parseIntOrThrow(line);
        validateMoney(money);

        return money;
    }

    public List<Integer> inputWinningNumbers() {
        System.out.println("당첨 번호를 입력해 주세요.");
        String line = readLine();

        validateNotEmpty(line);

        List<String> tokens = splitTokens(line);
        List<Integer> winningNumbers = parseTokens(tokens);

        validateSize(winningNumbers);
        validateRange(winningNumbers);
        validateNoDuplicates(winningNumbers);

        return winningNumbers;
    }

    public int inputBonusNumber() {
        System.out.println("보너스 번호를 입력해 주세요.");
        String line = readLine();

        validateNotEmpty(line);
        int bonusNumber = parseIntOrThrow(line);

        validateRange(bonusNumber);

        return bonusNumber;
    }

    private String readLine() {
        return Console.readLine().trim();
    }

    private void validateNotEmpty(String s) {
        if (s.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 입력이 비었습니다.");
        }
    }

    private int parseIntOrThrow(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자 형식이 아닙니다.");
        }
    }

    private void validateMoney(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("[ERROR] 음수는 입력할 수 없습니다.");
        }
        if (value < settings.getUnit()) {
            throw new IllegalArgumentException("[ERROR] 최소 " + settings.getUnit() + "원 이상이어야 합니다.");
        }
        if (value % settings.getUnit() != 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액이 " + settings.getUnit() + "원 단위이어야 합니다.");
        }
    }

    private List<String> splitTokens(String s) {
        return Arrays.stream(s.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private List<Integer> parseTokens(List<String> tokens) {
        return tokens.stream()
                .map(this::parseIntOrThrow)
                .toList();
    }

    private void validateSize(List<Integer> numbers) {
        if (numbers.size() != settings.getLottoNumberPickCount()) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호는 6개여야 합니다.");
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
}
