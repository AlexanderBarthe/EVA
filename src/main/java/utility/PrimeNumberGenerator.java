package utility;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class PrimeNumberGenerator {
    private final long maxNumber;
    private final AtomicLong nextCandidate;

    private final Random rand = new Random();

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
            if (isProbablePrime(candidate)) {
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

    public boolean isProbablePrime(long n) {

        final int k = 5;

        if (n < 2) return false;
        if (n == 2 || n == 3) return true;
        if (n % 2 == 0) return false;

        long d = n - 1;
        int s = 0;

        while ((d & 1) == 0) {
            d >>= 1;
            s++;
        }

        for (int i = 0; i < k; i++) {
            long a = 2 + Math.abs(rand.nextLong()) % (n - 3);
            if (!millerTest(a, d, s, n)) return false;
        }

        return true;
    }

    private static boolean millerTest(long a, long d, int s, long n) {
        BigInteger x = BigInteger.valueOf(a).modPow(BigInteger.valueOf(d), BigInteger.valueOf(n));
        if (x.equals(BigInteger.ONE) || x.equals(BigInteger.valueOf(n - 1))) {
            return true;
        }

        for (int r = 1; r < s; r++) {
            x = x.modPow(BigInteger.valueOf(2), BigInteger.valueOf(n));
            if (x.equals(BigInteger.valueOf(n - 1))) return true;
        }

        return false;
    }
}


