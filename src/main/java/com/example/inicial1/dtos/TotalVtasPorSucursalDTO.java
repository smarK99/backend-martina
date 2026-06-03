package com.example.inicial1.dtos;

//Se usa el dto como interfaz por que lo necesitamos asi ya que
//SQL nativo no puede usar una clase JAVA directamente en el repo, debemos usar una interfaz
//Si quisieramos usar el dto como clase deberiamos hacer la consulta con JPQL
public interface TotalVtasPorSucursalDTO {

    // Los nombres de estos métodos DEBEN coincidir con los alias ("AS ...") del SELECT
    String getNombreSucursal();

    Double getMontoTotalVentas();
}
