public class Main {
    public static void main(String[] args) {
        Database database = new Database();

        Thread[] readers = new Thread[5];
        Thread[] writers = new Thread[5];

        for (int i = 0; i < 5; i++) {
            readers[i] = new ReaderThread(database);
            writers[i] = new WriterThread(database);
            readers[i].start();
            writers[i].start();
        }
    }
}
