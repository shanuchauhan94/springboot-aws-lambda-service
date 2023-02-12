package org.aws.dto;

public class PatientCheckoutEvent {

    public String name;
    public String ssn;
    public int amount;

    public PatientCheckoutEvent() {
    }

    public PatientCheckoutEvent(String name, String ssn, int amount) {
        this.name = name;
        this.ssn = ssn;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PatientCheckoutEvent{" +
                "name='" + name + '\'' +
                ", ssn='" + ssn + '\'' +
                ", amount=" + amount +
                '}';
    }
}
