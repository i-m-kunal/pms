<%@ page import="com.tothenew.pms.User" %>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <!-- You'll want to use a responsive image option so this logo looks good on devices - I recommend using something like retina.js (do a quick Google search for it and you'll find it) -->
            <a class="navbar-brand" href="#/">Performance Monitoring System</a>
        </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
        <sec:ifLoggedIn>
            <div class="collapse navbar-collapse navbar-ex1-collapse">

                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#/services">User</a>
                    </li>
                    <li><a href="#/pricing">Pricing Table</a>
                    </li>
                    <li><a href="#/about">About</a>
                    </li>
                    <li><a href="#/contact">Contact</a>
                    </li>
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown">

                            <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="#/blog">Profile</a>
                            </li>
                            <li><g:link controller="logout">Signout</g:link>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </sec:ifLoggedIn>
    <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>