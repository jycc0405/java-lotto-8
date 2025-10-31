package lotto.config;

public record LottoSettings(int lottoPriceUnit, int lottoNumberPickCount, int minLottoNumber, int maxLottoNumber) {

    public static LottoSettings defaults() {
        return new LottoSettings(1000, 6, 1, 45);
    }
}
