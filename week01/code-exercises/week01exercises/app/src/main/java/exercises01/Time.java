package exercises01;

public class Time{
    public static void main(String[] args) {
    
        long start= System.nanoTime();
        int counter = 0;
        for(int i = 0; i < 100; i++){
            counter++;
        }
        System.out.println(counter);
        long spent = System.nanoTime()-start;
        System.out.println(spent);
    }
}
