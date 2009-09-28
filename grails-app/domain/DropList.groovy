class DropList {

    String name
    String description
    boolean completed
    Customer customer
    Date toTest
    Date toCustomer

    static hasMany = [items:Item]

    static constraints = {
        name(blank:false, unique:true)
        description(blank:true, size:1..500)
        completed(nullable:true)
    }

    static mapping = {
        description type:"text"
    }
}
