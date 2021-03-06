jQuery(function($) {

  var query = null;

  /* Formulario de filtros de búsqueda */

  $('#formBusqueda').submit(function() {
    query = {
      // TODO,
      firstResult: 1,
      maxResults: 10
    };
    if (validarFiltros()) enviarFiltros();
  });

  function validarFiltros() {
    if (!query.nombre) {
      // showMessage();
      return false;
    }
    return true;
  }

  function enviarFiltros() {
    $.post('/admin/alumnos/filtro', query, function(response) {
      if (response.error) {
        showErrorMessage(response.mensaje);
      } else {
        // TODO
      }
    });
  }

  /* Al inicio del programa, se enviarán los filtros de búsqueda por defecto */

  $('#formBusqueda').submit();

});
