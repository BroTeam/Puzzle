package model;

import java.util.ArrayList;

/**
 * A hard-coded list of the {@link Link}s of the considered puzzle.
 * 
 * @author <a href="mailto:joffrey.bion@gmail.com">Joffrey Bion</a>
 */
class LinksList extends ArrayList<Link> {

    /**
     * Contains the unique instance of the links list.
     */
    private static LinksList instance = null;

    /**
     * Returns the unique instance of the links list.
     * 
     * @return The unique instance of the links list.
     */
    static LinksList getInstance() {
        if (instance == null) {
            instance = new LinksList();
        }
        return instance;
    }

    private LinksList() {
        super();
        add(Link.END);
        add(Link.STRAIGHT);
        add(Link.ELBOW);
        add(Link.ELBOW);
        add(Link.ELBOW);
        add(Link.STRAIGHT);
        add(Link.ELBOW);
        add(Link.ELBOW);
        add(Link.STRAIGHT);
        add(Link.ELBOW);
        add(Link.ELBOW);
        add(Link.ELBOW);
        add(Link.STRAIGHT);
        add(Link.ELBOW);
        add(Link.STRAIGHT);
        add(Link.ELBOW);
        add(Link.ELBOW);
        add(Link.ELBOW);
        add(Link.ELBOW);
        add(Link.STRAIGHT);
        add(Link.ELBOW);
        add(Link.STRAIGHT);
        add(Link.ELBOW);
        add(Link.STRAIGHT);
        add(Link.ELBOW);
        add(Link.STRAIGHT);
        add(Link.END);
    }
}
