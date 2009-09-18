class Group {

    String name
    String description
    boolean completed

    static hasMany = [items:Item]

    static constraints = {
        name(blank:false, unique:true)
        description(blank:true, size:1..500)
        completed(nullable:true)
    }

    static mapping = {
        table "grouping"
        description type:"text"
    }
}
