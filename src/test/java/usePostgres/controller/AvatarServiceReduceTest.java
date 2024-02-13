package usePostgres.controller;

import org.junit.jupiter.api.Test;
import java.util.stream.Stream;

public class AvatarServiceReduceTest {

    @Test
    public void calculateReduce2() {
        var result = Stream.iterate(1, a -> ++a)
                .limit(1_000_000)
                .mapToInt(value -> value)
                .sum();

        System.out.println("result: " + result);
    }

    @Test
    public void calculateReduce() {
      var result = Stream.iterate(1, a -> ++a)
              .limit(1_000_000)
              .reduce(0, (a, b) -> a + b );

        System.out.println("result: " + result);
    }

    @Test
    public void calculateReduce_withParallel() {
        var result = Stream.iterate(1, a -> a +1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b );

        System.out.println("result: " + result);
    }

}
