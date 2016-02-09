package com.tothenew.pms

import com.tothenew.pms.constants.RoleConstants
import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.oauth.OAuthToken
import org.springframework.security.core.context.SecurityContextHolder

class SpringSecurityOAuthController {

    public static final String SPRING_SECURITY_OAUTH_TOKEN = 'springSecurityOAuthToken'

    def grailsApplication
    def oauthService
    def onSuccess = {
        if (!params.provider) {
            renderError 400, "The Spring Security OAuth callback URL must include the 'provider' URL parameter."
            return
        }

        def sessionKey = oauthService.findSessionKeyForAccessToken(params.provider)
        if (!session[sessionKey]) {
            renderError 500, "No OAuth token in the session for provider '${params.provider}'!"
            return
        }

        OAuthToken oAuthToken = createAuthToken(params.provider, session[sessionKey])

        if (oAuthToken.principal instanceof User) {
            authenticateAndRedirect(oAuthToken, defaultTargetUrl)
        } else {
            session[SPRING_SECURITY_OAUTH_TOKEN] = oAuthToken
            def redirectUrl = SpringSecurityUtils.securityConfig.failureHandler.defaultTargetUrl
            log.debug "Redirecting to get your account created link: ${redirectUrl}"
            redirect(redirectUrl instanceof Map ? redirectUrl : [uri: redirectUrl])
        }
    }

    def onFailure = {
        authenticateAndRedirect(null, defaultTargetUrl)
    }

    protected renderError(code, msg) {
        log.error msg + " (returning ${code})"
        render status: code, text: msg
    }


    protected OAuthToken createAuthToken(providerName, scribeToken) {
        def providerService = grailsApplication.mainContext.getBean("${providerName}SpringSecurityOAuthService")
        OAuthToken oAuthToken = providerService.createAuthToken(scribeToken)
        String email = oAuthToken.socialId
        User user = User.findByEmail(email)
        def response = oauthService.getGoogleResource(scribeToken, 'https://www.googleapis.com/oauth2/v1/userinfo')

        def userDetail = JSON.parse(response.body)

        if (email.endsWith(RoleConstants.EMAIL_DOMAIN_TO_AUTHENTICATE)) {
            if (!user) {
                user = new User(email: email, photo: userDetail.picture, firstName: userDetail.given_name,
                        lastName: userDetail.family_name, password: "igdefault")
                user.save(failOnError: true, flush: true)
                new UserRole(user: user, role: Role.findByAuthority('ROLE_TRAINEE')).save(flush: true)
                new OAuthID(provider: oAuthToken.providerName, accessToken: email, user: user).save(failOnError: true, flush: true)

            } else {
                user.photo = userDetail.picture
                user.firstName = userDetail.given_name
                user.lastName = userDetail.family_name
                user.save(flush: true)
            }
        }
        oAuthToken.principal = user
        oAuthToken.authorities = user?.authorities
        oAuthToken.authenticated = true
        return oAuthToken
    }



    protected Map getDefaultTargetUrl() {
        def config = SpringSecurityUtils.securityConfig
        def savedRequest = SpringSecurityUtils.getSavedRequest(session)
        def defaultUrlOnNull = '/'

        if (savedRequest && !config.successHandler.alwaysUseDefault) {
            return [url: (savedRequest.redirectUrl ?: defaultUrlOnNull)]
        } else {
            return [uri: (config.successHandler.defaultTargetUrl ?: defaultUrlOnNull)]
        }
    }

    protected void authenticateAndRedirect(OAuthToken oAuthToken, redirectUrl) {
        session.removeAttribute SPRING_SECURITY_OAUTH_TOKEN

        SecurityContextHolder.context.authentication = oAuthToken
        redirect(redirectUrl instanceof Map ? redirectUrl : [uri: redirectUrl])
    }

}


