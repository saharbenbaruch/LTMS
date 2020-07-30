import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CLiteral cLiteral = (CLiteral) o;
        return negative == cLiteral.negative &&
                Objects.equals(name, cLiteral.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, negative);
    }

    public String getName() {
        return name;
    }

}
