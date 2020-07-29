package BooleanSystem;

import java.util.ArrayList;

public abstract class Gate {
    protected java.lang.String id;
    protected ArrayList<java.lang.String> inputs;
    protected java.lang.String output;



    public Gate(java.lang.String id, java.lang.String output) {
        this.id = id;
        this.output = output;
        inputs = new ArrayList<java.lang.String>();
    }

    public void addInput(java.lang.String string) {
        inputs.add(string);
    }

    public java.lang.String getOutput(){
        return output;
    };

    public java.lang.String getHealthyVariableRepresentation() {
        StringBuilder sb = new StringBuilder();
        sb.append("H_").append(id).append("->").append("(").append(output).append("<->").append(getGateOp()).append(")");
        return sb.toString();
    }

    protected abstract java.lang.String getGateOp();

    public java.lang.String getId() {
        return id;
    }

    public ArrayList<java.lang.String> getInputs() {
        return inputs;
    }

}
