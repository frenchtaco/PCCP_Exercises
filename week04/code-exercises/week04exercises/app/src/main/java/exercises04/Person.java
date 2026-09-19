package exercises04;

public class Person {
    private static long nextId = 0;
    private final long id;
    private String name;
    private int zip;
    private String address;

    public Person() {
        this.id = nextId;
        nextId++;
        init();
    }

    public Person(long initId) {
        if (nextId == 0) {
            this.id = initId;
            nextId = this.id + 1;
        } else {
            this.id = nextId;
            nextId++;
        }
        init();
    }

    public synchronized void init() {
        this.name = "Bob";
        this.zip = 1234;
        this.address = "Nowhere special";
    }

    public synchronized void changeName(String newName) {
        this.name = newName;
    }

    public synchronized void move(int newZip, String newAddress) {
        this.zip = newZip;
        this.address = newAddress;
    }

    public synchronized long getId() {
        return this.id;
    }

    public synchronized String getName() {
        return this.name;
    }

    public synchronized int getZip() {
        return this.zip;
    }

    public synchronized String getAddress() {
        return this.address;
    }
}
