package lotto.domain;

import java.util.*;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = new ArrayList<>(numbers);
        this.numbers.sort(Comparator.naturalOrder());
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }
    }

    public List<Integer> getLottoNumbers(){
        return numbers;
    }

    public int countMatch(Lotto winningLotto){
        Set<Integer> numberSet = new HashSet<>(numbers);
        Set<Integer> winningSet = new HashSet<>(winningLotto.getLottoNumbers());
        numberSet.retainAll(winningSet);
        return numberSet.size();
    }
}
