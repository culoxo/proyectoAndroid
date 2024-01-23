package com.example.proyectoerp.Interfaces;

import com.example.proyectoerp.dto.ClienteDTO;
import com.example.proyectoerp.dto.FacturaDTO;
import com.example.proyectoerp.dto.ServicioDTO;
import com.example.proyectoerp.dto.UsuarioDTO;
import com.example.proyectoerp.model.Cliente;
import com.example.proyectoerp.model.Factura;
import com.example.proyectoerp.model.Servicio;
import com.example.proyectoerp.model.Usuario;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CRUDInterface {

    @GET("api/v1/clientes")
    Call<List<Cliente>> getAllClientes();

    @GET("api/v1/clientes/{id}")
    Call<Cliente> getOneCliente(@Path("id") Long id);

    @POST("api/v1/clientes")
    Call<Cliente> createCliente(@Body ClienteDTO clienteDto);

    @PUT("api/v1/clientes/{id}")
    Call<Cliente> editCliente(@Path("id") Long id, @Body ClienteDTO clienteDTO);

    @DELETE("api/v1/clientes/{id}")
    Call<Cliente> deleteCliente(@Path("id") Long id);



    @GET("api/v1/servicios")
    Call<List<Servicio>> getAllServicios();

    @GET("api/v1/servicios/{id}")
    Call<Servicio> getOneServicio(@Path("id") Long id);

    @POST("api/v1/servicios")
    Call<Servicio> createServicio(@Body ServicioDTO servicioDto);

    @PUT("api/v1/servicios/{id}")
    Call<Servicio> editServicio(@Path("id") Long id, @Body ServicioDTO servicioDTO);

    @DELETE("api/v1/servicios/{id}")
    Call<Servicio> deleteServicio(@Path("id") Long id);


    @GET("api/v1/usuarios")
    Call<List<Usuario>> getAllUsuarios(
            @Query("USERNAME") String username,
            @Query("PASSWORD") String password,
            @Query("active") Boolean active
    );

    @GET("api/v1/usuarios/{id}")
    Call<Usuario> getOneUsuario(@Path("id") Long id);

    @POST("api/v1/usuarios")
    Call<Usuario> createUsuario(@Body UsuarioDTO usuarioDto);

    @PUT("api/v1/usuarios/{id}")
    Call<Usuario> editUsuario(@Path("id") Long id, @Body UsuarioDTO usuarioDTO);

    @DELETE("api/v1/usuarios/{id}")
    Call<Usuario> deleteUsuario(@Path("id") Long id);



    @GET("api/v1/factura")
    Call<List<Factura>> getAllFacturas();
    @GET("api/v1/factura/{id}")
    Call<Factura> getOneFactura(@Path("id") Long id);

    @POST("api/v1/factura")
    Call<Factura> createFactura(@Body FacturaDTO facturaDto);

    @PUT("api/v1/factura/{id}")
    Call<Factura> editFactura(@Path("id") Long id, @Body FacturaDTO facturaDTO);

    @DELETE("api/v1/factura/{id}")
    Call<Factura> deleteFactura(@Path("id") Long id);

}