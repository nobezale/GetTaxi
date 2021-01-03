package model.backend;
import model.entities.Trip;

public interface IBackend {
    /**
     * function that gets a trip and add it to the database
     * @param trip
     * @throws Exception
     */
    public void addTrip(Trip trip);

    /**
     * function that return a trip according to an id of trip' if it exists
     * @param idTrip
     * @return
     * @throws Exception
     */
    public Trip getTrip(long idTrip)throws Exception;
}
