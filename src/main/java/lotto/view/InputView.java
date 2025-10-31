package lotto.view;

import camp.nextstep.edu.missionutils.Console;
import lotto.common.ErrorMessage;
import lotto.exception.LottoException;

import java.util.*;
import java.util.stream.Collectors;

public class InputView {
    public int inputMoney() {
        System.out.println("구입금액을 입력해 주세요.");
        String line = readLine();

        validateNotEmpty(line);

        return parseIntOrThrow(line);
    }

    public List<Integer> inputWinningNumbers() {
        System.out.println("당첨 번호를 입력해 주세요.");
        String line = readLine();

        validateNotEmpty(line);

        List<String> tokens = splitTokens(line);

        return parseTokens(tokens);
    }

    public int inputBonusNumber() {
        System.out.println("보너스 번호를 입력해 주세요.");
        String line = readLine();

        validateNotEmpty(line);

        return parseIntOrThrow(line);
    }

    private String readLine() {
        return Console.readLine().trim();
    }

    private void validateNotEmpty(String s) {
        if (s.isEmpty()) {
            throw new LottoException(
                    ErrorMessage.INPUT_EMPTY
            );
        }
    }

    private int parseIntOrThrow(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new LottoException(
                    ErrorMessage.INVALID_NUMBER_FORMAT
            );
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
}
