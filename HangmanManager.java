// Shivangi Khanna

// HangmanManager manages the state of the game Hangman. 
// Takes as an input words to be used in the game and updates it during the game.
import java.util.*;

public class HangmanManager {
   
   private Set<String> current;
   private int max;
   private Set<Character> guessed;
   private String game;
   
   // Takes as an input a dictionary of words, desired word length, maximum wrong guesses
   // a user can make. 
   // Throws illegal argument exception if the desired length entered is less than 1 or if
   // the maximum number of wrong guesses is less than 0.
   public HangmanManager(Collection<String> dictionary, int length, int max) {
      if(length < 1 || max < 0) {
         throw new IllegalArgumentException();
      }
      current = new TreeSet<String>();
      this.max = max;
      guessed = new TreeSet<Character>();
      for(String word: dictionary) {
         if(word.length() == length) {
            current.add(word);
         }
      }
      game = "";
      for(int i = 0; i < length; i++) {
         game += "-";
      }
   }
   
   // Returns the current set of words that are being considered.
   public Set<String> words() { 
      return current;
   }
   
   // Returns the number of wrong guesses left.
   public int guessesLeft() {
      return this.max;
   }
   
   // Returns the set of guesses already made by the user.
   public Set<Character> guesses() {
      return guessed;
   }
   
   // Returns a string for the current game pattern.
   // Shows the correct guesses and blank spaces.
   // Throws IllegalArgumentException if the set of words being used is empty.
   public String pattern() {
      if(current.isEmpty()) {
         throw new IllegalArgumentException();
      }
      String pattern = "";
      for (int i = 0; i < game.length(); i++) {
         pattern += " " + game.charAt(i); 
      }
      return pattern.substring(1);
   }
   
   // Adds the guessed letter to the guessed letters list. 
   // Returns the number of times the guessed letter appears in the word.
   // Updates the set of current words being considered.
   // Updates the number of guesses remaining.
   // Throws IllegalStateException if the number of wrong guesses left is less than 1
   // or if the current set of words is empty. 
   // Otherwise, it throws an IllegalArumentException if the character has already
   // been guessed.
   // We assume that the character entered is lowercase.
   public int record(char guess) {
      if(max < 1 || current.isEmpty()) {
         throw new IllegalStateException();
      } else if(guessed.contains(guess)) {
         throw new IllegalArgumentException();
      }
      guessed.add(guess);
      int setMax = 0;
      int size = 0;
      int right = 0;
      Map<String, Set<String>> wordFamily = new TreeMap<String, Set<String>>();
      for(String word: current) {
         String key = "";
         for(int i = 0; i < word.length(); i++) {
            if(guess == word.charAt(i)) {
               key += word.charAt(i);
            } else {
               key += game.charAt(i);
            }
         }
         if(!wordFamily.containsKey(key)) {
            wordFamily.put(key, new TreeSet<String>());
         }
         Set<String> pattern = wordFamily.get(key);
         pattern.add(word);
      }      
      Set<String> keys = wordFamily.keySet();
      for(String key: keys) {
         size = wordFamily.get(key).size();
         if(size > setMax) {
            game = key;
            current = wordFamily.get(key);
            setMax = size;
         }
      }
      for(int i = 0; i < game.length(); i++) {
         if(guess == game.charAt(i)) {
            right++;
         }
      }
      if(right == 0) {
         max--;
      }
      return right;
   }
}   
      