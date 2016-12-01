package global;

public class Nearby_keys {
    private String nearby_dict[][] =
                    {{"q","w","a"},
                    {"w","q","e","s","w"},
                    {"e","w","r","d"},
                    {"r","e","t","f"},
                    {"t","r","y","g"},
                    {"y","t","u","h"},
                    {"u","j","i","y"},
                    {"i","u","o","k"},
                    {"o","i","p","l"},
                    {"p","o"},
                    {"a","q","s","z"},
                    {"s","z","x","d","a","w"},
                    {"d","e","s","f","x","c"},
                    {"f","r","d","g","c","v"},
                    {"g","h","t","f","v","b"},
                    {"h","y","g","j","b","n"},
                    {"j","u","h","k","n","m"},
                    {"k","i","j","l","m"},
                    {"l","k","o"},
                    {"z","s","x"},
                    {"x","z","s","d","c"},
                    {"c","x","v","f"},
                    {"v","c","b","f","g"},
                    {"b","v","n","n","h"},
                    {"n","b","m","h","j"},
                    {"m","k","j","n"}} ;

    private String key[] =
            {"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m"}
            ;


    public String[] nearby_keys(String touche)
    {
        String nearby[] = null;
        if(touche.length() != 1) return null;
        int index = positionInKeyTable(touche);
        nearby = nearby_dict[index];
//		 nearby[nearby.length] = touche;
        //TODO : fix that shit

        return nearby;
    }

    private int positionInKeyTable(String touche)
    {
        for(int i=0; i<key.length; i++)
        {
            if(touche.toLowerCase().equals(key[i])) return i;
        }
        return -1;
    }

}
