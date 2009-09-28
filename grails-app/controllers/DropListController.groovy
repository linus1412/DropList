

class DropListController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ dropListInstanceList: DropList.list( params ), dropListInstanceTotal: DropList.count() ]
    }

    def show = {
        def dropListInstance = DropList.get( params.id )

        if(!dropListInstance) {
            flash.message = "DropList not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ dropListInstance : dropListInstance ] }
    }

    def delete = {
        def dropListInstance = DropList.get( params.id )
        if(dropListInstance) {
            try {
                dropListInstance.delete()
                flash.message = "DropList ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "DropList ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "DropList not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def dropListInstance = DropList.get( params.id )

        if(!dropListInstance) {
            flash.message = "DropList not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ dropListInstance : dropListInstance ]
        }
    }

    def update = {
        def dropListInstance = DropList.get( params.id )
        if(dropListInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(dropListInstance.version > version) {
                    
                    dropListInstance.errors.rejectValue("version", "dropList.optimistic.locking.failure", "Another user has updated this DropList while you were editing.")
                    render(view:'edit',model:[dropListInstance:dropListInstance])
                    return
                }
            }
            dropListInstance.properties = params
            if(!dropListInstance.hasErrors() && dropListInstance.save()) {
                flash.message = "DropList ${params.id} updated"
                redirect(action:show,id:dropListInstance.id)
            }
            else {
                render(view:'edit',model:[dropListInstance:dropListInstance])
            }
        }
        else {
            flash.message = "DropList not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
        def dropListInstance = new DropList()
        dropListInstance.properties = params
        return ['dropListInstance':dropListInstance]
    }

    def save = {
        def dropListInstance = new DropList(params)
        if(!dropListInstance.hasErrors() && dropListInstance.save()) {
            flash.message = "DropList ${dropListInstance.id} created"
            redirect(action:show,id:dropListInstance.id)
        }
        else {
            render(view:'create',model:[dropListInstance:dropListInstance])
        }
    }
}
