package exercises04;

public class PersonFactory {

    public static void main(String[] args) throws InterruptedException {
        FactoryThread t1 = new FactoryThread();
        FactoryThread t2 = new FactoryThread();
        FactoryThread t3 = new FactoryThread();
        FactoryThread t4 = new FactoryThread();
        FactoryThread t5 = new FactoryThread();

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
    }

    public static class FactoryThread extends Thread {
        public void run() {
            Person person = new Person(007);
            System.out.println(person.getId() + " " + person.getName() + " " + person.getZip() + " " + person.getAddress());
            person.changeName("Hans");
            person.move(2468, "Hans' Home");
            System.out.println(person.getId() + " " + person.getName() + " " + person.getZip() + " " + person.getAddress());
        }
    }
    
}
