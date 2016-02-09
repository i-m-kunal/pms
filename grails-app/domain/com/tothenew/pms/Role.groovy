package com.tothenew.pms

import org.springframework.security.core.GrantedAuthority

class Role implements GrantedAuthority {

	String authority

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
