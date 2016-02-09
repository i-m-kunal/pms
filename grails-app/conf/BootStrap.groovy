import com.tothenew.pms.*
import com.tothenew.pms.constants.RoleConstants

class BootStrap {

    def init = { servletContext ->
        User adminUser = User.findByEmail('kunal.kumar@tothenew.com')
        Role adminRole = Role.findOrSaveByAuthority(RoleConstants.ROLE_ADMIN)
        Role.findOrSaveByAuthority(RoleConstants.ROLE_TRAINEE)
        Role.findOrSaveByAuthority(RoleConstants.ROLE_AUTHOR)
        Role.findOrSaveByAuthority(RoleConstants.EMAIL_DOMAIN_TO_AUTHENTICATE)
        if (!adminUser) {
            User adminUser1 = new User(firstName: "kunal", lastName: "kumar", password: "password", email: "kunal.kumar@tothenew.com", enabled: true).save(failOnError: true)
            User adminUser2 = new User(firstName: "umesh", lastName: "kumar", password: "password", email: "umesh.kumar@tothenew.com", enabled: true).save(failOnError: true)
            new UserRole(user: adminUser1, role: adminRole).save(flush: true)
         //   new UserRole(user: adminUser2, role: adminRole).save(flush: true)

        }
    }
    def destroy = {
    }
}
