package br.ufes.inf.labes.circular.core.application;

import br.ufes.inf.labes.circular.core.domain.Workshop;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import jakarta.ejb.Local;

@Local
public interface ManageWorkshopsService extends CrudService<Workshop> {
}