package utility;

import java.util.concurrent.atomic.AtomicLong;

public class PrimeNumberGenerator {
    private final long maxNumber;
    private final AtomicLong nextCandidate;

    public PrimeNumberGenerator(long minNumber, long maxNumber) {
        if (minNumber < 2) minNumber = 2;
        if (maxNumber < minNumber)
            throw new IllegalArgumentException("Max number must be >= min number");

        this.nextCandidate = new AtomicLong(minNumber - 1);
        this.maxNumber = maxNumber;
    }

    public long generatePrimeNumber() {
        long candidate;
        while ((candidate = nextCandidate.incrementAndGet()) <= maxNumber) {
            if (isPrime(candidate)) {
                return candidate;
            }
        }
        return -1; // keine weiteren Primes
    }

    private boolean isPrime(long n) {
        if (n % 2 == 0) return n == 2;
        long limit = (long)Math.sqrt(n);
        for (long i = 3; i <= limit; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
