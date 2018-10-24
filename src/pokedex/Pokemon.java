package pokedex;

public class Pokemon {
    private String name;
    private String type1;
    private String type2;

    public Pokemon(String name, String type1, String type2){
        this.name = name;
        this.type1 = type1;
        this.type2 = type2;
    }

    public String getName() {
        return name;
    }

    public String type1() {
        return type1;
    }

    public String type2() {
        return type2;
    }

}
