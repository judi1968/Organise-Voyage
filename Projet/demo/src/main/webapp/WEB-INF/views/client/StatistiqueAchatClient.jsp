<%@ page import="judi.example.demo.Models.DataObject.ClientAchatVoyageDurre" %>
<%
ClientAchatVoyageDurre[] clientAchatVoyageDurre= (ClientAchatVoyageDurre[])request.getAttribute("clientachat");
int nombreHomme =  (int)request.getAttribute("nombreHomme");
int nombreFemme =  (int)request.getAttribute("nombreFemme");
int nombreTotale =  (int)request.getAttribute("nombreTotale");
double pourcentageHomme = (double)request.getAttribute("pourcentageHomme");
double pourcentageFemme = (double)request.getAttribute("pourcentageFemme");
%>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>Statistique achat client</title>
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
        <i class="ri-basketball-line"></i><span>Activite</span><i class="bi bi-chevron-down ms-auto"></i>
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
        <i class="ri-checkbox-multiple-blank-line"></i><span>Bouquet</span><i class="bi bi-chevron-down ms-auto"></i>
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
        <i class="ri-send-plane-fill"></i><span>Voyage</span><i class="bi bi-chevron-down ms-auto"></i>
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
      <a class="nav-link collapsed" data-bs-target="#fonction-nav" data-bs-toggle="collapse" href="#">
        <i class="ri-settings-line"></i><span>Fonction</span><i class="bi bi-chevron-down ms-auto"></i>
      </a>
      <ul id="fonction-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
        <li>
          <a href="/listFonctionEmploye">
            <i class="bi bi-circle"></i><span>Liste</span>
          </a>
        </li>
      </ul>
    </li>
    <li class="nav-item">
      <a class="nav-link collapsed" data-bs-target="#employer-nav" data-bs-toggle="collapse" href="#">
        <i class="ri-user-2-line"></i><span>Employer</span><i class="bi bi-chevron-down ms-auto"></i>
      </a>
      <ul id="employer-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
        <li>
          <a href="/creerEmployer">
            <i class="bi bi-circle"></i><span>Creer</span>
          </a>
        </li>
        <li>
          <a href="/listAllEmployer">
            <i class="bi bi-circle"></i><span>Liste</span>
          </a>
        </li>
        <li>
          <a href="/addEmployerToVoyage">
            <i class="bi bi-circle"></i><span>Ajouter a un voyage</span>
          </a>
        </li>
        <!-- <li>
          <a href="/listAllTauxEmployerByNiveau">
            <i class="bi bi-circle"></i><span>Gerer les salaires</span>
          </a>
        </li> -->
      </ul>
    </li>
    <li class="nav-item">
      <a class="nav-link collapsed" data-bs-target="#client-nav" data-bs-toggle="collapse" href="#">
        <i class="ri-user-line"></i><span>Client</span><i class="bi bi-chevron-down ms-auto"></i>
      </a>
      <ul id="client-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
        <li>
          <a href="/createClient">
            <i class="bi bi-circle"></i><span>Creer</span>
          </a>
        </li>
        <li>
          <a href="/acheterVoyage">
            <i class="bi bi-circle"></i><span>Ajouter voyage dans le panier</span>
          </a>
        </li>
        <li>
          <a href="/validerPanier">
            <i class="bi bi-circle"></i><span>Valider panier d'un client</span>
          </a>
        </li>
        <li>
          <a href="/statistiqueAchatClient">
            <i class="bi bi-circle"></i><span>Statistique d'achat</span>
          </a>
        </li>
      </ul>
    </li>
  </ul>

</aside><!-- End Sidebar-->

  <main id="main" class="main">

    <div class="pagetitle">
      <h1>Statistique</h1>
    </div><!-- End Page Title -->

        <!-- <h1 style="margin-top: 25%">Creer</h1> -->

        <section class="section">
            <div class="row">
                <div class="col-md-12">
                    <div class="card" style="margin-top: 0%;">
                        <div class="card-body">
                            <h5 class="card-title"></h5>
                            <div class="row">
                            <!-- TOTAL  -->
                            <div class="col-md-4">
                                <div class="card info-card sales-card">
                                <div class="card-body">
                                    <h5 class="card-title">Nombre Total</h5>
                
                                    <div class="d-flex align-items-center">
                                    <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                        <i class="bi bi-cart"></i>
                                    </div>
                                    <div class="ps-3">
                                        <h6><%= nombreTotale %> personne</h6> 
                                        <span class="text-success small pt-1 fw-bold">. </span> 
         
                                    </div>
                                    </div>
                                </div>
                
                                </div>
                            </div><!-- End Total Card -->

                            <!-- HOMME  -->
                            <div class="col-md-4">
                                <div class="card info-card sales-card">
                                <div class="card-body">
                                    <h5 class="card-title">Nombre d'homme</h5>
                
                                    <div class="d-flex align-items-center">
                                    <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                        <i class="bi bi-cart"></i>
                                    </div>
                                    <div class="ps-3">
                                        <h6><%= nombreHomme %> personne</h6>
                                        <span class="text-success small pt-1 fw-bold"><%= pourcentageHomme %> %</span> 
                
                                    </div>
                                    </div>
                                </div>
                
                                </div>
                            </div><!-- End Homme Card -->

                            <!-- FEMME  -->
                            <div class="col-md-4">
                                <div class="card info-card sales-card">
                                <div class="card-body">
                                    <h5 class="card-title">Nombre de femme</h5>
                
                                    <div class="d-flex align-items-center">
                                    <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                        <i class="bi bi-cart"></i>
                                    </div>
                                    <div class="ps-3">
                                        <h6><%= nombreFemme %> personnes</h6>
                                        <span class="text-success small pt-1 fw-bold"><%= pourcentageFemme %> %</span> 
                
                                    </div>
                                    </div>
                                </div>
                
                                </div>
                            </div><!-- End Femme Card -->
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <input type="hidden" value="<%= nombreHomme %>" id="nombreHomme">
                    <input type="hidden" value="<%= nombreFemme %>" id="nombreFemme">
                    <div class="card">
                      <div class="card-body">
                        <h5 class="card-title"></h5>
          
                        <!-- Pie Chart -->
                        <canvas id="pieChart" style="max-height: 400px;"></canvas>
                        <script>
                          document.addEventListener("DOMContentLoaded", () => {
                            const nombreHomme = document.getElementById("nombreHomme").value;
                            const nombreFemme = document.getElementById("nombreFemme").value;
        
                            new Chart(document.querySelector('#pieChart'), {
                              type: 'pie',
                              data: {
                                labels: [
                                  'Homme',
                                  'Femme'
                                ],
                                datasets: [{
                                  label: 'My First Dataset',
                                  data: [nombreHomme,nombreFemme],
                                  backgroundColor: [
                                      'rgb(54, 162, 235)',
                                    'rgb(255, 99, 132)'
                                  ],
                                  hoverOffset: 4
                                }]
                              }
                            });
                          });
                        </script>
                        <!-- End Pie CHart -->
          
                      </div>
                    </div>
                </div>
                <div class="col-12">
                    <div class="card top-selling overflow-auto">
        
                    <div class="card-body pb-0">
                        <h5 class="card-title">Liste client</h5>
        
                        <table class="table table-borderless">
                        <thead>
                            <tr>
                            <th scope="col">Nom</th>
                            <th scope="col">Prenom</th>
                            <th scope="col">Date d'achat</th>
                            <th scope="col">Nom voyage</th>
                            <th scope="col">Type voyage</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (ClientAchatVoyageDurre clientAchatVoyageDurre2 : clientAchatVoyageDurre) { %>
                                <tr>
                                    <td><%= clientAchatVoyageDurre2.getNom() %> </td>
                                    <td><%= clientAchatVoyageDurre2.getPrenom() %> </td>
                                    <td><%= clientAchatVoyageDurre2.getDateAchat().getDateString() %> </td>
                                    <td><%= clientAchatVoyageDurre2.getVoyageDurre().getVoyage().getNom_voyage() %> </td>
                                    <td><%= clientAchatVoyageDurre2.getVoyageDurre().getDurre().getNom() %> </td>
                                </tr>
                            <% } %>
                        </tbody>
                        </table>
        
                    </div>
        
                    </div>
                </div><!-- End Top Selling -->        
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