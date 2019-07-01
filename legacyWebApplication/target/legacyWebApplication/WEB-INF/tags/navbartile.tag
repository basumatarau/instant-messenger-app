<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@ attribute name="command" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="label" required="true" rtexprvalue="true" type="java.lang.String" %>

<%
    if (command.equalsIgnoreCase(request.getParameter("command"))){
        request.setAttribute("menuClassActive"," class='nav-item active'");
    }else{
        request.setAttribute("menuClassActive"," class='nav-item'");
    }
%>
<li ${menuClassActive}>
    <a class="nav-link" href="<%=request.getContextPath()%>/q?command=${command}">${label}</a>
</li>
