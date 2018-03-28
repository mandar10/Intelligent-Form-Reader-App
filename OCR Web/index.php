<?php
include 'connect.php';
$query="SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='ocrform'";
$query_run=mysqli_query($con,$query);
?>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  	    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,400italic,600,700' rel='stylesheet' type='text/css'>
	    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" type="text/css"/>
	    <link rel="stylesheet" href="fonts/flaticon/flaticon.css" type="text/css"/>
  <style type="text/css">
    /* Remove the navbar's default margin-bottom and rounded borders */ 
	    .navbar {
	      margin-bottom: 0;
	      border-radius: 0;
	    }
	    
	    /* Add a gray background color and some padding to the footer */
	    footer {
	      background-color: #f2f2f2;
	      padding: 25px;
	    }

			.post-module {
			  position: relative;
			  z-index: 1;
			  display: block;
			  background: #FFFFFF;
			  min-width: 270px;
			  height: 350px;
			  -webkit-box-shadow: 0px 1px 2px 0px rgba(0, 0, 0, 0.15);
			  -moz-box-shadow: 0px 1px 2px 0px rgba(0, 0, 0, 0.15);
			  box-shadow: 0px 1px 2px 0px rgba(0, 0, 0, 0.15);
			  -webkit-transition: all 0.3s linear 0s;
			  -moz-transition: all 0.3s linear 0s;
			  -ms-transition: all 0.3s linear 0s;
			  -o-transition: all 0.3s linear 0s;
			  transition: all 0.3s linear 0s;
			}
			.post-module:hover,
			.hover {
			  -webkit-box-shadow: 0px 1px 35px 0px rgba(0, 0, 0, 0.3);
			  -moz-box-shadow: 0px 1px 35px 0px rgba(0, 0, 0, 0.3);
			  box-shadow: 0px 1px 35px 0px rgba(0, 0, 0, 0.3);
			}
			.post-module:hover .thumbnail img,
			.hover .thumbnail img {
			  -webkit-transform: scale(1.1);
			  -moz-transform: scale(1.1);
			  transform: scale(1.1);
			  opacity: .6;
			}
			.post-module .thumbnail {
			  background: #000000;
			  height: 350px;
			  overflow: hidden;padding: 0;
			}
			.post-module .thumbnail .date {
			  position: absolute;
			  top: 20px;
			  right: 20px;
			  z-index: 1;
			  background: #0F5754;
			  width: 55px;
			  height: 55px;
			  padding: 12.5px 0;
			  -webkit-border-radius: 100%;
			  -moz-border-radius: 100%;
			  border-radius: 100%;
			  color: #FFFFFF;
			  font-weight: 700;
			  text-align: center;
			  -webkti-box-sizing: border-box;
			  -moz-box-sizing: border-box;
			  box-sizing: border-box;
			}
			.post-module .thumbnail .date .day {
			 font-size: 18px;
			    line-height: 31px;
			    color: #fff;
			}
			.post-module .thumbnail .date .month {
			  font-size: 12px;
			  text-transform: uppercase;
			}
			.post-module .thumbnail img {
			  display: block;
			  width: 120%;
			  -webkit-transition: all 0.3s linear 0s;
			  -moz-transition: all 0.3s linear 0s;
			  -ms-transition: all 0.3s linear 0s;
			  -o-transition: all 0.3s linear 0s;
			  transition: all 0.3s linear 0s;
			}
			.post-module .post-content {
			  position: absolute;
			  bottom: 0;
			  background: #FFFFFF;
			  width: 100%;
			    padding: 0 30px;
			  -webkti-box-sizing: border-box;
			  -moz-box-sizing: border-box;
			  box-sizing: border-box;
			  -webkit-transition: all 0.3s cubic-bezier(0.37, 0.75, 0.61, 1.05) 0s;
			  -moz-transition: all 0.3s cubic-bezier(0.37, 0.75, 0.61, 1.05) 0s;
			  -ms-transition: all 0.3s cubic-bezier(0.37, 0.75, 0.61, 1.05) 0s;
			  -o-transition: all 0.3s cubic-bezier(0.37, 0.75, 0.61, 1.05) 0s;
			  transition: all 0.3s cubic-bezier(0.37, 0.75, 0.61, 1.05) 0s;
			}
			.post-module .post-content .category {
			  position: absolute;
			  top: -34px;
			  left: 0;
			  background: #0F5754;
			  padding: 10px 15px;
			  color: #FFFFFF;
			  font-size: 14px;
			  font-weight: 600;
			  text-transform: uppercase;
			}
			.post-module .post-content .title {
			  margin: 0;
			  padding: 0 0 10px;
			  color: #222 !important;
			  font-size: 24px !important;
			  font-weight: 700;    margin: 40px 0 0 !important;
			}
			.post-module .post-content .sub_title {
			  margin: 0;
			  padding: 0 0 20px;
			  color: #0F5754;
			  font-size: 20px;
			  font-weight: 400;
			}
			.post-module .post-content .description {
			  display: none;
			  color: #666666;
			  font-size: 14px;
			  line-height: 1.8em;
			}
			.post-module .post-content .post-meta {
			  margin: 0px 0px 10px;
			  color: #999999;
			}
			.post-module .post-content .post-meta .timestamp {
			  margin: 0 16px 0 0;
			}
			.post-module .post-content .post-meta a {
			  color: #999999;
			  text-decoration: none;
			}
			.hover .post-content .description {
			  display: block !important;
			  height: auto !important;
			  opacity: 1 !important;
			}

			.container .column {
			     width: 100%;
			    /* padding: 0 25px; */
			    -webkti-box-sizing: border-box;
			    -moz-box-sizing: border-box;
			    box-sizing: border-box;
			    float: left;
			}
			.container .column .demo-title {
			  margin: 0 0 15px;
			  color: #666666;
			  font-size: 18px;
			  font-weight: bold;
			  text-transform: uppercase;
			}
			.container .info {
			  width: 300px;
			  margin: 50px auto;
			  text-align: center;
			}
			.container .info h1 {
			  margin: 0 0 15px;
			  padding: 0;
			  font-size: 24px;
			  font-weight: bold;
			  color: #333333;
			}
			.container .info span {
			  color: #666666;
			  font-size: 12px;
			}
			.container .info span a {
			  color: #000000;
			  text-decoration: none;
			}
			.container .info span .fa {
			  color: #f2b202;
			}



			.breadcrumb-image {
			    background-position: center center;
			    background-repeat: no-repeat;
			    background-size: cover;
			    padding: 104px 0 110px;
			    position: relative;
			    width:100%;
			}
			.breadcrumb-image::before {
			    background: rgba(0, 0, 0, 0.80) none repeat scroll 0 0;
			    content: "";
			    height: 100%;
			    left: 0;
			    position: absolute;
			    top: 0;
			    width: 100%;
			    z-index: 0;
			}
			.breadcrumb-image h1 {
			    color: #ffffff;
			    font-size: 33px;
			    font-weight: 600;
			    line-height: 40px;
			    position: relative;
			    text-transform: uppercase;
			}
			.breadcrumbs_path {
			    color: #fff;
			    margin-top: 8px;
			    position: relative;
			    z-index: 9;
			}
			.breadcrumbs_path > a {
			    color: #fff;
			    transition: all 0.3s ease 0s;
			}
  </style>
</head>
<body>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="#">Portfolio</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home</a></li>
        <li><a href="#">About</a></li>
        <li><a href="#">Contact</a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="jumbotron">
  <div class="container text-center">
    <h1>OCR 11 Portal</h1>      
    <p>View form data here!</p>
  </div>
</div>
 <?php
while($rows=mysqli_fetch_assoc($query_run))
{
	if($rows['TABLE_NAME']!='formtemp')
	{
		
?>
<div class="container-fluid bg-3 text-center">
	<div class="row">
		<div class="col-md-6 col-md-offset-3" onclick="location.href='viewform.php?name=<?php echo $rows['TABLE_NAME']; ?>'">
					        <div class="column"> 		          
					          <!-- Post-->
					          <div class="post-module"> 
					            <!-- Thumbnail-->
					            <div class="thumbnail">
					              <div class="date"> <a href="#0">
					                <div class="day"><i class="fa fa-bars" aria-hidden="true"></i></div>
					                </a> </div>
					              <img src="forms.jpg" class="img-responsive" alt=""> </div>
					            <!-- Post Content-->
					            <div class="post-content">
					              <div class="category">Form</div>
					              <h1 class="title"><?php echo $rows['TABLE_NAME'].'<br>'; ?></h1>
					              <h2 class="sub_title"></h2>
					              <p class="description">New York, the largest city in the U.S., is an architectural marvel with plenty of historic monuments, magnificent buildings and countless dazzling skyscrapers.</p>
					              <div class="post-meta"><span class="timestamp"> </span><span class="comments"><a href="#"></a></span></div>
					            </div>
					          </div>
					        </div>
					      </div>
	</div>
</div><br>

<?php
	}
}
?>


<footer class="container-fluid text-center">
  <p>@ 2018 OCR 11 All Right Reserved </p>
</footer>

</body>
</html>

