// PCPP 
// sestoft@itu.dk * 2015-09-08, 2026-10-26

package exercises10; 

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestWordStream {
  public static void main(String[] args) {
    String filename = "words.txt";

    // System.out.println(new java.io.File(filename).getAbsolutePath());
    long count = readWords(filename).count();
    System.out.println("Word-count: " + count);

    System.out.println("----------------------------------------");    
    readWords(filename)
      .limit(20)
      .forEach(System.out::println);

    System.out.println("----------------------------------------");
    readWords(filename)
      .filter(word -> word.length() >= 22)
      .forEach(System.out::println);

    System.out.println("----------------------------------------");
    readWords(filename)
      .map(String::toLowerCase)
      .limit(20)
      .forEach(System.out::println);

    System.out.println("----------------------------------------");
    long count5 = readWords(filename)
      .map(String::toLowerCase)
      .filter(word -> word.length() > 1)
      .filter(word -> onlyaz(word))
      .count();
    System.out.println(count5);  // should be 72097, but I get 74533

    System.out.println("----------------------------------------");
    List<String> cleanWords = readWords(filename)
      .map(String::toLowerCase)
      .filter(word -> word.length() > 1)
      .filter(word -> onlyaz(word))
      .distinct()
      .collect(Collectors.toList());

    System.out.println("Number of clean words without duplicates " + cleanWords.size());
    System.out.println("----------------------------------------");
    cleanWords.stream()
      .filter(TestWordStream::isPalindrome)
      .forEach(System.out::println);

    System.out.println("----------------------------------------");
    Map<Integer, List<String>> lengthGroups = cleanWords.stream()
      .collect(Collectors.groupingBy(String::length));

    System.out.print(lengthGroups.get(19));
   
    System.out.println("----------------------------------------");
    cleanWords.stream()
      .map(word -> counts(word))
      .limit(20)
      .forEach(System.out::println);

    System.out.println("----------------------------------------");
    Map<Map<Character,Long>, List<String>> anagram1 = cleanWords.stream()
      .collect(Collectors.groupingBy(word -> counts(word)));

    anagram1.entrySet().stream()
      .map(kv -> kv.getValue())
      .filter(group -> group.size() > 1)
      .forEach(System.out::println);

    System.out.println("----------------------------------------");
    Timer t = new Timer();
    Map<Map<Character,Long>, List<String>> anagramSeq = cleanWords.stream()
        .collect(Collectors.groupingBy(word -> counts(word)));
    System.out.println("Sequential: " + t.check() + " s"); // 1.3333001 s

    t = new Timer();
    Map<Map<Character,Long>, List<String>> anagramPar = cleanWords.stream()
        .parallel()
        .collect(Collectors.groupingBy(word -> counts(word)));
    System.out.println("Parallel:   " + t.check() + " s"); // 0.6687721 s

    System.out.println("----------------------------------------");
    // 10.1.13:

  }

  // 10.1.7:
  public static boolean isPalindrome(String s) {
    for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
      if (s.charAt(i) != s.charAt(j)) {
        return false;
      }
    }

    return true;
  }

  // 10.1.14:
  public static Map<Character,Long> counts2(String s) {
    return null;  // replace with solution
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
    
  public static Map<Character,Long> counts(String s) {
    Map<Character,Long> res = new TreeMap<>();
    for (char c : s.toCharArray()) {
      res.putIfAbsent(c, 0L);
      res.put(c, res.get(c) + 1);
    }
    return res;
  }
}
