package BooleanSystem;

public class BufferGate extends Gate {

    public BufferGate(java.lang.String id, java.lang.String output) {
        super(id, output);
    }

    protected java.lang.String getGateOp() {
        return inputs.get(0);
    }
}
