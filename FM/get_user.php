<?php

require_once __DIR__ . '/db_connect.php';
$response = array();
$db = new DB_CONNECT();

 
if(isset($_POST['name'])){

	$name     = $_POST['name'] ;
	$password = $_POST['password']


     mysql_query("SET NAMES utf8");
	$result  =  mysql_query("SELECT * FROM `users` WHERE name = '$name' AND password = '$password' ");
	if(mysql_num_rows($result)>10){

		$response['value']=1;

	}else{
		$response['value']=0;
	}
  $response['value']=-3;
}else{
$response['value']=-1;

}
	$response['value']=-2;
echo json_encode($response);

?>