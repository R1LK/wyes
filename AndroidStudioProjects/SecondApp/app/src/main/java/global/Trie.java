package global;

import java.util.ArrayList;

public class Trie {

    public int count;
    public boolean ended;
    public ArrayList<Trie> subtrie;
    private String value = null;
    private ArrayList<String> rv;

    public Trie(int count, String value, boolean ended)
    {
        this.count = count;
        this.value = value;
        this.ended = ended;
        this.subtrie = new ArrayList<Trie>();
    }

    public void add_word(String word)
    {
        this.count++;
        helper_add_word(word);

    }

    private void helper_add_word(String word)
    {
        int lastpos = subtrie.size();
        if(word.length() == 0) return;
        if((word.length() == 1)
                && (!is_in_trie(word) ))
        {
            subtrie.add(new Trie(1, word, true)) ;
        }
        else if(word.length() == 1)
        {
            subtrie.get(position_in_trie(word)).count++;
            subtrie.get(position_in_trie(word)).ended = true;
        }
        else if (!is_in_trie(word.substring(0,1)))
        {
            subtrie.add(new Trie(1, word.substring(0,1), false));
            subtrie.get(lastpos).helper_add_word(word.substring(1));
        }
        else
        {
            subtrie.get(position_in_trie(word.substring(0,1))).count++;
            subtrie.get(position_in_trie(word.substring(0,1))).helper_add_word(word.substring(1));

        }
    }

    private boolean is_in_trie(String word)
    {
        int i =0;
        for (i=0; i < subtrie.size(); i++)
        {
            if(word.equals(subtrie.get(i).value))
            {
                return true;
            }
        }
        return false;
    }

    private int position_in_trie(String word)
    {
        int i =0;
        for (i=0; i < subtrie.size(); i++)
        {
            if(word.equals(subtrie.get(i).value))
            {
                return i;
            }
        }

        return -1; //Faire une exception ici ?


    }

    public int num_completions(String word)
    {
        if(word.length() == 0) return 0;
        else if(!is_in_trie(word.substring(0,1)))
        {
            return 0;
        }
        else if(word.length() == 1)
        {
            return this.subtrie.get(position_in_trie(word)).count;
        }
        else
            return this.subtrie.get(position_in_trie(word.substring(0,1))).num_completions(word.substring(1));
    }




    public ArrayList<String> fuzzy_versions(String word)
    {
        rv = new ArrayList<String>();
        String tempWord = "";

        return fuzzy_helper(word, tempWord, rv);

    }

    private ArrayList<String> fuzzy_helper(String word, String tempWord, ArrayList<String> rv)
    {
        Nearby_keys possibility = new Nearby_keys();
        String around[] = null;
        String newWord = null;

        if(word.length()==0) return rv;
        else if(word.length() == 1)
        {
            if(this.ended) rv.add(tempWord);
            around = possibility.nearby_keys(word);
            for(int i = 0; i < around.length; i++)
            {
                if(is_in_trie(around[i]))
                {
                    if(this.subtrie.get(position_in_trie(around[i])).ended) rv.add(tempWord + around[i]) ;
                }
            }
        }
        else if(word.length() > 1)
        {
            /*if(word)
            {

            }*/
            around = possibility.nearby_keys(word.substring(0,1));
            for(int i = 0; i < around.length; i++)
            {
                if(is_in_trie(around[i]))
                {
                    newWord = tempWord + around[i];
                    rv = this.subtrie.get(position_in_trie(around[i])).fuzzy_helper(word.substring(1), newWord,rv);
                }

            }
        }
        return rv;
    }


    public ArrayList<String> get_completion(String word)
    {
        Trie temp = null;
        temp = this;
        for(int i=0;i < word.length();i++)
        {

            temp = temp.subtrie.get(temp.position_in_trie(word.substring(i,i+1)));
        }
        rv = new ArrayList<String>();

        return temp.make_completion_list(word,rv);
    }

    private ArrayList<String> make_completion_list(String word,ArrayList<String> rv)
    {
        String newWord = null;
        boolean more_letters = false;
        if(this.subtrie.size() > 0)  more_letters = true;
        if(this.ended && !more_letters)
        {
            rv.add(word);
        }
        else
        {
            if(this.ended) rv.add(word);
//			System.out.print(this.subtrie.size() +"\n");
            for(int i=0;i< this.subtrie.size(); i++)
            {
                newWord = word + this.subtrie.get(i).value;
//				System.out.print( newWord + "\n");
                this.subtrie.get(i).make_completion_list(newWord,rv);
            }
        }
        return rv;
    }


    public boolean is_word(String word)
    {
        if (word.length() == 0) return false;
        else if(!this.is_in_trie(word.substring(0,1)))return false;
        else if (word.length() == 1) return this.subtrie.get(this.position_in_trie(word.substring(0,1))).ended;
        else return this.subtrie.get(this.position_in_trie(word.substring(0,1))).is_word(word.substring(1));

    }

    public void display_tout(String word)
    {
        if(this.value != null)
        {
            word += this.value;
        }
        if(subtrie.size()==0)
        {
            System.out.print(word +"\t");
            return;
        }
        for(int i=0; i< this.subtrie.size();i++)
        {
            this.subtrie.get(i).display_tout(word);
        }

    }


}
