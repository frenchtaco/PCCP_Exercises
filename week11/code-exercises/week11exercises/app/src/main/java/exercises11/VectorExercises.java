// VectorExercises.java for PCPP
// sestoft@itu.dk 2026-11-06

package exercises11;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.function.LongToDoubleFunction;

// These are in module jdk.incubator.vector:
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.ShortVector;
import jdk.incubator.vector.VectorMask;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;


// To compile and run with vector (SIMD) instructions (2026):
//   javac --add-modules jdk.incubator.vector VectorExercises.java
//   java --add-modules jdk.incubator.vector VectorExercises

class VectorExercises {
  public static void main(String[] args) throws IOException {
    SystemInfo();
    // arrayOperations();   // 11.2
    // cosineSimilarity();  // 11.3
    // textProcessing();    // 11.4
  }

  // The CPU-supported lane counts etc for float vectors and short vectors:
  // private static final ... // 11.2.1
  // private static final ... // 11.3.1

  // Array operations 11.2

  static void arrayOperations() {
    final int SIZE = 1024;
    final float[] A = randVec(SIZE), B = randVec(SIZE), C = randVec(SIZE);
    Mark8('n', "arrayAdd", "", i -> { arrayAdd(A, B, C); return C[0]; } );
    // print(C);
    Mark8('n', "arrayAddSimd", "", i -> { arrayAddSimd(A, B, C); return C[0]; } );
    // print(C);
    // 11.2.2-11.2.5
  }

  static float[] randVec(int n) {
    float[] res = new float[n];
    for (int i=0; i<n; i++)
      res[i] = (float)(2 * Math.random() - 1.0);
    return res;
  }

  static void print(float[] C) {
    System.out.println(IntStream.range(0, 20).mapToObj(i -> String.valueOf(C[i])).collect(Collectors.joining(",", "[", "]")));
  }

  static void arrayAdd(float[] A, float[] B, float[] C) {
    assert A.length == B.length && B.length == C.length;
    for (int i=0; i<A.length; i++)
      C[i] = A[i] + B[i];
  }

  static void arrayAddSimd(float[] A, float[] B, float[] C) {
    assert A.length % FLOATSPEC.length() == 0;
    assert A.length == B.length && B.length == C.length;
    for (int i=0; i<A.length; i += FLOATSPEC.length()) {
      FloatVector av = FloatVector.fromArray(FLOATSPEC, A, i);
      FloatVector bv = FloatVector.fromArray(FLOATSPEC, B, i);
      FloatVector rvec = av.add(bv);
      rvec.intoArray(C, i);
    }
  }

  // 11.2.2

  static void arrayScale(float[] A, float scale, float[] C) { 

  }

  // 11.2.3
  
  static void arrayScaleSimd(float[] A, float scale, float[] C) {

  }

  // 11.2.4
  
  static float arraySum(float[] A) {
    float sum = 0.0f;
    
    return sum;
  }

  // 11.2.5
  
  static float arraySumSimd(float[] A) {
    float sum = 0.0f;
    
    return sum;
  }

  // Cosine similarity methods 11.3
  
  static void cosineSimilarity() {
    // 11.3.1 - 11.3.3
  }

  // Cosine similarity solutions 11.3

  // 11.3.1
  
  static float dotProd(float[] u, float[] v) {
    float sum = 0.0f;

    return sum;
  }
  
  static float norm(float[] u) {
    return 0.0f; //  Not really
  }

  // 11.3.2

  static float cosSim(float[] u, float[] v) {
    return 0.0f; //  Not really
  }

  // 11.3.3
  
  static float dotProdSimd(float[] u, float[] v) {

    return 0.0f; //  Not really
  }
  

  static float normSimd(float[] u) {
    return 0.0f; //  Not really
  }

  static float cosSimSimd(float[] u, float[] v) {
    return 0.0f; //  Not really
  }


  // Text processing methods 11.4
  
  static void textProcessing() throws FileNotFoundException {
    // 11.4.1 - 11.4.10
  }

  // Text processing method solutions below

  // 11.4.2
  
  static boolean contains(char[] chars, char ch) {    

    return false;
  }

  // 11.4.3
  
  static boolean containsSimd(char[] chars, char ch) {

    return false;
  }
  
  // 11.4.4

  static int countAZ(char[] chars) {
    int count = 0;

    return count;
  }

  // 11.4.5
  
  static int countAZSimd(char[] chars) {
    int count = 0;

    return count;
  }

  // 11.4.6
  
  static String replace(char[] chars, char oldChar, char newChar) {

    return new String(chars);
  }

  // 11.4.7
  
  static String replaceSimd(char[] chars, char oldChar, char newChar) {

    return new String(chars);
  }

  // 11.4.9
  
  static String toLowercaseAZ(char[] chars) {

    return new String(chars);
  }

  // 11.4.10
  
  static String toLowercaseAZSimd(char[] chars) {

    return new String(chars);
  }

  
  // ----------------------------------------------------------------------

  private static double scale(char tunit) {
    return tunit=='n' ? 1e9 : tunit=='u' ? 1e6 : tunit=='m' ? 1e3 : 0;
  }

  public static double Mark8(char tunit, String msg, String info, LongToDoubleFunction f) {
    long n = 10, count = 1, totalCount = 0;
    double dummy = 0.0, runningTime = 0.0, st = 0.0, sst = 0.0;
    do { 
      count *= 2;
      st = sst = 0.0;
      for (long j=0; j<n; j++) {
        Timer t = new Timer();
        for (long i=0; i<count; i++) 
          dummy += f.applyAsDouble(i);
        runningTime = t.check();
        double time = runningTime * scale(tunit) / count;
        st += time; 
        sst += time * time;
        totalCount += count;
      }
    } while (runningTime < 0.25);
    double mean = st/n, sdev = Math.sqrt((sst - mean*mean*n)/(n-1));
    System.out.printf("%-25s %s%15.1f %cs %10.2f %10d%n", msg, info, mean, tunit, sdev, count);
    return dummy / totalCount;
  }

  // Crude wall clock timing utility, measuring time in seconds   
  static class Timer {
    private long start, spent = 0;
    public Timer() { play(); }
    public double check() { return (System.nanoTime()-start+spent)/1e9; }
    public void pause() { spent += System.nanoTime()-start; }
    public void play() { start = System.nanoTime(); }
  }  

  public static void SystemInfo() {
    System.out.printf("# OS:   %s; %s; %s%n", 
                      System.getProperty("os.name"), 
                      System.getProperty("os.version"), 
                      System.getProperty("os.arch"));
    System.out.printf("# JVM:  %s; %s%n", 
                      System.getProperty("java.vendor"), 
                      System.getProperty("java.version"));
    // The processor identifier works only on MS Windows:
    System.out.printf("# CPU:  %s; %d \"cores\"%n", 
                      System.getenv("PROCESSOR_IDENTIFIER"),
                      Runtime.getRuntime().availableProcessors());
    java.util.Date now = new java.util.Date();
    System.out.printf("# Date: %s%n", 
      new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(now));
  }
}
