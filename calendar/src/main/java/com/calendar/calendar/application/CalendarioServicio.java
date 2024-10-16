package com.calendar.calendar.application;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calendar.calendar.core.interfaces.repositories.ICalendarioRepositorio;
import com.calendar.calendar.core.interfaces.services.ICalendarioServicio;
import com.calendar.calendar.domain.Calendario;
import com.calendar.calendar.domain.Tipo;
import com.calendar.calendar.domain.DTOs.FestivoDto;

@Service
public class CalendarioServicio implements ICalendarioServicio {
    @Autowired
    private ICalendarioRepositorio calendarioRepositorio;

    @Autowired
    private FestivoCliente cliente;

    private List<Calendario> getDiasAño(List<Calendario> festivos, List<Calendario> nofestivos) {
        List<Calendario> fechas = new ArrayList<>();
        fechas.addAll(festivos);
        fechas.addAll(nofestivos);
        // El comparador permite indicar un campo del objeto por el cual se va a
        // realizar el filtro y ordenamiento.
        fechas.sort(Comparator.comparing(Calendario::getFecha));
        // Ejemplo por si quisiera comparar por el objeto anidado "tipo" ->
        // calendarios.sort(Comparator.comparing(c -> c.getTipo().getTipo())); //
        // Programmación funcional, not yet.
        // Para ordenar en orden invertido o descendente se debe agregar .reversed()
        return fechas;
    }

    private List<Calendario> crearFestivos(int año) {
        try {
            // Obtengo los festivos en base a la api de festivos y crea los objetos de la
            // clase Calendario.

            // Cambios respecto a la v1, en lugar de tener una lista LocalDate, se crea una
            // lista de FestivoDto, y se accede al atributo con el getter.
            List<FestivoDto> festivos = cliente.obtenerFestivos(año);
            // La lógica para crear los festivos es la misma, cambia la fuente de los datos.

            List<Calendario> diasCalendario = new ArrayList<>();
            for (FestivoDto festivo : festivos) {
                LocalDate fecha = festivo.getFecha();
                String diasemana = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es"));
                Calendario calendario = new Calendario(0, fecha, new Tipo(3),
                        diasemana);
                diasCalendario.add(calendario);
            }
            return diasCalendario;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private List<Calendario> crearNoFestivos(int año) {
        try {
            // Obtengo el resto de días del año en base a los días festivos, es para no
            // tenerlos repetidos, ni tener que crear objetos duplicados de festivos.
            List<Calendario> diasCalendario = new ArrayList<>();
            List<Calendario> diasFestivos = crearFestivos(año);
            // String[] fds = { "sábado", "domingo" };
            // List<LocalDate> festivos = diasFestivos.stream()
            // .map(Calendario::getFecha) // Mapea cada objeto Calendario a su atributo
            // fecha
            // .collect(Collectors.toList()); // Colecta los resultados en una lista
            List<LocalDate> festivos = new ArrayList<>();
            for (Calendario dia : diasFestivos) {
                festivos.add(dia.getFecha()); // Se obtiene la fecha de tipo LocalDate y se agrega a la lista.
            }
            // Se sigue el mismo flujo de la v1
            List<LocalDate> noFestivos = new ArrayList<>();
            LocalDate fecha = LocalDate.of(año, 1, 1);
            LocalDate finAño = LocalDate.of(año, 12, 31);
            do {
                if (!festivos.contains(fecha)) {
                    noFestivos.add(fecha);
                }
                fecha = fecha.plusDays(1); // El método retorna un nuevo objeto no modifica el existente, se debe
                                           // reasignar
            } while (!fecha.isEqual(finAño));
            for (LocalDate dia : noFestivos) {
                String diasemana = dia.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es"));
                // for (String diafds : fds) {
                if (diasemana.equals("sábado") || diasemana.equals("domingo")) {
                    Calendario calendario = new Calendario(0, dia, new Tipo(2),
                            diasemana);
                    diasCalendario.add(calendario);
                } else {
                    Calendario calendario = new Calendario(0, dia, new Tipo(1),
                            diasemana);
                    diasCalendario.add(calendario);
                }
            }
            // }
            return diasCalendario;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean agregarCalendario(int año) {
        try {
            if (!calendarioRepositorio.findAll().isEmpty()) {
                calendarioRepositorio.deleteAll();
            }
            List<Calendario> festivos = crearFestivos(año);
            List<Calendario> nofestivos = crearNoFestivos(año);
            List<Calendario> calendario = getDiasAño(festivos, nofestivos);
            calendarioRepositorio.saveAll(calendario);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<Calendario> listarCalendario(int año) {
        return calendarioRepositorio.findAll();
    }
}