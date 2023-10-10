import java.util.Random;

public class ReaderThread extends Thread {
    private Database database;

    public ReaderThread(Database database) {
        this.database = database;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            String fullName = generateRandomName();
            String phoneNumber = database.findPhoneNumber(fullName);
            System.out.println("Потік " + getId() + " знайшов номер телефону для " + fullName + ": " + phoneNumber);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateRandomName() {
        return "Любов Приходько";
    }
}
