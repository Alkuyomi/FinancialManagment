<?php


require_once __DIR__ . '/db_connect.php';
$response = array();

if(isset($_POST['price'])){

	$price = $_POST['price'] ;
	$type    = $_POST['type'   ] ;
	$stock    = $_POST['stock'   ] ;
	$title  = $_POST['title' ] ;


	$db = new DB_CONNECT();


	mysql_query("SET NAMES utf8");
	$result=mysql_query("INSERT INTO product(`prod_title`,`prod_price`,`prod_stock`,`prod_type`)
																					   VALUES ( '$title','$price','$stock','$type')");
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
