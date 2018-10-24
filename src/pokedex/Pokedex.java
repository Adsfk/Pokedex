package pokedex;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;


public class Pokedex {
    private Map<Integer, Pokemon> pokedex;

    public Pokedex() {
        pokedex = new HashMap<>();
        loadPokedex();
    }

    public void selectedAction(String select) {
        if(select.equals("mostrar")){
            Show_Pokedex();
            return;
        }
        if(select.equals("numero")){
            Write_Number();
            return;
        }
        if(select.equals("nombre")){
            Write_Name();
            return;
        }
        System.out.println("Error: eso no es una opción wey");
    }

    private void Show_Pokedex() {
        pokedex.keySet().forEach(e->System.out.println(Identify_Pokemon(e)+"\n"));
    }

    private void Write_Number() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el numero primo: ");
        int number = scanner.nextInt();
        System.out.println(Identify_Pokemon(number));
        System.out.println(" ");
    }

    private void Write_Name() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select pokemon name: ");
        String name = scanner.next();
        for (Integer number : pokedex.keySet()) {
            if(pokedex.get(number).getName().toLowerCase().equals(name.toLowerCase())){
                System.out.println(Identify_Pokemon(number)+"\n");
                return;
            }
        }
        System.out.println("ERROR: Eso no es un pokemon mano");
        Write_Name();
    }

    public String Identify_Pokemon(int ID) {
        Pokemon pokemon = pokedex.get(ID);
        if(pokemon == null){
            System.out.println("Ese pokemon no existe wey");
            return "";
        }
        if(pokemon.type2().equals(" ")){
            return ID + " - "+pokemon.getName()+ " - "+  pokemon.type1();
        }
        return ID + " - "+pokemon.getName()+" - "+ pokemon.type1() +" / "+ pokemon.type2();
    }

    public void loadPokedex(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("pokemones.txt"));
            String st = "";
            for(int i = 1;(st = br.readLine())!=null;i++){
                String[] pok = st.split("-");
                Pokemon p;
                if(pok.length==2){
                    p = new Pokemon(pok[0],pok[1]," ");
                }else{
                    p = new Pokemon(pok[0],pok[1],pok[2]);
                }
                pokedex.put(i,p);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("No existe el fichero");
        }

    }
}