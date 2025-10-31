package lotto.common;

public final class ErrorMessage {
    private ErrorMessage() {

    }

    // 로또 번호 관련
    public static final String INVALID_LOTTO_EMPTY = "[ERROR] 로또 번호가 비었습니다.";
    public static final String INVALID_LOTTO_NUMBER_COUNT = "[ERROR] 로또 번호는 %,d개여야 합니다.";
    public static final String INVALID_LOTTO_NUMBER_RANGE = "[ERROR] 로또 번호의 범위는 %,d ~ %,d 사이입니다.";
    public static final String DUPLICATED_LOTTO_NUMBER = "[ERROR] 로또 번호는 중복될 수 없습니다.";
    public static final String INVALID_BONUS_DUPLICATE = "[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.";

    // 금액/구입 관련
    public static final String NEGATIVE_MONEY = "[ERROR] 음수는 입력할 수 없습니다.";
    public static final String MINIMUM_MONEY = "[ERROR] 최소 %,d원 이상이어야 합니다.";
    public static final String INVALID_MONEY_UNIT = "[ERROR] 구입 금액이 %,d원 단위이어야 합니다.";

    // 입력 관련
    public static final String INPUT_EMPTY = "[ERROR] 입력이 비었습니다.";
    public static final String INVALID_NUMBER_FORMAT = "[ERROR] 숫자 형식이 아닙니다.";
}
