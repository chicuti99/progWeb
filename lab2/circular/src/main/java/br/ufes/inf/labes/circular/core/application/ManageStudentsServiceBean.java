package br.ufes.inf.labes.circular.core.application;


import br.ufes.inf.labes.circular.core.domain.Student;
import br.ufes.inf.labes.circular.core.persistence.StudentDAO;
import br.ufes.inf.labes.jbutler.ejb.application.CrudServiceImpl;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseDAO;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
@PermitAll
public class ManageStudentsServiceBean extends CrudServiceImpl<Student>
        implements ManageStudentsService {
    @EJB
    private StudentDAO studentDAO;

    @Override
    public BaseDAO<Student> getDAO() {
        return studentDAO;
    }
}

