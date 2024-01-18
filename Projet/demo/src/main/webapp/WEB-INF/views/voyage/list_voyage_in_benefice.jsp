<%@ page import="judi.example.demo.Models.DataObject.ResultatVoyageDurrePrixBenefice" %>

<%
ResultatVoyageDurrePrixBenefice[] resultatVoyageDurrePrixBenefices = (ResultatVoyageDurrePrixBenefice[])request.getAttribute("resultatVoyageDurrePrixBenefices");
%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>Organise Voyage</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- Favicons -->
  <link href="${pageContext.request.contextPath}/assets/img/favicon.png" rel="icon">
  <link href="${pageContext.request.contextPath}/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Google Fonts -->
  <link href="https://fonts.gstatic.com" rel="preconnect">
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="${pageContext.request.contextPath}/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/assets/vendor/quill/quill.snow.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/assets/vendor/quill/quill.bubble.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/assets/vendor/remixicon/remixicon.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/assets/vendor/simple-datatables/style.css" rel="stylesheet">

  <!-- Template Main CSS File -->
  <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">

  <!-- =======================================================
  * Template Name: NiceAdmin - v2.2.2
  * Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
  * Author: BootstrapMade.com
  * License: https://bootstrapmade.com/license/
  ======================================================== -->
</head>

<body>

  <!-- ======= Header ======= -->
  <header id="header" class="header fixed-top d-flex align-items-center">

    <div class="d-flex align-items-center justify-content-between">
      <a href="index.html" class="logo d-flex align-items-center">
        <img src="${pageContext.request.contextPath}/assets/img/logo.png" alt="">
        <span class="d-none d-lg-block">Organise-Voyage</span>
      </a>
      <i class="bi bi-list toggle-sidebar-btn"></i>
    </div><!-- End Logo -->

    <div class="search-bar">
      
    </div><!-- End Search Bar -->

  </header><!-- End Header -->

  <!-- ======= Sidebar ======= -->
  <aside id="sidebar" class="sidebar">

    <ul class="sidebar-nav" id="sidebar-nav">

      <li class="nav-item">
        <a class="nav-link " href="/">
          <i class="bi bi-house"></i>
          <span>Accueil</span>
        </a>
      </li><!-- End Dashboard Nav -->

      <li class="nav-item">
        <a class="nav-link collapsed" data-bs-target="#activite-nav" data-bs-toggle="collapse" href="#">
          <i class="bi bi-menu-button-wide"></i><span>Activite</span><i class="bi bi-chevron-down ms-auto"></i>
        </a>
        <ul id="activite-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
          <li>
            <a href="/createActivite">
              <i class="bi bi-circle"></i><span>Creer</span>
            </a>
          </li>
          <li>
            <a href="/addActivite">
              <i class="bi bi-circle"></i><span>Ajouter</span>
            </a>
          </li>
          <li>
            <a href="listActivite">
              <i class="bi bi-circle"></i><span>Liste</span>
            </a>
          </li>
          <li>
            <a href="searchActiviteToVoyage">
              <i class="bi bi-circle"></i><span>Chercher dans un voyage</span>
            </a>
          </li>
          <li>
            <a href="updateActivitePrix">
              <i class="bi bi-circle"></i><span>Modifier Prix</span>
            </a>
          </li>
        </ul>
      </li><!-- End Components Nav -->
      <li class="nav-item">
        <a class="nav-link collapsed" data-bs-target="#bouquet-nav" data-bs-toggle="collapse" href="#">
          <i class="bi bi-menu-button-wide"></i><span>Bouquet</span><i class="bi bi-chevron-down ms-auto"></i>
        </a>
        <ul id="bouquet-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
          <li>
            <a href="/createBouquet">
              <i class="bi bi-circle"></i><span>Creer</span>
            </a>
          </li>
          <li>
            <a href="listBouquet">
              <i class="bi bi-circle"></i><span>Liste</span>
            </a>
          </li>
        </ul>
      </li><!-- End Components Nav -->
      <li class="nav-item">
        <a class="nav-link collapsed" data-bs-target="#voyage-nav" data-bs-toggle="collapse" href="#">
          <i class="bi bi-menu-button-wide"></i><span>Voyage</span><i class="bi bi-chevron-down ms-auto"></i>
        </a>
        <ul id="voyage-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
          <li>
            <a href="/createVoyage">
              <i class="bi bi-circle"></i><span>Creer</span>
            </a>
          </li>
          <li>
            <a href="/list_voyage">
              <i class="bi bi-circle"></i><span>Liste</span>
            </a>
          </li>
          <li>
            <a href="/form_search_activite_prix">
              <i class="bi bi-circle"></i><span>Chercher par intervalle de prix activite</span>
            </a>
          </li>
          <li>
            <a href="/vendre_voyage">
              <i class="bi bi-circle"></i><span>Vendre billet</span>
            </a>
          </li>
          <li>
            <a href="/ajouter_prix_voyage">
              <i class="bi bi-circle"></i><span>Ajouter prix</span>
            </a>
          </li>
          <li>
            <a href="/list_voyage_in_benefice">
              <i class="bi bi-circle"></i><span>Rechercher un voyage par son benefice</span>
            </a>
          </li>
        </ul>
      </li>
      <li class="nav-item">
        <a class="nav-link collapsed" data-bs-target="#employer-nav" data-bs-toggle="collapse" href="#">
          <i class="bi bi-menu-button-wide"></i><span>Employer</span><i class="bi bi-chevron-down ms-auto"></i>
        </a>
        <ul id="employer-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
          <li>
            <a href="/creerEmployer">
              <i class="bi bi-circle"></i><span>Creer</span>
            </a>
          </li>
          <li>
            <a href="/addEmployerToVoyage">
              <i class="bi bi-circle"></i><span>Ajouter a un voyage</span>
            </a>
          </li>
        </ul>
      </li>
    </ul>

  </aside><!-- End Sidebar-->

  <main id="main" class="main">

    <div class="pagetitle">
      <h1>Accueil</h1>
    </div><!-- End Page Title -->
    <section class="section dashboard">
        <div class="row">
          <div class="row">
          <div class="col-lg-3"></div>
          <div class="pagetitle col-lg-6" style="margin-top: 5%; margin-left: 2%;">
            <h1>Liste par benefice</h1>
          </div><!-- End Page Title -->
          <div class="col-lg-3"></div>
          </div>
              <!-- <h1 style="margin-top: 25%">Creer</h1> -->
              <section class="section">
                  <div class="card">
              <div class="card-body">
                  <div class="col-sm-10"  style="margin-top: 5%;" >
                      <form style="margin-top: 5%;" >
                          <label for="inputText" class="col-sm-2 col-form-label">minimum</label>
                              <div class="col-sm-10">
                                <input type="number" class="form-control" style="width: 20%;" name="min">
                              </div>
                          <label for="inputEmail" class="col-sm-2 col-form-label">maximum</label>
                              <div class="col-sm-10">
                                <input type="number" class="form-control" style="width: 20%;" name="max">
                              </div>
                            <button type="submit" class="btn btn-primary"   style="width: 20%;margin-top: 2%;">ok</button>
                      </form>
                    </div>  
                <!-- Table with stripped rows -->
                <table class="table table-striped" style="margin-top: 5%;">
                  <thead>
                    <tr>
                      <th scope="col">Nom voyage</th>
                      <th scope="col">Duree</th>
                      <th scope="col">Prix total des activitees</th>
                      <th scope="col">Total des salaires</th>
                      <th scope="col">Benefice</th>
  
  
                      
                    </tr>
                  </thead>
                  <tbody>    
                    <% for (ResultatVoyageDurrePrixBenefice resultatVoyageDurrePrixBenefice : resultatVoyageDurrePrixBenefices) { %>
                      <tr>
                        <td><%= resultatVoyageDurrePrixBenefice.getVoyageDurre().getVoyage().getNom_voyage()  %>
                        <td><%= resultatVoyageDurrePrixBenefice.getVoyageDurre().getDurre().getNom()  %>
                        <td><%= resultatVoyageDurrePrixBenefice.getPrix_voyageDurre() %>
                        <td><%= resultatVoyageDurrePrixBenefice.getPrix_depense() %>
                        <td><%= resultatVoyageDurrePrixBenefice.getBenefice() %>
                        </tr>
                      <% } %>
                  </tbody>
                </table>
                <!-- End Table with stripped rows -->
  
              </div>
            </div>
            </section>
        </div>
      </section>
  

  </main><!-- End #main -->

  <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

  <!-- Vendor JS Files -->
  <script src="${pageContext.request.contextPath}/assets/vendor/apexcharts/apexcharts.min.js"></script>
  <script src="${pageContext.request.contextPath}/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="${pageContext.request.contextPath}/assets/vendor/chart.js/chart.min.js"></script>
  <script src="${pageContext.request.contextPath}/assets/vendor/echarts/echarts.min.js"></script>
  <script src="${pageContext.request.contextPath}/assets/vendor/quill/quill.min.js"></script>
  <script src="${pageContext.request.contextPath}/assets/vendor/simple-datatables/simple-datatables.js"></script>
  <script src="${pageContext.request.contextPath}/assets/vendor/tinymce/tinymce.min.js"></script>
  <script src="${pageContext.request.contextPath}/assets/vendor/php-email-form/validate.js"></script>

  <!-- Template Main JS File -->
  <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>

</body>

</html>