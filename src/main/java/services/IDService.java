package services;

import utility.PrimeNumberGenerator;

import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IDService {

    private final PrimeNumberGenerator primeGenerator;
    private final HashSet<Long> activeIds = new HashSet<>();
    private final Queue<Long> availableIds = new ConcurrentLinkedQueue<>();

    private static final int MAX_GEN_ATTEMPTS = 1000;
    private static final int MIN_POOL_SIZE = 1000;
    private static final int FILL_BATCH_SIZE = 20000;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public IDService() {
        primeGenerator = new PrimeNumberGenerator(1_000_000_000L, 9_999_999_999L);
        System.out.println("First pool fill");
        startPoolFiller();
    }

    private void startPoolFiller() {
        scheduler.scheduleAtFixedRate(() -> {
            if (availableIds.size() < MIN_POOL_SIZE) {
                fillIdPool(FILL_BATCH_SIZE);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public synchronized long generateNewId() {
        Long id = availableIds.poll();
        if (id != null && !activeIds.contains(id)) {
            activeIds.add(id);
            return id;
        }

        // Fallback, falls Pool leer ist
        int remainingAttempts = MAX_GEN_ATTEMPTS;
        while (remainingAttempts-- > 0) {
            long newId = primeGenerator.generatePrimeNumber();
            if (!containsId(newId)) {
                activeIds.add(newId);
                return newId;
            }
        }
        return -1;
    }

    private void fillIdPool(int count) {
        int generated = 0;
        int attempts = 0;
        while (generated < count && attempts++ < count * 10) {
            long prime = primeGenerator.generatePrimeNumber();
            if (!activeIds.contains(prime) && !availableIds.contains(prime)) {
                availableIds.offer(prime);
                generated++;
            }
        }
    }

    public synchronized void removeId(long id) {
        activeIds.remove(id);
    }

    public boolean containsId(long id) {
        return activeIds.contains(id);
    }

    public synchronized void dropAllIds() {
        activeIds.clear();
    }

    public int getAvailableIdCount() {
        return availableIds.size();
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
