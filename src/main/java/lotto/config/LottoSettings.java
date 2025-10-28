package lotto.config;

public final class LottoSettings {
    private final int unit;
    private final int winningNumberCount;
    private final int minLottoNumber;
    private final int maxLottoNumber;

    public LottoSettings(int unit, int winningNumberCount,int minLottoNumber,int maxLottoNumber) {
        this.unit = unit;
        this.winningNumberCount = winningNumberCount;
        this.minLottoNumber = minLottoNumber;
        this.maxLottoNumber = maxLottoNumber;
    }

    public static LottoSettings defaults() {
        return new LottoSettings(1000, 6,1,45);
    }

    public int getUnit() {
        return unit;
    }

    public int getWinningNumberCount() {
        return winningNumberCount;
    }

    public int getMinLottoNumber(){
        return minLottoNumber;
    }

    public int getMaxLottoNumber(){
        return maxLottoNumber;
    }
}
