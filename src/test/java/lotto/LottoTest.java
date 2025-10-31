package lotto;

import lotto.domain.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LottoTest {
    @DisplayName("로또 번호 정렬과 복사 테스트")
    @Test
    void lottoNumbersAreSortedAndCopied() {
        List<Integer> numbers = new ArrayList<>(List.of(5, 2, 4, 1, 6, 3));
        Lotto lotto = new Lotto(numbers);

        // 오름차순으로 정렬 확인
        assertThat(lotto.getLottoNumbers()).containsExactly(1, 2, 3, 4, 5, 6);

        // 원본 리스트 변경이 내부에 영향 없는지 확인
        numbers.set(0, 100);
        assertThat(lotto.getLottoNumbers()).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @DisplayName("로또 번호 매칭 카운터 테스트 - 부분 일치")
    @Test
    void countMatchReturnsCorrectNumber() {
        Lotto lotto1 = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        Lotto lotto2 = new Lotto(List.of(4, 5, 6, 7, 8, 9));

        int matchCount = lotto1.countMatch(lotto2);

        assertThat(matchCount).isEqualTo(3);
    }

    @DisplayName("로또 번호 매칭 카운터 테스트 - 전체 불일치")
    @Test
    void countMatchWithNoMatch() {
        Lotto lotto1 = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        Lotto lotto2 = new Lotto(List.of(7, 8, 9, 10, 11, 12));

        assertThat(lotto1.countMatch(lotto2)).isEqualTo(0);
    }

    @DisplayName("로또 번호 매칭 카운터 테스트 - 전체 일치")
    @Test
    void countMatchWithAllMatch() {
        Lotto lotto1 = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        Lotto lotto2 = new Lotto(List.of(6, 5, 4, 3, 2, 1));

        assertThat(lotto1.countMatch(lotto2)).isEqualTo(6);
    }
}
