<?php
include 'connect.php';
$name=$_GET['name'];
$query="SELECT `COLUMN_NAME` 
FROM `INFORMATION_SCHEMA`.`COLUMNS` 
WHERE `TABLE_SCHEMA`='ocrform' 
    AND `TABLE_NAME`='".$name."'";
$query_run=mysqli_query($con,$query);
?>

<!DOCTYPE html>
<html lang="en">
<head>
<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
<style type="text/css">
	    	.row{
		    margin-top:40px;
		    padding: 0 10px;
		}
		.clickable{
		    cursor: pointer;   
		}

		.panel-heading div {
			margin-top: -18px;
			font-size: 15px;
		}
		.panel-heading div span{
			margin-left:5px;
		}
		.panel-body{
			display: none;
		}

		.navbar {
      margin-bottom: 0;
      border-radius: 0;
      background-color: black;
    }
    
    /* Add a gray background color and some padding to the footer */
    	footer {
      background-color: #f2f2f2;
      padding: 25px;
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
    	<div class="row">
			<div class="col-md-offset-1 col-md-10">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">Form Data</h3>
						<div class="pull-right">
							<span class="clickable filter" data-toggle="tooltip" title="Toggle table filter" data-container="body">
								<i class="glyphicon glyphicon-filter"></i>
							</span>
						</div>
					</div>
					<div class="panel-body">
						<input type="text" class="form-control" id="dev-table-filter" data-action="filter" data-filters="#dev-table" placeholder="Filter Developers" />
					</div>
					<table class="table table-hover" id="dev-table">
						<thead>
							<tr>
								<?php
									while($rows=mysqli_fetch_assoc($query_run))
									{
										echo '<th>'.$rows['COLUMN_NAME'].'</th>';
									}
									$query_data="SELECT * FROM $name ";
									$query_data_run=mysqli_query($con,$query_data);
								?>
							</tr>
						</thead>
						<tbody>
							<?php

							$query2="SELECT `COLUMN_NAME` 
							FROM `INFORMATION_SCHEMA`.`COLUMNS` 
							WHERE `TABLE_SCHEMA`='ocrform' 
							    AND `TABLE_NAME`='".$name."'";
							while($rows_data=mysqli_fetch_assoc($query_data_run))
							{
								echo '<tr>';
								$query_run2=mysqli_query($con,$query2);
								while($rows2=mysqli_fetch_assoc($query_run2))
								{
									
									echo '<td>'.$rows_data[$rows2['COLUMN_NAME']].'</td>';
								}
								echo '</tr>';
							}
							?>
						</tbody>
					</table>
				</div>
			</div>

		</div>
	<script type="text/javascript">
		/**
*   I don't recommend using this plugin on large tables, I just wrote it to make the demo useable. It will work fine for smaller tables 
*   but will likely encounter performance issues on larger tables.
*
*		<input type="text" class="form-control" id="dev-table-filter" data-action="filter" data-filters="#dev-table" placeholder="Filter Developers" />
*		$(input-element).filterTable()
*		
*	The important attributes are 'data-action="filter"' and 'data-filters="#table-selector"'
*/
(function(){
    'use strict';
	var $ = jQuery;
	$.fn.extend({
		filterTable: function(){
			return this.each(function(){
				$(this).on('keyup', function(e){
					$('.filterTable_no_results').remove();
					var $this = $(this), 
                        search = $this.val().toLowerCase(), 
                        target = $this.attr('data-filters'), 
                        $target = $(target), 
                        $rows = $target.find('tbody tr');
                        
					if(search == '') {
						$rows.show(); 
					} else {
						$rows.each(function(){
							var $this = $(this);
							$this.text().toLowerCase().indexOf(search) === -1 ? $this.hide() : $this.show();
						})
						if($target.find('tbody tr:visible').size() === 0) {
							var col_count = $target.find('tr').first().find('td').size();
							var no_results = $('<tr class="filterTable_no_results"><td colspan="'+col_count+'">No results found</td></tr>')
							$target.find('tbody').append(no_results);
						}
					}
				});
			});
		}
	});
	$('[data-action="filter"]').filterTable();
})(jQuery);

$(function(){
    // attach table filter plugin to inputs
	$('[data-action="filter"]').filterTable();
	
	$('.container').on('click', '.panel-heading span.filter', function(e){
		var $this = $(this), 
			$panel = $this.parents('.panel');
		
		$panel.find('.panel-body').slideToggle();
		if($this.css('display') != 'none') {
			$panel.find('.panel-body input').focus();
		}
	});
	$('[data-toggle="tooltip"]').tooltip();
})
	</script>
	</body>