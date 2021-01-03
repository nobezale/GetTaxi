package model.entities;

import java.util.Objects;

enum STATUS{available,handled,ended}
public class Trip
{
    private long idTrip;
    private STATUS status;
    private String startLoc;
    private String desLoc;
    private String startTime;
    private String finishTime;
    private String nameTraveler;
    private String telTraveler;
    private String emailTraveler;
    private String driverId;

    /**
     * empty constructor
     */
    public Trip(){
        idTrip=0;
        status=STATUS.available;
        startLoc="";
        desLoc="";
        startTime="";
        finishTime="";
        nameTraveler="";
        telTraveler="";
        emailTraveler="";
        driverId="";
    }

    /**
     * parameter constructor
     * @param newStartLoc
     * @param newDesLoc
     * @param newNameTraveler
     * @param newTelTraveler
     * @param newEmailTraveler
     */

    public Trip(String newStartLoc,String newDesLoc,String newNameTraveler,String newTelTraveler,String newEmailTraveler)
    {
        this.idTrip=0;
        this.status=STATUS.available;
        this.startLoc=newStartLoc;
        this.desLoc=newDesLoc;
        this.startTime="";
        this.finishTime="";
        this.nameTraveler=newNameTraveler;
        this.telTraveler=newTelTraveler;
        this.emailTraveler=newEmailTraveler;
        this.driverId="";


    }

    //getters and setters
    public long getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(long idTrip) {
        this.idTrip = idTrip;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public String getStartLoc() {
        return startLoc;
    }

    public void setStartLoc(String startLoc) {
        this.startLoc = startLoc;
    }

    public String getDesLoc() {
        return desLoc;
    }

    public void setDesLoc(String desLoc) {
        this.desLoc = desLoc;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getNameTraveler() {
        return nameTraveler;
    }

    public void setNameTraveler(String nameTraveler) {
        this.nameTraveler = nameTraveler;
    }

    public String getTelTraveler() {
        return telTraveler;
    }

    public void setTelTraveler(String telTraveler) {
        this.telTraveler = telTraveler;
    }

    public String getEmailTraveler() {
        return emailTraveler;
    }

    public void setEmailTraveler(String emailTraveler) {
        this.emailTraveler = emailTraveler;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    /**
     * a function that returns a string that describes the object
     *
     * @return
     */
    @Override
    public String toString() {
        return "Trip{" +
                "idTrip=" + idTrip +
                ", status=" + status +
                ", startLoc='" + startLoc + '\'' +
                ", desLoc='" + desLoc + '\'' +
                ", startTime='" + startTime + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", nameTraveler='" + nameTraveler + '\'' +
                ", telTraveler='" + telTraveler + '\'' +
                ", emailTraveler='" + emailTraveler + '\'' +
                ", driverId='" + driverId + '\'' +
                '}';
    }
    /**
     * a function that return true if the 2 trips are equals else,return false
     * @param obj
     * @return
     */

    @Override

    public boolean equals(Object obj){
        if(this.idTrip==((Trip)obj).getIdTrip())
            return true;
        else
            return false;
    }
}

