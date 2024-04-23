package br.ufes.inf.labes.circular.core.controller;

import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import br.ufes.inf.labes.jbutler.ejb.controller.CrudController;
import br.ufes.inf.labes.circular.core.application.ManageWorkshopsService;
import br.ufes.inf.labes.circular.core.domain.Workshop;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class ManageWorkshopsController extends CrudController<Workshop> {
    @EJB
    private ManageWorkshopsService manageWorkshopsService;

    @Override
    protected CrudService<Workshop> getCrudService() {
        return manageWorkshopsService;
    }
}