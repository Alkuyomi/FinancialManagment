<?php

require_once __DIR__ . '/db_connect.php';
$response = array();

if(isset($_POST['company'])){

	$company = $_POST['company'] ;
	$data    = $_POST['data'   ] ;
	$time    = $_POST['time'   ] ;
	$number  = $_POST['number' ] ;
	$type    = $_POST['type'   ] ;
	$amount  = $_POST['amount' ] ;
	$name    = $_POST['name'   ] ;
	$total   = $_POST['total'  ] ;

	$db = new DB_CONNECT();

	mysql_query("SET NAMES utf8");
	$result=mysql_query("INSERT INTO invoice(`company_name`,`invoice_data`,`invoice_time`,
		                                      `invoice_number`,`invoice_type`,`invoice_amount`,
																					`invoice_name`,`invoice_total`)
																					VALUES ( '$company','$data','$time','$number',
																						       '$type','$amount','$name','$total')");
	if($result){
		$response['value']=1;

	}else{
		$response['value']=0;

		echo mysql_error();
	}
}else{
	$response['value']=-1;

}
echo json_encode($response);

?>
