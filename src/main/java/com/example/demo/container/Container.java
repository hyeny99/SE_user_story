package com.example.demo.container;

import com.example.demo.data.UserStory;
import com.example.demo.strategy.db.DBPersistenceStrategy;
import com.example.demo.strategy.db.DBclient;
import com.example.demo.strategy.db.PersistenceException;
import com.example.demo.strategy.db.PersistenceStrategyMongoDB;
import org.bson.Document;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Container {
    private List<UserStory> list = null;
    //private Stack<UserStory> stack = null;
    private static Container instance = null; // = new Container();

    // Reference to the internal strategy (e.g. MongoDB or Stream)
    private DBclient client = null;

    // Flag to see, if a connection is opened
    private boolean connectionOpen = false;

    /*
     * Statische Methode um die einzige Instanz der Klasse
     * Container zu bekommen. Das Keyword synchronized bewirkt,
     * dass garantiert nur ein Objekt den alleinigen Zugriff
     * auf diese Methode hat. Anonsten koennte es passieren, dass
     * zwei parallel zugreifende Objekte zwei unterschiedliche
     * Objekte erhalten (vgl. auch Erlaeuterung in Uebung)
     *
     */
    public static synchronized Container getInstance() {
        if (instance == null) {
            instance = new Container();
            System.out.println("Object of type Container created!");
        }
        return instance;
    }

    // Der statische Initialisierungsblock. Dient nur für Debug-Zwecke
    // zur Verdeutlichung, wann eine Klasse geladen wird.
    static {
        System.out.println("Klasse Container wurde geladen!");
        // instance = new Container();
    }



    /*
     * Ueberschreiben des Konstruktors. Durch die Sichtbarkeit private
     * kann man von aussen die Klasse nicht mehr instanziieren,
     * sondern nur noch kontrolliert ueber die statische Methode
     * der Klasse Container!
     */
    private Container(){
        System.out.println("Container is instantiated! (constructor) ");
        this.list = new ArrayList<UserStory>();
        // this.stack = new Stack<>();
        try {
            this.client = new DBclient(new PersistenceStrategyMongoDB());
        } catch (PersistenceException e) {
            e.printStackTrace();
        }

    }


    /**
     * Method for getting the internal list. e.g. from a View-object
     */
    public List getCurrentList() {
        //list.sort(Comparator.comparing(UserStory::getGlogerVal).reversed());
        List<UserStory> sorted = list.stream()
                .filter(userStory -> !userStory.getGlogerVal().isNaN())
                .sorted(Comparator.comparing(UserStory::getGlogerVal)
                        .reversed())
                .collect(Collectors.toList());
        return sorted;
    }

    public List findByState(String state) {
        List<UserStory> statusList = new ArrayList<>();
        for (UserStory userStory: this.list) {
            if (userStory.getState().equals(state))
                statusList.add(userStory);
        }
        return statusList;
    }

    /**
     * Method for adding Member-objects
     */
    public void addUserStory ( UserStory userStory ) throws ContainerException {

        if ( contains( userStory )  ) {
            System.out.println("Duplicate: " + userStory);
            ContainerException ex = new ContainerException( ContainerException.ExceptionType.DuplicateMember );
            ex.addID ( userStory.getID() );
            throw ex;
        }
        list.add(userStory);
        //stack.push(userStory);
    }

    /**
     * Methode zur Ueberpruefung, ob ein Member-Objekt in der Liste enthalten ist
     *
     */
    private boolean contains( UserStory userStory ) {
        Integer ID = userStory.getID();
        for ( UserStory story : list) {
            if (story.getID().equals(ID)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method for deleting an object with a given id.
     *
     */
    public void deleteUserStory(Integer id ) {

        // filter-map-reduced pattern used
        list.stream()
                .filter(userStory -> userStory.getID().equals(id))
                .findFirst()
                .filter(userStory -> userStory != null)
                .map(userStory -> {
                    return list.remove(userStory);
                });
    }

    public void undoEnter() {
//        if (!stack.empty()) {
//            UserStory user = stack.pop();
//            deleteUserStory(user.getID());
//        }
        this.list.remove(this.list.size() - 1);
    }

    /*
     * Method for getting the number of currently stored objects
     *
     */
    public int size(){
        return list.size();
    }



    /*
     * Interne Methode zur Ermittlung eines Member
     *
     */
    private UserStory getUserStory(Integer id) {

        for ( UserStory userStory : list) {
            if (id.equals(userStory.getID())){
                return userStory;
            }
        }
        return null;
    }


    /**
     * Method for loading objects. Uses the internally stored strategy object
     */
    public List<UserStory> load() throws PersistenceException {
        if (this.client == null)
            throw new PersistenceException( PersistenceException.ExceptionType.NoStrategyIsSet,  "Strategy not initialized");

        if (connectionOpen == false) {
            this.openConnection();
            connectionOpen = true;
        }
        List<UserStory> dbStories = this.client.load();
        Set<UserStory> dbSet = new TreeSet<>(Comparator.comparing(UserStory::getID));
        dbSet.addAll(dbStories);
        dbSet.addAll(this.list);

        this.list = new ArrayList<>(dbSet);
        return this.list;
    }

    public List<Document> query(String key, String value) throws PersistenceException {
        if (this.client == null)
            throw new PersistenceException( PersistenceException.ExceptionType.NoStrategyIsSet,  "Strategy not initialized");

        if (connectionOpen == false) {
            this.openConnection();
            connectionOpen = true;
        }

        return this.client.query(key, value);
    }

    /**
     * Method for setting the Persistence-Strategy from outside.
     * If a new strategy is set, then the old one is closed before (if available)
     * ToDo here: delegate the exception to the client in case of problems when closing the connection
     * (not really relevant in the context of this assignment)
     *
     */
    public void setPersistenceStrategie(DBPersistenceStrategy strategy) {
        if (connectionOpen == true) {
            try {
                this.closeConnection();
            } catch (PersistenceException e) {
                // ToDo here: delegate to client (next year maybe ;-))
                e.printStackTrace();
            }
        }
        this.client = new DBclient(strategy);
    }


    /**
     * Method for storing objects. Uses the internally stored strategy object
     */
    public void store() throws PersistenceException {
        if (this.client == null)
            throw new PersistenceException( PersistenceException.ExceptionType.NoStrategyIsSet,
                    "Strategy not initialized");

        if (connectionOpen == false) {
            this.openConnection();
            connectionOpen = true;
        }
        this.client.save( this.list );
    }


    public Document findById(Integer id)  throws PersistenceException {
        if (this.client == null)
            throw new PersistenceException( PersistenceException.ExceptionType.NoStrategyIsSet,
                    "Strategy not initialized");

        if (connectionOpen == false) {
            this.openConnection();
            connectionOpen = true;
        }
        return this.client.findById(id);
    }

    public void update(int id, String key, String value) throws PersistenceException {
        if (this.client == null)
            throw new PersistenceException( PersistenceException.ExceptionType.NoStrategyIsSet,
                    "Strategy not initialized");

        if (connectionOpen == false) {
            this.openConnection();
            connectionOpen = true;
        }
        client.update(id, key, value);
    }

    private void openConnection() throws PersistenceException {
        try {
            this.client.openConnection();
            connectionOpen = true;
        } catch( java.lang.UnsupportedOperationException e ) {
            throw new PersistenceException( PersistenceException.ExceptionType.ImplementationNotAvailable , "Not implemented!" );
        }
    }

    private void closeConnection() throws PersistenceException {
        try {
            this.client.closeConnection();
            connectionOpen = false;
        } catch( java.lang.UnsupportedOperationException e ) {
            throw new PersistenceException( PersistenceException.ExceptionType.ImplementationNotAvailable , "Not implemented!" );
        }
    }
}
