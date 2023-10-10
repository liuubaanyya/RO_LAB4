import java.util.Random;

public class WriterThread extends Thread {
    private Database database;

    public WriterThread(Database database) {
        this.database = database;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            String fullName = generateRandomName();
            String phoneNumber = generateRandomPhoneNumber();
            database.addEntry(fullName, phoneNumber);
            System.out.println("Потік " + getId() + " додав запис: " + fullName + " - " + phoneNumber);
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

    private String generateRandomPhoneNumber() {
        return "123-456-7890";
    }
}
