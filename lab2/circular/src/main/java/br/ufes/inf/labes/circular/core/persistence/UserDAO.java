package br.ufes.inf.labes.circular.core.persistence;

import br.ufes.inf.labes.circular.core.domain.User;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseDAO;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.MultiplePersistentObjectsFoundException;
import br.ufes.inf.labes.jbutler.ejb.persistence.exceptions.PersistentObjectNotFoundException;
import jakarta.ejb.Local;

@Local
public interface UserDAO extends BaseDAO<User> {
    public default User retrieveByEmail(String email) throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
        return null;
    }
}
