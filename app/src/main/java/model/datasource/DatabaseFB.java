package model.datasource;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import model.backend.IBackend;
import model.entities.Trip;

public class DatabaseFB implements IBackend
{
    static private long countTrips;
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference ref1 = database.getReference("trip");
    private static ChildEventListener tripRefChildEventListener;
    static ArrayList<Trip> trips=new ArrayList<>();

    public interface NotifyDataChange<T> {
        void onDataChanged(T obj);

        void onFailure(Exception exception);
    }


 //   DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
public DatabaseFB()
{
    notifyToTripsList(new NotifyDataChange<List<Trip>>() {
        @Override
        public void onDataChanged(List<Trip> obj) {

        }

        @Override
        public void onFailure(Exception exception) {

        }
    });


}


    public static void notifyToTripsList(final NotifyDataChange<List<Trip>> notifyDataChange) {
        if (notifyDataChange != null) {

            if (tripRefChildEventListener != null)
            {
                notifyDataChange.onFailure(new Exception("first unNotify trips list"));
                return;
            }
            trips.clear();

            tripRefChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Trip trip = dataSnapshot.getValue(Trip.class);
                    String id = dataSnapshot.getKey();
                    trip.setIdTrip(Long.parseLong(id));
                    trips.add(trip);
                    notifyDataChange.onDataChanged(trips);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Trip trip = dataSnapshot.getValue(Trip.class);
                    String id = dataSnapshot.getKey();
                    trip.setIdTrip(Long.parseLong(id));


                    for (int i = 0; i < trips.size(); i++) {
                        if (((Long) trips.get(i).getIdTrip()).equals(id)) {
                            trips.set(i, trip);
                            break;
                        }
                    }
                    notifyDataChange.onDataChanged(trips);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Trip trip = dataSnapshot.getValue(Trip.class);
                    Long id = Long.parseLong(dataSnapshot.getKey());
                    trip.setIdTrip(id);

                    for (int i = 0; i < trips.size(); i++) {
                        if (trips.get(i).getIdTrip() == id) {
                            trips.remove(i);
                            break;
                        }
                    }
                    notifyDataChange.onDataChanged(trips);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    notifyDataChange.onFailure(databaseError.toException());
                }
            };
            ref1.addChildEventListener(tripRefChildEventListener);
        }
    }
    public static void stopNotifyToDriverList() {
        if (tripRefChildEventListener != null) {
            ref1.removeEventListener(tripRefChildEventListener);
            tripRefChildEventListener = null;
        }
    }

    /**
     *this function gats an instance of tha class Trip and add it to the Firebase
     * @param trip
     */
    @Override
    public void addTrip(Trip trip)
    {
        if(trips.size()>0)
            countTrips=trips.get(trips.size()-1).getIdTrip();
        else
            countTrips=0;
        countTrips++;
        trip.setIdTrip(countTrips);
        String key=Long.toString(countTrips);
        ref1.child(key).setValue(trip);

    }

    /**
     *this function gets an id of a trip and returns the trip from the firebase
     * @param idTrip
     * @return
     * @throws Exception
     */
    @Override
    public Trip getTrip(long idTrip) throws Exception {
        return null;
    }
}
