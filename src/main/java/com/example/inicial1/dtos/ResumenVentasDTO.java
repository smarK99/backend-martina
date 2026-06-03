package com.example.inicial1.dtos;

//Metrica: Obtener Monto total Ventas
//Devuelve el monto total de ventas de los ultimos 30 dias
//y el total de los pedidos realizados en ese periodo
public interface ResumenVentasDTO {
    Double getTotalVentas();
    Integer getTotalPedidos();
}
