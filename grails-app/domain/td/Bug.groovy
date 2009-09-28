package td

import java.beans.PropertyEditorSupport

class Bug {

    String status
    String summary
    String description
    String comments
    String severity
    String priority
    String assignedTo

    static constraints = {
    }

    static mapping = {
        table "BUG"
        id column: "BG_BUG_ID", sqlType: "integer", length: 4
        status column: "BG_STATUS", sqlType: "varchar", length: 70
        summary column: "BG_SUMMARY", sqlType: "varchar", length: 255
        description column: "BG_DESCRIPTION", sqlType: "longvarchar", length: 16
        comments column: "BG_DEV_COMMENTS", sqlType: "longvarchar", length: 16
        severity column: "BG_SEVERITY", sqlType: "varchar", length: 70
        priority column: "BG_PRIORITY", sqlType: "varchar", length: 70
        assignedTo column: "BG_RESPONSIBLE", sqlType: "varchar", length: 60
        version false
    }
}

class BugEditor extends PropertyEditorSupport {

    String getAsText() {
        value.summary
    }

    void setAsText(String id) {
        def bug
        if (id) {
            bug = td.Bug.read(id.toInteger())
            if (bug == null ) {
                throw new Exception("No bug with id: $id} found")
            }
        }
        setValue(bug)
    }

}
