package br.ufes.inf.labes.circular.core.application;

import br.ufes.inf.labes.circular.core.application.exceptions.LoginFailedException;
import br.ufes.inf.labes.circular.core.domain.User;
import jakarta.ejb.Local;

import java.io.Serializable;

/**
 * Local EJB interface for the service that implements authentication (login), allowing any academic
 * to authenticate herself and log in the system.
 * <p>
 * This service also provides the logged-in user to other beans that may need it.
 *
 * @author <a href="http://labes.inf.ufes.br/equipe/vitor-souza/">Vítor E. Silva Souza</a>
 * @see LoginServiceBean
 */
@Local
public interface LoginService extends Serializable {
    /**
     * Authenticates a user given her username and password. If the user is correctly authenticated,
     * she should be available as an Academic object through the getCurrentUser() method.
     *
     * @param username The username that identifies the user in the system.
     * @param password The password that authenticates the user.
     * @throws LoginFailedException If the username is unknown or the password is incorrect.
     */
    void login(String username, String password) throws LoginFailedException;

    /**
     * Obtains the currently logged-in user.
     *
     * @return The Academic object that represents the user that is currently logged in.
     */
    User getCurrentUser();
}
