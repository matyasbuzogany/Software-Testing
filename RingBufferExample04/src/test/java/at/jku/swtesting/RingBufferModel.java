package at.jku.swtesting;

import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionCoverage;

public class RingBufferModel implements FsmModel {

    protected int CAPACITY = 3;
    protected int size;

    @Override
    public Object getState() {
        if (size == 0) {
            return "EMPTY";
        } else if (size == CAPACITY) {
            return "FULL";
        } else if ((size > 0) && (size < CAPACITY)) {
            return "FILLED";
        }  else return "ERROR";
    }

    @Override
    public void reset(boolean b) {
        size = 0;
    }

    @Action
    public void enqueue() {
        if (getState() != "FULL") {
            size++;
        }
    }

    public boolean enqueueGuard() {
        return CAPACITY > 0;
    }

    @Action
    public void dequeue() {
        size--;
    }

    public boolean dequeueGuard() {
        return CAPACITY > 0 && size > 0;
    }

    @Action
    public RuntimeException dequeueFromEmptyBuffer() {
        if (getState() == "EMPTY") {
            return new RuntimeException();
        }
        return null;
    }

    @Action
    public void peek() {
        getState();
    }

    public static void main(String[] args) {
        Tester tester = new RandomTester(new RingBufferModel());
        tester.buildGraph();
        tester.addListener(new VerboseListener());
        tester.addListener(new StopOnFailureListener());
        tester.addCoverageMetric(new ActionCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new TransitionCoverage());

        tester.generate(200);

        tester.printCoverage();
    }

}
