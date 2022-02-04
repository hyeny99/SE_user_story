package com.example.demo.container;

import com.example.demo.data.UserStory;
import com.example.demo.persistence.PersistenceException;
import com.example.demo.persistence.PersistenceStrategy;
import com.example.demo.persistence.PersistenceStrategyMongoDB;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Container {
    private List<UserStory> list = null;
    private static Container instance = null; // = new Container();

    // Reference to the internal strategy (e.g. MongoDB or Stream)
    private PersistenceStrategy strategy = null;

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
        try {
            this.strategy = new PersistenceStrategyMongoDB();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }

    }


    /**
     * Method for getting the internal list. e.g. from a View-object
     * @return
     */
    public List getCurrentList() {
        list.sort(Comparator.comparing(UserStory::getGlogerVal).reversed());
        return this.list;
    }

    /**
     * Method for adding Member-objects
     * @param userStory
     * @throws ContainerException
     */
    public void addUserStory ( UserStory userStory ) throws ContainerException {

        if ( contains( userStory )  ) {
            System.out.println("Duplicate: " + userStory.toString() );
            ContainerException ex = new ContainerException( ContainerException.ExceptionType.DuplicateMember );
            ex.addID ( userStory.getID() );
            throw ex;
        }
        list.add(userStory);
    }

    /**
     * Methode zur Ueberpruefung, ob ein Member-Objekt in der Liste enthalten ist
     *
     */
    private boolean contains( UserStory userStory ) {
        Integer ID = userStory.getID();
        for ( UserStory story : list) {
            if ( story.getID() == ID ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method for deleting an object with a given id.
     *
     */
    public String deleteUserStory(Integer id ) {
        UserStory userStory = getUserStory( id );
        if (userStory == null)
            return "Member is not found- ERROR";
        else {
            list.remove(userStory);
            return "User story with ID" + id + " has been deleted.";
        }
    }

    /*
     * Method for getting the number of currently stored objects
     *
     */
    public int size(){
        return list.size();
    }


    public void update(String key, String value) {

    }


    /*
     * Interne Methode zur Ermittlung eines Member
     *
     */
    private UserStory getUserStory(Integer id) {
        for ( UserStory userStory : list) {
            if ( id == userStory.getID() ){
                return userStory;
            }
        }
        return null;
    }


    /**
     * Method for loading objects. Uses the internally stored strategy object
     * @throws PersistenceException
     */
    public List<UserStory> fetch() throws PersistenceException {
        if (this.strategy == null)
            throw new PersistenceException( PersistenceException.ExceptionType.NoStrategyIsSet,  "Strategy not initialized");

        if (connectionOpen == false) {
            this.openConnection();
            connectionOpen = true;
        }
        List<UserStory> list = this.strategy.load();
        this.list = list;
        return this.list;
    }

    public List<Document> query(String key, String value) throws PersistenceException {
        if (this.strategy == null)
            throw new PersistenceException( PersistenceException.ExceptionType.NoStrategyIsSet,  "Strategy not initialized");

        if (connectionOpen == false) {
            this.openConnection();
            connectionOpen = true;
        }

        return this.strategy.query(key, value);
    }

    /**
     * Method for setting the Persistence-Strategy from outside.
     * If a new strategy is set, then the old one is closed before (if available)
     * ToDo here: delegate the exception to the client in case of problems when closing the connection
     * (not really relevant in the context of this assignment)
     *
     * @param persistenceStrategy
     */
    public void setPersistenceStrategie(PersistenceStrategy persistenceStrategy) {
        if (connectionOpen == true) {
            try {
                this.closeConnection();
            } catch (PersistenceException e) {
                // ToDo here: delegate to client (next year maybe ;-))
                e.printStackTrace();
            }
        }
        this.strategy = persistenceStrategy;
    }


    /**
     * Method for storing objects. Uses the internally stored strategy object
     * @throws PersistenceException
     */
    public void store() throws PersistenceException {
        if (this.strategy == null)
            throw new PersistenceException( PersistenceException.ExceptionType.NoStrategyIsSet,
                    "Strategy not initialized");

        if (connectionOpen == false) {
            this.openConnection();
            connectionOpen = true;
        }
        this.strategy.save( this.list );
    }


    public Document findById(Integer id)  throws PersistenceException {
        if (this.strategy == null)
            throw new PersistenceException( PersistenceException.ExceptionType.NoStrategyIsSet,
                    "Strategy not initialized");

        if (connectionOpen == false) {
            this.openConnection();
            connectionOpen = true;
        }
        Document document = this.strategy.findById(id);
        return document;
    }

    private void openConnection() throws PersistenceException {
        try {
            this.strategy.openConnection();
            connectionOpen = true;
        } catch( java.lang.UnsupportedOperationException e ) {
            throw new PersistenceException( PersistenceException.ExceptionType.ImplementationNotAvailable , "Not implemented!" );
        }
    }

    private void closeConnection() throws PersistenceException {
        try {
            this.strategy.closeConnection();
            connectionOpen = false;
        } catch( java.lang.UnsupportedOperationException e ) {
            throw new PersistenceException( PersistenceException.ExceptionType.ImplementationNotAvailable , "Not implemented!" );
        }
    }
}