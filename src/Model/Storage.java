package Model;

public class Storage {
    private int appointmentId;
    private int customerId;

    public Storage() {}

    /**
     *
     * @return the stored appointment id.
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * stores an appointment id.
     * @param appointmentId the appointment id to store.
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     *
     * @return the stored customer id.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     *
     * @param customerId the customer id to store.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
