package services;

import utility.PrimeNumberGenerator;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class IDService {

    private final PrimeNumberGenerator primeGenerator;
    private final HashSet<Long> activeIds = new HashSet<>();
    private final Queue<Long> availableIds = new LinkedList<>();

    private static final int MAX_GEN_ATTEMPTS = 1000;

    public IDService() {
        primeGenerator = new PrimeNumberGenerator(1_000_000_000L, 9_999_999_999L);
    }

    /**
     * Gibt eine neue ID zurück – zuerst aus dem Pool, sonst durch Generierung.
     */
    public synchronized long generateNewId() {
        long newId;

        // Zuerst aus dem Pool holen
        while ((newId = pollFromPool()) != -1) {
            if (!containsId(newId)) {
                activeIds.add(newId);
                return newId;
            }
        }

        // Wenn Pool leer, Prime-Generator verwenden
        int remainingAttempts = MAX_GEN_ATTEMPTS;
        while (remainingAttempts-- > 0) {
            newId = primeGenerator.generatePrimeNumber();
            if (!containsId(newId)) {
                activeIds.add(newId);
                return newId;
            }
        }

        return -1; // Keine ID gefunden
    }

    /**
     * Befüllt den ID-Pool mit einer gegebenen Anzahl von Primzahlen.
     */
    public synchronized void fillIdPool(int count) {
        int attempts = 0;
        while (count > 0 && attempts++ < count * 10) {
            long prime = primeGenerator.generatePrimeNumber();
            if (!activeIds.contains(prime) && !availableIds.contains(prime)) {
                availableIds.offer(prime);
                count--;
            }
        }
    }

    /**
     * Entfernt eine aktive ID.
     */
    public synchronized void removeId(long id) {
        activeIds.remove(id);
    }

    /**
     * Prüft, ob eine ID aktiv ist.
     */
    public synchronized boolean containsId(long id) {
        return activeIds.contains(id);
    }

    /**
     * Löscht alle aktiven IDs.
     */
    public synchronized void dropAllIds() {
        activeIds.clear();
    }

    /**
     * Holt die nächste ID aus dem Pool.
     */
    private long pollFromPool() {
        Long id = availableIds.poll();
        return id != null ? id : -1;
    }

    /**
     * Gibt die Anzahl der IDs im Pool zurück.
     */
    public synchronized int getAvailableIdCount() {
        return availableIds.size();
    }

}
