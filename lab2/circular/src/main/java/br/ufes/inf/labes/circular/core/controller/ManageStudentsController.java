package br.ufes.inf.labes.circular.core.controller;

import br.ufes.inf.labes.circular.core.application.ManageStudentsService;
import br.ufes.inf.labes.circular.core.domain.Student;
import br.ufes.inf.labes.jbutler.ejb.application.CrudService;
import br.ufes.inf.labes.jbutler.ejb.controller.CrudController;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class ManageStudentsController extends CrudController<Student> {
    @EJB
    private ManageStudentsService manageStudentsService;

    @Override
    protected CrudService<Student> getCrudService() {
        return manageStudentsService;
    }
}

