package global;

import android.content.Context;
import android.widget.EditText;

import com.example.moi.secondapp.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;




import global.Trie;



public class Global {

    private static boolean misspelled = false;
    private static String message = "";
    private static int nbDisplay = 20;
    public static Trie dico;

    /**
     * @param
     */


    public static void init_dico() {

        File dictionary = new File("/res/dictionary.txt");

        dico = new Trie(0, null, false);


    }


    public static ArrayList<String> shell(Trie dico, String word, char c) {

        ArrayList<String> words = new ArrayList<String>();
        word = word.toLowerCase();
        misspelled = false;
        //prompt(message,word);
        if (word.length() > 0)  words = process_completions(word, dico, false);
        if(misspelled)  return did_you_mean(word,dico);

        if (!Character.isLetter(c) && !Character.isDigit(c)) {

            if(misspelled)  return did_you_mean(word,dico);
            else
            {
                words.clear();
            }
        }
        return words;

    }






    public static void read_words(File dictionary, Trie dico)
    {
        Scanner sc = null;
        String word = null;



        try {
            sc = new Scanner(dictionary);

            while(sc.hasNext()){
//			word = sc.nextLine();
                word = sc.next();
//			System.out.print(word.trim()+"\n");
                dico.add_word(word.trim());

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<String> process_completions(String word, Trie dico, boolean print_candidates)
    {
        int number_completions = 0;
        int i = 0;
        number_completions = dico.num_completions(word);
        misspelled = false;
        ArrayList<String> temp = new ArrayList<String>() ;

        if(number_completions == 0)
        {
//			System.out.print("\n\tHellow");
            misspelled = true;
            //misspelled_prompt(message,word,dico);
        }
        else if(number_completions == 1)
        {
            word=dico.get_completion(word).get(0);
            temp.add(word);

        }
        else
        {
            if(true)
            {
                temp = dico.get_completion(word);
            }
        }

        return temp;
    }

    public static void misspelled_prompt(String message, String word,Trie dico)
    {
        //System.out.print("\n There are no words that start with " + word + "\n");
        did_you_mean(word,dico);
       // prompt(message, word);
    }

        private static ArrayList<String> did_you_mean(String word, Trie dico)
    {
        ArrayList<String> fv = null;
        fv = dico.fuzzy_versions(word);

        if(fv.size() == 0) return fv;
        if(fv.size() <= nbDisplay)
        {
         //   System.out.print("Did you mean one of these ?\n");
            for(int i=0; i< fv.size();i++)
            {
       //         System.out.print(fv.get(i) + "\n");
            }
        }
        return fv;
    }



    public static void prompt(String message, String word)
    {
        String pre = "";
        if(message.length() == 0) pre = "";
        else pre = "|" + message + "|";

        System.out.print("\n" + pre + ">" + word);
        System.out.flush();
    }

    public static void readDico(Context ctx, int resId)
    {
        InputStream inputStream = ctx.getResources().openRawResource(resId);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = buffreader.readLine()) != null) {
                dico.add_word(line.trim());
            }
        } catch (IOException e) {
            return ;
        }
    }


}
