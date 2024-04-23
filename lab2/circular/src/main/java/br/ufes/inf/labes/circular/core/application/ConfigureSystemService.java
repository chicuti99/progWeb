package br.ufes.inf.labes.circular.core.application;

import jakarta.ejb.Local;

@Local
public interface ConfigureSystemService {
    public void installSystem() throws SystemAlreadyInstalledException;
}