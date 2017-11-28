<?php

require_once __DIR__ . '/db_connect.php';
$response = array();
$db = new DB_CONNECT();

	mysql_query("SET NAMES utf8");


	$result=mysql_query("SELECT invoice_id , invoice_name FROM `invoice`");
	if(mysql_num_rows($result) > 0 ){

		$response["invoices"]=array();

		while($row = mysql_fetch_array($result)){
			$invoice = array();

			$invoice["name"] = $row["invoice_name"];
      $invoice["id"  ] = $row["invoice_id"  ];


			array_push($response["invoices"], $invoice);
		}

		$response['value']=1;

	}else{
		$response['value']=0;
	}

echo json_encode($response);

?>
