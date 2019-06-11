<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<%@ taglib prefix="imtags" tagdir="/WEB-INF/tags" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="<%=request.getContextPath()%>">Home</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#navbarNav" aria-controls="navbarNav"
            aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse justify-content-between"
         id="navbarNav">
        <ul class="navbar-nav mr-auto">
            <% if (session.getAttribute("user") != null) { %>
                <imtags:navbartile command="Chat" label="messaging"/>
                <imtags:navbartile command="ContactList" label="contacts"/>
                <imtags:navbartile command="UserProfile" label="profile"/>
            <% } %>
        </ul>

        <ul class="navbar-nav">
            <li>
                <span class="navbar-text">
			        <% if (session.getAttribute("user") != null) { %>
                        <imtags:navbartile command="Logination" label="logout"/>
                    <% } else {%>
                        <imtags:navbartile command="Signup" label="sign-up"/>
                        <imtags:navbartile command="Logination" label="login"/>
                    <% } %>
			    </span>
            </li>
        </ul>
    </div>
</nav>
