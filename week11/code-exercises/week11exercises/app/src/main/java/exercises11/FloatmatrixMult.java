// FloatmatrixMult.java -- CPU Java version of floatmatrix-mult.cu with timing
// sestoft@itu.dk 2025-10-13, 2026-10-31

package exercises11;

import java.util.ArrayList;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.LongToDoubleFunction;

// These are in module jdk.incubator.vector:
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;

// To compile and run with vector (SIMD) instructions (2026):
//   javac --add-modules jdk.incubator.vector FloatmatrixMult.java
//   java --add-modules jdk.incubator.vector FloatmatrixMult

class FloatmatrixMult {
  public static void main(String[] args) {
    final int maxN = 1 << 11; // maxN^2 = 4m floats and maxN^3 = 8.5bn fma ops
    SystemInfo();
    System.out.println("SIMD float lanes: " + SPECIES.length());
    // matrix2DMultExamples(maxN);
    checkMultiplication(maxN);
    matrixMultExamples(maxN);
  }

  // This checks only that A*I = A and I*B=B
  static void checkMultiplication(int maxN) {
    final float[] A = new float[maxN*maxN], B = new float[maxN*maxN],
      Bt = new float[maxN*maxN], C = new float[maxN*maxN];
    for (int N = 32; N <= maxN; N *= 2) {
      checkMultAI("matrixMultRci", N, A, B, C, n -> matrixMultRci(A, B, C, n));
      checkMultIB("matrixMultRci", N, A, B, C, n -> matrixMultRci(A, B, C, n));
      checkMultAI("matrixMultRic", N, A, B, C, n -> matrixMultRic(A, B, C, n));
      checkMultIB("matrixMultRic", N, A, B, C, n -> matrixMultRic(A, B, C, n));
      checkMultAI("matrixMultRciTranspRec", N, A, B, C, n -> matrixMultRciTranspRec(A, B, Bt, C, n));
      checkMultIB("matrixMultRciTranspRec", N, A, B, C, n -> matrixMultRciTranspRec(A, B, Bt, C, n));
      checkMultAI("matrixMultTiled", N, A, B, C, n -> matrixMultTiled(A, B, C, n));
      checkMultIB("matrixMultTiled", N, A, B, C, n -> matrixMultTiled(A, B, C, n));
      checkMultAI("matrixMultRicSimd", N, A, B, C, n -> matrixMultRicSimd(A, B, C, n));
      checkMultIB("matrixMultRicSimd", N, A, B, C, n -> matrixMultRicSimd(A, B, C, n));
      checkMultAI("matrixMultRecur", N, A, B, C, n -> matrixMultRecur(A, B, C, n, false, false));
      checkMultIB("matrixMultRecur", N, A, B, C, n -> matrixMultRecur(A, B, C, n, false, false));
      checkMultAI("matrixMultRecurMimd", N, A, B, C, n -> matrixMultRecur(A, B, C, n, false, true));
      checkMultIB("matrixMultRecurMimd", N, A, B, C, n -> matrixMultRecur(A, B, C, n, false, true));
      checkMultAI("matrixMultRecurSimd", N, A, B, C, n -> matrixMultRecur(A, B, C, n, true, false));
      checkMultIB("matrixMultRecurSimd", N, A, B, C, n -> matrixMultRecur(A, B, C, n, true, false));
      checkMultAI("matrixMultRecurSimdMimd", N, A, B, C, n -> matrixMultRecur(A, B, C, n, true, true));
      checkMultIB("matrixMultRecurSimdMind", N, A, B, C, n -> matrixMultRecur(A, B, C, n, true, true));
      checkMultAI("Strassen", N, A, B, C, n -> Strassen.multiply(A, B, C, n, false));
      checkMultIB("Strassen", N, A, B, C, n -> Strassen.multiply(A, B, C, n, false));
      checkMultAI("Strassen simd", N, A, B, C, n -> Strassen.multiply(A, B, C, n, true));
      checkMultIB("Strassen simd", N, A, B, C, n -> Strassen.multiply(A, B, C, n, true));
    }
  }

  static void checkMultAI(String name, int N, float[] A, float[] B, float[] C, IntConsumer multiply) {
    initUnitB(A, B, N);
    checkMultiplier(name + " A*I", N, multiply, n -> almostEqual(A, C, n));
  }

  static void checkMultIB(String name, int N, float[] A, float[] B, float[] C, IntConsumer multiply) {
    initUnitB(B, A, N);
    checkMultiplier(name + " I*B", N, multiply, n -> almostEqual(B, C, n));
  }
  
  static void checkMultiplier(String name, int n, IntConsumer multiply, IntPredicate test) {
    multiply.accept(n);
    if (!test.test(n))
      System.out.printf("%s fail at N = %d%n", name, n);
  }

  static boolean almostEqual(float[] A, float[] C, int N) {
    for (int r = 0; r < N; r++)
      for (int c = 0; c < N; c++)
        if (Math.abs(A[r * N + c] - C[r * N + c]) > 1e-4)
          return false; 
    return true;
  }
  
  // Array-of-arrays representation float[][]
  static void matrix2DMultExamples(int maxN) {
    final float[][] A = new float[maxN][maxN], B = new float[maxN][maxN], C = new float[maxN][maxN];
    double dummy = 0.0;
    for (int N = 64; N <= maxN/4; N *= 2) {
      init2DData(A, B, N);
      matrix2DMultRci(A, B, C, N);
      dummy += C[13][37];
      matrix2DMultRic(A, B, C, N);
      dummy += C[13][37];
      matrix2DMultTiled(A, B, C, N);
      dummy += C[13][37];
    }
    for (int N = 64; N <= maxN; N *= 2) {
      init2DData(A, B, N);
      Timer t = new Timer();
      matrix2DMultRci(A, B, C, N);
      dummy += C[13][37];
      System.out.printf("%-25s %10d %15.3f ms%n", "floatmatrix2d mult rci", N*N, t.check() * 1e3);
    }
    for (int N = 64; N <= maxN; N *= 2) {
      init2DData(A, B, N);
      Timer t = new Timer();
      matrix2DMultRic(A, B, C, N);
      dummy += C[13][37];
      System.out.printf("%-25s %10d %15.3f ms%n", "floatmatrix2d mult ric", N*N, t.check() * 1e3);
    }
    for (int N = 64; N <= maxN; N *= 2) {
      init2DData(A, B, N);
      Timer t = new Timer();
      matrix2DMultTiled(A, B, C, N);
      dummy += C[13][37];
      System.out.printf("%-25s %10d %15.3f ms%n", "floatmatrix2d mult tiled", N*N, t.check() * 1e3);
    }
    System.out.println(dummy);
  }

  // Matrix multiplication in flat array representation float[]
  static void matrixMultExamples(int maxN) {
    final float[] A = new float[maxN*maxN], B = new float[maxN*maxN],
      Bt = new float[maxN*maxN], C = new float[maxN*maxN];
    double dummy = 0.0;
    // for (int N = 64; N <= maxN; N *= 2) {
    //   initData(A, B, N);
    //   final int myN = N; 
    //   Mark8('u', "transpose", String.format("%10d", myN*myN),
    //      i -> { transpose(A, B, myN); return C[37]; });
    // }
    // for (int N = 64; N <= maxN; N *= 2) {
    //   initData(A, B, N);
    //   final int myN = N; 
    //   Mark8('u', "transposeRec", String.format("%10d", myN*myN),
    //      i -> { transposeRec(A, B, myN); return C[37]; });
    // }
    for (int N = 64; N <= maxN; N *= 2) {
      initData(A, B, N);
      final int myN = N; 
      Mark8('m', "floatmat mult rci", String.format("%10d", myN*myN),
            i -> { matrixMultRci(A, B, C, myN); return C[37]; });
    }
    for (int N = 64; N <= maxN; N *= 2) {
      initData(A, B, N);
      final int myN = N; 
      Mark8('m', "floatmat mult ric", String.format("%10d", myN*myN),
            i -> { matrixMultRic(A, B, C, myN); return C[37]; });
    }
    for (int N = 64; N <= maxN; N *= 2) {
      initData(A, B, N);
      final int myN = N; 
      Mark8('m', "floatmat mult ric simd", String.format("%10d", myN*myN),
            i -> { matrixMultRicSimd(A, B, C, myN); return C[37]; });
    }
    for (int N = 64; N <= maxN; N *= 2) {
      initData(A, B, N);
      final int myN = N; 
      Mark8('m', "floatmat mult ric trec", String.format("%10d", myN*myN),
            i -> { matrixMultRciTranspRec(A, B, Bt, C, myN); return C[37]; });
    }
    for (int N = 64; N <= maxN; N *= 2) {
      initData(A, B, N);
      final int myN = N; 
      Mark8('m', "floatmat mult tiled", String.format("%10d", myN*myN),
            i -> { matrixMultTiled(A, B, C, myN); return C[37]; });
    }
    for (int N = 64; N <= maxN; N *= 2) {
      initData(A, B, N);
      final int myN = N; 
      Mark8('m', "matrixMultRecur", String.format("%10d", myN*myN),
            i -> { matrixMultRecur(A, B, C, myN, false, false); return C[37]; });
    }
    for (int N = 64; N <= maxN; N *= 2) {
      initData(A, B, N);
      final int myN = N; 
      Mark8('m', "matrixMultRecurMimd", String.format("%10d", myN*myN),
            i -> { matrixMultRecur(A, B, C, myN, false, true); return C[37]; });
    }
    for (int N = 64; N <= maxN; N *= 2) {
      initData(A, B, N);
      final int myN = N; 
      Mark8('m', "matrixMultRecurSimd", String.format("%10d", myN*myN),
            i -> { matrixMultRecur(A, B, C, myN, true, false); return C[37]; });
    }
    for (int N = 64; N <= maxN; N *= 2) {
      initData(A, B, N);
      final int myN = N; 
      Mark8('m', "matrixMultRecurSimdMimd", String.format("%10d", myN*myN),
            i -> { matrixMultRecur(A, B, C, myN, true, true); return C[37]; });
    }
    for (int N = 64; N <= maxN; N *= 2) {
      initData(A, B, N);
      final int myN = N; 
      Mark8('m', "Strassen", String.format("%10d", myN*myN),
            i -> { Strassen.multiply(A, B, C, myN, false); return C[37]; });
    }
    for (int N = 64; N <= maxN; N *= 2) {
      initData(A, B, N);
      final int myN = N; 
      Mark8('m', "Strassen simd", String.format("%10d", myN*myN),
            i -> { Strassen.multiply(A, B, C, myN, true); return C[37]; });
    }
    System.out.println(dummy);
  }

  // Compute product of matrices A and B in C
  static void matrix2DMultRci(float[][] A, float[][] B, float[][] C, int N) {
    for (int r = 0; r < N; r++)
      for (int c = 0; c < N; c++)
        C[r][c] = 0.0f;
    for (int r = 0; r < N; r++)
      for (int c = 0; c < N; c++)
        for (int i = 0; i < N; i++)
          C[r][c] += A[r][i] * B[i][c]; 
  }

  // Compute product of matrices A and B in C
  static void matrix2DMultRic(float[][] A, float[][] B, float[][] C, int N) {
    for (int r = 0; r < N; r++)
      for (int c = 0; c < N; c++)
        C[r][c] = 0.0f;
    for (int r = 0; r < N; r++)
      for (int i = 0; i < N; i++)
        for (int c = 0; c < N; c++) 
          C[r][c] += A[r][i] * B[i][c]; 
  }

  // Compute product of matrices A and B in C
  static void matrix2DMultTiled(float[][] A, float[][] B, float[][] C, int N) {
    for (int r = 0; r < N; r++)
      for (int c = 0; c < N; c++)
        C[r][c] = 0.0f;
    final int tileSide = 32;
    assert N % tileSide == 0;
    for (int cTileRow = 0; cTileRow < N; cTileRow += tileSide)
      for (int cTileCol = 0; cTileCol < N; cTileCol += tileSide) 
        for (int tileOffset = 0; tileOffset < N; tileOffset += tileSide)
          for (int r = cTileRow; r < cTileRow + tileSide; r++)        
            for (int i = tileOffset; i < tileOffset + tileSide; i++)
              for (int c = cTileCol; c < cTileCol + tileSide; c++)
                C[r][c] += A[r][i] * B[i][c];
  }

  // Compute product of matrices A and B in C
  static void matrixMultRci(float[] A, float[] B, float[] C, int N) {
    for (int r = 0; r < N; r++)
      for (int c = 0; c < N; c++)
        C[r * N + c] = 0.0f;
    for (int r = 0; r < N; r++)
      for (int c = 0; c < N; c++)
        for (int i = 0; i < N; i++)
          C[r * N + c] += A[r * N + i] * B[i * N + c]; 
  }

  // Compute product of matrices A and B in C
  static void matrixMultRic(float[] A, float[] B, float[] C, int N) {
    for (int r = 0; r < N; r++)
      for (int c = 0; c < N; c++)
        C[r * N + c] = 0.0f;
    for (int r = 0; r < N; r++)
      for (int i = 0; i < N; i++) 
        for (int c = 0; c < N; c++) 
          C[r * N + c] += A[r * N + i] * B[i * N + c]; 
  }

  private static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;
  
  // Compute product of matrices A and B in C using vector instructions
  static void matrixMultRicSimd(float[] A, float[] B, float[] C, int N) {
    assert N % SPECIES.length() == 0; 
    for (int r = 0; r < N; r++)
      for (int c = 0; c < N; c++)
        C[r * N + c] = 0.0f;
    for (int r = 0; r < N; r++)
      for (int i = 0; i < N; i++) {
        final float ari = A[r * N + i];
        for (int c = 0; c < N; c += SPECIES.length()) { 
          FloatVector bv = FloatVector.fromArray(SPECIES, B, i * N + c);
          FloatVector cv = FloatVector.fromArray(SPECIES, C, r * N + c);
          bv.mul(ari).add(cv).intoArray(C, r * N + c);
        }
      }
  }

  // Compute product of matrices A and B in C after transposing B to Bt
  static void matrixMultRciTranspRec(float[] A, float[] B, float[] Bt, float[] C, int N) {
    transposeRec(B, Bt, N);
    for (int r = 0; r < N; r++)
      for (int c = 0; c < N; c++) {
        float sum = 0.0f;
        for (int i = 0; i < N; i++)
          sum += A[r * N + i] * Bt[c * N + i];
        C[r * N + c] = sum;
      } 
  }

  // Compute product of matrices A and B in C
  static void matrixMultTiled(float[] A, float[] B, float[] C, int N) {
    for (int r = 0; r < N; r++)
      for (int c = 0; c < N; c++)
        C[r * N + c] = 0.0f;
    final int tileSide = 32; 
    assert N % tileSide == 0;    
    for (int cTileRow = 0; cTileRow < N; cTileRow += tileSide)
      for (int cTileCol = 0; cTileCol < N; cTileCol += tileSide) 
        for (int tileOffset = 0; tileOffset < N; tileOffset += tileSide)
          for (int r = cTileRow; r < cTileRow + tileSide; r++)        
            for (int i = tileOffset; i < tileOffset + tileSide; i++)
              for (int c = cTileCol; c < cTileCol + tileSide; c++)
                C[r * N + c] += A[r * N + i] * B[i * N + c];
  }

  // Compute product of matrices A and B in C by recursive decomposition
  static void matrixMultRecur(float[] A, float[] B, float[] C, int N, boolean simd, boolean mimd) {
    zeroC(C, 0, N, N);
    matrixMultRecurAux(A, 0, N, B, 0, N, C, 0, N, N, simd, mimd);
  }

  static void matrixMultRecurAux(float[] A, int a0, int as, float[] B, int b0, int bs, float[] C, int c0, int cs, int N, boolean simd, boolean mimd) {
    if (N <= 64) 
      subMatrixMult(A, a0, as, B, b0, bs, C, c0, cs, N, simd);
    else {
      final int m = N/2;
      final int a11 = a0, a12 = a0 + m, a21 = a0 + m * as, a22 = a21 + m;
      final int b11 = b0, b12 = b0 + m, b21 = b0 + m * bs, b22 = b21 + m;
      final int c11 = c0, c12 = c0 + m, c21 = c0 + m * cs, c22 = c21 + m;
      if (mimd) 
        parallel(
          () -> {
            matrixMultRecurAux(A, a11, as, B, b11, bs, C, c11, cs, m, simd, mimd);
            matrixMultRecurAux(A, a12, as, B, b21, bs, C, c11, cs, m, simd, mimd);
          },
          () -> {
            matrixMultRecurAux(A, a11, as, B, b12, bs, C, c12, cs, m, simd, mimd);
            matrixMultRecurAux(A, a12, as, B, b22, bs, C, c12, cs, m, simd, mimd);
          },
          () -> {
            matrixMultRecurAux(A, a21, as, B, b11, bs, C, c21, cs, m, simd, mimd);
            matrixMultRecurAux(A, a22, as, B, b21, bs, C, c21, cs, m, simd, mimd);
          },
          () -> {
            matrixMultRecurAux(A, a21, as, B, b12, bs, C, c22, cs, m, simd, mimd);
            matrixMultRecurAux(A, a22, as, B, b22, bs, C, c22, cs, m, simd, mimd);
          });
      else {
        matrixMultRecurAux(A, a11, as, B, b11, bs, C, c11, cs, m, simd, mimd);
        matrixMultRecurAux(A, a12, as, B, b21, bs, C, c11, cs, m, simd, mimd);
        
        matrixMultRecurAux(A, a11, as, B, b12, bs, C, c12, cs, m, simd, mimd);
        matrixMultRecurAux(A, a12, as, B, b22, bs, C, c12, cs, m, simd, mimd);
        
        matrixMultRecurAux(A, a21, as, B, b11, bs, C, c21, cs, m, simd, mimd);
        matrixMultRecurAux(A, a22, as, B, b21, bs, C, c21, cs, m, simd, mimd);
        
        matrixMultRecurAux(A, a21, as, B, b12, bs, C, c22, cs, m, simd, mimd);
        matrixMultRecurAux(A, a22, as, B, b22, bs, C, c22, cs, m, simd, mimd);
      }
    }
  }

  static void parallel(Runnable task0, Runnable... tasks) {
    ArrayList<Thread> threads = new ArrayList<>();
    for (Runnable task : tasks)
      threads.add(Thread.ofVirtual().start(task));
    task0.run();
    try {
      for (Thread t : threads)
        t.join();
    } catch (InterruptedException exn) { System.out.println("Thread was interrupted"); }
  }
  
  // Matrix transpose with sequential writes
  static void transpose(float[] B, float[] Bt, int N) {
    for (int r = 0; r < N; r++)
      for (int c = 0; c < N; c++)
        Bt[r * N + c] = B[r + c * N];
  }

  // Matrix transpose with recursion; assumes N = 2^k
  static void transposeRec(float[] B, float[] Bt, int N) {
      transposeAux(B, 0, Bt, 0, N, N); 
  }

  // Matrix transpose with recursion, helper; transpose N x N
  // submatrix starting at B[b0] to Bt[bt0]; assumes N = 2^k; S is the
  // stride: the distance from B[r,c] to B[r+1,c] and same for Bt.
  static void transposeAux(float[] B, int b0, float[] Bt, int bt0, int S, int N) {
    final int cutoff = 32; 
    if (N < cutoff) {
      for (int r = 0; r < N; r++)
        for (int c = 0; c < N; c++)
          Bt[bt0 + r * S + c] = B[b0 + r + c * S];
    } else {
      int m = N/2;
      transposeAux(B, b0,             Bt, bt0,             S, m);  // B11 -> Bt11
      transposeAux(B, b0 + m,         Bt, bt0 + m * S,     S, m);  // B12 -> Bt21
      transposeAux(B, b0 + m * S,     Bt, bt0 + m,         S, m);  // B21 -> Bt12
      transposeAux(B, b0 + m * S + m, Bt, bt0 + m * S + m, S, m);  // B22 -> Bt22
    }
  }

  // C = A * B, where all are N x N matrices.  Matrix A is stored
  // row-major with stride 'as' starting at a0, so A(0,0) is A[a0]
  // and A(0,1) is A[a0+1] and A(1,0) is A[a0+as]; same for B and C.
  private static void subMatrixMult(float[] A, int a0, int as, float[] B, int b0, int bs,
                                    float[] C, int c0, int cs, int N, boolean simd) {
    if (simd) {
      assert N % SPECIES.length() == 0;         
      for (int r = 0; r < N; r++) {
        final int arow = a0 + r * as, crow = c0 + r * cs;
        for (int i = 0; i < N; i++)
          for (int c = 0; c < N; c += SPECIES.length()) {
            FloatVector bv = FloatVector.fromArray(SPECIES, B, b0 + i * bs + c);
            FloatVector cv = FloatVector.fromArray(SPECIES, C, crow + c);
            bv.mul(A[arow + i]).add(cv).intoArray(C, crow + c);
          }
      }
    } else {
      for (int r = 0; r < N; r++) {
        final int arow = a0 + r * as, crow = c0 + r * cs;
        for (int i = 0; i < N; i++)
          for (int c = 0; c < N; c++) 
            C[crow + c] += A[arow + i] * B[b0 + i * bs + c]; 
      }
    }
  }

  // Set matrix C to zero
  private static void zeroC(float[] C, int c0, int cs, int N) {
      for (int r = 0; r < N; r++) 
        for (int c = 0; c < N; c++)
          C[c0 + r * cs + c] = 0.0f;
  }
  
  static class Strassen {
    // Strassen's subcubic matrix multiplication algorithm; see
    // Volker Strassen: Gaussian elimination is not optimal.
    // Numerische Mathematik 13 (1969) 354-356.

    // Initial code generated by MS Copilot 2026-10-21 from this prompt:
    // "Can you make me a Java implementation of Strassen's recursive
    // matrix multiplication, as simple as possible? It should use
    // flat float arrays with row-major layout of the matrices and
    // submatrices, and zero-based indexing as is natural for
    // Java. The code needs to work for square N by N matrices only,
    // and for the recursive decomposition it is fine to assume that N
    // is a power of 2, N = 2^k for some k."

    // Subsequent manual improvements of layout, grouping of declarations,
    // program comments, uniformity of style, removal of division and
    // modulo, optimization of loop order, etc.
    
    private static final int CUTOFF = 64;  // 64 is suitable for 128 KB L1 cache

    public static void multiply(float[] A, float[] B, float[] C, int N, boolean simd) {
      strassen(A, 0, N,  B, 0, N,  C, 0, N,  N, simd);
    }

    private static void strassen(float[] A, int a0, int as,
                                 float[] B, int b0, int bs,
                                 float[] C, int c0, int cs,
                                 int N, boolean simd) {
      if (N <= CUTOFF) {
        zeroC(C, c0, cs, N);      
        subMatrixMult(A, a0, as,  B, b0, bs,  C, c0, cs,  N, simd);
      } else {
        final int m = N / 2;
        final int a11 = a0, a12 = a0 + m, a21 = a0 + m * as, a22 = a21 + m;
        final int b11 = b0, b12 = b0 + m, b21 = b0 + m * bs, b22 = b21 + m;

        final float[]
          // Submatrix multiplications M1-M7; notation as in
          // Strassen's paper and the algorithm's Wikipedia page.
          M1 = new float[m * m],
          M2 = new float[m * m],
          M3 = new float[m * m],
          M4 = new float[m * m],
          M5 = new float[m * m],
          M6 = new float[m * m],
          M7 = new float[m * m], 
          // Temporary result matrices
          T1 = new float[m * m],
          T2 = new float[m * m];

        // M1 = (A11 + A22)(B11 + B22)
        add(A, a11, as, A, a22, as, T1, m);
        add(B, b11, bs, B, b22, bs, T2, m);
        strassen(T1, 0, m, T2, 0, m, M1, 0, m, m, simd);
      
        // M2 = (A21 + A22)B11
        add(A, a21, as, A, a22, as, T1, m);
        strassen(T1, 0, m, B, b11, bs, M2, 0, m, m, simd);

        // M3 = A11(B12 - B22)
        sub(B, b12, bs, B, b22, bs, T2, m);
        strassen(A, a11, as, T2, 0, m, M3, 0, m, m, simd);

        // M4 = A22(B21 - B11)
        sub(B, b21, bs, B, b11, bs, T2, m);
        strassen(A, a22, as, T2, 0, m, M4, 0, m, m, simd);

        // M5 = (A11 + A12)B22
        add(A, a11, as, A, a12, as, T1, m);
        strassen(T1, 0, m, B, b22, bs, M5, 0, m, m, simd);

        // M6 = (A21 - A11)(B11 + B12)
        sub(A, a21, as, A, a11, as, T1, m);
        add(B, b11, bs, B, b12, bs, T2, m);
        strassen(T1, 0, m, T2, 0, m, M6, 0, m, m, simd);
      
        // M7 = (A12 - A22)(B21 + B22)
        sub(A, a12, as, A, a22, as, T1, m);
        add(B, b21, bs, B, b22, bs, T2, m);
        strassen(T1, 0, m, T2, 0, m, M7, 0, m, m, simd);

        final int c11 = c0, c12 = c11 + m, c21 = c0 + m * cs, c22 = c21 + m;
        for (int r = 0; r < m; r++)
          for (int c = 0; c < m; c++) {
            final int ci = r * cs + c, mi = r * m + c; 
            // C11 = M1 + M4 - M5 + M7
            C[c11 + ci] = M1[mi] + M4[mi] - M5[mi] + M7[mi];
            // C12 = M3 + M5
            C[c12 + ci] = M3[mi] + M5[mi];
            // C21 = M2 + M4
            C[c21 + ci] = M2[mi] + M4[mi];
            // C22 = M1 - M2 + M3 + M6
            C[c22 + ci] = M1[mi] - M2[mi] + M3[mi] + M6[mi];
          }
      }
    }

    // C = A + B, where C is contiguous N x N
    private static void add(float[] A, int a0, int as,
                            float[] B, int b0, int bs,
                            float[] C, int N) {
      for (int r = 0; r < N; r++) {
        final int ai = a0 + r * as, bi = b0 + r * bs, ci = r * N;
        for (int c = 0; c < N; c++) 
          C[ci + c] = A[ai + c] + B[bi + c];
      }
    }

    // C = A - B, where C is contiguous N x N
    private static void sub(float[] A, int a0, int as,
                            float[] B, int b0, int bs,
                            float[] C, int N) {
      for (int r = 0; r < N; r++) {
        final int ai = a0 + r * as, bi = b0 + r * bs, ci = r * N;
        for (int c = 0; c < N; c++) 
          C[ci + c] = A[ai + c] - B[bi + c];
      }
    }
  } // End of class Strassen
  
  // Initialize input arrays with simple test data, float[][] version
  static void init2DData(float[][] A, float[][] B, int N) {
    for (int r = 0; r < N; r++)
      for (int c = 0; c < N; c++) {
        A[r][c] = 0.001f * r + 0.002f * c;
        B[r][c] = 1.0f - 0.0005f * r - 0.001f * c;
      }
  }

  // Initialize input arrays with simple test data, float[] version
  static void initData(float[] A, float[] B, int N) {
    for (int r = 0; r < N; r++)
      for (int c = 0; c < N; c++) {
        A[r * N + c] = 0.001f * r + 0.002f * c;
        B[r * N + c] = 1.0f - 0.0005f * r - 0.001f * c;
      }
  }

  // Initialize A with random data, B unit matrix, float[] version
  static void initUnitB(float[] A, float[] B, int N) {
    for (int r = 0; r < N; r++)
      for (int c = 0; c < N; c++) {
        A[r * N + c] = 0.001f * r + 0.002f * c;
        B[r * N + c] = r == c ? 1.0f : 0.0f;
      }
  }
  
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
