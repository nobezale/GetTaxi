package model.datasource;

import java.util.ArrayList;

import model.backend.IBackend;
import model.entities.Trip;

public class DatabaseList implements IBackend {

    private ArrayList<Trip> trips=new ArrayList<Trip>();
    static private long countTrips=0;

    /**
     * this funtion gets an instance of Trip and add it to the list of all trips
     * @param trip
     */
    @Override
    public void addTrip(Trip trip){
        countTrips++;
        trip.setIdTrip(countTrips);
        trips.add(trip);
    }

    /**
     * this funciton gets an id of a trip and returns the trip from the list-trips
     * @param idTrip
     * @return
     * @throws Exception
     */
    @Override
    public Trip getTrip(long idTrip) throws Exception {
        for(Trip tripItem:trips)
            if(idTrip==tripItem.getIdTrip())
                return tripItem;
        throw new Exception("this trip does not exist!");
    }

    /**
     * this function initialize the list of the trips
     */
    public void setList(){
        try{
            this.addTrip(new Trip("jerusalem,ktav sofer","jerusalem,beit hadfus","leah","052-8641770","leah123@gmail.com"));
            this.addTrip(new Trip("beit-shemesh,keren hayesod","beit-shemesh,big fashion","dani","054-4123677","dani99@gmail.com"));
            this.addTrip(new Trip("jerusalem,centeral station","beit-shemesh,havakok hanavi","yossi","058-4796314","yossi770@gmail.com"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
