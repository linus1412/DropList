import org.grails.comments.*

class Item implements Commentable {

    String tdRef
    boolean candidate
    boolean fixed
    boolean passed

    static belongsTo = Group

    static constraints = {
    }
}
