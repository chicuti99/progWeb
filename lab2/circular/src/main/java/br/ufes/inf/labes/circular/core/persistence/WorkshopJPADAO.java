package br.ufes.inf.labes.circular.core.persistence;

import br.ufes.inf.labes.circular.core.domain.Workshop;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseJPADAO;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class WorkshopJPADAO extends BaseJPADAO<Workshop> implements WorkshopDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}