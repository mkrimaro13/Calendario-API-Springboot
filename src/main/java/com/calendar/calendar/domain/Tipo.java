package com.calendar.calendar.domain;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Tipo")
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los campos
public class Tipo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Tipo_secuencia")
    @GenericGenerator(name = "Tipo_secuencia", strategy = "increment")
    private long id;

    @Column(name = "tipo")
    private String tipo;

    public Tipo(long id) {
        this.id = id;
    }
}
