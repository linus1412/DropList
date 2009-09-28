import org.grails.comments.*

class Item implements Commentable {

    Integer tdRef
    td.Bug bug
    boolean candidate
    boolean fixed
    boolean passed

    Priority priority

    static belongsTo = DropList

    static constraints = {
        tdRef(nullable:true, validator: {val, obj ->
                if (val && !td.Bug.get(val)) return "no.such.bug"
                }
            )
    }

    static transients = ['bug']

    static mapping = {
        bug sqlType: "integer", length: 4
    }

    def afterLoad = {
        bug = td.Bug.get(tdRef)
    }
}

enum Priority { MUST_HAVE, SHOULD_HAVE, COULD_HAVE, WONT_HAVE }
