package pokedex;

public class Pokemon {
    private final String name;
    private final String type1;
    private final String type2;
    private final String dir;

    public Pokemon(String name, String type1, String type2, String dir){
        this.name = name;
        this.type1 = type1;
        this.type2 = type2;
        this.dir = dir;
    }

    String getName() {
        return name;
    }

    String getDir(){
        return dir;
    }

    public String toString(){
        return type2.equals(" ")?(name + " - " + type1):(name + " - " + type1 + "/" + type2);
    }
}