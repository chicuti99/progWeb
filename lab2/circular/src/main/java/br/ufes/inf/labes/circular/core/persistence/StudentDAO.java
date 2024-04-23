package br.ufes.inf.labes.circular.core.persistence;

import br.ufes.inf.labes.circular.core.domain.Student;
import br.ufes.inf.labes.jbutler.ejb.persistence.BaseDAO;
import jakarta.ejb.Local;

@Local
public interface StudentDAO extends BaseDAO<Student> {
}
