package br.ufes.inf.labes.circular.core.application;

import br.ufes.inf.labes.circular.core.domain.Workshop;
import br.ufes.inf.labes.circular.core.persistence.WorkshopDAO;
import br.ufes.inf.labes.jbutler.ejb.application.CrudServiceImpl;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseDAO;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
@PermitAll
public class ManageWorkshopsServiceBean extends CrudServiceImpl<Workshop>
        implements ManageWorkshopsService {
    @EJB
    private WorkshopDAO workshopDAO;

    @Override
    public BaseDAO<Workshop> getDAO() {
        return workshopDAO;
    }
}