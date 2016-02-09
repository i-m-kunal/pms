<%@ page import="com.tothenew.pms.User" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="pmsLayout"/>
    <title>Performance Monitoring System</title>
</head>

<body>
this is list page of user
<sec:ifNotLoggedIn>
    <g:link controller="login" action="auth">Login</g:link>
</sec:ifNotLoggedIn>
<sec:ifAllGranted roles="ROLE_USER">
    <g:link class="create" controller="post" action="timeline">My Timeline</g:link>
</sec:ifAllGranted>
<sec:username />
${com.tothenew.pms.User.list()}
</body>
</html>