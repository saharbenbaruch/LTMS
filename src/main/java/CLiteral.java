public class CLiteral {
    String name;
    boolean negative;

    @Override
    public String toString() {
        return "CLiteral{" +
                "name='" + name + '\'' +
                ", negative=" + negative +
                '}';
    }

    public CLiteral(String name, boolean negative) {
        this.name = name;
        this.negative=negative;
    }

    public String getName() {
        return name;
    }

}
