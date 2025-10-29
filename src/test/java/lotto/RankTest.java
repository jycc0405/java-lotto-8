package lotto;

import lotto.domain.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RankTest {
    @DisplayName("조건별 랭크의 일치 검사")
    @Test
    void valueOfReturnsCorrectRank() {
        assertThat(Rank.findByMatch(6, false)).isEqualTo(Rank.FIRST);
        assertThat(Rank.findByMatch(5, true)).isEqualTo(Rank.SECOND);
        assertThat(Rank.findByMatch(5, false)).isEqualTo(Rank.THIRD);
        assertThat(Rank.findByMatch(4, false)).isEqualTo(Rank.FOURTH);
        assertThat(Rank.findByMatch(3, false)).isEqualTo(Rank.FIFTH);
        assertThat(Rank.findByMatch(2, false)).isEqualTo(Rank.NONE);
    }
}
