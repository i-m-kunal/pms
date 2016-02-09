package pms

class ApplicationFilters {

    def filters = {
        all(controller: '*', action: '*') {
            before = {
                println(">>>>>>>>>>>>>>>>>>>>>$params")
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
