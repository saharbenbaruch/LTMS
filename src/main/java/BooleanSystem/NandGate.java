package BooleanSystem;

public class NandGate extends Gate {

    public NandGate(java.lang.String id, java.lang.String output) {
        super(id, output);
    }


    protected java.lang.String getGateOp() {
        StringBuilder sb = new StringBuilder();
        sb.append("(~(");
        for (int i = 0; i < inputs.size() - 1; i++) {
            sb.append(inputs.get(i) + "&");

        }
        sb.append(inputs.get(inputs.size() - 1)).append("))");
        return sb.toString();
    }


}
