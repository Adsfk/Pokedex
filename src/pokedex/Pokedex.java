package pokedex;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

class Pokedex {
    private Map<Integer, Pokemon> pokedex;
    static int max;

    Pokedex() {
        pokedex = new HashMap<>();
        loadPokedex();
    }

    int write_Name(String name) {
        for (Integer number : pokedex.keySet()) {
            if(pokedex.get(number).getName().toLowerCase().equals(name)){
                return number;
            }
        }
        return 0;
    }

    Pokemon identify_Pokemon(int ID) throws Exception {
        Pokemon pokemon = pokedex.get(ID);
        if(pokemon == null){
            throw new Exception();
        }
        return pokemon;
    }

    private void loadPokedex(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("pokemones.txt"));
            String st;
            for(max = 1;(st = br.readLine())!=null;max++){
                String[] pok = st.split("-");
                Pokemon p = new Pokemon(pok[0],pok[1],pok[2],pok[3]);
                pokedex.put(max,p);
            }
            max--;
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}