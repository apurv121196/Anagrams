package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();


    ArrayList<String> wordList=new ArrayList<String>();
    HashSet<String> hashSet=new HashSet<String>();
    HashMap<String,ArrayList<String> > lettersToWord=new HashMap<String,ArrayList<String>>();
    HashMap<Integer,ArrayList<String> > sizeToWords=new HashMap<Integer,ArrayList<String>>();
    int wordLength=DEFAULT_WORD_LENGTH-1;


    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();

            wordList.add(word);
            hashSet.add(word);
            String sorted=sortLetters(word);
            ArrayList<String> value=lettersToWord.get(sorted);
            String w=word;
            int len=word.length();
            if(sizeToWords.get(len)==null)
            {
                sizeToWords.put(len,new ArrayList<String>());
            }
            sizeToWords.get(len).add(word);
            if(value==null)
            {
                lettersToWord.put(sorted,new ArrayList<String>());
            }

            lettersToWord.get(sorted).add(word);
        }
    }
    public ArrayList<String> getAnagram(String str)
    {
        ArrayList<String> temp;
        str=sortLetters(str);
        temp=lettersToWord.get(str);

        return temp;
    }

    public String sortLetters(String str)
    {
        char[] chars=str.toCharArray();
        Arrays.sort(chars);
        String sortedStr=new String(chars);
        return sortedStr;
    }

    public boolean isGoodWord(String word, String base) {

        if(base.contains(word))return false;
        if(hashSet.contains(word))return true;
        return false;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        String tmp;

        for(int i=97;i<123;i++)
        {
            tmp=word;
            tmp+=(char)i;
            if(lettersToWord.get(sortLetters(tmp))!=null)
            {
                ArrayList<String> anagrams=lettersToWord.get(sortLetters(tmp));
                for(int j=0;j<anagrams.size();j++)
                {
                    result.add(anagrams.get(j));
                }
            }
        }
        return result;
    }


    public String pickGoodStarterWord() {

        /*int start=random.nextInt(wordList.size());
        int chk=0;
        String res="stop";
        for(int i=start;i<wordList.size();i++)
        {
            if(lettersToWord.get(sortLetters(wordList.get(i))).size()>MIN_NUM_ANAGRAMS && wordList.get(i).length()==wordLength)
            {
                res=wordList.get(i);
                wordLength+=1;
                chk=1;
            }
            else if(chk==0 && i==wordList.size())i=0;
        }
        return res;*/
        wordLength++;
        int size=wordLength;
        if(size==22)size=3;
        return sizeToWords.get(size).get((new Random()).nextInt(sizeToWords.get(size).size()));


    }

}
