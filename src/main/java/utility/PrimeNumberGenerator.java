package utility;

import java.util.Random;

public class PrimeNumberGenerator {

    private long minNumber;
    private long maxNumber;

    private static final int MAX_GEN_ATTEMPTS = 1000;

    public PrimeNumberGenerator(long minNumber, long maxNumber) {
        this.minNumber = minNumber;

        if(minNumber < 0) minNumber = 0;

        if (maxNumber < minNumber) {
            throw new IllegalArgumentException("Max number must be greater than min");
        }

        this.maxNumber = maxNumber;

    }

    public long generatePrimeNumber() {
        Random rand = new Random();
        int remainingAttempts = MAX_GEN_ATTEMPTS;

        while (remainingAttempts-- > 0) {
            long candidate = rand.nextLong(maxNumber - minNumber + 1) + minNumber;
            if (isPrime(candidate)) {
                return candidate;
            }
        }

        return -1;

    }

    public boolean isPrime(long n) {

        for(long i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }

        return true;

    }


}
