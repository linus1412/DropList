import org.grails.comments.*

class Item implements Commentable {

    String tdRef
    boolean candidate
    boolean fixed
    boolean passed

    Priority priority

    static belongsTo = Group

    static constraints = {
    }
}

enum Priority { MUST_HAVE, SHOULD_HAVE, COULD_HAVE, WONT_HAVE }
