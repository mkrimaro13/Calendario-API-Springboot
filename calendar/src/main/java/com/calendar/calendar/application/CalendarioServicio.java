package com.calendar.calendar.application;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calendar.calendar.core.interfaces.repositories.ICalendarioRepositorio;
import com.calendar.calendar.core.interfaces.services.ICalendarioServicio;
import com.calendar.calendar.domain.Calendario;
import com.calendar.calendar.domain.Tipo;
import com.calendar.calendar.utils.HttpHandler;

@Service
public class CalendarioServicio implements ICalendarioServicio {
    @Autowired
    private ICalendarioRepositorio calendarioRepositorio;

    private List<LocalDate> getFestivos(int año) {
        try {
            HttpHandler handler = new HttpHandler("http://localhost:3030", "/festivos/" + año);
            String[] arr_fechas_tmp = handler.getRequest().replace("[", "").replace("]", "").replace("\"", "")
                    .split(","); // Si quita caracteres comodines.
            List<LocalDate> fechas = new ArrayList<>();
            for (String fecha : arr_fechas_tmp) {
                fechas.add(LocalDate.parse(fecha));
            }
            return fechas;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
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
            List<LocalDate> festivos = getFestivos(año);
            List<Calendario> diasCalendario = new ArrayList<>();
            for (LocalDate festivo : festivos) {
                String diasemana = festivo.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es"));
                Calendario calendario = new Calendario(0, festivo, new Tipo(3),
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
            // String[] fds = { "sábado", "domingo" };
            List<LocalDate> festivos = getFestivos(año);
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
    public List<LocalDate> listarFestivos(int año) {
        try {
            return getFestivos(año);
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