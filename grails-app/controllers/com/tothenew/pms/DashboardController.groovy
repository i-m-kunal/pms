package com.tothenew.pms

import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.SpringSecurityService

class DashboardController {
    SpringSecurityService springSecurityService


    @Secured(['ROLE_TRAINEE','ROLE_ADMIN'])
    def index() {
        User u=User.get(springSecurityService.principal.id)
        println u
         }




    @Secured(['permitAll'])
    def failure() {
        render "login with ttnd email address"
    }

    @Secured(['permitAll'])
    def login(){
    }
}
