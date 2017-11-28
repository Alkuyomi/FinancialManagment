<?php

require_once __DIR__ . '/db_connect.php';
$response = array();
$db = new DB_CONNECT();

	mysql_query("SET NAMES utf8");


	$result=mysql_query("SELECT prod_title FROM `product`");
	if(mysql_num_rows($result) > 0 ){

		$response["products"]=array();

		while($row = mysql_fetch_array($result)){
			$invoice = array();
			$invoice["title"]=$row["prod_title"];


			array_push($response["products"], $invoice);
		}

		$response['value']=1;

	}else{
		$response['value']=0;
	}

echo json_encode($response);

?>
