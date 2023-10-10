import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GardenApp {
    private static final int GARDEN_SIZE = 5;
    private static final int NUM_PLANTS = 10;
    private static final int NUM_GARDENERS = 2;
    private static final int NUM_NATURES = 2;
    private static final int NUM_MONITORS = 2;

    private static final ReentrantReadWriteLock gardenLock = new ReentrantReadWriteLock();
    private static final Lock readLock = gardenLock.readLock();
    private static final Lock writeLock = gardenLock.writeLock();

    private static boolean[][] garden = new boolean[GARDEN_SIZE][GARDEN_SIZE];

    public static void main(String[] args) {
        // Створюємо і запускаємо потоки садівників
        for (int i = 0; i < NUM_GARDENERS; i++) {
            Thread gardenerThread = new Thread(new Gardener());
            gardenerThread.start();
        }

        // Створюємо і запускаємо потоки природи
        for (int i = 0; i < NUM_NATURES; i++) {
            Thread natureThread = new Thread(new Nature());
            natureThread.start();
        }

        // Створюємо і запускаємо потоки моніторів
        for (int i = 0; i < NUM_MONITORS; i++) {
            Thread monitorThread = new Thread(new Monitor(i + 1));
            monitorThread.start();
        }
    }

    static class Gardener implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000); // Симуляція плину часу
                    writeLock.lock();
                    // Реалізуємо логіку поливу зів'ялих рослин у саду
                    Random random = new Random();
                    int row = random.nextInt(GARDEN_SIZE);
                    int col = random.nextInt(GARDEN_SIZE);
                    garden[row][col] = true;
                    System.out.println("Gardener watered a plant at (" + row + ", " + col + ")");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    writeLock.unlock();
                }
            }
        }
    }

    static class Nature implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(2000); // Симуляція плину часу
                    writeLock.lock();
                    // Реалізуємо логіку впливу природи на сад
                    Random random = new Random();
                    int row = random.nextInt(GARDEN_SIZE);
                    int col = random.nextInt(GARDEN_SIZE);
                    garden[row][col] = random.nextBoolean();
                    System.out.println("Nature changed a plant at (" + row + ", " + col + ")");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    writeLock.unlock();
                }
            }
        }
    }

    static class Monitor implements Runnable {
        private int monitorId;

        public Monitor(int id) {
            this.monitorId = id;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(3000); // Монітор періодично перевіряє та виводить стан
                    readLock.lock();
                    System.out.println("Monitor " + monitorId + " is printing the garden state:");
                    printGardenState();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    readLock.unlock();
                }
            }
        }
    }

    private static void printGardenState() {
        for (int i = 0; i < GARDEN_SIZE; i++) {
            for (int j = 0; j < GARDEN_SIZE; j++) {
                System.out.print(garden[i][j] ? "X " : "- ");
            }
            System.out.println();
        }
    }
}

