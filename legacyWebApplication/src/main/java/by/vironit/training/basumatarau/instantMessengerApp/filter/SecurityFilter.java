package by.vironit.training.basumatarau.instantMessengerApp.filter;

import by.vironit.training.basumatarau.instantMessengerApp.controller.SessionHandler;
import by.vironit.training.basumatarau.instantMessengerApp.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SecurityFilter implements Filter {

    private Set<String> adminOnlyCmds = new HashSet<>();
    private Set<String> unsecuredCmds = new HashSet<>();
    private Set<String> authUserOnly = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        unsecuredCmds.addAll(Set.of(filterConfig.getInitParameter("unsecuredCommands").split(",")));

        authUserOnly.addAll(unsecuredCmds);
        authUserOnly.addAll(Set.of(filterConfig.getInitParameter("userAccess").split(",")));

        adminOnlyCmds.addAll(unsecuredCmds);
        adminOnlyCmds.addAll(authUserOnly);
        adminOnlyCmds.addAll(Set.of(filterConfig.getInitParameter("adminAccess").split(",")));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final User authorizedUser = SessionHandler
                .getAuthorizedUser(request);

        final String command = servletRequest.getParameter("command");

        if (command == null || unsecuredCmds.contains(command)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (authUserOnly.contains(command) &&
                authorizedUser!=null &&
                authorizedUser.getRole().getName().equals("USER")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (adminOnlyCmds.contains(command) ||
                authorizedUser!=null &&
                authorizedUser.getRole().getName().equals("ADMIN")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect("q?command=Logination");
        }
    }

    @Override
    public void destroy() {
        adminOnlyCmds = null;
        unsecuredCmds = null;
        authUserOnly = null;
    }
}
