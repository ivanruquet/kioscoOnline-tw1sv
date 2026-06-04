package com.tallerwebi.dominio.Usuario;

import com.tallerwebi.dominio.SubidaDeImgs.ServicioImagenes;
import com.tallerwebi.dominio.excepcion.NoSePudoGuardarInformacionException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario {

  RepositorioUsuario repositorioUsuario;
  ServicioImagenes servicioImagenes;

  @Autowired
  public ServicioUsuarioImpl(
    RepositorioUsuario repositorioUsuario,
    ServicioImagenes servicioImagenes
  ) {
    this.repositorioUsuario = repositorioUsuario;
    this.servicioImagenes = servicioImagenes;
  }

  @Override
  public void actualizarMail(Long id, String mailNuevo) {
    Usuario usuario = repositorioUsuario.buscarUsuarioPorId(id);
    usuario.setEmail(mailNuevo);
    try {
      repositorioUsuario.modificar(usuario);
    } catch (Exception e) {
      throw new NoSePudoGuardarInformacionException("No se pudo guardar el nuevo mail ", e);
    }
  }

  @Override
  public void actualizarCelular(Long id, Long celular) {
    Usuario usuario = repositorioUsuario.buscarUsuarioPorId(id);
    usuario.setCelular(celular);
    try {
      repositorioUsuario.modificar(usuario);
    } catch (Exception e) {
      throw new NoSePudoGuardarInformacionException("No se pudo guardar el nuevo celular ", e);
    }
  }

  @Override
  public void actualizarFoto(Long id, MultipartFile fotoPerfil) {
    try {
      Usuario usuario = repositorioUsuario.buscarUsuarioPorId(id);

      //      String nombreFoto = fotoPerfil.getOriginalFilename();
      //      Path rutaCarpeta = Paths.get("src/main/webapp/resources/core/imagenes/img_Perfiles");
      //
      //      Files.createDirectories(rutaCarpeta);
      //
      //      Files.copy(
      //        fotoPerfil.getInputStream(),
      //        rutaCarpeta.resolve(nombreFoto),
      //        StandardCopyOption.REPLACE_EXISTING
      //      );
      //      String rutaGuardarEnBD = "/spring/imagenes/img_Perfiles/" + nombreFoto;
      String rutaGuardarEnHosting = servicioImagenes.subirImagen(
        fotoPerfil,
        "KionetTWI/img_perfiles"
      );

      usuario.setFotoPerfil(rutaGuardarEnHosting);

      repositorioUsuario.modificar(usuario);
    } catch (Exception e) {
      throw new NoSePudoGuardarInformacionException(
        "No se pudo guardar la nueva foto de perfil",
        e
      );
    }
  }

  @Override
  public Usuario buscarPorId(Long id) {
    return repositorioUsuario.buscarUsuarioPorId(id);
  }
}
