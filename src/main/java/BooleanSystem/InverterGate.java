package BooleanSystem;

public class InverterGate extends Gate {

    public InverterGate(java.lang.String id, java.lang.String output) {
        super(id, output);
    }

    protected java.lang.String getGateOp() {
        return "(~" + inputs.get(0) + ")";
    }
}
