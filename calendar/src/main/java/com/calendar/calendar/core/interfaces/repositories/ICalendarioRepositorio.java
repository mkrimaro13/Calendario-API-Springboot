package com.calendar.calendar.core.interfaces.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calendar.calendar.domain.Calendario;

@Repository
public interface ICalendarioRepositorio extends JpaRepository<Calendario, Long>{

}
