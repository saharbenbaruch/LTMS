package BooleanSystem;

public class XorGate extends Gate {


    public XorGate(java.lang.String id, java.lang.String output) {
        super(id, output);
    }


    protected java.lang.String getGateOp() {
        StringBuilder sb = new StringBuilder();
        sb.append("((");
        sb.append("(").append(inputs.get(0)).append("&").append("~").append(inputs.get(1)).append(")");
        sb.append("|");
        sb.append("(").append("~").append(inputs.get(0)).append("&").append(inputs.get(1)).append(")");
        sb.append("))");

        return sb.toString();
    }
}
