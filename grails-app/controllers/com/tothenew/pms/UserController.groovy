package com.tothenew.pms
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import com.tothenew.pms.constants.RoleConstants


@Secured([RoleConstants.ROLE_ADMIN])
class UserController {
    SpringSecurityService springSecurityService

    def index(){
        render view: "list"
    }

    @Secured(['permitAll'])
    def failure() {
        render " login with ttnd email address"
    }
    def success(String code) {

    }


}
