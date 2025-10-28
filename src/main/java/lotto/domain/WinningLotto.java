package lotto.domain;

public class WinningLotto {
    private final Lotto lotto;
    private final int bonusNumber;

    public WinningLotto(Lotto lotto, int bonusNumber) {
        this.lotto = lotto;
        this.bonusNumber = bonusNumber;
    }

    public Rank match(Lotto userLotto) {
        int matchCount = userLotto.countMatch(lotto);
        boolean bonusMatched = userLotto.getLottoNumbers().contains(bonusNumber);
        return Rank.findByMatch(matchCount, bonusMatched);
    }
}
