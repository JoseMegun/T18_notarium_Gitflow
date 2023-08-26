<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es-pe" dir="ltr">
<head>
<!-- Required meta tags -->
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<link 
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<title>NOTARIUM</title>

<script src="https://unpkg.com/xlsx/dist/xlsx.full.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.9.3/html2pdf.bundle.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.4.0/jspdf.umd.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.5.0-beta4/html2canvas.min.js"></script>

<!-- Llamar automaticamente la lista de apoderado -->
<script>window.onload = function() {
    fnBtnBuscar();
    };
</script>

<style>
.sidebar {
	position: fixed;
	top: 0;
	bottom: 0;
	left: 0;
	z-index: 100;
	padding: 48px 20px;
	background-color: #5A57FF;
}

.sidebar .title {
	font-size: 1.5rem;
	font-weight: bold;
	margin-bottom: 20px;
	color: #fff;
}

.nav-link {
	color: #fff;
}

.content {
	margdding: 20px;
}

@media ( max-width : 767.98px) {
	.sidebar {
		position: static;
		padding: 20px;
		margin-bottom: 20px;
	}
	.content {
		margin-left: 0;
		padding: 20px;
	}
}

.col-md-9 {
	margin: 1% 15%;
	flex: 0 0 auto;
	width: 90%;
}

.pagination-container {
	display: flex;
	justify-content: center;
	margin-top: 20px;
}

.pagination-button {
	padding: 8px 12px;
	margin: 0 5px;
	border: 1px solid #ccc;
	background-color: #fff;
	color: #333;
	cursor: pointer;
	text-decoration: none;
	transition: background-color 0.3s, color 0.3s;
}

.pagination-button:hover {
	background-color: #eaeaea;
}

.pagination-button.current {
	background-color: #007bff;
	color: #fff;
}

.btn-reactivate {
	background-color: transparent;
	border: none;
	color: #4285F4;
	cursor: pointer;
	font-size: 14px;
	outline: none;
	padding: 0;
}

.btn-reactivate:hover {
	text-decoration: underline;
}
</style>

</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-3">
				<jsp:include page="menu.jsp"></jsp:include>
			</div>
			<div class="col-md-9">
				<h1>CRUD DE PROFESORES</h1>
				<!-- Card de datos de entrada -->
				<div class="card" lang="es-ES">
				<div class="card-header">Criterios de búsqueda</div>
				<div class="card-body">
				<form method="post" action="teacherBuscar">
					<div class="row">
								<div class="col-md-4 mb-3">
									<input type="text" class="form-control" id="names" name="names"
										placeholder="Nombres">
								</div>
								<div class="col-md-4 mb-3">
									<input type="text" class="form-control" id="surname"
										name="surname" placeholder="Apellidos">
								</div>
								<div class="col-md-1 mb-2">
									<button type="button" class="btn btn-outline-primary"
										id="btnBuscar" name="btnBuscar">Buscar</button>
								</div>
								<div class="col-md-1 mb-2">
									<button type="button" class="btn btn-outline-primary"
										id="btnBuscarInactivo" name="btnBuscarInactivo">Inactivo</button>
								</div>
								<div class="col-md-1 mb-2">
									<button type="button" class="btn btn-outline-primary"
										id="btnNuevo" name="btnNuevo">Nuevo</button>
								</div>
							</div>
						</form>
					</div>
				</div>

				<br />

				<div class="card text-center">
					<div class="card-header">Descargas</div>
					<div class="card-body">
						<button type="button" class="btn btn-outline-success"
							onclick="exportToExcel()">Exportar a XLSX</button>
						<button type="button" class="btn btn-outline-dark"
							onclick="exportToCSV()">Exportar a CSV</button>
						<button type="button" class="btn btn-outline-danger"
							onclick="exportToPDF()">Exportar a PDF</button>
					</div>
				</div>

<br>

				<!-- Card de resultados -->
				<div class="card" id="divResultado">
					<div class="card-header">
						Resultado <span id="teacherCount"></span>
					</div>
					<div class="card-body">
						<div class="table-responsive">
							<table class="table table-striped table-hover">
								<thead>
									<tr>
										<th class="text-center" lang="es">Id</th>
										<th class="text-center" lang="es">Nombre</th>
										<th class="text-center" lang="es">Apellido</th>
										<th class="text-center" lang="es">Dni</th>
										<th class="text-center" lang="es">Email</th>
										<th class="text-center" lang="es">Celular</th>
										<th class="text-center" lang="es">Direccion</th>
										<th class="text-center" lang="es">Distrito</th>
										<th class="text-center" lang="es">Estado</th>
										<th class="text-center">Acciones</th>
									</tr>
								</thead>
								<tbody id="TeacherTable">
								</tbody>
							</table>
						</div>
                        <div class="pagination-container">
							<a href="javascript:void(0);" class="pagination-button"
								id="previousPage">&laquo; Anterior</a> <span id="currentPage"
								class="pagination-button current"></span> <a
								href="javascript:void(0);" class="pagination-button"
								id="nextPage">Siguiente &raquo;</a> <a
								href="javascript:void(0);" class="pagination-button"
								id="btnMostrar">Mostrar Todos Los Activos</a><a
								href="javascript:void(0);" class="pagination-button"
								id="btnMostrarInactivo">Mostrar Todos Los Inactivos</a>
						</div>
					</div>
				</div>


		<!-- Formulario para insertar y eliminar registros -->
				<div class="card" id="divRegistro" style="display: none;">
					<div class="card-header" id="tituloRegistro">{action} TEACHER</div>
					<div class="card-body">
						<form id="teacherForm">
							<input type="hidden" id="action" name="action">
							<div class="form-row">
								<div class="col-md-6 mb-3">
									<label for="frmId_teacher">ID del profesor</label> <input
										type="text" class="form-control" id="frmId_teacher" 
										disabled="disabled">
								</div>
								<div class="col-md-6 mb-3">
									<label for="frmNames">Nombres</label> <input type="text"
										class="form-control" id="frmNames" name="frmNames" required
										pattern="[A-Za-zá-´z\s]+">
									<div class="valid-feedback">Campo válido.</div>
									<div class="invalid-feedback">Por favor, ingresa los
										nombres correctamente.</div>
								</div>
							</div>
							<div class="form-row">
								<div class="col-md-6 mb-3">
									<label for="frmSurname">Apellidos</label> <input type="text"
										class="form-control" id="frmSurname" name="frmSurname"
										required pattern="[A-Za-zá-´z\s]+">
									<div class="valid-feedback">Campo válido.</div>
									<div class="invalid-feedback">Por favor, ingresa los
										apellidos correctamente.</div>
								</div>
								<div class="col-md-6 mb-3">
									<label for="frmDNI">DNI</label> <input type="text"
										class="form-control" id="frmDNI" name="frmDNI" required
										pattern="[0-9]{8}">
									<div class="valid-feedback">Campo válido.</div>
									<div class="invalid-feedback">Por favor, ingresa un DNI
										válido (8 dígitos numéricos).</div>
								</div>
							</div>
							<div class="form-row">
								<div class="col-md-6 mb-3">
									<label for="frmEmail">Gmail</label> <input type="email"
										class="form-control" id="frmEmail" name="frmEmail" required>
									<div class="valid-feedback">Campo válido.</div>
									<div class="invalid-feedback">Por favor, ingresa un
										correo válido.</div>
								</div>
							</div>
							<div class="form-row">
								<div class="col-md-6 mb-3">
									<label for="frmCell_phone">Celular</label> <input type="tel"
										class="form-control" id="frmCell_phone" name="frmCell_phone"
										required pattern="[0-9]{9}">
									<div class="valid-feedback">Campo válido.</div>
									<div class="invalid-feedback">Por favor, ingresa un
										número de celular válido (9 dígitos numéricos).</div>
								</div>
								<div class="col-md-6 mb-3">
									<label for="frmDirections">Dirección</label> <input type="text"
										class="form-control" id="frmDirections" name="frmDirections"
										required>
									<div class="valid-feedback">Campo válido.</div>
									<div class="invalid-feedback">Por favor, ingresa la
										dirección correctamente.</div>
								</div>
								<div class="col-md-6 mb-3">
									<label for="frmUbigeous">Distrito</label> <input type="text"
										class="form-control" id="frmUbigeous" name="frmUbigeous" 
										pattern="[a-zA-Z0-9\s]+" required>
									<div class="valid-feedback">Todo bien</div>
									<div class="invalid-feedback">Es necesario el nombre del distrito de la dirección 
										y no se permiten símbolos</div>
								</div>
								<div class="col-md-6 mb-3">
									<label for="frmTea">Estado</label> <input type="text"
										class="form-control" id="frmTeacher_status" disabled="disabled">
								</div>
							</div>
							<button type="button" class="btn btn-primary" id="btnProcesar">Procesar</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

<!-- Bootstrap Bundle with Popper -->
<script src="https://unpkg.com/xlsx/dist/xlsx.full.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
crossorigin="anonymous"></script>

	<!-- Botones -->
	<script>
		// Constantes del CRUD
		const ACCION_NUEVO = "NUEVO";
		const ACCION_EDITAR = "EDITAR";
		const ACCION_ELIMINAR = "ELIMINAR";

		// Arreglo de registros
		let arreglo = [];

		// Función para inicializar los controles
		function initializeControls() {
			let btnBuscar = document.getElementById("btnBuscar");
			let btnBuscarInactivo = document.getElementById("btnBuscarInactivo");
			let btnNuevo = document.getElementById("btnNuevo");
			let btnProcesar = document.getElementById("btnProcesar");
			let btnMostrar = document.getElementById("btnMostrar")
			let btnMostrarInactivo = document.getElementById("btnMostrarInactivo")

			btnBuscar.addEventListener("click", fnBtnBuscar);
			btnBuscarInactivo.addEventListener("click", fnBtnBuscarInactivo);
			btnMostrar.addEventListener("click", fnBtnMostrar);
			btnMostrarInactivo.addEventListener("click", fnBtnMostrarInactivo);
			btnNuevo.addEventListener("click", fnBtnNuevo);
			btnProcesar.addEventListener("click", fnBtnProcesar);
		}

		// Función fnBtnNuevo
		function fnBtnNuevo() {
			// Restablecer el formulario
			resetForm();

			// Preparando el formulario
			document.getElementById("tituloRegistro").innerHTML = ACCION_NUEVO
					+ " REGISTRO";
			document.getElementById("action").value = ACCION_NUEVO;
			// Mostrar formulario
			document.getElementById("divResultado").style.display = "none";
			document.getElementById("divRegistro").style.display = "block";
		}

		// Función fnEditar
		function fnEditar(id_teacher) {
			// Verificar si id_teacher es nulo
			if (id_teacher === null) {
				console.log("Error: id_teacher es nulo");
				return;
			}

			// Restablecer el formulario
			resetForm();

			// Preparando el formulario
			document.getElementById("tituloRegistro").innerHTML = ACCION_EDITAR
					+ " REGISTRO";
			document.getElementById("action").value = ACCION_EDITAR;
			fnCargarForm(id_teacher);

			// Mostrar formulario
			document.getElementById("divResultado").style.display = "none";
			document.getElementById("divRegistro").style.display = "block";
			event.preventDefault(); // Evitar redireccionamiento del enlace
		}

		// Función fnEliminar
		function fnEliminar(id) {
			// Verificar si id es nulo
			if (id === null) {
				console.log("Error: id es nulo");
				return;
			}

			// Restablecer el formulario
			resetForm();

			// Preparando el formulario
			document.getElementById("tituloRegistro").innerHTML = ACCION_ELIMINAR
					+ " REGISTRO";
			document.getElementById("action").value = ACCION_ELIMINAR;
			fnCargarForm(id);
			// Mostrar formulario
			document.getElementById("divResultado").style.display = "none";
			document.getElementById("divRegistro").style.display = "block";
			event.preventDefault(); // Evitar redireccionamiento del enlace
		}

		function fnBtnProcesar() {
			  if (!document.getElementById("teacherForm").checkValidity()) {
			    // El formulario no es válido, mostrar mensajes de error
			    document.getElementById("teacherForm").classList.add("was-validated");
			    return;
			  }

			  // Preparar los datos
			  let datos = "action=" + document.getElementById("action").value;
			  datos += "&id_teacher=" + document.getElementById("frmId_teacher").value;
			  datos += "&names=" + document.getElementById("frmNames").value;
			  datos += "&surname=" + document.getElementById("frmSurname").value;
			  datos += "&dni=" + document.getElementById("frmDNI").value;
			  datos += "&email=" + document.getElementById("frmEmail").value;
			  datos += "&cell_phone=" + document.getElementById("frmCell_phone").value;
			  datos += "&directions=" + document.getElementById("frmDirections").value;
			  datos += "&ubigeous=" + document.getElementById("frmUbigeous").value;
			  datos += "&teacher_status=" + document.getElementById("frmTeacher_status").value;

			  // El envío con AJAX
			  let xhr = new XMLHttpRequest();
			  xhr.open("POST", "teacherProcesar", true);
			  xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			  xhr.onreadystatechange = function () {
			    if (xhr.readyState === 4 && xhr.status === 200) {
			      // La solicitud se completó correctamente
			      Swal.fire({
			        title: '¿Estás seguro?',
			        icon: 'warning',
			        showCancelButton: true,
			        confirmButtonColor: '#3085d6',
			        cancelButtonColor: '#d33',
			        confirmButtonText: 'Sí'
			      }).then((result) => {
			        if (result.isConfirmed) {
			          // Acción confirmada
			          Swal.fire(
			            '¡Enviado!',
			            'Los datos han sido enviados correctamente.',
			            'success'
			          ).then(() => {
			            // Redireccionar a la tabla o realizar alguna otra acción
			            window.location.href = "Teacher.jsp";
			          });
			          resetForm(); // Restablecer los campos del formulario
			        }
			      });
			    }
			  };
			  xhr.send(datos);
			}

		// Función fnBtnBuscar
		function fnBtnBuscar() {
		// Datos
		let names = document.getElementById("names").value;
		let surname = document.getElementById("surname").value;
		// Preparar la URL
		let url = "teacherBuscar2?names=" + encodeURIComponent(names)
			+ "&surname=" + encodeURIComponent(surname) + "&teacher_status='A'";
		// Llamada AJAX
		let xhttp = new XMLHttpRequest();
		xhttp.open("GET", url, true);
		xhttp.onreadystatechange = function() {
			if (this.readyState === 4 && this.status === 200) {
				let respuesta = xhttp.responseText;
				arreglo = JSON.parse(respuesta);
				let currentPage = 1;
				const rowsPerPage = 5;
				const totalRows = arreglo.length;
				const totalPages = Math.ceil(totalRows / rowsPerPage);
				function showCurrentPage() {
				const startIndex = (currentPage - 1) * rowsPerPage;
				const endIndex = startIndex + rowsPerPage;
				let TeacherTable = "";
				for (let i = startIndex; i < endIndex; i++) {
				const item = arreglo[i];
				if (item) {
					TeacherTable += "<tr>";
					TeacherTable += "<td class='text-center'>" + item.id_teacher + "</td>";
					TeacherTable += "<td class='text-center'>" + item.names + "</td>";
					TeacherTable += "<td class='text-center'>" + item.surname + "</td>";
					TeacherTable += "<td class='text-center'>" + item.dni + "</td>";
					TeacherTable += "<td class='text-center'>" + item.email + "</td>";
					TeacherTable += "<td class='text-center'>" + item.cell_phone + "</td>";
					TeacherTable += "<td class='text-center'>" + item.directions + "</td>";
					TeacherTable += "<td class='text-center'>" + item.ubigeous + "</td>";
					TeacherTable += "<td class='text-center'>" + item.teacher_status + "</td>";
					TeacherTable += "<td>";
					TeacherTable += "<a href='javascript:void(0);' onclick='fnEditar(" + item.id_teacher + ");'><i class='fas fa-pencil-alt'></i></a> ";
					TeacherTable += "<a href='javascript:void(0);' onclick='fnEliminar(" + item.id_teacher + ");'><i class='fas fa-trash'></i></a>";
					TeacherTable += "</td>";
					TeacherTable += "</tr>";
				}
			}
				document.getElementById("TeacherTable").innerHTML = TeacherTable;
				document.getElementById("currentPage").textContent = currentPage;
		}
			showCurrentPage();
			const previousButton = document.getElementById('previousPage');
			const nextButton = document.getElementById('nextPage');
			previousButton.addEventListener('click', function() {
			if (currentPage > 1) {
				currentPage--;
				showCurrentPage();
			}
		});
			nextButton.addEventListener('click', function() {
			if (currentPage < totalPages) {
				currentPage++;
				showCurrentPage();
			}
		});

			// Actualizar el conteo de profesores
			document.getElementById("teacherCount").innerText = "Total de profesores registrados: "
				+ arreglo.length;
			// Mostrar formulario
			document.getElementById("divResultado").style.display = "block";
			document.getElementById("divRegistro").style.display = "none";
			}
		};
	xhttp.send();
	}

		// Llamar a la función fnBtnBuscar al cargar la página
		window.addEventListener('DOMContentLoaded', function () {
		  fnBtnBuscar();
		});


		// Mostrar todos de Usuarios de Estado Activo
		function fnBtnMostrar() {
			// Datos
			let names = document.getElementById("names").value;
			let surname = document.getElementById("surname").value;
			// Preparar la URL
			let url = "teacherBuscar2?names=" + encodeURIComponent(names)
					+ "&surname=" + encodeURIComponent(surname) + "&teacher_status='A'";
			// Llamada AJAX
			let xhttp = new XMLHttpRequest();
			xhttp.open("GET", url, true);
			xhttp.onreadystatechange = function() {
				if (this.readyState === 4 && this.status === 200) {
					let respuesta = xhttp.responseText;
					arreglo = JSON.parse(respuesta);
					let TeacherTable = "";
					arreglo
							.forEach(function(item) {
								TeacherTable += "<tr>";
								TeacherTable += "<td class='text-center'>" + item.id_teacher + "</td>";
								TeacherTable += "<td class='text-center'>" + item.names + "</td>";
								TeacherTable += "<td class='text-center'>" + item.surname + "</td>";
								TeacherTable += "<td class='text-center'>" + item.dni + "</td>";
								TeacherTable += "<td class='text-center'>" + item.email + "</td>";
								TeacherTable += "<td class='text-center'>" + item.cell_phone + "</td>";
								TeacherTable += "<td class='text-center'>" + item.directions + "</td>";
								TeacherTable += "<td class='text-center'>" + item.ubigeous + "</td>";
								TeacherTable += "<td class='text-center'>" + item.teacher_status + "</td>";
								TeacherTable += "<td>";
								TeacherTable += "<a href='javascript:void(0);' onclick='fnEditar(" + item.id_teacher + ");'><i class='fas fa-pencil-alt'></i></a> ";
								TeacherTable += "<a href='javascript:void(0);' onclick='fnEliminar(" + item.id_teacher + ");'><i class='fas fa-trash'></i></a>";
								TeacherTable += "</td>";
								TeacherTable += "</tr>";
						});
							
					document.getElementById("TeacherTable").innerHTML = TeacherTable;

					// Mostrar formulario
					document.getElementById("divResultado").style.display = "block";
					document.getElementById("divRegistro").style.display = "none";

				}
			};
			xhttp.send();
		}

		//BUSCAR INACTIVOS

		function fnBtnBuscarInactivo() {
			// Datos
			let names = document.getElementById("names").value;
			let surname = document.getElementById("surname").value;
			// Preparar la URL
			let url = "teacherBuscarInactivos?names="
					+ encodeURIComponent(names) + "&surname="
					+ encodeURIComponent(surname) + "&teacher_status='I'";
			// Llamada AJAX
			let xhttp = new XMLHttpRequest();
			xhttp.open("GET", url, true);
			xhttp.onreadystatechange = function() {
				if (this.readyState === 4 && this.status === 200) {
					let respuesta = xhttp.responseText;
					arreglo = JSON.parse(respuesta);
					let currentPage = 1;
					const rowsPerPage = 5;
					const totalRows = arreglo.length;
					const totalPages = Math.ceil(totalRows / rowsPerPage);

					function showCurrentPage() {
						const startIndex = (currentPage - 1) * rowsPerPage;
						const endIndex = startIndex + rowsPerPage;

						let TeacherTable = "";
						for (let i = startIndex; i < endIndex; i++) {
							const item = arreglo[i];
							if (item) {
								TeacherTable += "<tr>";
								TeacherTable += "<td class='text-center'>" + item.id_teacher + "</td>";
								TeacherTable += "<td class='text-center'>" + item.names + "</td>";
								TeacherTable += "<td class='text-center'>" + item.surname + "</td>";
								TeacherTable += "<td class='text-center'>" + item.dni + "</td>";
								TeacherTable += "<td class='text-center'>" + item.email + "</td>";
								TeacherTable += "<td class='text-center'>" + item.cell_phone + "</td>";
								TeacherTable += "<td class='text-center'>" + item.directions + "</td>";
								TeacherTable += "<td class='text-center'>" + item.ubigeous + "</td>";
								TeacherTable += "<td class='text-center'>" + item.teacher_status + "</td>";
								TeacherTable += "<td>";
								TeacherTable += "<button class='btn-reactivate' onclick='fnReactivar("
										+ item.id_teacher + ");'>Reactivar</button>";
								TeacherTable += "</td>";
								TeacherTable += "</tr>";
							}
						}
						document.getElementById("TeacherTable").innerHTML = TeacherTable;
						document.getElementById("currentPage").textContent = currentPage;
					}

					showCurrentPage();

					const previousButton = document.getElementById('previousPage');
					const nextButton = document.getElementById('nextPage');

					previousButton.addEventListener('click', function() {
						if (currentPage > 1) {
							currentPage--;
							showCurrentPage();
						}
					});

					nextButton.addEventListener('click', function() {
						if (currentPage < totalPages) {
							currentPage++;
							showCurrentPage();
						}
					});

					// Actualizar el conteo de profesores
					document.getElementById("teacherCount").innerText = "Total de Profesores inactivos: "
							+ arreglo.length;

					// Mostrar formulario
					document.getElementById("divResultado").style.display = "block";
					document.getElementById("divRegistro").style.display = "none";
				}
			};

			xhttp.send();

		}
		function fnReactivar(id_teacher) {
			  Swal.fire({
			    title: '¿Estás seguro de reactivar este profesor?',
			    icon: 'warning',
			    showCancelButton: true,
			    confirmButtonColor: '#3085d6',
			    cancelButtonColor: '#d33',
			    confirmButtonText: 'Sí'
			  }).then((result) => {
			    if (result.isConfirmed) {
			      // Preparar la URL para la llamada AJAX
			      let url = "teacherProcesar?action=REACTIVAR&id_teacher=" + id_teacher;
			      // Llamada AJAX
			      let xhttp = new XMLHttpRequest();
			      xhttp.open("GET", url, true);
			      xhttp.onreadystatechange = function () {
			        if (this.readyState === 4 && this.status === 200) {
			          // Recargar la lista de profesores inactivos
			          fnBtnBuscarInactivo();
			          // Mostrar mensaje de éxito o error
			          Swal.fire({
			            icon: 'success',
			            title: 'Profesor reactivado',
			            text: this.responseText
			          });
			        }
			      };
			      xhttp.send();
			    }
			  });
			}


		// Mostrar todos de Usuarios de Estado Inactivo
		function fnBtnMostrarInactivo() {
			// Datos
			let names = document.getElementById("names").value;
			let surname = document.getElementById("surname").value;
			// Preparar la URL
			let url = "teacherBuscarInactivos?names="
					+ encodeURIComponent(names) + "&surname="
					+ encodeURIComponent(surname) + "&teacher_status='I'";
			// Llamada AJAX
			let xhttp = new XMLHttpRequest();
			xhttp.open("GET", url, true);
			xhttp.onreadystatechange = function() {
				if (this.readyState === 4 && this.status === 200) {
					let respuesta = xhttp.responseText;
					arreglo = JSON.parse(respuesta);
					let TeacherTable = "";
					arreglo
							.forEach(function(item) {
								TeacherTable += "<tr>";
								TeacherTable += "<td class='text-center'>" + item.id_teacher + "</td>";
								TeacherTable += "<td class='text-center'>" + item.names + "</td>";
								TeacherTable += "<td class='text-center'>" + item.surname + "</td>";
								TeacherTable += "<td class='text-center'>" + item.dni + "</td>";
								TeacherTable += "<td class='text-center'>" + item.email + "</td>";
								TeacherTable += "<td class='text-center'>" + item.cell_phone + "</td>";
								TeacherTable += "<td class='text-center'>" + item.directions + "</td>";
								TeacherTable += "<td class='text-center'>" + item.ubigeous + "</td>";
								TeacherTable += "<td class='text-center'>" + item.teacher_status + "</td>";
								TeacherTable += "<td>";
								TeacherTable += "<button class='btn-reactivate' onclick='fnReactivar("
									+ item.id_teacher + ");'>Reactivar</button>";
								TeacherTable += "</td>";
								TeacherTable += "</tr>";
							});
					document.getElementById("TeacherTable").innerHTML = TeacherTable;

					// Mostrar formulario
					document.getElementById("divResultado").style.display = "block";
					document.getElementById("divRegistro").style.display = "none";

				}
			};
			xhttp.send();
		}
		// Actualizar
		function fnCargarForm(id_teacher) {
			arreglo
					.forEach(function(item) {
						if (item.id_teacher == id_teacher) {
							document.getElementById("frmId_teacher").value = item.id_teacher;
							document.getElementById("frmNames").value = item.names;
							document.getElementById("frmSurname").value = item.surname;
							document.getElementById("frmDNI").value = item.dni;
							document.getElementById("frmEmail").value = item.email;
							document.getElementById("frmCell_phone").value = item.cell_phone;
							document.getElementById("frmDirections").value = item.directions;
							document.getElementById("frmUbigeous").value = item.ubigeous;
							document.getElementById("frmTeacher_status").value = item.teacher_status;
							
						}
					});
		}

		// Función para restablecer los campos del formulario
		function resetForm() {
			document.getElementById("teacherForm").reset();
		}

		// Evento de carga completa de la página
		window.onload = function() {
			// Inicializar los controles
			initializeControls();
		};

		//      <<< EXPORTACION DE CSV >>>       //

		//Funcion de Exportacion de datos a Excel
		function exportToExcel() {
			// Obtener los datos de la tabla
			let rows = document.querySelectorAll("#TeacherTable tr");
			// Crear una matriz de datos con las columnas deseadas
			let data = [];
			// Agregar los encabezados de columna
			data.push([ "ID", "NOMBRE", "APELLIDO", "DNI", "EMAIL", "CELULAR", "DIRECCION", 
				"DISTRITO", "ESTADO"  ]);
			rows
					.forEach(function(row) {
						let rowData = [];
						let columns = row
								.querySelectorAll("td:nth-child(1), td:nth-child(2), td:nth-child(3), td:nth-child(4), td:nth-child(5), td:nth-child(6), td:nth-child(7),td:nth-child(8), td:nth-child(9)"); // Incluir solo las columnas deseadas
						columns.forEach(function(column) {
							rowData.push(column.innerText);
						});
						data.push(rowData);
					});
			// Crear una hoja de cálculo de Excel
			let worksheet = XLSX.utils.aoa_to_sheet(data);
			// Crear un libro de Excel y agregar la hoja de cálculo
			let workbook = XLSX.utils.book_new();
			XLSX.utils.book_append_sheet(workbook, worksheet, "Profesores");
			// Guardar el archivo de Excel
			XLSX.writeFile(workbook, "reporteProfesores.xlsx");
		}

		//validaciones
		// Example starter JavaScript for disabling form submissions if there are invalid fields
		(function() {
			'use strict'

			// Fetch all the forms we want to apply custom Bootstrap validation styles to
			var forms = document.querySelectorAll('.needs-validation')

			// Loop over them and prevent submission
			Array.prototype.slice.call(forms).forEach(function(form) {
				form.addEventListener('submit', function(event) {
					if (!form.checkValidity()) {
						event.preventDefault()
						event.stopPropagation()
					}

					form.classList.add('was-validated')
				}, false)
			})
		})()

		//       <<< EXPORTACION DE PDF >>>       //

		// Escucha el evento submit del formulario
		function exportToPDF() {
			// Lógica para exportar la tabla a PDF
			var table = document.getElementById("TeacherTable");
			var exportOptions = {
				margin : [ 10, 10, 10, 10 ],
				filename : 'reporteProfesores.pdf',
				image : {
					type : 'jpeg',
					quality : 0.98
				},
				html2canvas : {
					scale : 2,
					logging : true,
					dpi : 192,
					letterRendering : true
				},
				jsPDF : {
					unit : 'mm',
					format : 'a4',
					orientation : 'portrait'
				}
			};

			html2pdf().set(exportOptions).from(table).save();
		}

		//       <<< EXPORTACION DE CSV >>>       //

		function exportToCSV() {
			var table = document.getElementById("TeacherTable");
			var rows = table.getElementsByTagName("tr");
			var csvContent = "";

			// Recorrer filas y columnas de la tabla
			for (var i = 0; i < rows.length; i++) {
				var cells = rows[i].getElementsByTagName("td");

				for (var j = 0; j < cells.length; j++) {
					var cellData = cells[j].innerText.replace(/(\r\n|\n|\r)/gm,
							""); // Eliminar saltos de línea
					csvContent += cellData + ","; // Agregar dato de celda al contenido CSV
				}

				csvContent += "\n"; // Agregar salto de línea al final de cada fila
			}

			// Crear un enlace de descarga del archivo CSV
			var link = document.createElement("a");
			link.setAttribute("href", "data:text/csv;charset=utf-8,"
					+ encodeURIComponent(csvContent));
			link.setAttribute("download", "reporteProfesores.csv");
			link.style.display = "none";
			document.body.appendChild(link);
			link.click();
			document.body.removeChild(link);
		}
	</script>
</body>
</html>
