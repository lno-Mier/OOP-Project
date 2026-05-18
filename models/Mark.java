package models;

import java.io.Serializable;

public class Mark implements Serializable {
    private static final long serialVersionUID = 1L;

    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;

    public Mark() {
        this.firstAttestation = 0;
        this.secondAttestation = 0;
        this.finalExam = 0;
    }

    public double getTotal() {
        return firstAttestation + secondAttestation + finalExam;
    }

    public double getGpaValue() {
        double total = getTotal();
        if (total >= 90) {
            return 4.0;
        }
        if (total >= 80) {
            return 3.67;
        }
        if (total >= 70) {
            return 3.0;
        }
        if (total >= 60) {
            return 2.33;
        }
        if (total >= 50) {
            return 1.0;
        }
        return 0.0;
    }

    public boolean isFail() {
        return getTotal() < 50;
    }

    public void setFirstAttestation(double score) {
        this.firstAttestation = score;
    }

    public void setSecondAttestation(double score) {
        this.secondAttestation = score;
    }

    public void setFinalExam(double score) {
        this.finalExam = score;
    }

    public double getFirstAttestation() {
        return firstAttestation;
    }

    public double getSecondAttestation() {
        return secondAttestation;
    }

    public double getFinalExam() {
        return finalExam;
    }

    @Override
    public String toString() {
        return String.format("%.1f/%.1f/%.1f (Total: %.1f)", firstAttestation, secondAttestation, finalExam, getTotal());
    }
}
