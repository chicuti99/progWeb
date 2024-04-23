package br.ufes.inf.labes.circular.core.application;

import br.ufes.inf.labes.circular.core.application.exceptions.LoginFailedException;
import br.ufes.inf.labes.circular.core.domain.User;
import br.ufes.inf.labes.circular.core.persistence.UserDAO;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBTransactionRolledbackException;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stateless session bean that implements the Login use case. See its local EJB interface for more
 * information.
 *
 * @author <a href="http://labes.inf.ufes.br/equipe/vitor-souza/">Vítor E. Silva Souza</a>
 * @see LoginService
 */
@Stateless
@PermitAll
public class LoginServiceBean implements LoginService {
    /** Logger for this class. */
    private static final Logger logger = Logger.getLogger(LoginServiceBean.class.getCanonicalName());

    /** The Jakarta Security password hash generator. */
    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private Pbkdf2PasswordHash passwordHash;

    /**
     * The Academic DAO is used to retrieve the academic that is trying to log in to check her
     * password and update the last login date when an academic successfully logs in.
     */
    @EJB
    private UserDAO userDAO;

    /** The EJB session context, used to retrieve the user that logged in via Jakarta Security. */
    @Resource
    private SessionContext sessionContext;

    @Override
    public void login(String username, String password) throws LoginFailedException {
        try {
            // Obtains the user given the e-mail address (that serves as username).
            logger.log(Level.FINER, "Authenticating user with username \"{0}\"...", username);
            User user = userDAO.retrieveByEmail(username);

            // Checks if the passwords match.
            if ((user.getPassword() != null) && passwordHash.verify(password.toCharArray(),
                    user.getPassword())) {
                logger.log(Level.FINEST, "Passwords match for user \"{0}\".", username);

                // Login successful. The retrieved user is the current user.
                logger.log(Level.FINE, "Academic \"{0}\" successfully logged in.", username);

                // Registers the user login.
                LocalDateTime now = LocalDateTime.now();
                logger.log(Level.FINER,
                        "Setting last login date for academic with username \"{0}\" as \"{1}\"...",
                        new Object[] {user.getEmail(), now});
                userDAO.save(user);
            } else {
                // Passwords don't match.
                logger.log(Level.INFO, "Academic \"{0}\" not logged in: password didn't match.", username);
                throw new LoginFailedException(LoginFailedException.LoginFailedReason.INCORRECT_PASSWORD);
            }
        } catch (PersistentObjectNotFoundException e) {
            // No academic was found with the given username.
            logger.log(Level.INFO,
                    "User \"{0}\" not logged in: no registered academic found with given username.",
                    username);
            throw new LoginFailedException(e, LoginFailedException.LoginFailedReason.UNKNOWN_USERNAME);
        } catch (MultiplePersistentObjectsFoundException e) {
            // Multiple academics were found with the same username.
            logger.log(Level.WARNING,
                    "User \"{0}\" not logged in: there are more than one registered academic with the given username.",
                    username);
            throw new LoginFailedException(e, LoginFailedException.LoginFailedReason.MULTIPLE_USERS);
        } catch (EJBTransactionRolledbackException e) {
            // Unknown original cause. Throw the EJB exception.
            logger.log(Level.WARNING, "User \"" + username + "\" not logged in: unknown cause.", e);
            throw e;
        }
    }

    @Override
    public User getCurrentUser() {
        try {
            return userDAO.retrieveByEmail(sessionContext.getCallerPrincipal().getName());
        } catch (PersistentObjectNotFoundException | MultiplePersistentObjectsFoundException e) {
            return null;
        }
    }
}