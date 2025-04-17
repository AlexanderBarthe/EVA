import utility.PrimeNumberGenerator;

import java.util.HashSet;

public class IDService {

    private final PrimeNumberGenerator primeGenerator;
    private HashSet<Long> activeIds;

    private static final int MAX_GEN_ATTEMPTS = 1000;

    public IDService() {
        primeGenerator = new PrimeNumberGenerator(1000000000L, 9999999999L);
        activeIds = new HashSet<>();
    }

    public long generateNewId() {

        long newId = 0;
        int remainingAttempts = MAX_GEN_ATTEMPTS;

        while(remainingAttempts-- > 0) {
            newId = primeGenerator.generatePrimeNumber();
            if(!containsId(newId)) {
                activeIds.add(newId);
                return newId;
            }
        }

        return -1;

    }

    public void removeId(long id) {
        activeIds.remove(id);
    }

    public boolean containsId(long id) {
        return activeIds.contains(id);
    }

    public void dropAllIds() {
        activeIds.clear();
    }



}
