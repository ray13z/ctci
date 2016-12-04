import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        
        Trie dictionary = new Trie();
        
        for(int a0 = 0; a0 < n; a0++){
            String op = in.next();
            String contact = in.next();
            switch(op) {
                case "add":
                    dictionary.insert(contact);
                    break;
                
                case "find":
                    System.out.println(dictionary.searchSuffixes(contact));
                    break;
                
                default:
                    System.err.println("invalid op");
            }
        }
    }
}

class Trie {
    TrieNode root;
    
    public Trie() {
        root = new TrieNode();
    }
    
    public int searchSuffixes(String word) {
        TrieNode t = searchPrefix(word);
        int count = 0;
        
        if(t == null)
            return count;
        
        if(t.isLeaf) 
            count++;
        
        count += getWords(t).size();
        
        return count;
    }
    
    public List getWords(TrieNode t) {
        List<TrieNode> list = new ArrayList<TrieNode>();
        HashMap<Character, TrieNode> children = t.children;
        
        for(TrieNode node : children.values()) {
            if(node.isLeaf)
                list.add(node);
            list.addAll(getWords(node));
        }
        
        return list;
    }
    
    public void insert(String word) {
        HashMap<Character, TrieNode> children = root.children;
        
        for(int i=0; i<word.length(); i++) {
            char c = word.charAt(i);
            if(!children.containsKey(c)) {
                children.put(c, new TrieNode(c));
            }
            
            if(i == word.length()-1) {
                children.get(c).isLeaf = true;
            }
            
            children = children.get(c).children;
        }
    }
    
    public TrieNode searchPrefix(String prefix) {
        HashMap<Character, TrieNode> children = root.children;
        TrieNode t = new TrieNode();
        
        for(int i=0; i<prefix.length(); i++) {
            char c = prefix.charAt(i);
            
            if(!children.containsKey(c)) {
                return null;
            }
            
            t = children.get(c); // Get node containing prefix till prefix[0..i]
            children = t.children;
            
        }
        
        return t;
    }
}

class TrieNode {
    char c;
    boolean isLeaf;
    HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
    
    public TrieNode(char c) {
        this.c = c;
    }
    
    public TrieNode() {}
}

