package pokedex;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Pokedex {
    private Map<Integer, Pokemon> pokedex;
    public int max;

    public Pokedex() {
        pokedex = new HashMap<>();
        loadPokedex();
    }

    public int getMax() {
        return this.max;
    }

    public int writeName(String name) {
        for (Integer number : pokedex.keySet())
            if(pokedex.get(number).getName().equalsIgnoreCase(name))
                return number;
        return 0;
    }

    Pokemon identifyPokemon(int ID) {
        return pokedex.get(ID);
    }

    private void loadPokedex(){
        try(BufferedReader br = new BufferedReader(new FileReader("pokemones.txt"))){
            String st;
            Pokemon p;
            for(max = 1;(st = br.readLine())!=null;max++){
                String[] pok = st.split("-");
                p = new Pokemon(pok[0],pok[1],pok[2],pok[3]);
                pokedex.put(max,p);
            }
            max--;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}