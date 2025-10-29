package lotto.domain;

import java.util.*;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        this.numbers = new ArrayList<>(numbers);
        this.numbers.sort(Comparator.naturalOrder());
    }

    public List<Integer> getLottoNumbers() {
        return numbers;
    }

    public int countMatch(Lotto winningLotto) {
        Set<Integer> numberSet = new HashSet<>(numbers);
        Set<Integer> winningSet = new HashSet<>(winningLotto.getLottoNumbers());
        numberSet.retainAll(winningSet);

        return numberSet.size();
    }
}
