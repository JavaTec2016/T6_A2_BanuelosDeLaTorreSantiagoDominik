package controller;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import entities.Alumno;

@Dao
public interface AlumnoDAO {
    //------Altas-------
    @Insert
    public void agregarAlumno(Alumno alumno);
    @Insert
    public void agregarAlumnos(Alumno ...alumnos);
    //------Bajas-------
    @Delete
    public void eliminarAlumno(Alumno alumno);
    @Query("DELETE FROM alumno WHERE numControl=:nc")
    public void eliminarAlumnoPorNumControl(String nc);
    //-----Cambios------
    @Query("UPDATE alumno SET nombre= :n, edad= :e WHERE numControl= :nc")
    public void actualizarAlumnoPorNumControl(String n, byte e, String nc);
    //----Consultas-----
    @Query("SELECT * FROM alumno")
    public List<Alumno> buscarAlumnos();
    @Query("SELECT * FROM alumno WHERE numControl= :nc")
    public List<Alumno> buscarAlumnoPorNumControl(String nc);
    @Query("SELECT * FROM alumno WHERE numControl LIKE :nc ")
    public List<Alumno> filtrarAlumnosPorNumControl(String nc);
    @Query("SELECT * FROM alumno WHERE nombre= :n")
    public List<Alumno> consultarAlumnoPorNombre(String n);
}
