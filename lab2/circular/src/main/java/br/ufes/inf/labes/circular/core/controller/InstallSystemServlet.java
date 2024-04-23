package br.ufes.inf.labes.circular.core.controller;

import br.ufes.inf.labes.circular.core.application.ConfigureSystemService;
import br.ufes.inf.labes.circular.core.application.SystemAlreadyInstalledException;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "InstallSystemServlet", urlPatterns = {"/install"})
public class InstallSystemServlet extends HttpServlet {

    @EJB
    ConfigureSystemService configureSystemService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        try {
            configureSystemService.installSystem();
        } catch (SystemAlreadyInstalledException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect(request.getContextPath() + "/login.xhtml");
    }
}
