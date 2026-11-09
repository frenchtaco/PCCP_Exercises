// PCPP 
// sestoft@itu.dk * 2026-10-26

package exercises10; 

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestSpellingBee {
  public static void main(String[] args) {
    String filename = "words.txt";
    // 10.2.1:
    List<String> cleanWords = readWords(filename)
      .map(String::toLowerCase)
      .filter(word -> word.length() >= 4)
      .filter(word -> onlyaz(word))
      .distinct()
      .collect(Collectors.toList());

    System.out.println("Number of clean words " + cleanWords.size());    
    System.out.println("----------------------------------------");    
    // 10.2.4:
    Puzzle p = new Puzzle('h', letterSet("chikmnu"));
    p.solutions(cleanWords).forEach(System.out::println);
    System.out.println(p.solutions(cleanWords).count()); // 22

    System.out.println("----------------------------------------");
    // 10.2.5:
    List<Puzzle> puzzles = cleanWords.stream()
      .map(word -> letterSet(word))
      .filter(W -> W.size() == 7)
      .distinct()
      .flatMap(W -> W.stream().map(m -> new Puzzle(m, W)))
      .collect(Collectors.toList());

    System.out.println("Number of puzzles " + puzzles.size());
    System.out.println("----------------------------------------");
    // 10.2.6:
    puzzles.stream()
      .limit(20)
      .forEach(System.out::println);
      
    System.out.println("----------------------------------------");
    // 10.2.7: Warning: SLOW
    List<Puzzle> goodPuzzles = null;  // replace with solution

    System.out.println("Number of good puzzles " + goodPuzzles.size());
  }

  public static Stream<String> readWords(String filename) {
    try {      
      return new BufferedReader(new FileReader(filename)).lines();
    } catch (IOException exn) { 
      return Stream.<String>empty();
    }
  }

  public static boolean onlyaz(String s) {
    return s.chars().allMatch(ch -> 'a' <= ch && ch <= 'z');  
  }
  
  public static Set<Character> letterSet(String s) {
    return s.chars().mapToObj(i -> (char)i).collect(Collectors.toSet());
  }

  static class Puzzle {
    final char m;
    final Set<Character> W;
    public Puzzle(char m, Set<Character> W) {
      this.m = m;
      this.W = W;
    }

    // 10.2.2:
    public boolean hasSolution(String s) {
      Set<Character> letters = letterSet(s);
      return s.length() >= 4
        && letters.contains(m)
        && letters.stream().allMatch(W::contains);
    }

    // 10.2.3:
    public Stream<String> solutions(List<String> cleanWords) {
      return cleanWords.stream()
        .filter(word -> hasSolution(word));
    }

    public String toString() {
      return String.format("(%c, %s)", m, W.stream().map(String::valueOf).collect(Collectors.joining(",", "{", "}")));
    }
  }
}
