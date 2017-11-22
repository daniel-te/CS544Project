<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Appointment Management System</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="css/font-awesome.min.css">
	<link href="css/animate.min.css" rel="stylesheet">
    <link href="css/prettyPhoto.css" rel="stylesheet">      
	<link href="css/main.css" rel="stylesheet">
	 <link href="css/responsive.css" rel="stylesheet">
	 <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->       
    
  </head>
<body class="homepage">

	<%@ include file="header.jsp"%>
	
	<section id="contact-page">
        <div class="container">
        <div class="row">
	        <div class="col-md-12">
	        <h2 class="">Login to Your Account</h2>
	       	</div>
	    </div>
        	 <div class="row"> 
        	 <div class="col-sm-6">
				<form:form commandName="user" action="login" method="POST">
				<c:if test="${param.error != null}">
					<p>Invalid username and password.</p>
				</c:if>
		        
		        	<div class="form-group">
			        	<form:input path="username" placeholder="Username" autofocus="true" class="form-control"/>
			        	<form:errors path="username"/>
		        	</div>
		        	<div class="form-group">
			        	<form:password path="password" placeholder="Password" class="form-control"/>
			        	<form:errors path="password"/>
		        	</div>
		        	<form:button  class="btn btn-primary btn-lg" >Log In</form:button>
		        </div>	
			</form:form>
			</div>
		</div>
    </section><!--/#bottom-->
	
	<div id="loginFooter">
		<%@ include file="footer.jsp"%>
	</div>
	
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.prettyPhoto.js"></script>
    <script src="js/jquery.isotope.min.js"></script>   
    <script src="js/wow.min.js"></script>
	<script src="js/main.js"></script>
  </body>
</html>