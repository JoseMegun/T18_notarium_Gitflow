<style>
.sidebar {
	position: fixed;
	top: 0;
	bottom: 0;
	left: 0;
	z-index: 100;
	padding: 48px 10px; /* Ajusta el padding según tus preferencias */
	background-color: #5A57FF;
	width: 250px; /* Ajusta el ancho del sidebar según tus preferencias */
	text-align: center;
	font-family: 'Poppins', sans-serif;
	/* Cambia la fuente de letra a Poppins */
}

.nav-link:hover {
	background-color: #fff; /* Cambia el fondo a blanco en hover */
	color: #5A57FF; /* Cambia el color del texto en hover */
	border-radius: 20px;
}

.sidebar .title {
	font-size: 1.5rem;
	font-weight: bold;
	margin-bottom: 20px;
	color: #fff;
}

.nav-link {
	font-size: 1.2rem;
	color: #fff;
}

.content {
	margin-left: 200px;
	padding: 20px;
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
</style>


<div class="container-fluid">
	<div class="row">
		<div class="col-md-3 sidebar">
			<div class="title">NOTARIUM</div>
			<ul class="nav flex-column">
				<li class="nav-item"><a class="nav-link" href="Student.jsp">Estudiantes</a></li>
				<li class="nav-item"><a class="nav-link" href="Teacher.jsp">Profesores</a></li>
				<li class="nav-item"><a class="nav-link" href="#">Cursos</a></li>
				<li class="nav-item"><a class="nav-link" href="#">Notas</a></li>
			</ul>
		</div>
	</div>
</div>
